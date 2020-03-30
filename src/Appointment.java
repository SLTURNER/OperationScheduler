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
	private int ID;
	private SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	
	/**
	 * Constructor
	 * @param date - the start date and time of appointment
	 * @param duration - length of appointment in minutes
	 */
	public Appointment(String date,  int duration, String description, String location)
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
		
		endSlotCal(start);
		
		this.description = description;
		this.location = location;
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
	 * accessor
	 * @return description
	 */
	public String getDescription()
	{
		return description;
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
	 * Calculates the end time of the appointment
	 * @param start - of appointment
	 */
	private void endSlotCal(Date start)
	{
		long min = start.getTime();
		end.setTime(min + (duration * 60 * 1000));
		System.out.println(end);
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
	 * accessor 
	 * @return end 
	 */
	public Date getEnd()
	{
		return end;
	}
	
	
	/**
	 * formats to ensure strings are outputted/returned correctly
	 */
	public String toString() 
	{
	    String str = "";
	    str = str + form.format(start) + " for " + duration + " minutes";
	    return str;
	 }

	
}
