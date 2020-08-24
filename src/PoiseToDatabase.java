import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**CLASS CONSIST OF MOST OF THE METHODS USED IN PROJECTS.
 * @author Mthobisi Mahlangu
 * @version 1.1.4
 * @since 1.1.0
 * @param Constructor PoiseToDatabase 
 * @throws Errors dealt with internal
 */
public class PoiseToDatabase {
	
	public PoiseToDatabase() {
		
	}
	//======================= METHOD ==================================
	public static void mySQLMethods(int userChoice) {
		
		//<<<<<<<<<<<<<<<<< TRY BLOCK >>>>>>>>>>>>>>>>>>>>
		try {
			
			//Establishing Connection
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/PoisePMS?useSSL=false",
					"otheruser",
					"manqoba1806");

			// Create a direct line to the database for running our queries
			Statement statement = connection.createStatement();
			Statement statement1 = connection.createStatement();
			ResultSet results;
			ResultSet results1 = null;
			int rowsAffected;
			
			AllMethods methods = new AllMethods();
			
			

			//=================================== SELECTION 1 ==================================
			// IF 1A: If user wants to add/create a project
			if (userChoice == 1)
			{	
				addNewProject(connection,statement);		
			}	
			
			// =================================== SELECTION 2 ===============================================
			// ELSE IF 1B: If user wishes to change due date
			else if (userChoice == 2)
			{
				
				int returnedProjectNum = searchForProject(statement, methods);
				
				if (returnedProjectNum != 0) {	
					String status = "";
				
					results = statement.executeQuery(String.format("SELECT Complete FROM project_status "
							+ "WHERE ProjectNum = %s",returnedProjectNum));
					
					while (results.next()) {
						status += results.getString("Complete");
					}
					
					if (status.equals("No")) {
			
						String newDate = methods.getDeadline();
					
						rowsAffected = statement.executeUpdate(String.format("UPDATE project_status "
								+ "SET Deadline = \'%s\' "
								+ "WHERE ProjectNum = %s"
						,newDate,returnedProjectNum));
						
						System.out.println("\nUpdate complete, " + rowsAffected + " rows affected.");
					}
					else
						System.out.println("\nCannot change due date for finalized project.");
				}										
			}
			
			//=================================== SELECTION 3 ================================================
			//If user wishes to change amount paid
			else if (userChoice == 3){
				
				int returnedProjectNum = searchForProject(statement, methods);
				
				if (returnedProjectNum != 0) {
				
					Double projectFee = 0.0;
					results = statement.executeQuery(String.format("SELECT ProjectFee, Complete "
							+ "FROM finance JOIN all_projects using (ProjectNum) "
							+ "JOIN project_status using (ProjectNum) "
							+ "WHERE ProjectNum = %s ",returnedProjectNum));
					String checkComplete = "";
					
					while(results.next()) {
						projectFee += results.getDouble("ProjectFee");
						checkComplete += results.getString("Complete");
			
					}
					
						
					Double newPaidAmount = methods.getAmountPaid();
					
					Double newDueAmount = (projectFee - newPaidAmount);
					
					rowsAffected = statement.executeUpdate(String.format("UPDATE finance "
							+ "SET AmountPaid = %s, AmountDue =%s WHERE ProjectNum = %s"
						,newPaidAmount,newDueAmount,returnedProjectNum));
					
					System.out.println("\nAmount paid updated.");
				}
			}
			
			//======================================= SELECTION 4 ===========================================
			// When user wants to change associate
			else if (userChoice == 4)
			{
				
				int returnedProjectNum = searchForProject(statement, methods);
				
				if (returnedProjectNum != 0) {
					
					System.out.println("Which associate would you like to change or modify?"
							+ "\n1-Project Manager"
							+ "\n2-Structural Engineer"
							+ "\n3-Architect"
							+ "\n4-Architect");
					
					int optedChoice = validateInt("\nEnter your choice: ");
					
					if (optedChoice == 1 || optedChoice == 2 || optedChoice == 3 || optedChoice == 4 ) {
						
						associate_detailsTableInsert(statement,methods,returnedProjectNum,optedChoice,"\nFollow prompts\n");
					}
					else
						System.out.println("Error.Entered selection is not on the selection menu.Try again");			
				}
				else {
					System.out.println("Couldn't find project.Try again");
				}
			}

			
			//==================================== SELECTION 5 ================================================
			// When user wishes to finalise a project
			else if(userChoice == 5){
				
				int projectNum = searchForProject(statement,methods);
				String status ="";
				if (projectNum != 0) {
					
					results = statement.executeQuery(String.format("SELECT Complete FROM project_status WHERE ProjectNum = %s",projectNum));
					
					while (results.next()) {
						status += results.getString("Complete");
					}
					
					//Checks for completion, to avoid double finalizing
					if (status.equals("No")) {
						String invoice = "		====== INVOICE DETAILS ========";
						String todayDate = methods.currentDate();
						rowsAffected = statement.executeUpdate(String.format("UPDATE project_status SET Complete = \'Yes\' WHERE ProjectNum = %s",projectNum));
						rowsAffected = statement.executeUpdate(String.format("UPDATE project_status SET DateComplete = \'%s\' WHERE ProjectNum = %s",todayDate,projectNum));
				
						results = statement.executeQuery(String.format("SELECT FName,LName,Tel,HomeAddress,Email,ProjectFee,AmountPaid,AmountDue,DateComplete,ProjectNum,ProjectName FROM project_status JOIN all_projects using (ProjectNum) JOIN associate_details using(ProjectNum) JOIN finance using (ProjectNum)  WHERE ProjectNum = %s AND Complete = \'Yes\' AND RoleID = 4",projectNum));
				
						while (results.next()) {
							invoice += ("			\nDate generated	: " + results.getString("DateComplete")
								+"\n\nFor:\n\tProject number	: " + results.getInt("ProjectNum")
								+"\n	Project name	: " + results.getString("ProjectName")				
								+"		\n\nCUSTOMER INFO" 					
								+"\n	Name		: " + results.getString("FName") + " " +results.getString("LName")					
								+"\n	Tel		: " + results.getString("Tel") + " (" + results.getString("Email")+ ")"					
								+"\n	Address		: " + results.getString("HomeAddress")
								+"\n\n		  "
								+ "\n	Project fee	: R" + results.getDouble("ProjectFee")
								+ "\n	Amount paid	: R" + results.getDouble("AmountPaid")
								+"\n	AMOUNT TO PAY 	: R" + results.getDouble("AmountDue"));
						}
				
						System.out.println("\nSuccessfully finalised, find invoice below\n");
						System.out.println(invoice);
				
					}
					else
						System.out.println("\nProject already been finalized previously.");
				}
			}
			
			//============== SELECTION 6: FOR INCOMPLETE PROJECTS =================================================
			else if(userChoice == 6) {
				
				String incomplete_projects = "";
				
				results = statement.executeQuery(
						
				"SELECT ProjectNum,ProjectName,BuildingType,ProjectAddress,ERFNumber,DateAssigned,Complete,Deadline,ProjectFee,AmountPaid,AmountDue,DateComplete FROM project_status JOIN all_projects using (ProjectNum) JOIN finance using (ProjectNum) WHERE Complete = 'No'"
						);

				incomplete_projects = printFromDatabase(statement, statement1, results,results1, incomplete_projects);
				
				System.out.println(incomplete_projects);
				
			
			}
			
			//============== SELECTION 7: TO VIEW COMPLETE PROJECTS  ==================================================
			else if (userChoice == 7) {
				String complete_projects = "";
				
				results = statement.executeQuery(					
				"SELECT ProjectNum,ProjectName,BuildingType,ProjectAddress,ERFNumber,DateAssigned,Complete,Deadline,ProjectFee,AmountPaid,AmountDue,DateComplete FROM project_status JOIN all_projects using (ProjectNum) JOIN finance using (ProjectNum) WHERE Complete = 'Yes'");

				complete_projects = printFromDatabase(statement, statement1, results,results1, complete_projects);
				
				System.out.println(complete_projects);	
	
								
	
			}
			
			//================= Selection 8: VIEW OVERDUE PROJECTS =================================================
			else if (userChoice == 8) {
				String overdue_projects = "";
				
				results = statement.executeQuery(String.format(				
				"SELECT ProjectNum,ProjectName,BuildingType,ProjectAddress,ERFNumber,DateAssigned,Complete,Deadline,ProjectFee,AmountPaid,AmountDue,DateComplete FROM project_status JOIN all_projects using (ProjectNum) JOIN finance using (ProjectNum) WHERE Complete = 'No' AND Deadline <= \'%s\'",methods.currentDate()));

				overdue_projects = printFromDatabase(statement, statement1, results,results1, overdue_projects);
				
				System.out.println(overdue_projects);	

		}
			
			//=============== SELECTION 9: VIEW INVOICES ==================================================
			else if (userChoice == 9) {
				String allInvoices = "";
				results = statement.executeQuery(String.format("SELECT FName,LName,Tel,HomeAddress,Email,ProjectFee,AmountPaid,AmountDue,DateComplete,ProjectNum,ProjectName FROM project_status JOIN all_projects using (ProjectNum) JOIN associate_details using(ProjectNum) JOIN finance using (ProjectNum)  WHERE Complete = \'Yes\' AND RoleID = 4"));
				
				while (results.next()) {
					allInvoices += ("			\nDate generated	: " + results.getString("DateComplete")
						+"\n\nFOR:\n\tProject number	: " + results.getInt("ProjectNum")
						+"\n	Project name	: " + results.getString("ProjectName")				
						+"		\n\nCUSTOMER INFO" 					
						+"\n	Name		: " + results.getString("FName") + " " +results.getString("LName")					
						+"\n	Tel		: " + results.getString("Tel") + " (" + results.getString("Email")+ ")"					
						+"\n	Address		: " + results.getString("HomeAddress")
						+"\n\n		  "
						+ "\n	Project fee	: R" + results.getDouble("ProjectFee")
						+ "\n	Amount paid	: R" + results.getDouble("AmountPaid")
						+"\n	AMOUNT TO PAY 	: R" + results.getDouble("AmountDue")
						+ "\n========== ========== ===========\n");
				}
				
				System.out.println(allInvoices);		
			}
			
			//================================= SELECTION 10 View all ======================================================
			else if (userChoice == 10) {
				String all_projects = "";
				
				results = statement.executeQuery(
						
				"SELECT ProjectNum,ProjectName,BuildingType,ProjectAddress,ERFNumber,DateAssigned,Complete,Deadline,ProjectFee,AmountPaid,AmountDue,DateComplete FROM project_status JOIN all_projects using (ProjectNum) JOIN finance using (ProjectNum)");

				all_projects = printFromDatabase(statement, statement1, results,results1, all_projects);
				
				System.out.println(all_projects);	

			}
			
			//================================ SELECTION 11 DELETE PROJECT ============================================
			else if (userChoice  == 11) {
				int deleteProject = searchForProject(statement,methods);
				
				if (deleteProject != 0){
					String confirm = methods.validateStr("\nYou are about to delete the above project with all its links including from all invoices\n"
						+ "Enter Yes to CONFIRM or No to CANCEL\n:");
				
					String confirmManip = confirm.trim().toLowerCase();
				
					if (confirmManip.equals("yes")) {
						rowsAffected = statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
						rowsAffected = statement.executeUpdate(String.format("DELETE FROM project_status WHERE ProjectNum = %s",deleteProject));
						rowsAffected = statement.executeUpdate(String.format("DELETE FROM associate_details WHERE ProjectNum = %s",deleteProject));
						rowsAffected = statement.executeUpdate(String.format("DELETE FROM all_projects WHERE ProjectNum = %s",deleteProject));
						rowsAffected = statement.executeUpdate(String.format("DELETE FROM finance WHERE ProjectNum = %s",deleteProject));
						rowsAffected = statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
					
						System.out.println("Project deleted successsful");
					}
					else {
						System.out.println("Execution cancelled! Project not deleted");
					}
				}
			}
			//============================= SELECTION 12: VIEW DESIRED PROJECT =======================================
			else if (userChoice ==12) {
				searchForProject(statement,methods);
			}
			
			//=================================== SELECTION 13: EXIT =================================================
			// When user wishes to Exit
			else if (userChoice == 13)
			{
				System.out.println("\nProjects overview exited!!");
				statement.close();
				connection.close();

			}
			
			//When entered number by user is not in the options
			else
			{
				System.out.println("\nEntered selection not found, please try again.\nMake sure correct number is entered for correct option\n");
			}
		
			statement.close();
			statement.close();
			connection.close();				

		}
		
		//@@@@@@@@@@@@@@@@ CATCH BLOCK FOR MAIN TRY @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		catch (SQLException e) {
			 // We only want to catch a SQLException - anything else is off-limits for now.
			 e.printStackTrace();
			  }
	} 
	
	//========================================= METHOD printing anything from database ++++++++++++++++++++++++++++++++++++++++++
	
	public static String printFromDatabase(Statement statement,Statement statement1, ResultSet results,ResultSet results1,String selected_set)
			throws SQLException {
		
		/*Search for projects and prints out  for given msSQL commands on method parameter
		 *Returns found project number for in case user searched using Project Name
		*/
		while (results.next()) {
			 
			int current_projectNum = results.getInt("ProjectNum");
			selected_set += ("\n======= PROJECT DETAILS ======="
							+ "\nProject number 		:" + results.getInt("ProjectNum")
							+"\nProject name		:" + results.getString("ProjectName")
							+"\nBuilding type		:" + results.getString("BuildingType")
							+"\nProject address		:" + results.getString("ProjectAddress")
							+"\nERF Number		:" + results.getString("ERFNumber")
							+"\nDate assigned		:" + results.getString("DateAssigned")
							+"\nComplete		:" + results.getString("Complete")
							+"\nDeadline		:" + results.getString("Deadline")
							+"\nProject fee		:R" + results.getString("ProjectFee")
							+"\nAmount paid		:R" + results.getString("AmountPaid")
							+"\nAmount due		:R" + results.getString("AmountDue")
							+"\nDate complete		:" + results.getString("DateComplete"));
		
		int scanRoles = 1;
	
		while (scanRoles < 5) {
			results1 = statement1.executeQuery(String.format("SELECT RoleName, FName,LName,Tel,Email,HomeAddress FROM associate_roles JOIN associate_details using (RoleID) JOIN all_projects using (ProjectNum) WHERE RoleID = %s AND ProjectNum =%s"
			,scanRoles,current_projectNum));
			
			while (results1.next()) {
				selected_set += ("\n\n====== " + results1.getString("RoleName") + " ======"
						+"\nFirst name		:" +results1.getString("FName")
						+"\nLast name		:" + results1.getString("LName")
						+"\nTel			:" + results1.getString("Tel")
						+"\nEmail			:" + results1.getString("Email")
						+"\nHome address		:" + results1.getString("HomeAddress"));
						
			}
			scanRoles++;
		}
		selected_set += "\n==========================================\n"
				+ 		"==========================================\n\n";
		}
		
		if (selected_set.equals("")) {
			System.out.println("\nNo projects found.");
		}
		return selected_set;
	}
	
	//===================== METHOD: ADDING NEW PROJECT ==================================================
	
	public static void addNewProject(Connection connection, Statement statement) throws SQLException {
		int rowsAffected;
		// User must enter the number of projects they wish to create
		// Validates entered number

		
		int numberOfProjects = validateInt("Enter number of projects you want to add: ");

		// FOR LOOP 1A: Will repeat for the number of projects that the user wishes to create
		for (int projectNumIterator = 0; projectNumIterator <  numberOfProjects; projectNumIterator++)
		{
			String newProjectData = "";       // ALL DETAILS NEEDED FOR A COMPLETE PROJECT WILL BE APPENDED HERE
			AllMethods data_in_project = new AllMethods(); // CALLING CLASS WITH PROJECT DETAILS FOR CAPTURING
			 
			// The following called methods are from the projectDetails class which was recently called
			int projectNum = data_in_project.getProjectNumber();			

			String projectName = data_in_project.getProjectName();
			
			String buildingType = data_in_project.getBuildingType();
			
			String projectAddress= data_in_project.getProjectAddress();
								
			int erfNumber = data_in_project.getERFnumber();
			
			rowsAffected = statement.executeUpdate(
					  		String.format("INSERT INTO all_projects VALUES (%s, \'%s\', \'%s',\'%s\', %s)"
							  ,projectNum, projectName, buildingType, projectAddress, erfNumber));
			
			System.out.println("Query complete " + rowsAffected + " rows affected");
			 					
			double projectFee = data_in_project.getProjectFee();
			
			double amountPaid= data_in_project.getAmountPaid();
			
			double amountDue = (projectFee-amountPaid);
			
			rowsAffected = statement.executeUpdate(String.format(
					  "INSERT INTO finance VALUES (%s,%s,%s,%s,%s)"
					   ,projectNum, projectFee, amountPaid, amountDue, projectNum));
			 
			System.out.println("Query complete " + rowsAffected + " rows affected");
			
			String dateAssigned = data_in_project.currentDate();
					
			String dueDate = data_in_project.getDeadline();
			
			  rowsAffected = statement.executeUpdate(String.format(
			"INSERT INTO project_status VALUES (%s,\'%s\',\'%s\', 'No', Null, %s )",projectNum,
			dateAssigned, dueDate, projectNum));
			  System.out.println("Query complete " + rowsAffected + " rows affected");

								

			// We call the personDetails class and personalInfo method to register Project Manager
			associate_detailsTableInsert(statement, data_in_project, projectNum,
					1,"\nNOTE: Prompts are for Project Manager\n");

			

			// Again we call personDetails class and personalInfo method to register Structural Engineer 
			associate_detailsTableInsert(statement, data_in_project, projectNum,
					2,"\nNOTE: Prompts are for Structural Engineer\n");
			
			//Again for Architect
			associate_detailsTableInsert(statement, data_in_project, projectNum,
					3,"\nNOTE: Prompts are for Architect\n");
			
			//Again for Customer
			associate_detailsTableInsert(statement, data_in_project, projectNum,
					4,"\nNOTE: Prompts are for Customer\n");
			
			 statement.close();
			 connection.close();


			System.out.print("\nProject successfully added\n"); //When project is successful added
		}
		
 statement.close();
 connection.close();
	}

	//================== NETHOD : INSERTING TO CERTAIN TABLE ========================================
	
	public static void associate_detailsTableInsert(Statement statement, AllMethods data_in_project, int projectNum
			, int roleID, String messageToUser )
	
			throws SQLException{
		int rowsAffected;
		PersonDetails person_info = new PersonDetails();
		
		System.out.println(messageToUser);
		
		PersonDetails personObject = new PersonDetails();
		
		String Fname = data_in_project.validateStr("Enter first name: ");
		String Lname = data_in_project.validateStr("Enter last name: ");
		String Tel = data_in_project.validateStr("Enter phone number: ");
		String Email = personObject.validateEmail();
		String Haddress = data_in_project.validateStr("Enter home address: ");
		
		int nameID = (projectNum*2)  + (roleID*2);
		  rowsAffected = statement.executeUpdate(String.format(
		"INSERT INTO associate_details VALUES (%s, %s, \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', %s)",
		roleID,nameID,Fname,Lname,Tel,Email,Haddress,
		projectNum));
		  System.out.println("Query complete " + rowsAffected + " rows affected");
	}
	
	//======================= METHOD : SEARCH FOR PROJECT =================================================
	
	public static int searchForProject(Statement statement, AllMethods methods) throws SQLException 
	{
		int projectNum = 0;
		String projectName = "";
		
		ResultSet results;

		while (true){
			System.out.println("\nFind project using Project Number or Project Name?.\n1-Project Number\n2-Project Name");
			int findUsing = validateInt("\nEnter choice:");
		
		
			if (findUsing == 1) {
				projectNum += validateInt("\nEnter project number:");
				results = statement.executeQuery(String.format("SELECT * FROM all_projects "
						+ " WHERE ProjectNum = %s",
							projectNum));
				break;
			}
			else if (findUsing == 2) {
			
				projectName = methods.validateStr("\nEnter project name:");
				results = statement.executeQuery(String.format("SELECT * FROM all_projects "
						+ " WHERE ProjectName =\'%s'",
						projectName));
				break;

			}
			
			else {
				System.out.println("Invalid selection! Choose accordingly");
				
			}
		}
	
		
		String foundProject = "";
		int correctProjectNumber = 0; 
		
		if (results != null){
			
		while (results.next()) {
						 
				int current_projectNum = results.getInt("ProjectNum");
				String current_projectName = results.getString("ProjectName");
				
				if (current_projectNum == projectNum || current_projectName.equals(projectName)) {
					
					correctProjectNumber += current_projectNum;
					
					results = statement.executeQuery(String.format(
					"SELECT ProjectNum,ProjectName,BuildingType,ProjectAddress,ERFNumber,DateAssigned,Complete,Deadline,ProjectFee,AmountPaid,AmountDue,DateComplete FROM project_status JOIN all_projects using (ProjectNum) JOIN finance using (ProjectNum) WHERE ProjectNum = %s"
							,current_projectNum));
					
					while (results.next()) {
						 
						foundProject += ("\nProject number 		:" + results.getInt("ProjectNum")
										+"\nProject name		:" + results.getString("ProjectName")
										+"\nBuilding type		:" + results.getString("BuildingType")
										+"\nProject address		:" + results.getString("ProjectAddress")
										+"\nERF Number		:" + results.getString("ERFNumber")
										+"\nDate assigned		:" + results.getString("DateAssigned")
										+"\nComplete		:" + results.getString("Complete")
										+"\nDeadline		:" + results.getString("Deadline")
										+"\nProject fee		:R" + results.getString("ProjectFee")
										+"\nAmount paid		:R" + results.getString("AmountPaid")
										+"\nAmount due		:R" + results.getString("AmountDue")
										+"\nDate complete		:" + results.getString("DateComplete"));
					}
					int scanRoles = 1;
					while (scanRoles < 4) {
						results = statement.executeQuery(String.format("SELECT RoleName,FName,LName,Tel,Email,HomeAddress FROM associate_roles JOIN associate_details using (RoleID) JOIN all_projects using (ProjectNum) WHERE RoleID = %s AND ProjectNum =%s"
						,scanRoles,current_projectNum));
						
						while (results.next()) {
							foundProject += ("\n\n====== " + results.getString("RoleName") + " ======"
									+"\nFirst name		:" +results.getString("FName")
									+"\nLast name		:" + results.getString("LName")
									+"\nTel			:" + results.getString("Tel")
									+"\nEmail			:" + results.getString("Email")
									+"\nHome address		:" + results.getString("HomeAddress") + "\n\n"
							
									);
									
						}
						scanRoles++;
					}		

				}
			}
		}
		
		String checkResults = foundProject.trim();
		
		if (!checkResults.equals("")) {
			System.out.print("\n\n========== Project Details ===========\n\n" + foundProject);
		}
		else {
			System.out.println("\nProject not found. Input corrent Project Number or Project Name");
		}
		
		return correctProjectNumber;
	}
	
	//================== METHOD: VALIDATES INTEGER WHERE REQUIRED ================================
	 public static int validateInt(String messageUser) {
		 
		 int validInteger = 0;	
		 while(true) {
				
			 try {
				Scanner userInteger = new Scanner (System.in);
				System.out.print(messageUser);
				validInteger += userInteger.nextInt();
				break;
				}
			catch(Exception e) {
				System.out.println("\nERROR!.Enter number in digits only.");
			}
		 }		 
		 return validInteger;
	 }


		 
		 
	 

	
	
}
