import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
/**
 * StaffTree class
 * 
 * @author Lubo Tsenkov
 * @version April 2020
 */
public class StaffTree 
{
    private Staff root;

    private int count;
    
    /**
     * Constructor for objects of class StaffTree
     * Create a root
     */
    public StaffTree()
    {
        root = null;
    }
    
    /**
     * Returns root of tree
     * @return    Root of tree as Staff
     */
    public Staff getRoot() 
    {
    	return root;
    }
    
    /**
     * @return true if the list is empty
     */
    public boolean isTreeEmpty()
    {
    	return (root == null);
    }

    
    /**
     * Print the tree for user
     */
     public void printTree()
     {
    	 traverseTree(root, 1);
    }
     /**
      * Show the tree for developer
      */
      public void showTree()
      {
     	 traverseTree(root, 0);
     }
     
      /**
       * Save to file
       */
       public void saveTree()
       {
    	   traverseTreePreorder(root, 0);
      }
      
       /**
        * Load from file
        */
        public void loadTree()
        {
        	traverseTreePreorder(root, 1);
       }
        

   /**
     * Find in the tree
     * @param Id - the Staff Id to look for
     * @return the node it is looked for
     */
     public Staff findInTree(int Id)
     {
        Staff current = root;
        Boolean found = false;
        while( !found && current != null)
        {  
        	if(current.getId() == Id)
	        {
        		found = true;
//	        	return current;
	        }
	        else
	        {
	        	if( Id < current.getId())
	        		current = current.getLeft();
	        	else
	        		current = current.getRight();
	        }
        }
        return current;
    }
     
     /**
      * Add a node to the tree if Staff is New
      * @param Id - an Staff Id to add
      */
     public void addToTree(int Id)
     {
    	 Staff newNode = new Staff();
    	 newNode.setId(Id);
    	 addTree(newNode);
    	 
     }
     
     /**
      * Adds Staff node to tree
      * @param newNode
      * @return    NewNode as Staff
      */
     public Staff addTree(Staff newNode) 
     {	
    	 int Id = newNode.getId();
    	 Staff current = new Staff();
    	 if(isTreeEmpty())
    	 {
    		 root = newNode;
    		 return newNode;
    	 }
    	 else
    	 {
    		if(findInTree(Id) != null)
    		{
    			System.out.println("Node already in the tree");
    			return null;
    		}
    		else
    		{
    			current = root;
    			while(current != null && current.getId() != Id)
    			{
    				if(Id < current.getId() && current.getLeft() != null)
    					current = current.getLeft();
    				if(Id < current.getId() && current.getLeft() == null)
    				{
    					current.setLeft(newNode);
    					current = current.getLeft();
    				}
    				if(Id > current.getId() && current.getRight() != null)
    					current = current.getRight();
    				if(Id > current.getId() && current.getRight() == null)
    				{
    					current.setRight(newNode);
    					current = current.getRight();
    				}
    			}
    			return newNode;
    		}
    	 }
     }
     
     
     
     /**
      * traversing the tree inorder
      * @param node - the node to start (root)
      * @param type - what type of processing to be executed
      */
     public void traverseTree(Staff node, int type)
     {
    	 if(node != null)
    	 {
    		 traverseTree(node.getLeft(), type);
    		 process(node, type);
        	 traverseTree(node.getRight(), type);
    	 }
     }
     
     /**
      * Prints all appointments in tree
      * @param node    Current node
      */
     public void printAppointment(Staff node) 
     {
    	 if(node != null)
    	 {
    		 printAppointment(node.getLeft());
    		 node.printApppointments();
    		 printAppointment(node.getRight());
    	 }
     }

     
     /**
      * process a node when traversing
      * @param node - the node to be processed
      * @param type - the type of processing
      */
     public void process(Staff node, int type)
     {
    	 switch(type)
    	 {
	    	 case 0: //developer print tree
			 {
				System.out.println("---------------------------------");
				System.out.println("Id: " + node.getId());
				System.out.println("Name: " + node.getName());
				System.out.println("Office: " + node.getOffice());
				if(node.getLeft() == null)
					System.out.println("Left: " + node.getLeft());
				else
					System.out.println("Left: " + node.getLeft().getId());
				if(node.getRight() == null)
					System.out.println("Right: " + node.getRight());
				else
					System.out.println("Right: " + node.getRight().getId());
				System.out.println("---------------------------------");
			 }
			 break;
    		 case 1: //user print tree
    		 {
    			System.out.println("---------------------------------");
    			System.out.println("Id: " + node.getId());
    			System.out.println("Name: " + node.getName());
    			System.out.println("Office: " + node.getOffice());
    			System.out.println("---------------------------------");
    		 }
    		 break;

    		 case 2: //count the nodes
    		 {
    			 count ++;
    		 }
    		 break;
    		 default:
    			 System.out.println();
    	 }
     }
     
     /**
      * process a node when file saving/loading
      * @param node - the node to be processed
      * @param type - the type of processing
      */
     public void processFile(Staff node, int type)
     {
    	 switch(type)
    	 {
	    	 case 0: //save
			 {
				 saveToFile(node);
			 }
			 break;
    		 case 1: //load
    		 {
    			loadFromFile();
    		 }
    		 break;
    		 default:
    			 System.out.println();
    	 }
     }
     
     /**
      * traversing the tree preorder
      * @param node - the node to start (root)
      * @param type - what type of procesing to be executed
      */
     public void traverseTreePreorder(Staff node, int type)
     {
    	 if(node != null)
    	 {
    		 processFile(node, type);
    		 traverseTree(node.getLeft(), type);
        	 traverseTree(node.getRight(), type);
    	 }
     }
     

     /**
      * traversing the tree postorder
      * @param node - the node to start (root)
      * @param type - for what type of process to be executed
      */
     public void traverseTreePostorder(Staff node, int type)
     {
    	 if(node != null)
    	 {
    		 traverseTree(node.getLeft(), type);
        	 traverseTree(node.getRight(), type);
        	 process(node, type);
    	 }
     }
     
     /**
      * save to file
      * @param node - the node to save to a file
      */
     public void saveToFile(Staff node)
     {
    	// display information about which example we are showing
         System.out.println("Saving data to a file 'data.txt' ");
         
         // file variables
         FileOutputStream outputStream = null;
         PrintWriter printWriter = null;
          
         try
         {
             // open the file for writing
             outputStream = new FileOutputStream("data.txt");
             printWriter = new PrintWriter(outputStream); 
             
             //print the data to the file
            printWriter.println(node.getId());
         	printWriter.println(node.getName());
			printWriter.println(node.getOffice());
             
         }
         catch (IOException e)
         {
             System.out.println("IO Error detected when opening or writing to file: " + e);
         }
         finally
         {
             //  if the file was opened successfully
             if (printWriter != null)
             {
                 // make sure it is closed
                 printWriter.close();
             }
         }
     }
     
     /**
      * load from file
      */
     public void loadFromFile()
     {
    	// display information about which example we are showing
         System.out.println("Loading data from file 'data.txt'");
                  
         // file variables
         FileReader fileReader = null;
         BufferedReader bufferedReader = null;
          
         try
         {
             // open the file for reading
             fileReader = new FileReader("data.txt");
             bufferedReader = new BufferedReader(fileReader); 
             
             String nextRow = bufferedReader.readLine();
             
             while(nextRow != null)
             {
	             int id = Integer.parseInt(nextRow);
	             String  name = bufferedReader.readLine();
	             String  office = bufferedReader.readLine();
	             
	             addToTree(id);
	             findInTree(id).setName(name);
	             findInTree(id).setOffice(office);
	             
	             nextRow = bufferedReader.readLine();
             }
         }
         catch (IOException e)
         {
             System.out.println("IO Error detected when opening or reading from file: " + e);
         }
         finally
         {
             //  if the file was opened successfully
             if (bufferedReader != null)
             {
                 // make sure it is closed
                 try
                 {
                     bufferedReader.close();
                 }
                 catch (IOException e)
                 {
                     System.out.println("Error closing file: " + e.getMessage());
                 }
             }
         }
     }
     
     /**
      * Count the number of nodes
      * @return the number of the nodes in the tree
      */
     public int count()
     {
    	 count = 0;
    	 traverseTree(root, 2); 
    	 return count;
     }
     
     /**
      * Delete from Tree
      * @param Id - item Id to delete
      */
     public void delete(int Id)
     {
    	 System.out.println("Deleting an item...");
    	 
    	 if(findInTree(Id) == null)
  		{
  			System.out.println("The node is not found!");
  			return;
  		}
    	 
    	 Staff toDelete = findInTree(Id);
    	 Staff current = new Staff();
    	 Staff previous = new Staff();
    	 
    	current = root;
    	previous = null;
    	int previousCount = count();
    	boolean repeat = true;
    	
    	 while(current != null && repeat)
    	 {
    		 if(current.getId() == Id) //means current is the node to delete (also is = toDelete)
    		 {
    			 if(current.getLeft() == null && current.getRight() == null)
    			 {
    				 if(previous == null && current == root) 
    				 {
    					 root = null;
    					 return;
    				 }
    				 if(previous.getLeft() == current && previous != null)
    					 previous.setLeft(null);
    				 if(previous.getRight() == current && previous != null)
    					 previous.setRight(null);
    			 }
    			 
    			 if(current.getLeft() != null && current.getRight() == null)
    			 {
    				 if(previous == null  && current == root)
    				 {
    					 root = current.getLeft();
    					 delete(root.getLeft().getId());
    				 }
    				 if(previous.getLeft() == current && previous != null)
    					 previous.setLeft(current.getLeft());
    				 if(previous.getRight() == current && previous != null)
    					 previous.setRight(current.getLeft());
    			 }
    			 if(current.getLeft() == null && current.getRight() != null)
    			 {
    				 if(previous == null  && current == root)
    				 {
    					 root = current.getRight();
    					 delete(root.getRight().getId());
    				 }
    				 if(previous.getLeft() == current && previous != null)
    					 previous.setLeft(current.getRight());
    				 if(previous.getRight() == current && previous != null)
    					 previous.setRight(current.getRight());
    			 }
    			 
    			 if(current.getLeft() != null && current.getRight() != null)
    			 {
    				 Staff parentDelete = previous;
    				 current = toDelete.getLeft();
    				 previous = null;
    				 while(current.getRight() != null)
    				 {
    					 previous = current;
    					 current = current.getRight();
    				 }
    				 if(toDelete == root)
    				 {
    					 root = current;
    					 root.setRight(toDelete.getRight());
    				 }
    				 
    				 current.setRight(toDelete.getRight());
    				 parentDelete.setLeft(current);
    				 
    				 
    				 if(previous != null)
    					 previous.setRight(current.getLeft());
    				 else
    					 toDelete.setLeft(current.getLeft()); 
    			 }
    		 }
    		 else
    		 {
    			 
    			 if(current.getId() > Id)
    			 {
    				 previous = current;
    				 current = current.getLeft();
    			 }

    			 if(current.getId() < Id)
    			 {
    				 previous = current;
    				 current = current.getRight();
    			 }
    		 }
    		 if(previousCount != count())
    			 repeat = false;
    	 }
     }
}
