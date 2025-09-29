//John Pacheco
//COP 3330
//Homework 1
//5.19.22

import java.util.Scanner;

public class payroll 
{
	public static void main(String[] args) 
	{	
		//Read in Input:///////////////////////////////////////////
		
		//initialize variables and Scanner
		String name, ID;
		double hourlyRate, hoursWorked;
		Scanner input = new Scanner(System.in);
		
		//read in name
		System.out.print("Enter Employee Name: ");
		name = input.nextLine();
		
		//read in ID
		System.out.print("Enter Employee ID: ");
		ID = input.nextLine();
		
		//read in hourlyRate
		System.out.print("Enter Hourly Rate: ");
		hourlyRate = input.nextDouble();
			
		//read in hoursWorked
		System.out.print("Enter Hours Worked: ");
		hoursWorked = input.nextDouble();
		
		//Output Results:///////////////////////////////////////////
		
		System.out.println("\nHere is your paycheck:");
		System.out.println("----------------------------------------");
		
		//output results
		System.out.println("Employee Name: " + name);
		System.out.println("Employee ID: " + ID);
		System.out.printf("Hourly Rate: %.2f\n",hourlyRate);
		System.out.printf("Hours Worked: %.2f\n\n",hoursWorked);
		
		double grossPay = hoursWorked * hourlyRate;
		System.out.printf("Total Gross Pay: %.2f\n\n",grossPay);
		
		double tax = grossPay * 0.06;
		
		System.out.println("Deductions:");
		System.out.printf("Tax(6%%): %.2f\n\n",tax);

		System.out.printf("Net Pay: %.2f\n",(grossPay - tax));
		System.out.println("----------------------------------------");		
		
		//close scanner at the end
		input.close();
	}

}
