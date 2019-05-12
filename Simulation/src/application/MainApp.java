package application;


import java.util.InputMismatchException;
import java.util.Scanner;

import draw.StdDraw;
import queues_analytical.M_M_c_L;
import randomGens.ExponentialGenerator;
import randomGens.TestGenerator;
import simulationModels.MMCL;
import simulationModels.MMCLBreakdown;

public class MainApp {

	public static void main(String[] args) {
		
		int c, maxLength, temp;
		double miat, mst, mtbf, mttr, tempDouble;
		boolean errorFlag=false;
		queues_analytical.Queue theoriticalModel;
		int choice, exit=0;
		Scanner in = new Scanner(System.in);
		System.out.println("\n--------------------------------------------------------------");
		System.out.println("\nMain Menu:");
		System.out.println("1->M/M/C/L \n2->M/M/C/L (w/breakdowns and repairs) \n3->Test Input Generator \nelse->exit");
		System.out.print("Your choice: ");
		try {
		choice = in.nextInt();
		in.nextLine();
		} catch (InputMismatchException e) {
			in.nextLine();
			System.out.println("Invalid input; please enter integers only for the menu options!!");
			choice = 4;
			errorFlag = true;
		}
		while(exit != 1)
		{
			try {
			switch(choice)
			{
				case 1:
					
					System.out.println("Enter number of Servers: ");
					c = in.nextInt();
					in.nextLine();
					if(c<1)
						throw new Exception("Error: The number of servers has to be 1 or more");
					
					System.out.println("Enter L (maximum number of jobs in the system at a time): ");
					maxLength = in.nextInt();
					in.nextLine();
					if(maxLength<1)
						throw new Exception("Error: L has to be 1 or more");
					
					MMCL mmcl = new MMCL(c, maxLength);
					
					System.out.println("Enter mean inter-arrival TIME (minutes): ");
					miat = in.nextDouble();
					in.nextLine();
					if(miat <= 0)
						throw new Exception("Error: Mean inter-arrival time has to be positive");
					
					System.out.println("Enter mean service TIME (minutes): ");
					mst = in.nextDouble();
					in.nextLine();
					if(mst <= 0)
						throw new Exception("Error: Mean service time has to be positive");
					
					mmcl.startSimulation(miat, mst);
					
					theoriticalModel = new M_M_c_L(1/(double)miat, 1/(double)mst, c, maxLength);
					mmcl.calculateMetrics(theoriticalModel);
					
					break;
			
				case 2:
					
					System.out.println("Enter number of Servers: ");
					c = in.nextInt();
					in.nextLine();
					if(c<1)
						throw new Exception("Error: The number of servers has to be 1 or more");
					
					System.out.println("Enter L (maximum number of jobs in the system at a time): ");
					maxLength = in.nextInt();
					in.nextLine();
					if(maxLength<1)
						throw new Exception("Error: L has to be 1 or more");
					
					MMCLBreakdown mmclBreakdown = new MMCLBreakdown(c, maxLength);
					
					System.out.println("Enter mean inter-arrival TIME (minutes): ");
					miat = in.nextDouble();
					in.nextLine();
					if(miat <= 0)
						throw new Exception("Error: Mean inter-arrival time has to be positive");
					
					System.out.println("Enter mean service TIME (minutes): ");
					mst = in.nextDouble();
					in.nextLine();
					if(mst <= 0)
						throw new Exception("Error: Mean service time has to be positive");
					
					System.out.println("Enter mean TIME between failures (minutes): ");
					mtbf = in.nextDouble();
					in.nextLine();
					if(mtbf <= 0)
						throw new Exception("Error: Mean time between failures has to be positive");
					
					System.out.println("Enter mean TIME to repair (minutes): ");
					mttr = in.nextDouble();
					in.nextLine();
					if(mttr <= 0)
						throw new Exception("Error: Mean time to repair has to be positive");
					
					System.out.println("1->One Repairman, 2->Multiple Repairmen: ");
					temp = in.nextInt();
					in.nextLine();
					if(temp != 1 && temp != 2)
						throw new Exception("Invalid choice!!");
					if(temp == 2)
						mmclBreakdown.setMultipleRepairMen(true);
					else 
						mmclBreakdown.setMultipleRepairMen(false);
					
					mmclBreakdown.startSimulation(miat, mst, mtbf, mttr);
					
					mmclBreakdown.calculateMetrics_unreliable();
					
					break;
					
				case 3:
					
					System.out.println("Enter the mean to be tested: ");
					tempDouble = in.nextDouble();
					in.nextLine();
					if(tempDouble <= 0)
						throw new Exception("Error: Only positive means are allowed in this test");
					
					TestGenerator.test(new ExponentialGenerator(tempDouble));
					break;	
						
				default:
					if(errorFlag)
						errorFlag = false;
					else
						exit = 1;
					break;
				}
			
			} catch (InputMismatchException e) {
				in.nextLine();
				System.out.println("Invalid input");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(exit != 1)
			{
				System.out.println("\n--------------------------------------------------------------");
				System.out.println("\nMain Menu:");
				System.out.println("1->M/M/C/L \n2->M/M/C/L (w/breakdowns and repairs) \n3->Test Input Generator \nelse->exit");
				System.out.print("Your choice: ");
				try {
				choice = in.nextInt();
				in.nextLine();
				} catch (InputMismatchException e) {
					in.nextLine();
					System.out.println("Invalid input; please enter integers only for the menu options!!");
					choice = 4;
					errorFlag = true;
				}
			}
		}
		in.close();
		StdDraw.closeFrame();
		System.out.println("Program Terminated");
		System.exit(0);

	}

}
