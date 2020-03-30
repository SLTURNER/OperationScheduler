import java.util.Date;
import java.text.SimpleDateFormat;



public class Appointment {
	
	private Date start;
	private int duration;
	private Date end;
	private String description;
	private String location;
	private int id;
	private boolean hidden;
	
	private SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	
	public Appointment(String date,  int duration)
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
		
		description = "";
		location = "";
	
		
	}
	
	/**
	 * Alternative constructor for where Description and location are known
	 * @param date    Date as String to be parsed to date
	 * @param duration    Duration of appointment
	 * @param description
	 * @param location
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
	 * Will make a deep copy of the appointment
	 * @param a    Appointment to copy
	 */
	public Appointment(Appointment a) 
	{
		start = a.getStart();
		duration = a.getDuration();
		end = a.getEnd();
		description = a.getDescription();
		location = a.getLocation();
		id = a.getID();
		hidden = a.getHidden();
		
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	/**
	 * Will get ID
	 * @return    ID as integer
	 */
	public int getID() 
	{
		return id;
	}
	
	/**
	 * Will set the value of ID, to parameter ID
	 * @param id    New ID as integer
	 */
	public void setID(int id) 
	{
		this.id = id;
	}
	
	
	private void endSlotCal(Date start)
	
	{
		long min = start.getTime();
		end.setTime(min + (duration * 60 * 1000));
		System.out.println(end);
	}
	
	public Date getStart()
	{
		return start;
	}
	
	public Date getEnd()
	{
		return end;
	}
	
	
	public String toString() 
	{
	    String str = "";
	    str = str + form.format(start) + " for " + duration + " minutes";
	    return str;
	}
	
	
	
	
	
	

}
