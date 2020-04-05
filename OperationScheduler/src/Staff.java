import java.util.HashSet;
import java.util.Set;

/**
 * Class for Staff
 * @author Lubo Tsenkov
 * @version April 2020
 */

public class Staff {
	
	private Staff left;
	private Staff right;
	
	private int Id;
	private String name;
//	private Diary diary;
	private String office;

	private Set<Appointment> diary;


    /**
     * Constructor for objects of class Staff
     */
    public Staff()
    {
        // initialise instance variables
        this.Id = 0;
        this.name = "";
        this.office = "";
        this.left = null;
        this.right = null;
        this.diary = new HashSet<Appointment>();
    }
    
     /**
     * Get the left node
     * 
     * @return the left node
     */
    public Staff getLeft()
    {
        return left;
    }
    
    /**
     * Get the right node
     * 
     * @return the right node
     */
    public Staff getRight()
    {
        return right;
    }
    
    /**
     * Get the id
	 *
     * @return id at this node
     */
    public int getId()
    {
        return Id;
    }
    
     /**
     * Get the name
     * 
     * @return name at this node
     */
    public String getName()
    {
        return name;
    }
    
     /**
     * Get the office
	 *
     * @return office at this node
     */
    public String getOffice()
    {
        return office;
    }
    
    /**
     * Set the Id
	 *
     * @param Id to be set
     */
    public void setId(int id)
    {
        this.Id = id;
    }
    
    /**
     * Set the office
	 *
     * @param office to be set
     */
    public void setOffice(String office)
    {
        this.office = office;
    }

    /**
     * Set the name
	 *
     * @param name to be set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
     /**
     * Set the left node
     * 
     * @param  the node to be added at this node's left
     */
    public void setLeft(Staff left)
    {
        this.left = left;
    }
    
    /**
    * Set the right node
    * 
    * @param  the node to be added at this node's right
    */
   public void setRight(Staff right)
   {
       this.right = right;
   }
    
     /**
     * Return a string containing the data from this node, formatted
     */
    public String printInfo()
    {
        String info;
        
        info = "Staff " + name + " is in office" + office;
        
        return info;
    }
}
