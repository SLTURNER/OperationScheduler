import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *@author Mohsin Raza 
 *@version 03/04/20
 *Class to hold and perform operations on appointments
 */
public class Diary {
	private String staffName;
	ArrayList<Appointment> appointment;
	
	/**
	 * Constructor
	 * @param name - of staff
	 */
	public Diary(String name)
	{
		this.staffName = name;
		
		
	}
	/**
	 * 
	 * @param date - start date of appointment
	 * @param duration - duration of appointment in minutes
	 * @param description - of appointment
	 * @param location - of appointment
	 * @return true if added, false otherwise
	 */
	public boolean addAppointment(String date, int duration, String description, String location)
	{
		
		Date startDate = new Date();
		
		Date current = new Date();
		
		Appointment holder = new Appointment(date, duration, description, location);
		
		startDate = dateParser(date);
		
		//checking if string of date input is valid
		if(isValidDate(date))
		{
			//only allows duration of 5 or more
			if(duration <5)
			{
				System.out.println("Please enter a duration of at least 5 minutes");
			}
			
			//only allows future dates
			if(startDate.before(current))
			{
				System.out.println("Please enter a date in the future");
			}
			
			//Creates a new appointment arraylist if none have already been booked provided that the first two if statements are false
			if(appointment == null && duration >=5 && startDate.after(current))
			{
				appointment = new ArrayList<Appointment>();
				appointment.add(holder);
				
				return true;
			}
			//adds appointment to existing arraylist provided that there are no overlaps with existing appointments and the first two if statements are false
			else if(appointment != null && overlapCheck(date,duration) == false && duration >=5 && startDate.after(current))
			{
				appointment.add(holder);
				return true;
			}
			else
			{
				return false;
			}
		}
		//returns false if the appointment cannot be booked due to invalid input
		else
		{
			System.out.println("Date input invalid");
			return false;
		}
	}
	
	/**
	 * 
	 * @param date - start date of appointment
	 * @return parsed date
	 */
	public Date dateParser(String date)
	{
		Date parsedDate = new Date();
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm");
		
		
		try {
		      parsedDate = form.parse(date);
		    } 
			catch (ParseException e) 
			{
		      System.out.println();  
		    }
		
		return parsedDate;
	}
	
	/**
	 * 
	 * @param start - start date of appointment
	 * @param duration - duration of appointment in minutes
	 * @return end date which has been calculated
	 */
	public Date calculateEnd(Date start, int duration)
	{
		Date end = new Date();
		long min = start.getTime();
		
		//calculation to get end time
		end.setTime(min + (duration * 60 * 1000));
		
		return end;
	}
	
	/**
	 * Prints the booked appointments
	 */
	public void printApp()
	{
		//check to see if there are appointments to be printed
		if(appointment !=null)
		{
			sortAppointments();
			
			System.out.println("\tThe following appointments have been booked for " + staffName );
		
				for (int i = 0;i<appointment.size();i++)
				{
					if(!"".equals(appointment.get(i).getLocation()) && !"".equals(appointment.get(i).getDescription()))
					{
						System.out.println("--" + appointment.get(i).getStart() + " for " +  appointment.get(i).getDuration() + " minutes at " + appointment.get(i).getLocation() + "--");
					}
				}
		}
		//if no appointments have been booked
		else
		{
			System.out.println("No Appointments have been booked for " + staffName);
		}
	}
	
	/**
	 * 
	 * @param start - start date of appointment
	 * @param duration - duration of appointment in minutes
	 * @return true if there is an overlap, false otherwise
	 */
	public boolean overlapCheck(String start, int duration)
	{
		//new dates
		Date startDate = new Date();
		startDate = dateParser(start);
		
		//dates to be compared against
		Date primeStart = new Date();
		Date primeEnd = new Date();
		
		//counting clashes
		int counter = 0;
		
		Date endDate = new Date();
		endDate = calculateEnd(startDate, duration);
		
		//checking if there are appointments that may be overlapped
		if(appointment !=null)
		{
			for(int i = 0;i<appointment.size();i++)
			{
				primeStart = appointment.get(i).getStart();
				primeEnd = appointment.get(i).getEnd();
			
				if(startDate.before(primeEnd) && primeStart.before(endDate))
				{
					counter++;
				}
			}
		}
		
		//if any clashes has occurred
		if (counter >0)
		{
			System.out.println("Clash detected. Appointment not booked.");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 
	 * @param start date of appointment
	 * @return true if the appointment has been deleted, false otherwise
	 */
	public boolean deleteAppointment(String date) 
	{
	    Date date01 = new Date();
	    date01= dateParser(date);
	    
	    //preventing null pointer exceptions
	    if(appointment !=null)
	    {
		    for (int i = 0; i < appointment.size(); i++) 
		    {
		      
		    	if (date01.equals(appointment.get(i).getStart())) 
		    	{
		    		appointment.remove(i);
		    		return true;
		    	}
		    }
	    }
	    else
	    {
	    	return false;
	    }
		return false;
	}
	
	/**
	 * 
	 * @param date - start date of appointment
	 * @param newDescription - description to replace of old one
	 * @return true if description has been edited successfully
	 */
	public boolean editDescription(String date, String newDescription) 
	{
	    Date date01 = new Date();
	    date01= dateParser(date);
	    
	    if(appointment !=null)
	    {	//going through all the appointments
		    for (int i = 0; i < appointment.size(); i++) 
		    {
		      
		    	if (date01.equals(appointment.get(i).getStart())) 
		    	{
		    		appointment.get(i).setDescription(newDescription);;
		    		return true;
		    	}
		    }
	    }
	    else
	    {
	    	return false;
	    }
		return false;
    }
	
	/**
	 * 
	 * @param date - start date of appointment to be edited
	 * @param newLocation - new location to be input
	 * @return true if edit is successful, false otherwise
	 */
	public boolean editLocation(String date, String newLocation) 
	{
	    Date date01 = new Date();
	    date01= dateParser(date);
	    
	    for (int i = 0; i < appointment.size(); i++) 
	    {
	     
	    	if (date01.equals(appointment.get(i).getStart())) 
	    	{
	    		appointment.get(i).setLocation(newLocation);
	    		return true;
	    	}
	    }
	    	return false;
	}
	
	/**
	 * 
	 * @param date - start date of appointment to be searched
	 * @return
	 */
	public boolean searchAppointment(String date)
	{
		Date date01 = new Date();
	    date01 = dateParser(date);
	    int found = 0;
	    
	    
	    if(appointment != null)
	    {
			for (int i = 0;i<appointment.size();i++)
			{
				if(appointment.get(i).getStart().equals(date01))
				{
					System.out.println("\tFound:");
				System.out.println("--" + appointment.get(i).getStart() + " for " +  appointment.get(i).getDuration() + " minutes at " + appointment.get(i).getLocation() + "--");
				found++;
				}
				
			}
	    }
		
		if(found == 1)
		{
			return true;
		}
		else
		{
			System.out.println("No appointment has been booked at that date and time");
			return false;
		}
	}
	
	/**
	 * 
	 * @param start - start date to search from
	 * @param end - end date to search to
	 */
	public void searchByRange(String start, String end)
	{
		Date dateStart = new Date();
		Date dateEnd = new Date();
	    
	    dateStart = dateParser(start);
	    dateEnd = dateParser(end);
	    
	    if(appointment !=null && dateEnd.after(dateStart))
	    {
	    	System.out.println();
	    	System.out.println("Appointments from " + start + " to " + end);
	    	for (int i = 0;i<appointment.size();i++)
			{
				if(appointment.get(i).getStart().after(dateStart) && appointment.get(i).getEnd().before(dateEnd))
				{
					System.out.println("--" + appointment.get(i).getStart() + " for " +  appointment.get(i).getDuration() + " minutes at " + appointment.get(i).getLocation() + "--");
					
				}
			}
	    }
	    
	    if(appointment == null )
	    {
	    	System.out.println("There are no appointments to look through");
	    }
	    else if(dateStart.after(dateEnd))
	    {
	    	System.out.println("The start date must come before the end date");
	    }
	}
	
	/**
	 * Wrapper method to sort appointments by date
	 */
	public void sortAppointments()
	{
		if(appointment == null)
		{
			
		}
		else
		{
			Collections.sort(appointment,byDate);
		}
	}
	
	/**
	 * Checks if the string entered can be parsed
	 * @param date - string of date to be parsed
	 * @return true if the date can be parsed, false otherwise
	 */
	public boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.setLenient(true);
        
        try {
            dateFormat.parse(date.trim());
        } 
        catch (ParseException e) 
        {
            return false;
        }
        return true;
    }
	
	/**
	 * Comparator to aid in sorting arraylist of appointments by date
	 * 	Sourced from stack overflow
	 */
	static final Comparator<Appointment> byDate = new Comparator<Appointment>() 
	{
	   /**
	    * method to sort arraylist of appointments
	    */
	    public int compare(Appointment order01, Appointment order02) {
	        
	    	Date date01 = new Date();
	        Date date02 = new Date();
	        
            date01 = order01.getStart();
            date02 = order02.getStart();
	        	        
	       return (date01.getTime() > date02.getTime() ? 1 : -1);     
	    }
    };
	
}
