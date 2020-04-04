import javax.swing.JOptionPane;

import java.util.*;
import java.util.Scanner;

/* THIS IS A HEAD UP PROGRAM FOR POISED CONSTRUCTION, SOME FEATURES  LIKE DUE DATE CHANGE CAN ONLY BE EDITED DURING PROGRAM RUN
 * SINCE THERE IS NO OUTPUT TEXT FILE YET TO SAVE PREVIOUSLY ADDED PROJECTS
 * USER MUST ADD PROJECT FIRST BEFORE MAKING ANY MODIFICATIONS
 */

//-----------ATTRIBUTES------------
public class projectDetails 
{
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
	public projectDetails()
	{
		
	}
	
	// METHOD 1: Getting the project number
	public String getProjectNumber()
	{
		String projectNum = "";
		System.out.print("Enter project number\n:");
		Scanner projNum = new Scanner(System.in);
		int projectNumber = projNum.nextInt();		
		projectNum = "Project Number: " + projectNumber + "\n";
		
		return projectNum;	
	}		
	
	// METHOD 2: Getting project name
	public String getProjectName()
	{
		String ProjectName = "";
		System.out.print("Enter project name\n:");
		Scanner projName = new Scanner(System.in);
		String projectName = projName.nextLine();		
		ProjectName = "Project name: " + projectName + "\n";
		
		return ProjectName;
	}
	
	// METHOD 3: Getting type of building
	public String getBuildingType()
	{
		String projectType = "";
		System.out.print("Enter building type. Eg Townhouses, House and etc\n:");
		Scanner type = new Scanner(System.in);
		String buildingType = type.nextLine();		
		projectType = "Building Type: " + buildingType + "\n";
		
		return projectType;
	}
	
	// METHOD 4: Getting physical address of the project
	public String getHomeAddress()
	{
		String homeAddress = "";
		//String projectAddress = (JOptionPane.showInputDialog("Enter project address:"));
		System.out.print("Enter project address\n:");
		Scanner hAddress = new Scanner(System.in);
		String projectAddress = hAddress.nextLine();		
		homeAddress = "Project Address: " + projectAddress + "\n";
		
		return homeAddress;		
	}
	
	// METHOD 5: Getting ERF Number of the project stand
	public String getERFnumber()
	{
		System.out.print("Enter project ERF Number\n:");
		Scanner erfNumber = new Scanner(System.in);
		String ERF_Number = erfNumber.nextLine();		
		String erfNum = "ERF Number: " + ERF_Number + "\n";
		
		return erfNum;
	}
	
	// METHOD 6: Getting total amount charged for the project
	public  String getProjectFee()
	{
		System.out.print("How much is the project fee?\nR");
		Scanner projFee = new Scanner(System.in);
		float projectFee = projFee.nextFloat();
		String ProjectFee = "Charged amount for project: R" + projectFee + "\n";
		
		return ProjectFee;
	}
		
	// METHOD 7: Gets the up to date amount paid by client for the project
	public String getAmountPaid()
	{
		System.out.print("Enter project amount that has been paid up to date\nR");
		Scanner PaidAmount = new Scanner (System.in);
		Float strAmountPaid = PaidAmount.nextFloat();
		String paidAmount = "Amount paid up to date: R" + strAmountPaid + "\n";
		
		return paidAmount;
	}
	
	// METHOD 8: Creates a deadline date for the project
	public String getDeadline()
	{
		System.out.print("Enter project due date\n:");
		Scanner dDate = new Scanner(System.in);
		String projectDeadline = dDate.nextLine();		
		String dueDate = "Deadline: " + projectDeadline + "\n";
		
		return dueDate;
	}
	
	// METHOD 9: Finalises the project and generates invoice
	public String finaliseProject()
	{
		// Material cost
		System.out.print("Enter material cost\nR");
		Scanner mCost = new Scanner(System.in);
		Float materialCost = mCost.nextFloat();			
		
		//Labour cost
		System.out.print("\nEnter labour cost\nR");
		Scanner LCost = new Scanner(System.in);
		Float labourCost = LCost.nextFloat();	
		
		// Generates invoice for project
		String invoice = ("\n\t\tINVOICE\nFinal invoice for project:\n");
		invoice += ("\tTotal cost of material: R" + materialCost);
		invoice += ("\n\tTotal cost of labour  : R" + labourCost + "\n");
		invoice += ("\tGRAND TOTAL = R" +(materialCost + labourCost) + "\n");
		
		return invoice;
		
	}
	

}
