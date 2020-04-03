import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Test exe = new Test();
		
		exe.run();

	}
	
	public void run()
	{
		Diary diary01 = new Diary("Dr Hamlin");
		
		String datePrompt = "Please enter the start date in the form \"dd/MM/yyyy HH:mm\" ";
		String durationPrompt = "How long will will this appointment last, in minutes";
		String locationPrompt = "Where is this appointment";
		String descriptionPrompt = "What is the purpose of this appointment";
		
		
		//adding appointment via userPrompts
		diary01.addAppointment(getString(datePrompt), getInt(durationPrompt), getString(descriptionPrompt), getString(locationPrompt));
		
		//hardcoding 
		diary01.addAppointment("16/03/2021 15:00", 10, "sore knee", "mklnds");
		diary01.addAppointment("16/03/2023 15:00", 10, "sore knee", "mklnds");
		diary01.addAppointment("16/03/2025 15:00", 10, "sore knee", "mklnds");
		diary01.addAppointment("16/03/2027 15:00", 10, "sore knee", "mklnds");
		
		//testing overlap
		//expected outcome: false, print "clash detected"
		diary01.addAppointment("16/03/2021 14:50", 15, "blood test", "nwells");
		
		//search for appointment
		//expected result: return true
		diary01.searchAppointment("16/03/21 15:00");
		
		//search for appoint with invalid string
		//expected result: return false
		diary01.searchAppointment("wert");
		
		//search by range
		//expected result: should work
		diary01.searchByRange("15/05/2020 00:00", "15/05/2024 00:00");
		
		//mix up order of dates
		//expected result: return error message
		diary01.searchByRange("15/05/2024 00:00", "15/05/2020 00:00");
		
		//displaying booked appointments
		diary01.printApp();
		
		//expected result: should work
		diary01.editLocation("16/03/2021 15:00", "new town");
		
		diary01.printApp();
	}
	
	public int getInt(String userPrompt)
	{
		Scanner s = new Scanner(System.in);
	
		System.out.print(userPrompt);
		while (!s.hasNextInt())
		{
			s.next();
			System.out.print("Please enter a number");
		}
		
		int num = s.nextInt();
	
		return num;
	}
	
	public String getString(String userPrompt)
	{
		Scanner myObj = new Scanner(System.in);
		System.out.println(userPrompt);
		
		String userInput = myObj.nextLine(); 
		
		return userInput;
	}
	

}
