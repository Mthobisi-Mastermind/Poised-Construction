
import javax.swing.JOptionPane;
import java.util.*;

/** THIS CLASS WILL DEAL WITH ADDING OR MODIFYING PERSON DETAILS
 * @author Mthobisi Mahlangu
 * @version 1.1.4
 * @since 1.1.0
 * @param Constructor PersonDetails 
 * @throws Errors dealt with internal
 */

public class PersonDetails 
{
	//--------------------ATTRIBUTES--------------------
	String personTask;
	String name;
	int telNum;
	String emailAddress;
	String homeAddress;
	
	//----------------------METHODS-------------------
	//         CONSTRUCTOR
	public PersonDetails()
	{
				
	}
	
	// ===============METHOD 1: Gets details of the Associate to be added ======================================
	public String personalInfo()
	{
		String personData = ""; // ALL OBTAINED PERSON DETAILS WILL BE STORED IN THIS VARIABLE		
		Scanner userInput = new Scanner(System.in);
		
		//~~~~~~~~~~~~~~~~~~~ PROMPT 1 FOR PERSON DETAILS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		String validFName = "";
		while(true)	{
		
			// Gets the name of the person	
			System.out.print("Please enter first name of the person\n-");	
			String name = userInput.nextLine();		
			String userName = name.trim();
			if (!userName.equals("")) {
				validFName += userName;
				break;
			}
			else
				System.out.println("\nNo name entered. Try again!\n");
		}
	
		personData += "\'" + validFName + "\', "; 
		
		//~~~~~~~~~~~~~~~~~~~ PROMPT 1.1 FOR PERSON DETAILS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		String validLName = "";
		while(true)	{
		
			// Gets the name of the person	
			System.out.print("Please enter last name of the person\n-");	
			String name = userInput.nextLine();		
			String userName = name.trim();
			if (!userName.equals("")) {
				validLName += userName;
				break;
			}
			else
				System.out.println("\nNo name entered. Try again!\n");
		}
	
		personData += "\'" + validLName + "\', "; 

		//~~~~~~~~~~~~~~~~ PROMPT 2 FOR PERSON DETAILS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Obtains telephone number and validates it	
		personData = validatesTelNum(personData, userInput);
				
		//~~~~~~~~~~~~~~~~~~~~ PROMPT 4 FOR PERSON DETAILS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Obtains home address of the person
		String validAddress = "";
		while(true) {
			System.out.print("What is their home address?\n-");
			String homeAddress = userInput.nextLine();	
			String addressEntered = homeAddress.trim();
			
			if (!addressEntered.equals("")) {
				validAddress += addressEntered;
				break;
			}
			else
				System.out.println("\nNo address entered.Try again.\n");
		}
		personData += "\'" + validAddress + "\'";
		
		return personData;
	}
	
	//=================== METHOD: VALIDATES EMAIL ==============================================
	public String validateEmail() {
		String validEmail = "";
		while(true) {
			Scanner userInput = new Scanner (System.in);
			System.out.print("Enter email address?\n-");
			String emailAddress = userInput.nextLine();	
			String emailEntered = emailAddress.trim();
			
			if(!emailEntered.equals("") && emailEntered.contains("@")
					 && !(emailEntered.contains("\'")))
				
			{
				validEmail += emailEntered;
				break;
			}
			else
				System.out.println("\nInvalid email address entered. Try again");
		}
		
		return validEmail;
	}

	//================ METHOD 2: validates telephone number =====================================
	public String validatesTelNum(String personData, Scanner userInput) {
		String telNum = "";
		while(true) {
			
			try {
				System.out.print("Enter telephone number\n-");
				String userIn = userInput.nextLine();
				String[]digitsArray = userIn.split("");
				for(int digitsCheck = 0; digitsCheck<digitsArray.length; digitsCheck++) {
					Integer.parseInt(digitsArray[digitsCheck]);
					telNum += digitsArray[digitsCheck];
				}
				
				if(digitsArray.length == 9)
					break;
				else
					System.out.println("\nError! Enter a 9 digit number only");
					telNum = "";
			}
			catch(Exception e) {
				System.out.println("Error! Enter a 9 digit number only");
				telNum = "";
			}
		}
		personData += "\'" + telNum + "\', ";
		return personData;
	}

}
