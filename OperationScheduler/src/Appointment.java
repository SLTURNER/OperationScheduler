import java.util.Date;
import java.text.SimpleDateFormat;



public class Appointment {
	
	private Date start;
	private int duration;
	private Date end;
	private String description;
	private String location;
	private int ID;
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
	
	
	public String toString() {
	    String str = "";
	    str = str + form.format(start) + " for " + duration + " minutes";
	    return str;
	  }
	
	
	
	
	
	

}
