
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
			// If ID is -1, then the stored Object is the StaffTree, as that means there is no appointment
			if(n.getID() == -1) 
			{	
				Staff s = getStaff().find(n.getObjectID());
				
				// If staff is not in StaffTree, then it must have been deleted, so add it back
				if(s == null) {
					getStaff().add(n);
					// Fill in blank
					getRedo().push(new UndoNode(-1, n, "added"));
					return n;
				}
				// Values of Staff must have been changed, so change them back.
				else
				{	
					// Set constructor of Staff to duplicate values
					Staff toReturn = new Staff(s);
					
					s.setName(n.getObject().getName());
					s.setOffice(n.getObject().getOffice());
					
					getRedo().push(new UndoNode(-1, toReturn));
					return toReturn;
				}
			}
			else
			{
				// If stored object is an appointment. 
				
			}
		}
		
	}
	
	/**
	 * Will read last undo action and undo the undoneness. Returns returned object from Undo method to appropriate place
	 * @return    ReOverridden Object
	 */
	public Object redo() 
	{
		
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
