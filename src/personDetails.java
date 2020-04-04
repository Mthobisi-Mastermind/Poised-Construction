
import javax.swing.JOptionPane;
import java.util.*;

// THIS CLASS WILL DEAL WITH ADDING OR MODIFYING THE ARCHITECT AND/OR CONTRACTOR


public class personDetails 
{
	//--------------------ATTRIBUTES--------------------
	String personTask;
	String name;
	int telNum;
	String emailAddress;
	String homeAddress;
	
	//----------------------METHODS-------------------
	//         CONSTRUCTOR
	public personDetails()
	{
				
	}
	
	// METHOD 1: Gets details of the person to be added
	public String personalInfo()
	{
		String personData = ""; // ALL OBTAINED PERSON DETAILS WILL STORED IN THIS VARIABLE
		
		// Gets the name of the person
		System.out.print("Please enter name of the person\n:");
		Scanner Name = new Scanner(System.in);
		String name = Name.nextLine();		
		personData += "\tName:" + name + "\n";
		
		// Obtains telephone number 
		System.out.print("Enter telephone number\n:");
		Scanner telnum = new Scanner(System.in);
		String telNum = telnum.nextLine();		
		personData += "\ttel number:" + telNum +"\n";
		
		// Obtains email address of the person
		System.out.print("What is " + name + "'s email address?\n:");
		Scanner eAddress = new Scanner(System.in);
		String emailAddress = eAddress.nextLine();		
		personData += "\tEmail address: " + emailAddress + "\n";
		
		// Obtains home address of the person
		System.out.print("What is their home address?\n:");
		Scanner hAddress = new Scanner(System.in);
		String homeAddress = hAddress.nextLine();		

		personData += "\tPhysical address: " + homeAddress + "\n";
		
		return personData;
	}

}
