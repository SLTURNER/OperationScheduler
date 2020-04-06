import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
		s.displayMenu();
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
					print("What is the ID of the Staff you would like to book?");
					int staffID = Integer.parseInt(s.nextLine());;

					print("When would you like to book an appointment? (Format should be: dd/MM/yy HH:mm)");
					String start = s.nextLine();

					print("How long will the appointment last?");
					int duration = Integer.parseInt(s.nextLine());;
					
					print("What will be happening at the appointment?");
					String description = s.nextLine();
					
					print("Where will the appointment take place?");
					String location = s.nextLine();
					
					print("Is this a public appointment, or personal task? (true or false)");
					boolean hidden = Boolean.parseBoolean(s.nextLine());

					Appointment toUndo = newAppointment(staffID, start, duration, description, location, hidden);
					if(toUndo != null) 
					{
						getUndo().push(new UndoNode(toUndo.getID(), staffID, toUndo, "Added"));
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
					int appointID = Integer.parseInt(s.nextLine());;
					
					Appointment deleted = deleteAppointment(staff1ID, appointID);
					if(deleted != null) 
					{
						getUndo().push(new UndoNode(appointID, staff1ID, deleted, "Deleted"));
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
						getUndo().push(new UndoNode(appoint1ID, staff2ID, old, "Edited"));
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
					
					if(newStaff != null) 
					{
						getUndo().push(new UndoNode(-1, id, newStaff, "Added"));
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
						
						if(old != null) 
						{
							getUndo().push(new UndoNode(-1, sID, old, "Edited"));
						}
						
					}
				case "i":
					displayStaff();
					print("What is the ID of the member of Staff you would like to remove?");
					int idToDel = Integer.parseInt(s.nextLine());;
					
					Staff removed = deleteStaff(idToDel);
					
					if(removed != null) 
					{
						getUndo().push(new UndoNode(-1, idToDel, removed, "Deleted"));
					}
				
				case "j":
					save(getStaff().getRoot());
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
				case "z":
					exit = true;
				default:
					print("Invalid selection");
					break;
				
					
				}



			}
			catch(Exception e)
			{
				print(e.getMessage());
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

		// If n is null, no item in UndoStack
		if(n == null)
		{
			return null;
		}
		else
		{
			Staff s = getStaff().findInTree(n.getSID());

			// If AID is -1, then the stored Object is the StaffTree, as that means there is no appointment
			if(n.getAID() == -1)
			{

				// If staff is not in StaffTree, then it must have been deleted, so add it back
				if(s == null) {
					getStaff().addTree((Staff)n.getObject());

					getRedo().push(new UndoNode(-1, n.getSID(), n.getObject(), "Added"));
					return n;
				}
				// Values of Staff must have been changed, so change them back.
				else
				{
					// If all fields have the same values, then the previous operation was an add of the current object to tree, so delete it
					if(s.getName().equals(((Staff) n.getObject()).getName()) && s.getOffice().equals(((Staff) n.getObject()).getOffice()))
					{
						getStaff().delete(n.getSID());

						getRedo().push(new UndoNode(-1, n.getSID(), s, "Deleted"));
						return s;
					}
					else
					{
						// Set constructor of Staff to duplicate values
						Staff toReturn = new Staff(s);

						s.setName( ((Staff) n.getObject()).getName());
						s.setOffice( ((Staff) n.getObject()).getOffice());

						getRedo().push(new UndoNode(-1, toReturn.getId(), toReturn, "Edited"));
						return toReturn;
					}

				}
			}
			else
			{
				// If stored object is an appointment.
				Appointment a = s.searchAppointment(n.getAID());

				// If appointment is null, it has been deleted
				if(a == null)
				{
					// Add it back
					s.addAppointment((Appointment) n.getObject());

					// Push newly added node to redo stack
					getRedo().push(new UndoNode(n.getAID(), n.getSID(), n.getObject(), "Added"));
					return n;
				}
				else
				{
					// If Appointment is still in set, check if values math UndoStack object

					if(a.getStart().compareTo(((Appointment) n.getObject()).getStart()) == 0 && a.getDuration() == ((Appointment) n.getObject()).getDuration()
						&& a.getDescription().equals(((Appointment) n.getObject()).getDescription()) && a.getLocation().equals(((Appointment) n.getObject()).getLocation())
						&& a.getHidden() == ((Appointment) n.getObject()).getHidden())

					{
						s.deleteAppointment(a);

						getRedo().push( new UndoNode(a.getID(), s.getId(), a, "Deleted"));

						return a;
					}

					// Make copy of Appointment stored, to add to redo stack
					Appointment toReturn = new Appointment(a);

					// Copy values from undo stack to appointment set
					a.setStart(((Appointment) n.getObject()).getStart());
					a.setDuration(((Appointment) n.getObject()).getDuration());
					a.setDescription(((Appointment) n.getObject()).getDescription());
					a.setLocation(((Appointment) n.getObject()).getLocation());
					a.setHidden(((Appointment) n.getObject()).getHidden());


					getRedo().push(new UndoNode(toReturn.getID(), s.getId(), toReturn, "Edited"));
					return toReturn;
				}

			}
		}

	}

	/**
	 * Will read last undo action and undo the undoneness. Returns returned object from Undo method to appropriate place
	 * @return    ReOverridden Object
	 */
	public Object redo()
	{
		UndoNode n = getRedo().pop();
		Staff s = getStaff().findInTree(n.getSID());

		// If Appointment ID is negative, it doesnt exist, therefore the object is Staff
		if(n.getAID() < 0)
		{
			switch(n.getAction())
			{
				case "Added":
					getStaff().delete(n.getSID());

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

					getUndo().push(new UndoNode(-1, toReturn.getId(), toReturn, "Edited"));
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
					s.deleteAppointment(a);

					getUndo().push( new UndoNode(a.getID(), s.getId(), a, "Deleted"));

					return a;

				case "Deleted":
					// Add it back
					s.addAppointment((Appointment) n.getObject());

					// Push newly added node to redo stack
					getUndo().push(new UndoNode(n.getAID(), n.getSID(), n.getObject(), "Added"));
					return n;

				case "Edited":
					// Make copy of Appointment stored, to add to redo stack
					Appointment toReturn = new Appointment(a);

					// Copy values from undo stack to appointment set
					a.setStart(((Appointment) n.getObject()).getStart());
					a.setDuration(((Appointment) n.getObject()).getDuration());
					a.setDescription(((Appointment) n.getObject()).getDescription());
					a.setLocation(((Appointment) n.getObject()).getLocation());
					a.setHidden(((Appointment) n.getObject()).getHidden());


					getUndo().push(new UndoNode(toReturn.getID(), s.getId(), toReturn, "Edited"));
					return toReturn;

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
	           int id = Integer.parseInt(staffFields[0]);
	           String name = staffFields[1];
	           String office = staffFields[2];
	
	           //Create a staff with the fields
	           Staff newStaff = new Staff(name, office, id);
	
	           //For each appointment in the line
	           String[] appointments = staffFields[3].split("&");
	           for (int i = 0; i <appointments.length; i++)
	           {
	             //Split into appointment fields
	             String[] appointmentFields = appointments[i].split(";");
	             String date = appointmentFields[0];
	             int duration = Integer.parseInt(appointmentFields[1]);
	             String description = appointmentFields[2];
	             String location = appointmentFields[3];
	             boolean hidden = Boolean.parseBoolean(appointmentFields[4]);
	
	             //Create an appointment with the appointment fields
	             Appointment newAppointment = new Appointment(date, duration, description, location, hidden);
	
	             //Add the appointment to the staff member's diary
	             newStaff.addAppointment(newAppointment);
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
	public boolean save(Staff currentStaff)
	{
      String path = "staffTree.txt";
	    FileOutputStream stream = null;
	    PrintWriter writer = null;

	    String staffFieldDelimiter = ",";
	    String appointmentDelimiter = "&";
	    String appointmentFieldDelimiter = ";";

	    //Save the tree node to the left
	    save(currentStaff.getLeft());

	    //If the node has a member of staff
	    if (currentStaff != null)
	    {
    		try 
    		{
				stream = new FileOutputStream(path);
			} 
    		catch (FileNotFoundException e) 
    		{
				System.out.println(e.getMessage());
				return false;
			}
    		writer = new PrintWriter(stream);

    		//Print the id, name and office of the staff to a new line on the file
    		int id = currentStaff.getId();
    		String name = currentStaff.getName();
    		String office = currentStaff.getOffice();
    		writer.println(id + staffFieldDelimiter + name + staffFieldDelimiter + office + staffFieldDelimiter);

    		//For each appointment in the staff member's diary
    		ArrayList<Appointment> appointments = currentStaff.getDiary().getAppointment();
    		for (int i = 0; i < appointments.size(); i++)
    		{
    			//Print out the appointment data to the file
    			writer.print(appointments.get(i).getStart() + appointmentFieldDelimiter);
    			writer.print(appointments.get(i).getDuration() + appointmentFieldDelimiter);
    			writer.print(appointments.get(i).getDescription() + appointmentFieldDelimiter);
    			writer.print(appointments.get(i).getLocation() + appointmentFieldDelimiter);
    			writer.print(appointments.get(i).getHidden() + appointmentFieldDelimiter);
    			writer.print(appointmentDelimiter);
    		}
    		writer.close();
	    }

	    //Save the tree node to the right
	    save(currentStaff.getRight());

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
	public Appointment newAppointment(int staffID, String start, int duration, String description, String location, boolean hidden)
	{
		Staff toAdd = getStaff().findInTree(staffID);
		Appointment added = toAdd.addAppointment(start, duration, description, location, hidden);

		return toAdd.searchAppointment(added.getID());
	}

	/**
	 * Will prompt for Staff ID, and then Appointment ID
	 * @return    Deleted appointment to be added to Undo Stack
	 */
	public Appointment deleteAppointment(int staffID, int appointID)
	{
		Staff toEdit = getStaff().findInTree(staffID);
		Appointment toDelete = toEdit.searchAppointment(appointID);
		return toEdit.deleteAppointment(toDelete);
	}

	/**
	 * Will prompt for Staff ID, and then Appointment ID, then select changes to be made
	 * Verify changes are valid before making changes final
	 *
	 * @return    Unedited appointment to be added to Undo Stack
	 */
	public Appointment editAppointment(int staffid, int appointid, String edit, String newValue)
	{
		Appointment toEdit = getStaff().findInTree(staffid).searchAppointment(appointid);
		Appointment toReturn = new Appointment(toEdit);

		switch(edit)
		{
		case "start":
			try
			{
				toEdit.setStart(toEdit.getForm().parse(newValue));
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
				toEdit.setDuration(Integer.parseInt(newValue));
				return toReturn;
			}
			catch(Exception e)
			{
				System.out.println("Invalid duration");
			}
			break;
		case "description":
			toEdit.setDescription(newValue);
			return toReturn;
		case "location":
			toEdit.setLocation(newValue);
			return toReturn;
		case "hidden":
			toEdit.setHidden(Boolean.parseBoolean(newValue));
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

		if(newStaff == null)
		{
			return null;
		}
		else
		{
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

		Staff check = getStaff().findInTree(id);

		if(check == null)
		{
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
					return toReturn;
				case "office":
					toEdit.setOffice(newValue);
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

	}



}
