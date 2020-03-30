
public class Mohsin {

	public static void main(String[] args) {
		
		Mohsin test01 = new Mohsin();
		
		test01.test();
		
		//book appointment *2
		// check overlap
		//print appointments
		//delte appointment 
		//cancel appointment
		//print appointments
	}
	
	public void test()
	{
		
		Diary diary01 = new Diary("staffName");
		
		//creating a slot for the appointments
	    Appointment slot1 = new Appointment("28/08/15 09:00:00", 30);
	    boolean flag = diary01.initAppointment(slot1);
	    
	    //expected outcome: successful
	    if(flag)
	    {
	      System.out.println(slot1 + " successfully created");
	    }
	    else
	    {
	      System.out.println(slot1 + " already exists");
	    }
	    
	    Appointment slot2 = new Appointment("28/08/15 09:15:00", 45);
	    flag = diary01.initAppointment(slot2);

	    //Expected output: unsuccessful
	    if(flag)
	      System.out.println(slot2 + "successfully created");
	    else
	      System.out.println(slot2 + " cannot be created");
	    
	    Appointment slot3 = new Appointment("28/08/15 17:00:00", 25);
	    flag = diary01.initAppointment(slot3);

	    //Expected output: successful
	    if(flag)
	      System.out.println(slot3 + " successfully created");
	    else
	      System.out.println(slot3 + " cannot be created");
	    
	    
	    //booking appointment in pre-made slot
	    flag = diary01.bookAppointment("sore head", "monklands", "28/08/15 09:00:00", 30);

	    // Expected output: successful
	    if(flag)
	      System.out.println("Appointment successfully booked");
	    else
	      System.out.println("Appointment cannot be booked");
	    
	  //booking appointment in pre-made slot
	    flag = diary01.bookAppointment("itchy scalp", "royal infirmary", "28/08/15 17:00:00", 25);

	    // Expected output: successful
	    if(flag)
	      System.out.println("Appointment successfully booked");
	    else
	      System.out.println("Appointment cannot be booked");
	    
	    		System.out.println();
	    
		//displays the booked appointments 
	    diary01.displayBookedAppointments();
	    
	    System.out.println();
	    
	    //deleting an appointment using its start time
	    flag = diary01.deleteAppointment("28/08/15 17:00:00");
	    
	    //redisplaying appointments
	    diary01.displayBookedAppointments();
	}
}
