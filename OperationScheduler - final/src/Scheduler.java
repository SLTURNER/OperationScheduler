import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Scheduler
{
	StaffTree staff;
	Undo undo;
	Undo redo;

	Scanner s = new Scanner(System.in);


	public static void main(String[] args)
	{
		
		System.out.println("Test");
		Scheduler s = new Scheduler();
		s.processSelection();

	}

	/**
	 * Constructor. Will initialise all fields to default setting
	 */
	public Scheduler()
	{
		staff = new StaffTree();
		undo = new Undo();
		redo = new Undo();
	}


	/**
	 * Accessor, returns Staff as StaffTree
	 * @return    staff
	 */
	public StaffTree getStaff()
	{
		return staff;
	}

	/**
	 * Will override current staffTree, with stored version.
	 * @param n    New StaffTree
	 * @return     Now overridden staffTree
	 */
	public StaffTree setStaff(Object n)
	{
		StaffTree toReturn = getStaff();
		staff = (StaffTree) n;

		return toReturn;
	}

	/**
	 * Accessor, return Undo object
	 * @return    undo Stack
	 */
	public Undo getUndo()
	{
		return undo;
	}

	/**
	 * Accessor, returns Redo as Undo Object
	 * @return    redo Stack
	 */
	public Undo getRedo()
	{
		return redo;
	}

	/**
	 * Wrapper method for printing new line of text
	 * @param toPrint    String to print
	 */
	public void print(String toPrint)
	{
		System.out.println(toPrint);
	}

	/**
	 * Will display all menu options
	 */
	public void displayMenu()
	{
		print("A.    Display Appointments");
		print("B.    Display Tasklist");
		print("C.    New Appointment");
		print("D.    Delete Appointment");
		print("E.    Edit Appointment");
		print("F.    Display Staff");
		print("G.    New Staff");
		print("H.    Edit Staff");
		print("I.    Delete Staff");
		print("J.    Save State");
		print("K.    Load from file");
		print("L.    Undo previous action");
		print("M.    Redo last undone action");
		print("T.    Run testplan");
		print("Z.    Exit");
	}

	/**
	 * Will take user input, and call appropriate methods
	 */
	public void processSelection()
	{
		boolean exit = false;
		while(exit == false)
		{
			try
			{	
				displayMenu();
				String selection = s.nextLine();
				switch(selection.toLowerCase())
				{
				case "a":
					displayAppointments();
					break;

				case "b":
					displayStaff();
					print("Please type the ID of the staff you want to see tasks for");
					displayTasklist(Integer.parseInt(s.nextLine()));
					
					break;

				case "c":

					displayStaff();
					LinkedList<Integer> staffIDs = new LinkedList<>();
					
					print("What is the ID of the Staff you would like to book? (Listed and seperated by comma)");
					String input = s.nextLine();
					String[] splitInput = input.split(",");
					for(String s: splitInput) 
					{
						staffIDs.add(Integer.parseInt(s));
					}
					

					print("When would you like to book an appointment? (Format should be: dd/MM/yy HH:mm)");
					String start = s.nextLine();

					print("How long will the appointment last?");
					int duration = Integer.parseInt(s.nextLine());;
					
					print("What will be happening at the appointment?");
					String description = s.nextLine();
					
					print("Where will the appointment take place?");
					String location = s.nextLine();
					
					print("Is this a personal task? (true or false)");
					boolean hidden = Boolean.parseBoolean(s.nextLine());

					Appointment toUndo = newAppointment(staffIDs, start, duration, description, location, hidden);
					if(toUndo != null) 
					{
						getUndo().push(new UndoNode(toUndo.getID(), staffIDs, toUndo, "Added"));
					}
					else 
					{
						print("Adding failed");
					}
					break;
				case "d":
					displayStaff();
					print("What is the ID of the staff, whose appointment you would like to delete?");
					int staff1ID = Integer.parseInt(s.nextLine());;
					
					Staff toEdit = getStaff().findInTree(staff1ID);
					toEdit.printTaskList();
					
					print("What is the ID of the appointment you would like to delete?");
					int appointID = Integer.parseInt(s.nextLine());
					
					UndoNode deleted = deleteAppointment(staff1ID, appointID);
					
					if(deleted != null) 
					{
						
						print("Deletion succesful");
					}
					else 
					{
						print("Deletion failed");
					}
					break;
				case "e":
					displayStaff();
					print("What is the ID of the staff, whose appointment you would like to edit?");
					int staff2ID = Integer.parseInt(s.nextLine());;
					
					Staff staffA = getStaff().findInTree(staff2ID);
					staffA.printTaskList();
					
					print("What is the ID of the appointment you would like to edit?");
					int appoint1ID = Integer.parseInt(s.nextLine());;
					
					print("What would you like to edit?");
					print("    A.    Start time");
					print("    B.    Duration");
					print("    C.    description");
					print("    D.    Location");
					print("    E.    Hidden");
					
					String sub = s.nextLine();
					String edit = null;
					
					switch(sub.toLowerCase()) 
					{
					case "a":
						edit = "start";
						break;
					case "b":
						edit = "duration";
						break;
					case "c":
						edit = "description";
						break;
					case "d":
						edit = "location";
						break;
					case "e":
						edit = "hidden";
						break;
					default:
						print("Invalid selection");
						break;
					}
					
					if(!edit.isEmpty()) 
					{
						print("What should the new value be? If start time, format should be dd/MM/yy HH:mm");
						String newValue = s.nextLine();
						Appointment old = editAppointment(staff2ID, appoint1ID, edit, newValue);
						
						LinkedList<Integer> staffIDList2 = new LinkedList<Integer>();
						staffIDList2.add(staff2ID);
						
						getUndo().push(new UndoNode(appoint1ID, staffIDList2, old, "Edited"));
					}
					
					break;
				case "f":
					displayStaff();
					break;
				case "g":
					print("What is the name of the new Employee?");
					String name = s.nextLine();
					
					print("Where is their office located?");
					String office = s.nextLine();
							
					print("What is their staff ID?");
					int id = Integer.parseInt(s.nextLine());;
							
					Staff newStaff = newStaff(name, office, id);
					
					LinkedList<Integer> staffIDList3 = new LinkedList<Integer>();
					staffIDList3.add(id);
					
					if(newStaff != null) 
					{
						
					}
					
					break;
				case "h":
					displayStaff();
					
					print("What is the ID of the member of staff you would like to update?");
					int sID = Integer.parseInt(s.nextLine());;
					
					print("What would you like to update?");
					print("    A.    Name");
					print("    B.    Office");
					String selection2 = s.nextLine();
					String edit2 = null;
					
					switch(selection2.toLowerCase()) 
					{
					case "a":
						edit2 = "name";
						break;
					case "b":
						edit2 = "office";
						break;
					default:
						break;
					}
					
					if(!edit2.isEmpty()) 
					{
						print("What is the new value?");
						String newValue2 = s.nextLine();
						
						Staff old = editStaff(sID, edit2, newValue2);
						
						LinkedList<Integer> staffIDList4 = new LinkedList<Integer>();
						staffIDList4.add(sID);
						
						if(old != null) 
						{
							
						}
						
					}
					break;
				case "i":
					displayStaff();
					print("What is the ID of the member of Staff you would like to remove?");
					int idToDel = Integer.parseInt(s.nextLine());;
					
					Staff removed = deleteStaff(idToDel);
					
					if(removed != null) 
					{
						print("Deletion succesful");
					}
					
					break;
				
				case "j":
					String path = "staffTree.txt";
				    FileOutputStream stream = null;
				    PrintWriter writer = null;
				    
				    try 
		    		{
						stream = new FileOutputStream(path);
					} 
		    		catch (FileNotFoundException e) 
		    		{
						System.out.println(e.getMessage());
					}
		    		writer = new PrintWriter(stream);
				    
					save(getStaff().getRoot(), writer);
					writer.close();
					
					undo = new Undo();
					redo = new Undo();
					
					break;
				case "k":
					load();
					undo = new Undo();
					redo = new Undo();
					
					break;
					
				case "l":
					undo();
					break;
					
				case "m":
					redo();
					break;
				
				case "t":
					testPlan();
					break;
					
				case "z":
					exit = true;
					break;
					
				default:
					print("Invalid selection");
					break;
				
					
				}



			}
			catch(Exception e)
			{
				print("Something went wrong. Please be careful when typing");
			}
		}
	}

	/**
	 * Will read last changed Object, and put it back from whence it came
	 * @return    Overriden Object
	 */
	public Object undo()
	{
		UndoNode n = getUndo().pop();
		Staff s = getStaff().findInTree(n.getSID().get(0));

		// If Appointment ID is negative, it doesnt exist, therefore the object is Staff
		if(n.getAID() < 0)
		{
			switch(n.getAction())
			{
				case "Added":
					getStaff().delete(n.getSID().get(0));

					getRedo().push(new UndoNode(-1, n.getSID(), s, "Deleted"));
					return s;

				case "Deleted":
					getStaff().addTree((Staff) n.getObject());

					getRedo().push(new UndoNode(-1, n.getSID(), n.getObject(), "Added"));
					return n;

				case "Edited":
					Staff toReturn = new Staff(s);

					s.setName(((Staff) n.getObject()).getName());
					s.setOffice(((Staff) n.getObject()).getOffice());

					getRedo().push(new UndoNode(-1, n.getSID(), toReturn, "Edited"));
					return toReturn;

				default:
					break;
			}
		}
		else
		{
			Appointment a = s.searchAppointment(n.getAID());

			switch(n.getAction())
			{
				case "Added":
					deleteAppointment(s.getId(), a.getID());
					
					LinkedList<Integer> staffIDList = new LinkedList<Integer>();
					staffIDList.add(s.getId());

					getRedo().push( new UndoNode(a.getID(), staffIDList, a, "Deleted"));

					return a;

				case "Deleted":
					LinkedList<Integer> staffIDList2 = n.getSID();
					// Add it back
					
					for(int i = 0; i < staffIDList2.size(); i++) 
					{
						getStaff().findInTree(staffIDList2.get(i)).addAppointment((Appointment) n.getObject());
					}

					// Push newly added node to redo stack
					getRedo().push(new UndoNode(n.getAID(), n.getSID(), n.getObject(), "Added"));
					return n;

				case "Edited":
					// Make copy of Appointment stored, to add to redo stack

					Appointment toReturn2 = new Appointment(a);
					
					getStaff().findAll(getStaff().getRoot(), a);
					
					LinkedList<Appointment> allApp = getStaff().sendList();
					LinkedList<Integer> allID = getStaff().sendListID();

					// Copy values from undo stack to appointment set
					for(int i = 0; i < allApp.size(); i++) 
					{
						allApp.get(i).setStart(((Appointment) n.getObject()).getStart());
						allApp.get(i).setDuration(((Appointment) n.getObject()).getDuration());
						allApp.get(i).setDescription(((Appointment) n.getObject()).getDescription());
						allApp.get(i).setLocation(((Appointment) n.getObject()).getLocation());
						allApp.get(i).setHidden(((Appointment) n.getObject()).getHidden());
					}


					getRedo().push(new UndoNode(toReturn2.getID(), allID, toReturn2, "Edited"));
					return toReturn2;

				default:
					break;
			}
		}

		return null;

	}

	/**
	 * Will read last undo action and undo the undoneness. Returns returned object from Undo method to appropriate place
	 * @return    ReOverridden Object
	 */
	public Object redo()
	{
		UndoNode n = getRedo().pop();
		Staff s = getStaff().findInTree(n.getSID().get(0));

		// If Appointment ID is negative, it doesnt exist, therefore the object is Staff
		if(n.getAID() < 0)
		{
			switch(n.getAction())
			{
				case "Added":
					getStaff().delete(n.getSID().get(0));

					getUndo().push(new UndoNode(-1, n.getSID(), s, "Deleted"));
					return s;

				case "Deleted":
					getStaff().addTree((Staff) n.getObject());

					getUndo().push(new UndoNode(-1, n.getSID(), n.getObject(), "Added"));
					return n;

				case "Edited":
					Staff toReturn = new Staff(s);

					s.setName(((Staff) n.getObject()).getName());
					s.setOffice(((Staff) n.getObject()).getOffice());

					getUndo().push(new UndoNode(-1, n.getSID(), toReturn, "Edited"));
					return toReturn;

				default:
					break;
			}
		}
		else
		{
			Appointment a = s.searchAppointment(n.getAID());

			switch(n.getAction())
			{
				case "Added":
					deleteAppointment(s.getId(), a.getID());
					
					LinkedList<Integer> staffIDList = new LinkedList<Integer>();
					staffIDList.add(s.getId());

					getUndo().push( new UndoNode(a.getID(), staffIDList, a, "Deleted"));

					return a;

				case "Deleted":
					LinkedList<Integer> staffIDList2 = n.getSID();
					// Add it back
					
					for(int i = 0; i < staffIDList2.size(); i++) 
					{
						getStaff().findInTree(staffIDList2.get(i)).addAppointment((Appointment) n.getObject());
					}

					// Push newly added node to redo stack
					getUndo().push(new UndoNode(n.getAID(), n.getSID(), n.getObject(), "Added"));
					return n;

				case "Edited":
					// Make copy of Appointment stored, to add to redo stack

					Appointment toReturn2 = new Appointment(a);
					
					getStaff().findAll(getStaff().getRoot(), a);
					
					LinkedList<Appointment> allApp = getStaff().sendList();
					LinkedList<Integer> allID = getStaff().sendListID();

					// Copy values from undo stack to appointment set
					for(int i = 0; i < allApp.size(); i++) 
					{
						allApp.get(i).setStart(((Appointment) n.getObject()).getStart());
						allApp.get(i).setDuration(((Appointment) n.getObject()).getDuration());
						allApp.get(i).setDescription(((Appointment) n.getObject()).getDescription());
						allApp.get(i).setLocation(((Appointment) n.getObject()).getLocation());
						allApp.get(i).setHidden(((Appointment) n.getObject()).getHidden());
					}


					getUndo().push(new UndoNode(toReturn2.getID(), allID, toReturn2, "Edited"));
					return toReturn2;

				default:
					break;
			}
		}

		return null;

	}

	/**
	 * Will store overridden staff tree in undo stack, and load new data from file
	 * @return    Old StaffTree
	 */
	public StaffTree load()
	{
	    StaffTree newTree = new StaffTree();
	
	    FileReader fReader = null;
	    BufferedReader bReader = null;
	     String path = "staffTree.txt";
	     
	     try
	     {
	    	 fReader = new FileReader(path);
	    	 bReader = new BufferedReader(fReader);
	    	 
	    	 String currentLine = "";
	
	         //While the next line in the file has data
	         while ((currentLine = bReader.readLine()) != null)
	         {
	           //Split the line into staff fields
	           String[] staffFields = currentLine.split(",");
	           int id = Integer.parseInt(staffFields[1]);
	           String name = staffFields[2];
	           String office = staffFields[3];
	
	           //Create a staff with the fields
	           Staff newStaff = new Staff(name, office, id);
	
	           
	           if (!staffFields[0].isEmpty())
	           {
	        	   //For each appointment in the line
		           String[] appointments = staffFields[0].split("&");
		           for (int i = 0; i <appointments.length; i++)
		           {
		             //Split into appointment fields
		             String[] appointmentFields = appointments[i].split(";");
		            
		             String date = appointmentFields[0];
		             int duration = Integer.parseInt(appointmentFields[1]);
		             String description = appointmentFields[2];
		             String location = appointmentFields[3];
		             boolean hidden = Boolean.parseBoolean(appointmentFields[4]);
		
		             //Add the appointment to the staff member's diary
		             newStaff.getDiary().addAppointment(date, duration, description, location, hidden);
		           }
	           }
	
	           //Add the new saff to the new tree
	           newTree.addTree(newStaff);
	         }
	
	         bReader.close();
	         
	         //Set the new tree to the current tree
	         StaffTree oldTree = staff;
	         staff = newTree;
	
	    		undo = new Undo();
	    		redo = new Undo();
	
	    		System.out.println("Loaded succesfully");
	
	        //Return the old tree
	        return oldTree;
	     }
	     catch (IOException e)
	     {
	    	 System.out.println(e.getMessage());
	    	 return null;
	     }
      
     
	}

	/**
	 * Will save current state of Scheduler to file
	 *     Note: Undo and Redo stacks are NOT saved
	 * @return    true once saved succesfully
	 */
	public boolean save(Staff currentStaff, PrintWriter writer)
	{
      

	    String staffFieldDelimiter = ",";
	    String appointmentDelimiter = "&";
	    String appointmentFieldDelimiter = ";";

	  //If the node has a member of staff
	    if (currentStaff != null)
	    {
	    	//Save the tree node to the left
	    	save(currentStaff.getLeft(), writer);

    		//Print the id, name and office of the staff to a new line on the file
    		int id = currentStaff.getId();
    		String name = currentStaff.getName();
    		String office = currentStaff.getOffice();
    		writer.println(staffFieldDelimiter + id + staffFieldDelimiter + name + staffFieldDelimiter + office + staffFieldDelimiter);

    		//For each appointment in the staff member's diary
    		ArrayList<Appointment> appointments = currentStaff.getDiary().getAppointment();
    		if (appointments != null)
    		{
	    		for (int i = 0; i < appointments.size(); i++)
	    		{
	    			//Print out the appointment data to the file
	    			DateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm");
	    			String start = form.format(appointments.get(i).getStart());
	    			writer.print(start + appointmentFieldDelimiter);
	    			writer.print(appointments.get(i).getDuration() + appointmentFieldDelimiter);
	    			writer.print(appointments.get(i).getDescription() + appointmentFieldDelimiter);
	    			writer.print(appointments.get(i).getLocation() + appointmentFieldDelimiter);
	    			writer.print(appointments.get(i).getHidden() + appointmentFieldDelimiter);
	    			writer.print(appointmentDelimiter);
	    		}
    		}
    		//Save the tree node to the right
    		save(currentStaff.getRight(), writer);
	    }
	    return true;
	}

	/**
	 * Will load and display all appointments where hidden == false
	 */
	public void displayAppointments()
	{
		getStaff().printAppointment(getStaff().getRoot());
	}

	/**
	 * Will load and display ALL appointments, for specific member of staff
	 */
	public void displayTasklist(int id)
	{
		Staff toPrint = getStaff().findInTree(id);
		toPrint.printTaskList();

	}

	/**
	 * Will start by getting data from user, to find suitable timeslot, then add to appointments
	 * @return    Appointment once booked
	 */
	public Appointment newAppointment(LinkedList<Integer> staffIDs, String start, int duration, String description, String location, boolean hidden)
	{
		Appointment newAppointment = new Appointment(start, duration, description, location, hidden);
		
		for (int i = 0; i < staffIDs.size(); i++)
		{
			staff.findInTree(staffIDs.get(i)).addAppointment(start, duration, description, location, hidden);
		}
		getUndo().push(new UndoNode(getStaff().findInTree(staffIDs.get(0)).searchAppointment(newAppointment.getStart()).getID(), staffIDs, newAppointment, "Added"));
		return newAppointment;
	}

	/**
	 * Will prompt for Staff ID, and then Appointment ID
	 * @return    Deleted appointment to be added to Undo Stack
	 */
	public UndoNode deleteAppointment(int staffID, int appointID)
	{
		Staff toEdit = getStaff().findInTree(staffID);
		Appointment toDelete = toEdit.searchAppointment(appointID);
		
		getStaff().findAll(getStaff().getRoot(), toDelete);
		
		LinkedList<Appointment> allApp = getStaff().sendList();
		LinkedList<Integer> allID = getStaff().sendListID();
		
		
		for(int i = 0; i < allApp.size(); i++) 
		{
			getStaff().findInTree(allID.get(i)).deleteAppointment(allApp.get(i));
		}
		
		UndoNode deleted = new UndoNode(toDelete.getID(), allID, toDelete, "Deleted");
		getUndo().push(deleted);
		return deleted;
	}

	/**
	 * Will prompt for Staff ID, and then Appointment ID, then select changes to be made
	 * Verify changes are valid before making changes final
	 *
	 * @return    Unedited appointment to be added to Undo Stack
	 */
	public Appointment editAppointment(int staffid, int appointid, String edit, String newValue)
	{
		Staff toEdit = getStaff().findInTree(staffid);
		Appointment toDelete = toEdit.searchAppointment(appointid);
		Appointment toReturn = new Appointment(toDelete);
		
		getStaff().findAll(getStaff().getRoot(), toDelete);
		
		LinkedList<Appointment> allApp = getStaff().sendList();
		LinkedList<Integer> allID = getStaff().sendListID();

		switch(edit)
		{
		case "start":
			try
			{	
				Date newDate = toDelete.getForm().parse(newValue);
				for(int i = 0; i < allApp.size(); i++) 
				{
					allApp.get(i).setStart(newDate);
				}
				
				getUndo().push(new UndoNode(toReturn.getID(), allID, toReturn, "Edited"));
				return toReturn;
			}
			catch(Exception e)
			{
				System.out.println("Invalid date format");
			}
			break;
		case "duration":
			try
			{
				int newDur = Integer.parseInt(newValue);
				for(int i = 0; i < allApp.size(); i++) 
				{
					allApp.get(i).setDuration(newDur);
				}
				
				getUndo().push(new UndoNode(toReturn.getID(), allID, toReturn, "Edited"));
				return toReturn;
			}
			catch(Exception e)
			{
				System.out.println("Invalid duration");
			}
			break;
		case "description":
			
			for(int i = 0; i < allApp.size(); i++) 
			{
				allApp.get(i).setDescription(newValue);
			}
			
			return toReturn;
		case "location":

			for(int i = 0; i < allApp.size(); i++) 
			{
				allApp.get(i).setLocation(newValue);
			}
			
			getUndo().push(new UndoNode(toReturn.getID(), allID, toReturn, "Edited"));
			return toReturn;
		case "hidden":
			boolean newBool = Boolean.parseBoolean(newValue);
			for(int i = 0; i < allApp.size(); i++) 
			{
				allApp.get(i).setHidden(newBool);
			}
			
			getUndo().push(new UndoNode(toReturn.getID(), allID, toReturn, "Edited"));
			return toReturn;
		default:
			break;

		}
		return null;
	}

	/**
	 * Will create a new member of staff, and return it to undo stack
	 * @return    Newly created member of staff
	 */
	public Staff newStaff(String name, String office, int id)
	{
		Staff newStaff = getStaff().addTree(new Staff(name, office, id));
		LinkedList<Integer> ids = new LinkedList<>();
		ids.add(id);

		if(newStaff == null)
		{
			return null;
		}
		else
		{	
			getUndo().push(new UndoNode(-1, ids, newStaff, "Added"));
			return newStaff;
		}

	}

	/**
	 * Will prompt for Staff ID, then delete it
	 * @return    Deleted staff member, to be added to undo stack
	 */
	public Staff deleteStaff(int id)
	{
		Staff toDelete = getStaff().findInTree(id);
		getStaff().delete(id);
		LinkedList<Integer> ids = new LinkedList<Integer>();
		ids.add(id);

		Staff check = getStaff().findInTree(id);

		if(check == null)
		{	
			
			getUndo().push(new UndoNode(-1, ids, toDelete, "Deleted"));
			return toDelete;
		}
		else
		{
			return null;
		}

	}

	/**
	 * Prompt user for Staff ID, and which field to Change
	 * Verify changes are valid
	 * @return    Unedited member of staff to be added to undo stack
	 */
	public Staff editStaff(int id, String edit, String newValue)
	{
		Staff toEdit = getStaff().findInTree(id);
		LinkedList<Integer> ids = new LinkedList<>();
		ids.add(id);
		if(toEdit == null)
		{
			System.out.println(" Staff does not exist ");
		}
		else
		{
			Staff toReturn = new Staff(toEdit);
			switch(edit) {
				case "name":
					toEdit.setName(newValue);
					
					getUndo().push(new UndoNode(-1, ids, toReturn, "Edited"));
					return toReturn;
				case "office":
					toEdit.setOffice(newValue);
					
					getUndo().push(new UndoNode(-1, ids, toReturn, "Edited"));
					return toReturn;
				default:
					break;
			}
		}
		return null;
	}

	/**
	 * Display list of all staff
	 */
	public void displayStaff()
	{
		getStaff().traverseTree(getStaff().getRoot(), 1);
	}

	/**
	 * Code implementation of testPlan
	 */
	public void testPlan()
	{
		print("Test 1. Create new appointment when no Staff exists (Redacted, test manually)");
		LinkedList<Integer> ids = new LinkedList<>();
		
		print("Test 2. Add staff, then appointment");
		newStaff("Iain Martin", "QMB", 133);
		ids.clear();
		ids.add(133);
		newAppointment(ids, "22/06/20 12:30", 120, "Lecture for AC12001", "QMB", false);
		displayAppointments();
		
		print("Test 3. Add a further 3 members of staff");
		newStaff("Craig Ramsay", "QMB", 134);
		newStaff("Michael Crabb", "QMB", 12);
		newStaff("Jacky Visser", "QMB", 13);
		displayStaff();
		
		print("Test 4. Delete a member of staff");
		deleteStaff(13);
		displayStaff();
		
		print("Test 5. Delete a further 3 members of staff");
		deleteStaff(12);
		deleteStaff(134);
		deleteStaff(133);
		displayStaff();
		
		print("Test 6. Add appointment to staff with no appointments");
		newStaff("Iain Martin", "QMB", 133);
		newAppointment(ids, "29/06/20 12:30", 120, "Lecture for AC12001", "QMB", false);
		displayAppointments();
		
		print("Test 7. Invalid start date (Redacted, test manually)");

		
		print("Test 8. Appointment for multiple Staff");
		newStaff("Craig Ramsay", "QMB", 134);
		newStaff("Michael Crabb", "QMB", 12);
		newStaff("Jacky Visser", "QMB", 13);
		ids.add(134);
		ids.add(12);
		ids.add(13);
		Appointment a = newAppointment(ids, "30/09/20 12:30", 120, "Lecture for MA12001", "Tower D'Arcy", false);
		displayAppointments();
		
		print("Test 9. Remove appointment with multiple staff attached");
		undo();
		displayAppointments();
		
		
		print("Test 10. Display Tasklist for a member of staff");
		displayTasklist(133);
		
		print("Test 11. Add staff, then undo addition");
		Staff allison = newStaff("Allison Pearse", "QMB", 137);
		ids.clear();
		ids.add(137);
		displayStaff();
		undo();
		displayStaff();
		
		print("Test 12. Redo undone action (Add back Allison)");
		redo();
		displayStaff();
		
		print("Test 13. Add appointment for multiple staff, undo addition");
		ids.clear();
		ids.add(134);
		ids.add(12);
		ids.add(13);
		a = newAppointment(ids, "30/09/21 12:30", 120, "Lecture for MA12001", "Tower D'Arcy", false);
		displayAppointments();
		undo();
		displayAppointments();
		
		print("Test 14. Redo undone action (Add back removed appointments)");
		redo();
		displayAppointments();
		
		print("Test 15. Add a staff, an appointment, and an edit, undo 3x");
		ids.clear();
		ids.add(3);
		allison = newStaff("Alexander Russel", "Fulton Building", 3);
		a = newAppointment(ids, "30/07/21 12:30", 120, "Lecture for MA11001", "Tower D'Arcy", false);
		editAppointment(3, 1, "office", "The Moon");
		displayTasklist(3);
		undo();
		undo();
		undo();
		displayStaff();
		
	}



}
