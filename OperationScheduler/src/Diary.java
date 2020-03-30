import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;


public class Diary {
	
	private String patientName;
	
	ArrayList<Appointment> appointment;
	int slot;
	
	
	
	public Diary(String name)
	{
		this.patientName = name;
		
		slot = 0;
	}
	
	
	
	public boolean initAppointment(Appointment appointmentSlot)
	{
		int flag = 0;
		
		if(slot == 0)
		{
			appointment = new ArrayList<Appointment>();
			appointment.add(appointmentSlot);
			slot++;
			
			return true;
		}
		else
		{
			for(int i = 0; i<appointment.size(); i++)
			{
				if( (appointment.get(i).getEnd().compareTo(appointmentSlot.getStart())<0))
				{
					flag = 1;
				}
			}
			
			if (flag == 1)
			{
				appointment.add(appointmentSlot);
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean bookAppointment(String description, String location, String date, int duration)
	{
		int flag = 0;
		
		Date date01 = new Date();
		
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		
		try 
		{
			date01 = form.parse(date);
		}
		catch(Exception e)
		{
			System.out.println();
		}
		
		int temp = 0;
		
		for (int i =0; i< appointment.size(); i++)
		{
			if ( date01.equals(appointment.get(i).getStart())) {
		        //System.out.print("true");
		        temp = i;
		        flag = 1;
		      }
		}
		
		if (flag == 1) {
		      if (appointment.get(temp).getDescription().equals("")) {
		        appointment.get(temp).setDescription(description);
		        appointment.get(temp).setLocation(location);
		        return true;
		      } else {
		        return false;
		      }
		    }
		    return false;
	}
	
	
	public boolean deleteAppointment(String date) {
	    Date date01 = new Date();
	    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    try {
	      date01 = form.parse(date);
	    } catch (Exception e) {
	      System.out.println();  
	    }
	    for (int i = 0; i < appointment.size(); i++) {
	      if (date01.equals(appointment.get(i).getStart())) {
	        appointment.get(i).setDescription("");
	        appointment.get(i).setLocation("");
	        return true;
	      }
	    }
	    return false;
	}
	
	
	public void displayBookedAppointments()
	{
				
		for (int i = 0;i<appointment.size();i++)
		{
			if(!"".equals(appointment.get(i).getLocation()) && !"".equals(appointment.get(i).getDescription()))
			{
			System.out.println(appointment.get(i).getStart() + " for " +  appointment.get(i).getDuration() + " minutes at " + appointment.get(i).getLocation());
			}
			}
	}
	
	

}
