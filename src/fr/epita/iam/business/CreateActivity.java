package fr.epita.iam.business;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityDAO;


/**
 * CreateActivity is a class that allows to create an Identity using
 * user inputs
 * @author Nwadishi 
 *
 */
public class CreateActivity extends Activity{
	
	/**
	 * This method get a string as input and generate associated Date
	 * @param birthday_str: user input value
	 * @return java.sql.Date
	 * @throws ParseException
	 */
	private static java.sql.Date getDate(String birthday_str) throws ParseException {
		SimpleDateFormat  dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		java.util.Date date = dateFormat.parse(birthday_str);
		java.sql.Date birthDay = new java.sql.Date(date.getTime());
		return birthDay;
	}
	
	/**
	 * This method create an Identity from user inputs
	 * @param scanner: Get the user input 
	 */
	public static void execute(Scanner scanner){
		System.out.println("Identity Creation");
		System.out.println("please input the displayName");
		String displayName = scanner.nextLine();
		System.out.println("please input the email address");
		String email = scanner.nextLine();
		System.out.println("please input the password");
		String password = scanner.nextLine();
		System.out.println("please input the birthday (dd-mm-yyyy)");
		String birthdayStr = scanner.nextLine();
		Date birthday = null;
		try{
			birthday = getDate(birthdayStr);
		}
		catch(Exception e){
			System.out.println("Error while reading the birthday");
		}
		Identity identity = new Identity("",displayName,
								email, birthday, password);
		
	
		//persist the identity somewhere
		System.out.println("this is the identity you created\n"+identity);
		IdentityDAO identityDAO = new IdentityDAO();
		identityDAO.create(identity);
		System.out.println("creation Done");
		
	}
}
