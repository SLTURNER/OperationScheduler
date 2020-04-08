import java.util.LinkedList;

/**
 * This is the node in the stack of previous actions
 * @author Sean Turner
 * @version 11/03/2020
 *
 */
public class UndoNode 
{
	int aID;
	LinkedList<Integer> sID;
	Object obj;
	String action;
	UndoNode next;
	
	/**
	 * Default constructor
	 */
	public UndoNode() 
	{
		aID = 0;
		sID = new LinkedList<>();
		obj = null;
		next = null;
	}
	
	/**
	 * The constructor that will actually be used. Takes all field values at creation
	 * @param i    ID of staff whose diary got changed
	 * @param o    Object changed (Staff or Diary)
	 */
	public UndoNode(int a, LinkedList<Integer> s, Object o) 
	{
		aID = a;
		sID = s;
		obj = o;
	}
	
	/**
	 * The constructor that will actually be used. Takes all field values at creation
	 * @param i    ID of staff whose diary got changed
	 * @param o    Object changed (Staff or Diary)
	 */
	public UndoNode(int a, LinkedList<Integer> s, Object o, String action) 
	{
		aID = a;
		sID = s;
		obj = o;
		this.action = action;
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
	 * Accessor, returns ID of appointment as integer
	 * @return    ID as integer
	 */
	public int getAID() 
	{
		return aID;
	}
	
	/**
	 * Accessor, returns ID of staff as integer
	 * @return    ID as integer
	 */
	public LinkedList<Integer> getSID() 
	{
		return sID;
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
