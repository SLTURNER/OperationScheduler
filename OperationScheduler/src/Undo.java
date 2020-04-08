/**
 * Stack class to store and run relevant method on the previous action nodes
 * @author Sean Turner
 * @version 11/03/2020
 *
 */

public class Undo 
{
	UndoNode head;
	
	
	/**
	 * Default constructor
	 */
	public Undo() 
	{
		head = new UndoNode();
	}
	
	
	/**
	 * Accessor, returns head
	 * @return    head as UndoNode
	 */
	public UndoNode getHead() 
	{
		return head;
	}
	
	/**
	 * Mutator, changes head
	 * @param n    new head
	 * @return    new head
	 */
	public UndoNode setHead(UndoNode n) 
	{
		head = n;
		return n;
	}
	
	/**
	 * Will push new node to stack
	 * @return    new node
	 */
	public UndoNode push(UndoNode n) 
	{
		n.setNext(getHead());
		setHead(n);
		return n;
	}
	
	/**
	 * Used for when changes have been made to appointments
	 * 
	 * @param s    Reference to changed Staff Object (Copied internally)
	 * @param a    Action performed as String
	 * @return     UndoNode containing all appropriate values
	 */
	public UndoNode push(Staff s, String a) 
	{
		UndoNode n = new UndoNode(-1, s.getId(), new Staff(s), a);
		n.setNext(getHead());
		setHead(n);
		return n;
	}
	
	/**
	 * Used for when changes have been made to appointments
	 * 
	 * @param staffID    Integer ID of staff
	 * @param s    Reference to changed Appointment (Copy of Appointment made internally)
	 * @param a    Action performed as String
	 * @return     UndoNode containing all appropriate values
	 */
	public UndoNode push(int staffID, Appointment s, String a) 
	{
		UndoNode n = new UndoNode(s.getID(), staffID, new Appointment(s), a);
		n.setNext(getHead());
		setHead(n);
		return n;
	}
	
	/**
	 * Will pop top node in stack
	 * @return    top node
	 */
	public UndoNode pop() 
	{
		if(!isEmpty()) 
		{
			UndoNode n = getHead();
			setHead(n.getNext());
			
			return n;
		}
		return null;
	}
	
	public boolean isEmpty() 
	{
		if(getHead() == null) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	

}
