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
	 * Accesssor, returns head
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
		
	}
	
	/**
	 * Will pop top node in stack
	 * @return    top node
	 */
	public UndoNode pop() 
	{
		
		
	}
	

}
