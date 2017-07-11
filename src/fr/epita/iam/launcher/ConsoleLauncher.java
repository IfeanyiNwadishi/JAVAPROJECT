package fr.epita.iam.launcher;

import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.business.CreateActivity;
import fr.epita.iam.business.DeleteActivity;
import fr.epita.iam.business.UpdateActivity;
import fr.epita.iam.services.Connector;

/**
 * This class is the main class of the project IAM  
 * @author Nwadishi Ifeanyi
 *
 */
public class ConsoleLauncher {
	
	public static void main(String[] args){
		System.out.println("Welcome to the IAM CORE");
		Scanner scanner = new Scanner(System.in);
		//Authentification
		if (authenticate(scanner)){
			String answer = "y";
			while(answer.equalsIgnoreCase("y")){
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
						CreateActivity.execute(scanner);
						break;
					case "2":
						//modify
						UpdateActivity.execute(scanner);
						break;
					case "3":
						//delete
						DeleteActivity.execute(scanner);
						break;
					case "5":
						//Quit
						answer= "n";
						break;
					default:
						System.out.println("invalid selection");
				}
				System.out.println("Do you want to continue: y/n?");
				answer = scanner.nextLine();
				if (!answer.equalsIgnoreCase("y")){
					break;
				}
			}
		}
		end(scanner);
	}

	private static void end(Scanner scanner) {
		System.out.print("Thank you for using this application , goodbye");
		scanner.close();
	}

	private static boolean authenticate(Scanner scanner) {
		System.out.println("Please typer your login");
		String login = scanner.nextLine();
		System.out.println("Please typer your password");
		String password = scanner.nextLine();
		boolean flag = false;
		try{
			ResourceBundle props = Connector.getConnectionProperties(); 
				if (login.equals(props.getString("user")) 
						&& password.equals(props.getString("password"))){
					System.out.println("Authenfication was Successfull");
					flag = true;
				}
				else{
					System.out.println("Authentifaction failed");
					scanner.close();
					flag = false;
				}
		}
		catch(Exception e){
			Logger.getLogger(ConsoleLauncher.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
		return flag;
	}
}
