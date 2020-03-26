
public class Scheduler 
{
	StaffTree staff;
	Undo undo;
	Undo redo;

	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

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
		staff = n;
		
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
	 * Will display all menu options
	 */
	public void displayMenu() 
	{
		
	}
	
	/**
	 * Will take user input, and call appropriate methods
	 */
	public void processSelection() 
	{
		
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
			Staff s = getStaff().find(n.getSID());
			
			// If AID is -1, then the stored Object is the StaffTree, as that means there is no appointment
			if(n.getAID() == -1) 
			{	
				
				// If staff is not in StaffTree, then it must have been deleted, so add it back
				if(s == null) {
					getStaff().add(n.getObject());
					
					getRedo().push(new UndoNode(-1, n.getSID(), n.getObject(), "Added"));
					return n;
				}
				// Values of Staff must have been changed, so change them back.
				else
				{	
					// If all fields hve the same values, then the previous operation was an add of the current object to tree, so delete it
					if(s.getName().equals(n.getObject().getName()) && s.getOffice().equals(n.getObject().getOffice())) 
					{
						getStaff().delete(n.getSID());
						
						getRedo().push(new UndoNode(-1, n.getSID(), s, "Deleted"));
						return s;
					}
					else 
					{
						// Set constructor of Staff to duplicate values
						Staff toReturn = new Staff(s);
						
						s.setName(n.getObject().getName());
						s.setOffice(n.getObject().getOffice());
						
						getRedo().push(new UndoNode(-1, toReturn.getID(), toReturn, "Edited"));
						return toReturn;
					}
					
				}
			}
			else
			{
				// If stored object is an appointment. 
				Appointment a = s.findAppointment(n.getAID());
				
				// If appointment is null, it has been deleted
				if(a == null) 
				{
					// Add it back
					s.newAppointment(n.getObject());
					
					// Push newly added node to redo stack
					getRedo().push(new UndoNode(n.getAID(), n.getSID(), n.getObject(), "Added"));
					return n;
				}
				else
				{
					// If Appointment is still in set, check if values math UndoStack object
					
					if(a.getStart().compareTo(n.getObject().getStart()) == 0 && a.getDuration().compareTo(n.getObject().getDuration()) 
						&& a.getDesicription().equals(n.getObject().getDescription()) && a.getLocation().equals(n.getObject().getLocation())
						&& a.getHidden() == n.getObject().getHidden()) 
						
					{
						s.deleteAppointment(a.getID());
						
						getRedo().push( new UndoNode(a.getID(), s.getID(), a, "Deleted"));
						
						return a;
					}
					
					// Make copy of Appointment stored, to add to redo stack
					Appointment toReturn = new Appointment(a);
					
					// Copy values from undo stack to appointment set
					a.setStart(n.getObject().getStart());
					a.setDuration(n.getObject().getDuration());
					a.setDescription(n.getObject().getDescription());
					a.setLocation(n.getObject().getLocation());
					a.setHidden(n.getObject().getHidden());
					
					
					getRedo().push(new UndoNode(toReturn.getID(), s.getID(), toReturn, "Edited"));
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
		Staff s = getStaff().find(n.getSID());
		
		// If Appointment ID is negative, it doesnt exist, therefore the object is Staff
		if(n.getAID() < 0) 
		{
			switch(n.getAction()) 
			{
				case "Added":
					getStaff().delete(n.getSID());
					
					getUndo().push(new UndoNode(-1, n.getSID(), s, "Deleted"));
					return s;
					
					break;
					
				case "Deleted":
					getStaff().add(n);
					
					getUndo().push(new UndoNode(-1, n.getSID(), n.getObject(), "Added"));
					return n;
					
					break;
					
				case "Edited":
					Staff toReturn = new Staff(s);
					
					s.setName(n.getObject().getName());
					s.setOffice(n.getObject().getOffice());
					
					getUndo().push(new UndoNode(-1, toReturn.getID(), toReturn, "Edited"));
					return toReturn;
					
					break;
					
				default:
					break;
			}
		}
		else 
		{
			Appointment a = s.findAppointment(n.getAID());
			
			switch(n.getAction()) 
			{
				case "Added":
					s.deleteAppointment(a.getID());
					
					getUndo().push( new UndoNode(a.getID(), s.getID(), a, "Deleted"));
					
					return a;					
					break;
					
				case "Deleted":
					// Add it back
					s.newAppointment(n.getObject());
					
					// Push newly added node to redo stack
					getUndo().push(new UndoNode(n.getAID(), n.getSID(), n.getObject(), "Added"));
					return n;
					break;
					
				case "Edited":
					// Make copy of Appointment stored, to add to redo stack
					Appointment toReturn = new Appointment(a);
					
					// Copy values from undo stack to appointment set
					a.setStart(n.getObject().getStart());
					a.setDuration(n.getObject().getDuration());
					a.setDescription(n.getObject().getDescription());
					a.setLocation(n.getObject().getLocation());
					a.setHidden(n.getObject().getHidden());
					
					
					getUndo().push(new UndoNode(toReturn.getID(), s.getID(), toReturn, "Edited"));
					return toReturn;
					break;
					
				default:
					break;
			}
		}
		
	}
	
	/**
	 * Will store overriden staff tree in undo stack, and load new data from file
	 * @return    Old StaffTree
	 */
	public StaffTree load() 
	{
		
	}
	
	/**
	 * Will save current state of Scheduler to file
	 *     Note: Undo and Redo stacks are NOT saved
	 * @return    true once saved succesfully
	 */
	public boolean save() 
	{
		
	}
	
	/**
	 * Will load and display all appointments where hidden == false
	 */
	public void displayAppointments() 
	{
		
	}
	
	/**
	 * Will load and display ALL appointments, for specific member of staff
	 */
	public void displayTasklist() 
	{
		
	}
	
	/**
	 * Will start by getting data from user, to find suitable timeslot, then add to appointments
	 * @return    Appointment once booked
	 */
	public Appointment newAppointment() 
	{
		
	}
	
	/**
	 * Will prompt for Staff ID, and then Appointment ID
	 * @return    Deleted appointment to be added to Undo Stack
	 */
	public Appointment deleteAppointment() 
	{
		
	}
	
	/**
	 * Will prompt for Staff ID, and then Appointment ID, then select changes to be made
	 * Verify changes are valid before making changes final
	 * 
	 * @return    Unedited appointment to be added to Undo Stack
	 */
	public Appointment editAppointment() 
	{
		
	}
	
	/**
	 * Will create a new member of staff, and return it to undo stack
	 * @return    Newly created member of staff
	 */
	public Staff newStaff() 
	{
		
	}
	
	/**
	 * Will prompt for Staff ID, then delete it
	 * @return    Deleted staff member, to be added to undo stack
	 */
	public Staff deleteStaff() 
	{
		
	}
	
	/**
	 * Prompt user for Staff ID, and which field to Change
	 * Verify changes are valid
	 * @return    Unedited member of staff to be added to undo stack
	 */
	public Staff editStaff() 
	{
		
	}
	
	/**
	 * Display list of all staff
	 */
	public void displayStaff() 
	{
		
	}
	
	
	

}
