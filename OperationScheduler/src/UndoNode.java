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
	UndoNode next;
	
	/**
	 * Default constructor
	 */
	public UndoNode() {
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
	public UndoNode(int i, Object o, UndoNode n) {
		id = i;
		obj = o;
		next = n;
	}

}
