package fr.epita.iam.launcher;

import java.util.Scanner;


import fr.epita.iam.business.CreateFileActivity;

public class ConsoleFileLauncher {
	
	public static void main(String[] args){
		System.out.println("Welcome to the IAM CORE");
		System.out.println("What would you like to do today?");
		Scanner scanner = new Scanner(System.in);
		//Authentification
		/*if (authenticate(scanner)){
			end(scanner);
			return;
		}*/
		//menu
		System.out.println("Please select an action: ");
		System.out.println("1, Create an identity");
		System.out.println("2, Modify an identity");
		System.out.println("3, delete an identity");
		System.out.println("4, list an identity");
		System.out.println("5, exit");
		String choice = scanner.nextLine();
		
		switch (choice) {
		case "1":
			//create
			//CreateActivity createActivity = new CreateActivity();
			CreateFileActivity.execute(scanner);
			break;
		case "2":
			//modify
			break;
		case "3":
			//delete
			break;
		case "5":
			//Quit
			break;
		default:
			System.out.println("invalid selection,try again...");
		}
		end(scanner);
	}

	private static void end(Scanner scanner) {
		System.out.print("Thank you for using this application , Have a great day");
		scanner.close();
	}

	private static boolean authenticate(Scanner scanner) {
		System.out.println("Please enter your username");
		String login = scanner.nextLine();
		System.out.println("Please enter your password");
		String password = scanner.nextLine();
		
		
		if (login.equals("admin") && password.equals("admin")){
			System.out.println("Authenfication was Successfull");
			return true;
		}
		else{
			System.out.println("Authentifaction failed");
			scanner.close();
			return false;
		}
	}
	
}

