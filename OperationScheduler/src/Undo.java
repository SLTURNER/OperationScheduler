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
	 * Will pop top node in stack
	 * @return    top node
	 */
	public UndoNode pop() 
	{
		UndoNode n = getHead();
		setHead(n.getNext());
		
		return n;
		
	}
	

}
