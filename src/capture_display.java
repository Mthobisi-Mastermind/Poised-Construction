import javax.swing.JOptionPane;
import java.util.Arrays;


import java.util.*;
import java.util.Scanner;

// THIS IS THE CLASS THAT WILL BE IN CHARGE OF CAPTURING AND DISPLAYING PROJECT DATA AND ALL ASSOCIATED DETAILS

public class capture_display {

	public static void main(String[] args) 
	
	{
		String info = "";       // ALL DETAILS NEEDED FOR A COMPLETE PROJECT WILL BE APPENDED HERE
		boolean exit = false;   // Variable to control while loop
		while (exit == false)   // WHILE LOOP will only be exited when user selects exit(6)
		{
			// Displays menu option to user
			System.out.println("\nWhat would you like to do? Choose from menu and enter correct number");
			System.out.println("1-add project.\n2-change due date.\n3-update paid amount\n4-update contractor details\n5-finalise project");
			System.out.print("6-Exit\n:");
			
			//User must enter their choice, depending on what they want to do
			Scanner choice = new Scanner(System.in);
			int userChoice = choice.nextInt();

			// IF 1A: If user wants to add/create a project
			if (userChoice == 1)
			{	
				// User must enter the number of projects they wish to create
				System.out.print("How many projects do you wish to create or add?\n:");
				Scanner projectsNum = new Scanner(System.in);
				int numberOfProjects = projectsNum.nextInt();
				info += "\n";
			
				// FOR LOOP 1A: Will repeat for the number of projects that the user wishes to create
				for (int i = 0; i <  numberOfProjects; i++)
				{
					
					projectDetails data_in_project = new projectDetails(); // CALLING CLASS WITH PROJECT DETAILS FOR CAPTURING
					
					// The following called methods are from the projectDetails class which was recently called
					info += data_in_project.getProjectNumber();			
			
					info += data_in_project.getProjectName();
					
					info += data_in_project.getBuildingType();
					
					info += data_in_project.getHomeAddress();
										
					info += data_in_project.getERFnumber();
										
					info += data_in_project.getProjectFee();
		
					info += data_in_project.getAmountPaid();
	
					info += data_in_project.getDeadline();
										
			
					// We call the personDetails class and personalInfo method to register Architect details
					System.out.println("\nNOTE:The following prompts are for the Architect");
					info += "\nARCHITECT:\n";
					personDetails data_in_person = new personDetails();
					info += data_in_person.personalInfo();
			
					// Again we call personDetails class and personalInfo method to register Contractor details 
					System.out.println("\nNOTE:The following prompts are for the Contractor.");
					info += "\nCONTRACTOR:\n";
					personDetails person_info = new personDetails();
					info += person_info.personalInfo();
			
					System.out.print("\nProject successfully added\n"); //When project is successful added
			
					System.out.println(info);                           // Display of the project
				}
			}
			
			// ELSE IF 1B: If user wishes to change due date
			else if (userChoice == 2)
			{
				// IF 1B.1: Allows user to change only when there was a previously added project
				if (info != "")
				{
					//Manipulates stored project details so to scan it through using FOR LOOP					
					String date_string = ""; //Project data will now be stored here as the for loop scans 
					
					// Splitted project data will be stored in an array,for easier for loop access
					String [] main_manip = info.replace("\n\n","\n").replace("\n", ":").split(":");
					
					int n = 1; // Odd and Even control
					
					for (int i=0;i<main_manip.length;i++) // Helps us locate "Deadline", so to update DUE DATE at the right place
						
					{
						// This If condition Will store every 2nd element in array to new line in variable date_string
						if (n%2== 0) 
						{
						
							// Condition checks if that element is not "Deadline"
							// If true, user must enter new due date
							if (main_manip[i].equals("Deadline"))
							{
								System.out.println("Enter new due date:");
								Scanner newDdate = new Scanner (System.in);
								String dateEntered = newDdate.nextLine();
								
								// Deadline is appended to new line in date_string variable
								date_string += "\n" +"Deadline: ";
								main_manip[i+1] = dateEntered;    // Replaces the element in the index after deadline, as that is the old due date
								
							}
							
							// When that element is not == deadline, it will still be appended to new line in date string
							else
							{
								date_string += "\n" + main_manip[i] + ": ";
							}
						}
						
						// This is when the being scanned elements's index is odd,
						// Element must go to same line as previously stored even index element
						else
						{
							date_string += " " + main_manip[i];
						}
							
						
					n++; // Increments that will check for odds and evens	
						
					}
					
					// Print out project details with updated due date
					System.out.println("Project due date successful updated!!.\n\n\t\tUPDATED PROJECT DETAILS:\n" + date_string);
					
					
				}		
				else 
				{
					System.out.print("No projects to edit. First add a project\n");
				}
		
			}
			
			//If user wishes to change amount paid
			else if (userChoice == 3)
			{
				//Allows user to change only when there was a previously added project
				if (info != "")
				{
					//Manipulates stored project details so to scan it through using FOR LOOP					
					String date_string = "";
					
					// Splitted project data will be stored in an array,for easier for loop access
					String [] main_manip = info.replace("\n\n","\n").replace("\n", ":").split(":");
					
					int n = 1;// Odd and Even control
					
					for (int i=0;i<main_manip.length;i++) //Helps us locate "Amount paid up to date", so to update amount at the right place
						
					{
						// This condition will add every even indexed element from array to new line in date_string					
						if (n%2== 0) 
						{
							// If element matches condition, the element after the current one is replaced by new entered amount by user
							if (main_manip[i].equals("Amount paid up to date"))
							{
								System.out.println("Enter amount paid up to date:");
								Scanner newAmount = new Scanner (System.in);
								String updatedAmount = newAmount.nextLine();
								
								date_string += "\n" +"Amount paid up to date: R";
								main_manip[i+1] = updatedAmount;							
							}
							
							//If element did not match above condition
							else
							{
								date_string += "\n" + main_manip[i] + ": ";
							}
						}
						
						// Adds element to same line in date_string  as previous even indexed element
						else
						{
							date_string += " " + main_manip[i];
						}
												
					n++;// Increments by 1 for every scan
						
					}
					//Display updated project details
					System.out.println("Amount paid successful updated!!.\n\n\t\tUPDATED PROJECT DETAILS:\n" + date_string);
									
				}
				
				// If no project is found for editing
				else 
				{
					System.out.print("No projects to edit. First add a project\n");
				}
		
			
			}
			
			// When user wants to change contractor
			else if (userChoice == 4)
			{
				// To check if there is a previously added project
				if (info != "")
				{
					// Manipulates data in project details and stores as an array for easier for loop scan
					// Elements will be added to date_string as for loop executes
					String date_string = "";
					String [] main_manip = info.replace("\n\n","\n").replace("\n", ":").split(":");
					int n = 1; // Even and Odd control
					
					for (int i=0;i<main_manip.length;i++) // Helps in find the right location for editng
						
					{
						// Adds even indexed elements new line in date_string
						if (n%2== 0) 
						{
							// 	IF element matches condition,
							//  The method for assigning a new person is called
							// And all old details of "CONTRACTOR" will be replaced by new entries
							if (main_manip[i].equals("CONTRACTOR"))
							{
								personDetails obj = new personDetails();
								String updateDetails = obj.personalInfo();
								
								date_string += "\n" +"CONTRACTOR: ";
								String[] arrayContractor = updateDetails.replace("\n",":").split(":");
								
								// Replacing all old stored contractor details with new details
								main_manip[i+2] = arrayContractor[0];
								main_manip[i+3] = arrayContractor[1];
								main_manip[i+4] = arrayContractor[2];
								main_manip[i+5] = arrayContractor[3];
								main_manip[i+6] = arrayContractor[4];
								main_manip[i+7] = arrayContractor[5];
								main_manip[i+8] = arrayContractor[6];
								
								
							}
							
							// When elements doesn't match "Contractor"
							else
							{
								date_string += "\n" + main_manip[i] + ": ";
							}
						}
						
						// Odd indexed elements will be added to same line in data_string as previous even indexed element
						else
						{
							date_string += " " + main_manip[i];
						}
													
					n++; // Increments by 1	
						
					}
					
					//Displays updated project
					System.out.println("Contractor details successful updated!!.\n\n\t\tUPDATED PROJECT DETAILS:\n" + date_string);
					
					
				}	
				
				// When no projects are found
				else 
				{
					System.out.print("No projects to edit. First add a project\n");
				}

			}
			
			// When user wishes to finalise a project
			else if(userChoice == 5)
			{
				//Calls a method from another class that generates an invoice
				projectDetails finalInvoice = new projectDetails();
				String inv = finalInvoice.finaliseProject();
				System.out.print(inv);
			}
			
			// When user wishes to Exit
			else if (userChoice == 6)
			{
				System.out.println("\nProject overview exited!!");
				exit = true;
			}
			
			//When entered number by user is not in the options
			else
			{
				System.out.println("Entered selection not found, please try again.\nMake sure correct number is entered for correct option\n");
			}
		}
	}

}
