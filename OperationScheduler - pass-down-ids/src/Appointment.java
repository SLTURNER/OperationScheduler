import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * Class to hold information about an appointment
 * @author Mohsin Raza
 * @version 30/03/20
 *
 */
public class Appointment {
	
	//instance variables
	private Date start;
	private int duration;
	private Date end;
	private String description;
	private String location;
	private SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm");
	private int id;
	
	// Used to differentiate between tasks and appointments
	private boolean hidden;
	
	
	/**
	 * Constructor
	 * @param date - the start date and time of appointment
	 * @param duration - length of appointment in minutes
	 */
	public Appointment(String date,  int duration, String description, String location, boolean hidden)
	{
		start = new Date();
		end = new Date();
		
		try 
		{
			start = form.parse(date);
		}
		catch(Exception e)
		{
			System.out.println();
		}
		
		this.duration = duration;
		
		end = calculateEnd(start);
		
		this.description = description;
		this.location = location;
		this.hidden = hidden;
	}
	
	/**
	 * Constructor for undo method, will make deep copy
	 * @param temp    Object to copy
	 */
	public Appointment(Appointment temp)
	{
		start = temp.getStart();
		end = temp.getEnd();
		
		this.duration = temp.getDuration();
		
		this.description = temp.getDescription();
		this.location = temp.getLocation();
		this.hidden = temp.getHidden();
	}
	
	
	/**
	 * Mutator
	 * @param description of appointment
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	
	/**
	 * mutator
	 * @param location of appointment
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}
	
	/**
	 * Sets ID
	 * @param id    Integer id of appointment
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Gets ID
	 * @return    integer id of appointment stored in fields
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * accessor
	 * @return description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Gets hidden value
	 * @return    Boolean value of isHidden
	 */
	public boolean getHidden() 
	{
		return hidden;
	}
	
	/**
	 * Changes value of hidden
	 * @param hidden    New hidden value
	 */
	public void setHidden(boolean hidden) 
	{
		this.hidden = hidden;
	}
	
	
	/**
	 * accessor 
	 * @return location 
	 */
	public String getLocation()
	{
		return location;
	}
	
	
	/**
	 * accessor 
	 * @return duration
	 */
	public int getDuration()
	{
		return duration;
	}
	
	/**
	 * Updates length of appointment
	 * @param duration    New duration
	 */
	public void setDuration(int duration) 
	{
		this.duration = duration;
		calculateEnd(getStart());
	}
	
	
	/**
	 * Calculates the end time of the appointment
	 * @param start - of appointment
	 */
	private Date calculateEnd(Date start)
	{
		long min = start.getTime();
		end.setTime(min + (getDuration() * 60 * 1000));
		return end;
	}
	
	
	/**
	 * accessor 
	 * @return start
	 */
	public Date getStart()
	{
		return start;
	}
	
	/**
	 * Updates value of start field
	 * @param start     Time of appointment start
	 */
	public void setStart(Date start) 
	{
		this.start = start;
		calculateEnd(getStart());
	}
	
	
	/**
	 * accessor 
	 * @return end 
	 */
	public Date getEnd()
	{
		return end;
	}
	
	/**
	 * Returns dateformat
	 * @return    Dateformat as simmpleDateForm
	 */
	public SimpleDateFormat getForm() 
	{
		return form;
	}
	
	
	/**
	 * formats to ensure strings are outputted/returned correctly
	 */
	public String toString() 
	{
	    String string = "";
	    string = string + form.format(start) + " for " + duration + " minutes";
	    return string;
	 }

	
}