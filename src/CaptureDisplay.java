import javax.swing.JOptionPane;


import java.util.Arrays;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**CLASS THAT WILL BE IN CHARGE OF CAPTURING AND DISPLAYING PROJECT DATA AND ALL ASSOCIATED DETAILS
 * @author Mthobisi Mahlangu
 * @version 1.1.4
 * @since 1.1.0
 * @param MainMethod 
 * @throws Errors dealt with internal
 */
public class CaptureDisplay {
	
	//============== MAIN METHOD HANDLING PROJECT ==============================================
	public static void main(String[] args) 
	
	{
		int userChoice = 0;
		while (userChoice != 13)   // WHILE LOOP will only be exited when user selects exit(13)
		{
		   /*Displays selection menu to user		
			*User must enter their choice, depending on what they want to do
			*Validates entered selection
			*/	
			
					System.out.println("\nWhat would you like to do? Choose from menu and enter correct number");
					System.out.println("1-add project.\n2-change due date."
							+ "\n3-update paid amount\n4-update contractor details"
							+ "\n5-finalise project\n6-View incomplete projects"
							+ "\n7-View complete projects\n8-view overdue "
							+ "projects\n9-view all invoices \n10-View all \n11-Delete project "
							+ "\n12-view desired project\n13-Exit\n");
					
					PoiseToDatabase sqlMethods = new PoiseToDatabase();
					userChoice = sqlMethods.validateInt("Enter your choice: ");
					sqlMethods.mySQLMethods(userChoice);		
				
		}
		
	}
}
			

