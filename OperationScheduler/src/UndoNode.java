/**
 * This is the node in the stack of previous actions
 * @author Sean Turner
 * @version 11/03/2020
 *
 */
public class UndoNode 
{
	int id;
	Object obj;
	String action;
	UndoNode next;
	
	/**
	 * Default constructor
	 */
	public UndoNode() 
	{
		id = 0;
		obj = null;
		next = null;
	}
	
	/**
	 * The constructor that will actually be used. Takes all field values at creation
	 * @param i    ID of staff whose diary got changed
	 * @param o    Object changed (Staff or Diary)
	 * @param n    Next node (getHead())
	 */
	public UndoNode(int i, Object o, UndoNode n) 
	{
		id = i;
		obj = o;
		next = n;
	}
	
	/**
	 * The constructor that will actually be used. Takes all field values at creation
	 * @param i    ID of staff whose diary got changed
	 * @param o    Object changed (Staff or Diary)
	 * @param n    Next node (getHead())
	 */
	public UndoNode(int i, Object o, String a, UndoNode n) 
	{
		id = i;
		obj = o;
		action = a;
		next = n;
	}
	
	
	/**
	 * Accessor, return next UndoNode
	 * @return   next node
	 */
	public UndoNode getNext() 
	{
		return next;
	}
	
	/**
	 * mutators, will change next node to input parameter
	 * @param n    Next undoNode
	 * @return     New UndoNode
	 */
	public UndoNode setNext(UndoNode n) 
	{
		next = n;
		return n;
	}
	
	/**
	 * Accessor, returns ID as integer
	 * @return    ID as integer
	 */
	public int getID() 
	{
		return id;
	}
	
	/**
	 * Accessor, returns unchanged Object
	 * @return     Object 
	 */
	public Object getObject() 
	{
		return obj;
	}
	
	/**
	 * Accessor, returns action taken as string. *Might be obsolete*
	 * @return    Action as string
	 */
	public String getAction() 
	{
		return action;
	}

}
