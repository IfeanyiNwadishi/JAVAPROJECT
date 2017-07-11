package fr.epita.iam.tests;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.datamodel.Address;
import fr.epita.iam.datamodel.Attribute;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DAOInitializationException;
import fr.epita.iam.services.AddressAttributeDAO;
import fr.epita.iam.services.AddressDAO;
import fr.epita.iam.services.Connector;
import fr.epita.iam.services.IdentityDAO;

/**
 * Test adding an address to an Identity
 * @author Nwadishi Ifeanyi
 *
 */
public class TestAddress {

	public static void main(String[] args) {
		
		testIdentityDAO();
	}
	private static void testIdentityDAO(){
		
		// create an instance
		AddressDAO addressDAO = new AddressDAO();
		AddressAttributeDAO addressAttrDAO = new AddressAttributeDAO();
		try {
			
			// clear table identities
			resetIdentities();
			// read empty Identities table
			IdentityDAO identityDAO = new IdentityDAO();
			List<Identity> identities = identityDAO.readAll();
			if (!identities.isEmpty()){
				System.out.println("Method resetIdentities fails to empty table IDENTITIES");
			}
			// clear table address
			resetAddresses();
			// read empty address table
			List<Address> addresses = addressDAO.readAll();
			List<Attribute> attributes = addressAttrDAO.readAll();
			if (!addresses.isEmpty() || !attributes.isEmpty()){
				System.out.println("Method resetAddresses fails to empty table IDENTITIES");
			}
			
			// test create Identities
			System.out.println("Inserting 2 identities table IDENTITIES:");
			Identity identity1 = new Identity(null,"galina","galina@gmail.com");
			identity1.setBirthday(getDate("02-01-1985"));
			identityDAO.create(identity1);
			
			Identity identity2 = new Identity(null,"Lois","lois@gmail.com");
			identity2.setBirthday(getDate("03-02-1984"));
			Address address2 = new Address(null, 
					     identity2.getUid(),"20 rue Voltaire", 
					"Paris", "7500");
			address2.setAttribute("lattitude", "13");
			address2.setAttribute("longitude", "12");
			identity2.setAddress(address2);
			identityDAO.create(identity2);
			
			identities = identityDAO.readAll();
			if (identities.size() != 2){
				System.out.println("Fails to insert identities table IDENTITIES");
			}
			else {
				for (Identity i: identities){
					System.out.println(i);
				}
			}
			
			System.out.println("------------------------\n");
			// test update identities with address
			System.out.println("Updating the first identities:");
			Identity identity0 = identities.get(0);
			
			Address address = new Address(null, identity0.getUid(),
						"rue Voltaire", 
						"Paris", "7500");
			address.setAttribute("lattitude", "11");
			address.setAttribute("longitude", "12");
			identity0.setAddress(address);
			identityDAO.update(identity0);
			
			List<Identity> identitiesUpdated = identityDAO.readAll();
			for (Identity i: identitiesUpdated){
				System.out.println(i);
			}
			System.out.println("------------------------\n");
			
			//test delete entities
			System.out.println("Deleting the 2nd identities from table IDENTITIES");
			Identity identityToDelete = identitiesUpdated.get(1);
			identityDAO.delete(identityToDelete);
			findIdentity(identityDAO, 
					Integer.parseInt(identityToDelete.getUid()));
			List<Identity> identitiesDeleted = identityDAO.readAll();
			for (Identity i: identitiesDeleted){
				System.out.println(i);
			}
			System.out.println("------------------------\n");
			
			// test create addresses with attributes
			System.out.println("Inserting 1 identities with attributes:");
			Identity temp = new Identity(null,"userWithAttributes","user@gmail.com");
			temp.setBirthday(getDate("02-01-1985"));
			Address tempAddress = new Address(null, null, "street1", "city1", "zipcode");
			tempAddress.setAttribute("2nd line address", "33 underground building");
			temp.setAddress(tempAddress);
			identityDAO.create(temp);
			
			List<Identity> identityAddress = identityDAO.readAll();
			
			for (Identity i: identityAddress){
				System.out.println(i);
			}
		
			AddressDAO address2DAO = new AddressDAO();
			List<Address> listAddresses = address2DAO.readAll();
			for (Address i: listAddresses){
				System.out.println(i);
			}
			
			
			System.out.println("------------------------\n");
			
		
		} catch (Exception e) {
			Logger.getLogger(TestAddress.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
	}
	private static java.sql.Date getDate(String birthday_str) throws ParseException {
		SimpleDateFormat  dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		java.util.Date date = dateFormat.parse(birthday_str);
		java.sql.Date birthDay = new java.sql.Date(date.getTime());
		return birthDay;
	}
	private static void findIdentity(IdentityDAO  identityDAO, int uid) {
		Identity iFound = (Identity)identityDAO.find((Object)uid);
		if (iFound == null){
			System.out.println("could not find Identity with id ="+uid);
		}
		else{
			System.out.println("Found Identity "+ iFound);
		}
	}
	
	public static void resetIdentities() throws DAOInitializationException{
		Connection connection = Connector.getConnection();
		try {
			String sql = "DELETE FROM ATTRIBUTE";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			
			sql = "Delete from ADDRESS_ATTRIBUTE";
			statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			
			
			sql = "Delete from ADDRESS";
			statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			
			sql = "Delete from IDENTITIES";
			statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(TestIdentity.class.getName()).log(Level.SEVERE, null, e);
			
		}
	}
	public static void resetAddresses() throws DAOInitializationException{
		Connection connection = Connector.getConnection();
		try {
			String sql = "DELETE FROM ADDRESS_ATTRIBUTE";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			
			sql = "Delete from ADDRESS";
			statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(TestAddress.class.getName()).log(Level.SEVERE, null, e);
			
		}
	}
}
