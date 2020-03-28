
public class Mohsin {

	public static void main(String[] args) {
		
		Mohsin test01 = new Mohsin();
		
		test01.test();
		
		
	}
	
	public void test()
	{
		//create diary for staff member
		Diary diary01 = new Diary("Staff_Name");
		
		//create an appointment slot 
		Appointment slot1 = new Appointment("28/03/20 12:00:00", 30);
	    boolean flag = diary01.initAppointment(slot1);
	    
	    //checking that the slot isn't already taken up
	    //expected outcome: available 
	    if(flag)
	        System.out.println(slot1 + " available");
	      else
	        System.out.println(slot1 + " is taken");
	    
	    //book an appointment for heart surgery
	    flag = diary01.bookAppointment("Heart Surgery", "Monklands", "28/03/20 12:00:00", 30);
	    
	    //checking if the appointment can be booked
	    //expected outcome: appointment booked
	    if(flag)
	      System.out.println("Appointment successfully booked");
	    else
	      System.out.println("Appointment cannot be booked");
		
		
		
		
	    
	    
	    diary01.displayBookedAppointments();
	}
}
