package fr.epita.iam.business;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityDAO;

/**
 * This class allows to update an Identity using user inputs
 * @author Nwadishi Ifeanyi
 *
 */
public class UpdateActivity extends Activity{
	

	private static java.sql.Date getDate(String birthday_str) {
		java.sql.Date birthDay = null;
		try{
			SimpleDateFormat  dateFormat = new SimpleDateFormat("dd-mm-yyyy");
			java.util.Date date = dateFormat.parse(birthday_str);
			birthDay = new java.sql.Date(date.getTime());
		}
		catch(ParseException e){
			Logger.getLogger(UpdateActivity.class.getName()).log(Level.SEVERE, null, e);
			System.out.println("please enter a date format dd-mm-yyyy");
		}
		return birthDay;
	}
	
	/**
	 * This method can user input and update an existing identity
	 * @param scanner: Get user inputs
	 */
	public static void execute(Scanner scanner){
		System.out.println("Identity Update");
		IdentityDAO identityDAO = new IdentityDAO();
		List<Identity> identities = identityDAO.readAll();
		System.out.println("Select an identity id");
		for(Identity i : identities){
			System.out.println("ID: "+i.getUid()+ " \n"+i);
		}
		String identity_id = scanner.nextLine();
		Identity foundIdentity = identityDAO.find(identity_id);
		if (foundIdentity == null){
			System.out.println("Did not find identity "+identity_id);
		}
		else{
			System.out.println("Do you want to update identity: "+identity_id+"  y/n");
			String answer = scanner.nextLine();
			if (answer.equalsIgnoreCase("y")){
				while(answer.equalsIgnoreCase("y")){
					System.out.println("Choose the field to update");
					System.out.println("1, Birthday"); 
					System.out.println("2, DisplayName"); 
					System.out.println("3, Password");
					System.out.println("4, Quit");
					String options = scanner.nextLine();
					switch(options.toLowerCase()){
						case "1":
							System.out.println("please enter new birthday");
							String birthdayStr = scanner.nextLine();
							foundIdentity.setBirthday(getDate(birthdayStr));
							break;
						case "2":
							System.out.println("please enter new display name");
							String displayName = scanner.nextLine();
							foundIdentity.setDisplayName(displayName);
							break;
						case "3":
							System.out.println("please enter new password");
							String password = scanner.nextLine();
							foundIdentity.setPassword(password);
							break;
						case "4":
							answer = "n";
							break;
						default:
							answer = "y";
					}
				}
				
			}
			else{
				System.out.println("Update cancel");
			}
		}
		
		//persist the identity somewhere
		System.out.println("this is the identity you updated: \n"+foundIdentity);
		identityDAO.update(foundIdentity);
		System.out.println("update Done");
		
	}
}
