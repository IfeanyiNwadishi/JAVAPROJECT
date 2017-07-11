package fr.epita.iam.business;



import java.util.List;
import java.util.Scanner;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityDAO;

/**
 * This class allows to delete an Identity selected by the user
 * @author Nwadishi Ifeanyi
 *
 */
public class DeleteActivity extends Activity {
	
	/**
	 * This method delete an existing Identity
	 * @param scanner: Get user selection
	 */
	public static void execute(Scanner scanner){
		System.out.println("Identity Deleting");
		IdentityDAO identityDAO = new IdentityDAO();
		List<Identity> identities = identityDAO.readAll();
		
		System.out.println("Select an identity id");
		for(Identity i : identities){
			System.out.println("ID : "+i.getUid()+ " \n"+i);
		}
		String identity_id = scanner.nextLine();
		if (identity_id.isEmpty()) {
			System.out.println("Did not understand you answer ");
			return;
		}
		Identity foundIdentity = identityDAO.find(identity_id);
		if (foundIdentity == null){
			System.out.println("Did not find identity: "+identity_id);
		}
		else{
			System.out.println("Do you really want to "
					+ "delete identity: " + identity_id+ " y/n");
			String answer = scanner.nextLine();
			if (answer.equalsIgnoreCase("y")){
				//persist the identity somewhere
				System.out.println("This is the identity you want to delete: \n"+foundIdentity);
				identityDAO.delete(foundIdentity);
				System.out.println("delete Done");
			}
			else{
				System.out.println("delete cancel");
			}
		}
	}
}
