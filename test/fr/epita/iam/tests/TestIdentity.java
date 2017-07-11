package fr.epita.iam.tests;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.datamodel.Attribute;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DAOInitializationException;
import fr.epita.iam.services.AttributeDAO;
import fr.epita.iam.services.Connector;
import fr.epita.iam.services.IdentityDAO;

/**
 * Test delete, create, find, modify identities in database
 * @author Nwadishi Ifeanyi
 *
 */
public class TestIdentity {

	public static void main(String[] args) {
		
		testIdentityDAO();
	}
	private static void testIdentityDAO(){
		
		// create an instance
		IdentityDAO identityDAO = new IdentityDAO();
	
		try {
			// clear table identities
			resetIdentities();
			// read empty Identities table
			List<Identity> result = identityDAO.readAll();
			if (!result.isEmpty()){
				System.out.println("Method resetIdentities fails to empty table IDENTITIES");
			}
			
			// test create Identities
			System.out.println("Inserting 2 identities table IDENTITIES:");
			Identity identity1 = new Identity(null,"galina","galina@gmail.com");
			identity1.setBirthday(getDate("02-01-1985"));
			identityDAO.create(identity1);
			
			Identity identity2 = new Identity(null,"Lois","lois@gmail.com");
			identity2.setBirthday(getDate("03-02-1984"));
			identityDAO.create(identity2);
			List<Identity> identities = identityDAO.readAll();
			if (identities.size() != 2){
				System.out.println("Fails to insert identities table IDENTITIES");
			}
			else {
				for (Identity i: identities){
					System.out.println(i);
				}
			}
			System.out.println("------------------------\n");
			// test update identities
			System.out.println("Updating the first identities:");
			Identity identity0 = identities.get(0);
			identity0.setDisplayName("new name");
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
			
			// test create Identities with attributes
			System.out.println("Inserting 1 identities with attributes:");
			Identity temp = new Identity(null,"userWithAttributes","user@gmail.com");
			temp.setBirthday(getDate("02-01-1985"));
			identityDAO.create(temp);
			
			List<Identity> identitiesAttribute = identityDAO.readAll();
			for (Identity i: identitiesAttribute){
				System.out.println(i);
			}
			Identity identityAttr = identitiesAttribute.get(identitiesAttribute.size()-1);
			
			Attribute attr = new Attribute(identityAttr.getUid(), "size", "XL");
			AttributeDAO attributeDAO = new AttributeDAO();
			attributeDAO.create(attr);
			List<Attribute> listAttributes = attributeDAO.readAll();
			for (Attribute i: listAttributes){
				System.out.println(i);
			}
			
			
			System.out.println("------------------------\n");
			
			// test create Identities with attributes
			System.out.println("Inserting 2nd identity with attributes:");
			Identity temp2 = new Identity(null,"user2_attr","use2@gmail.com");
			temp.setBirthday(getDate("02-13-1985"));
			
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("Height", "10m");
			attributes.put("size", "M");
			attributes.put("weight", "56kg");
			temp.setAttributes(attributes);
			identityDAO.create(temp2);
			
			List<Identity> identitiesAttribute2 = identityDAO.readAll();
			for (Identity i: identitiesAttribute2){
				System.out.println(i);
			}
			System.out.println("List if attributes");
			List<Attribute> listAttributes2 = attributeDAO.readAll();
			for (Attribute i: listAttributes2){
				System.out.println(i);
			}
			
			System.out.println("------------------------\n");
			// test update identities
			System.out.println("Updating the last identities:");
			Identity identityLast= identitiesAttribute2.get(identitiesAttribute2.size()-1);
			identityLast.setDisplayName("Last name");
			identityLast.setAttribute("new attribute", "new value");
			identityLast.setAttribute("size", "MM");
			identityDAO.update(identityLast);
			List<Identity> identitiesAttrUpdated = identityDAO.readAll();
			for (Identity i: identitiesAttrUpdated){
				System.out.println(i);
			}
		} catch (Exception e) {
			Logger.getLogger(TestIdentity.class.getName()).log(Level.SEVERE, null, e);
			
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
			
			sql = "Delete from IDENTITIES";
			statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(TestIdentity.class.getName()).log(Level.SEVERE, null, e);
			
		}
	}
}
