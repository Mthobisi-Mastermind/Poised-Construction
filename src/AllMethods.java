import javax.swing.JOptionPane;


import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**CLASS CONSIST OF MOST OF THE METHODS USED IN PROJECTS.
 * @author Mthobisi Mahlangu
 * @version 1.1.4
 * @since 1.1.0
 * @param Constructor AllMethods 
 * @throws Errors dealt with internal
 */

public class AllMethods {
	
	int projectNumber;
	String projectName;
	String buildingType;
	String projectAddress;
	int ERF_Number;
	float projectFee;
	float amaountPaid;
	String projectDeadline;
	
	
	//-------- METHODS---------
	
	// CONSTRUCTOR
	public AllMethods()
	{
		
	}
	
	// ===================== METHOD 1: Getting the project number =====================================
	public int getProjectNumber()
	{
		int projectNum = 0;
		
		//Exits once entered number is found valid
		while(true) {
		
			try {
				System.out.print("Enter project number\n-");
				Scanner projNum = new Scanner(System.in);
				int projectNumber = projNum.nextInt();		
				projectNum += projectNumber;
				break;
			}
			
			catch(Exception e) {
				System.out.println("\nError! Enter project number in digits only\n");
			}
		}
		
		return projectNum;	
	}		
	
	// =========================== METHOD 2: Getting project name =======================================
	public String getProjectName()
	{
		String projectName = "";
		while(true) {
			System.out.print("Enter project name\n-");
			Scanner projName = new Scanner(System.in);
			String enteredProjectName = projName.nextLine();	
			String validProjectName = enteredProjectName.trim();
			
			if(!validProjectName.equals("")) {
				projectName += validProjectName;
				break;
			}
			else
				System.out.println("\nNo entered project name. Try again\n");
			}
		String ProjectName = projectName ;
		
		return ProjectName;
	}
	
	// =========================== METHOD 3: Getting type of building =====================================
	public String getBuildingType()
	{
		String projectType = "";
		
		while(true) {
			
			System.out.print("Enter building type. Eg Townhouses, House and etc\n-");
			Scanner type = new Scanner(System.in);
			String buildingType = type.nextLine();		
			String validType = buildingType.trim();
			
			if (!validType.equals("")) {
				projectType += validType;
				break;
			}
			else	
				System.out.println("Nothing entered. Please try again");
		}
		
		String projectTypeValid = projectType;
		
		return projectTypeValid;
		
		
	}
	
	// ========================== METHOD 4: Getting physical address of the project ===============================
	public String getProjectAddress()
	{
		String homeAddress = "";
		
		while(true) {	
			System.out.print("Enter project address\n-");
			Scanner hAddress = new Scanner(System.in);
			String projectAddress = hAddress.nextLine();	
			String validProjectAddress = projectAddress.trim();
			
			if (!validProjectAddress.equals("")) {
				homeAddress += validProjectAddress;
				break;
			}
			else
				System.out.println("\nEntered address not valid. Try again\n");
		}
			
		String checkedAddress = homeAddress;
		
		return checkedAddress;		
	}
	
	// =========================== METHOD 5: Getting ERF Number of the project stand ================================
	public static int getERFnumber()
	{
		
		int ERF_Number = 0;
		
		while(true) {
		try
			{
				System.out.print("Enter project ERF Number\n-");
				Scanner erfNumber = new Scanner(System.in);
				ERF_Number += erfNumber.nextInt();	
				break;
			}
		

		catch(Exception e) {
				System.out.println("No entered erf number. Try again");
		}
					
		}
		return ERF_Number;
	}
	
	// ========================== METHOD 6: Getting total amount charged for the project ============================
	public  double getProjectFee()
	{
		double projectAmount = 0;
		
		// Will throw an error till user enters amount in digits
		while(true) {
			try {
				System.out.print("How much is the project fee?\nR");
				Scanner projFee = new Scanner(System.in);
				double projectFee = projFee.nextDouble();
				projectAmount += projectFee;
				break;
			}
			catch(Exception e) {
				System.out.println("\nERROR! Enter amount in digits only. Use \".\" where necessary");
			}
		}
		
		return projectAmount;
		
	}
		
	// ======================= METHOD 7: Gets the up to date amount paid by client for the project ===================
	public double getAmountPaid()
	{
		double paidAmount = 0;
			
		//Will throw an error till user enters amount in digits
		while(true) {			
			try {		
				Scanner PaidAmount = new Scanner (System.in);
				System.out.print("\nEnter project amount that has been paid up to date\nR");		
				Double strAmountPaid = PaidAmount.nextDouble();
				
				paidAmount += strAmountPaid;
				
				break;
			}
			catch(Exception e) {
				System.out.println("\nERROR! Enter amount in digits. Use \".\" where necessary");
			}
		}
		return paidAmount;
	}
	
	// ============================ METHOD 8 GETS TODAY'S DATE ============================================
	public String currentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String todayDate = formatter.format(date);
		Date dateToday = null;
		try {
			dateToday = formatter.parse(todayDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return todayDate;
	}

	// ============================ METHOD 9: Creates a deadline date for the project =============================
	public String getDeadline()
	{
		String chosenDate = "";
		
		//Validates date by checking its format and characters
		while(true) {
			
			System.out.print("\nEnter project due date\n(yyyy-mm-dd):");
			Scanner dDate = new Scanner(System.in);
			String projectDeadline = dDate.nextLine();
			String deadline = projectDeadline.replaceAll("(?m)^[ \\t]*\\r?\\n","");
					
			String [] validateArray = deadline.split("");
			try {
				Integer.parseInt(validateArray[0]);
				Integer.parseInt(validateArray[1]);
				Integer.parseInt(validateArray[2]);
				Integer.parseInt(validateArray[3]);
				Integer.parseInt(validateArray[5]);
				Integer.parseInt(validateArray[6]);
				Integer.parseInt(validateArray[8]);
				Integer.parseInt(validateArray[9]);
				
				if(validateArray[4].equals("-") && validateArray[7].equals("-") && validateArray.length == 10) {
					chosenDate += deadline;
					break;
				}
					
				else {
					System.out.println("NOTE!! Failed to validate entered date");
				}
			}
			catch(Exception e) {
				System.out.println("\nNOTE!!Failed to validate entered date");
			}
		}
		
		String dueDate = chosenDate;
		return dueDate;
	}
	
	//------------------------ METHOD 10: VALIDATES ANY GIVEN STRING FOR MYSQL COMMANDS ---------------------------
	
	 public static String validateStr(String messageUser) {
		 	
		 String validString = "";
		 while(true) {			
			 Scanner enteredString = new Scanner (System.in);
			 System.out.print(messageUser);
			 String validTitle = enteredString.nextLine();
			 String checkChar = validTitle.trim();
			
			 if (!checkChar.equals("")) {
				 
				 String [] stringArray = checkChar.split("");	
				 ArrayList<String> tempArray = new ArrayList<String>();
				 
				 //To manipulate strings with single quotes so to be valid for mySQL commands
				for(int scanArray = 0; scanArray<stringArray.length; scanArray++) {
					
					if(stringArray[scanArray].equals("\'")) {
						tempArray.add("\\'");
					}
					else {
						tempArray.add(stringArray[scanArray]);
					}
				}
				
				//Adds corrected characters of the manipulated string to new string variable
				String correctedString = "";
				for(int scanTempArray = 0; scanTempArray < tempArray.size(); scanTempArray++) {
					correctedString += tempArray.get(scanTempArray);
				}
				
				validString += correctedString;
				break;
			 }
			 else
				System.out.println("\nNothing was entered. Please try again");	
			}
		 
		 return validString;
	 }
}
