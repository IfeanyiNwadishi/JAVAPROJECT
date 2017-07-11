package fr.epita.iam.tests;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.Connector;

/**
 * Test the connection to the database
 * @author Nwadishi Ifeanyi
 *
 */
public class TestConnection {

	public static void main(String[] args) {
		testConnectorDAO();
	
	}

	private static void testConnectorDAO() {
		try {
			Connection connection = Connector.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("select * from IDENTITIES");
			List<Identity> identities = new ArrayList<>();
			while(result.next()){
				String uid = result.getString("identity_id");
				String display_name = result.getString("display_name");
				String email = result.getString("email");
				Identity identity = new Identity(uid, display_name, email);
				identities.add(identity);
			}
			System.out.println(identities);
		} catch (SQLException e) {
			Logger.getLogger(TestConnection.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
