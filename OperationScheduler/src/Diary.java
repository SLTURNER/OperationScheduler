import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	int aCount = 0;
	
	/**
	 * Constructor
	 * @param name - of staff
	 */
	public Diary(String name)
	{
		this.staffName = name;
	}
	
	public ArrayList<Appointment> getAppointment()
	{
		return appointment;
	}
	
	
	/**
	 *  Will, given the time and duration, check if the slot is taken, if not, will add Appointment to ArrayList
	 * @param date - start date of appointment
	 * @param duration - duration of appointment in minutes
	 * @param description - of appointment
	 * @param location - of appointment
	 * @return true if added, false otherwise
	 */
	public Appointment addAppointment(String date, int duration, String description, String location, boolean hidden)
	{
		//initialise start date and current date
		Date startDate = new Date();
		Date current = new Date();
		
		//construct new appointment
		Appointment holder = new Appointment(date, duration, description, location, hidden);
		
		//parse string into Date data type
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
				holder.setID(incrementCount());
				appointment = new ArrayList<Appointment>();
				appointment.add(holder);
				
				return holder;
			}
			//adds appointment to existing arraylist provided that there are no overlaps with existing appointments and the first two if statements are false
			else if(appointment != null && overlapCheck(date,duration) == false && duration >=5 && startDate.after(current))
			{	
				holder.setID(incrementCount());
				appointment.add(holder);
				return holder;
			}
			else
			{
				return null;
			}
		}
		//returns false if the appointment cannot be booked due to invalid input
		else
		{
			System.out.println("Date input invalid");
			return null;
		}
	}
	
	/**
	 * Will add Appointment node to Diary, and check if added successfully
	 * @param temp    New node
	 * @return    New node
	 */
	public Appointment addAppointment(Appointment temp) 
	{
		appointment.add(temp);
		
		int id = temp.getID();
		return searchAppointment(id);

	}
	
	/**
	 * Returns current value and increments count by 1
	 * @return    Current count value
	 */
	public int incrementCount() {
		int count = aCount;
		aCount = count +1;
		return count;
	}
	
	
	/**
	 * 
	 * @param date - start date of appointment
	 * @return parsed date
	 */
	public Date dateParser(String date)
	{
		Date parsedDate = new Date();
		
		//declaring date format to be used
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm");
		
		//handling exceptions
		try {
		      parsedDate = form.parse(date);
		    } 
			catch (ParseException e) 
			{
		      System.out.println();  
		    }
		
		//returning date once parsed
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
		
		//return end date once calculated
		return end;
	}
	
	
	/**
	 * Prints the booked tasks
	 */
	public void printTaskList()
	{
		//check to see if there are appointments to be printed
		if(appointment !=null)
		{
			//sorting appointments by date
			sortAppointments();
			
			System.out.println("\tThe following tasks have been recorded for " + staffName );
				
				//looping over all existing appointments
				for (int i = 0;i<appointment.size();i++)
				{	
					Appointment current = appointment.get(i);
					System.out.println("ID:" + current.getID() + " Start: " + current.getStart() + " for " +  current.getDuration() + " minutes \n At " + current.getLocation());
					System.out.println("________________________________________________________________________________");
				}
		}
		//if no appointments have been booked
		else
		{
			System.out.println("No Appointments have been booked for " + staffName);
		}
	}
	
	/**
	 * Prints booked appointments
	 */
	public void printApp()
	{
		//check to see if there are appointments to be printed
		if(appointment !=null)
		{
			//sorting appointments by date
			sortAppointments();
			
			System.out.println("\tThe following tasks have been recorded for " + staffName );
				
				//looping over all existing appointments
				for (int i = 0;i<appointment.size();i++)
				{	
					Appointment current = appointment.get(i);
					if(current.getHidden() == false) 
					{
						System.out.println("Start: " + current.getStart() + " for " +  current.getDuration() + " minutes /n At " + current.getLocation());
						System.out.println("________________________________________________________________________________");
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
				//ensuring all existing dates in the arraylist are checked against
				primeStart = appointment.get(i).getStart();
				primeEnd = appointment.get(i).getEnd();
			
				if(startDate.before(primeEnd) && primeStart.before(endDate))
				{
					//counter increases if there are any clashes
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
		//if no clashes have occurred
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
	public Appointment deleteAppointment(String date) 
	{
	    Date date01 = new Date();
	    date01= dateParser(date);
	    
	    return deleteAppointment(date01);
	    
	   
	}
	
	/**
	 * Deletes appointment from date start
	 * @param date01    Start of appointment
	 * @return    Deleted appointment
	 */
	public Appointment deleteAppointment(Date date01) 
	{
		 //preventing null pointer exceptions
	    if(appointment !=null)
	    {
		    for (int i = 0; i < appointment.size(); i++) 
		    {
		    	/*if the start date searched matches one in the arraylist we can assume its the appointment
		    	the user wants deleted as only one appointment can start at any given time*/
		    	if (date01.equals(appointment.get(i).getStart())) 
		    	{
		    		//removal of the appointment found
		    		Appointment removed = appointment.remove(i);
		    		return removed;
		    	}
		    }
	    }
	    else
	    {
	    	return null;
	    }
		return null;
	}
	
	
	/**
	 * 
	 * @param date - start date of appointment
	 * @param newDescription - description to replace of old one
	 * @return true if description has been edited successfully
	 */
	public Appointment editDescription(String date, String newDescription) 
	{
	    Date date01 = new Date();
	    date01= dateParser(date);
	    
	    if(appointment !=null)
	    {	//going through all the appointments
		    for (int i = 0; i < appointment.size(); i++) 
		    {
		    	/*if the start date searched matches one in the arraylist we can assume its the appointment
		    	the user wants as only one appointment can start at any given time*/
		    	if (date01.equals(appointment.get(i).getStart())) 
		    	{	
		    		Appointment toEdit = appointment.get(i);
		    		Appointment toReturn = new Appointment(toEdit);
		    		toEdit.setDescription(newDescription);;
		    		return toReturn;
		    	}
		    }
	    }
	    else
	    {
	    	return null;
	    }
		return null;
    }
	
	
	/**
	 * 
	 * @param date - start date of appointment to be edited
	 * @param newLocation - new location to be input
	 * @return true if edit is successful, false otherwise
	 */
	public Appointment editLocation(String date, String newLocation) 
	{
	    Date date01 = new Date();
	    date01= dateParser(date);
	    
	    for (int i = 0; i < appointment.size(); i++) 
	    {
	     
	    	if (date01.equals(appointment.get(i).getStart())) 
	    	{	
	    		Appointment toEdit = appointment.get(i);
	    		Appointment toReturn = new Appointment(toEdit);
	    		toEdit.setLocation(newLocation);
	    		return toReturn;
	    	}
	    }
	    	return null;
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
	    
	    //preventing null pointer exception
	    if(appointment != null)
	    {
	    	//going through all existing appointments
			for (int i = 0;i<appointment.size();i++)
			{
				/*if the start date searched matches one in the arraylist we can assume its the appointment
		    	the user wants as only one appointment can start at any given time*/
				if(appointment.get(i).getStart().equals(date01))
				{
					System.out.println("\tFound:");
					System.out.println("--" + appointment.get(i).getStart() + " for " +  appointment.get(i).getDuration() + " minutes at " + appointment.get(i).getLocation() + "--");
				
					//counter increases if appointment is found
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
	 * Searches through appoointments by ID
	 * @param id    Search parameter
	 * @return    Found Appointment
	 */
	public Appointment searchAppointment(int id) 
	{
		//preventing null pointer exception
	    if(appointment != null)
	    {
	    	//going through all existing appointments
			for (int i = 0;i<appointment.size();i++)
			{
				/*if the start date searched matches one in the arraylist we can assume its the appointment
		    	the user wants as only one appointment can start at any given time*/
				if(appointment.get(i).getID() == id)
				{
					return appointment.get(i);
				}
			}
	    }
		
		return null;
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
	    
	    //preventing null pointer exception and checking that the dates are entered in the correct ordered
	    if(appointment !=null && dateEnd.after(dateStart))
	    {
	    	System.out.println();
	    	System.out.println("Appointments from " + start + " to " + end);
	    	
	    	//going through all appointments
	    	for (int i = 0;i<appointment.size();i++)
			{
	    		//printing appointments only in specified range
				if(appointment.get(i).getStart().after(dateStart) && appointment.get(i).getEnd().before(dateEnd))
				{
					System.out.println("--" + appointment.get(i).getStart() + " for " +  appointment.get(i).getDuration() + " minutes at " + appointment.get(i).getLocation() + "--");
				}
			}
	    }
	    
	    //if there are no appointments
	    if(appointment == null )
	    {
	    	System.out.println("There are no appointments to look through");
	    }
	    //if the dates are incorrect order
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
			//do nothing if appointment is null
			//preventing null pointer exceptions
		}
		else
		{
			//sort the arraylist by date
			Collections.sort(appointment,byDate);
		}
	}
	
	
	/**
	 * Checks if the string entered can be parsed
	 * @param date - string of date to be parsed
	 * @return true if the date can be parsed, false otherwise
	 */
	public boolean isValidDate(String date) 
	{
		//defining format of date to be entered
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //dateFormat.setLenient(true);
        
        //handling invalid input exceptions
        try 
        {
        	//parsing string date and trimming string date, i.e. getting rid of surrounding spaces
            dateFormat.parse(date.trim());
        } 
        catch (ParseException e) 
        {
        	//if string date cannot be parsed
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