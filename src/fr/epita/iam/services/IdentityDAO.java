package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.datamodel.Address;
import fr.epita.iam.datamodel.Attribute;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DAOInitializationException;


public class IdentityDAO implements DAO<Identity> {
	
	
	private Connection connection;
	/**
	 * 
	 */
	public IdentityDAO(){
		connection = Connector.getConnection();
	}

	/* (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#create(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void create(Identity entity) throws DAOInitializationException{
		
		connection = Connector.getConnection();
		try {
			String sql = "insert into IDENTITIES (DISPLAY_NAME, EMAIL, "
					+ "BIRTHDAY, PASSWORD, ISADMIN)"
					+" values (?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql,
											Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, entity.getDisplayName());
			statement.setString(2, entity.getEmail());
			statement.setDate(3, entity.getBirthday());
			statement.setString(4, entity.getPassword());
			int isAdmin = (entity.isAdmin())?1:0;
			statement.setInt(5, isAdmin);
			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			AttributeDAO  attributeDAO = new AttributeDAO();
			if(result.next())
            {
				//create associated attributes
				String identityID = result.getString(1);
				for (Map.Entry<String, String> entry :entity.getAttributes().entrySet()){
					attributeDAO.create(new Attribute(identityID,
							entry.getKey(), entry.getValue()));
				}
				//create associated addresses
				AddressDAO addressDAO = new AddressDAO();
				for (Address entry : entity.getAddresses()){
					entry.setIdentityID(identityID);
					addressDAO.create(entry);
				}
            }
			
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(IdentityDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		
		 
		
	}
	/* (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#update(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void update(Identity entity) throws DAOInitializationException{
		connection = Connector.getConnection();
		try {
			String sql = "update IDENTITIES  set EMAIL=?, "
					+ "DISPLAY_NAME=?, "
					+ "BIRTHDAY=?, "
					+ "PASSWORD=?, "
					+ "ISADMIN=?"
					+" where IDENTITY_ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.getEmail());
			statement.setString(2, entity.getDisplayName());
			statement.setDate(3, entity.getBirthday());
			statement.setString(4, entity.getPassword());
			int isAdmin = (entity.isAdmin())?1:0;
			statement.setInt(5, isAdmin);
			statement.setString(6, entity.getUid());
			statement.executeUpdate();
			statement.close();
			sql = "select *  from ATTRIBUTE  where IDENTITY_ID=?";
			PreparedStatement statement0 = connection.prepareStatement(sql);
			statement0.setString(1, entity.getUid());
			ResultSet temp = statement0.executeQuery();
			if(temp.next()){
				for (Map.Entry<String, String> entry : entity.getAttributes().entrySet()){
					 sql = "update ATTRIBUTE  set  "
							+ "NAME=?, "
							+ "VALUE=?"
							+" where IDENTITY_ID=?";
						PreparedStatement statement1 = connection.prepareStatement(sql);
						statement1.setString(1, entry.getKey());
						statement1.setString(2, entry.getValue());
						statement1.setString(3, entity.getUid());
						statement1.executeUpdate();
						statement1.close();
					}
			}
			else{
				for (Map.Entry<String, String> entry : entity.getAttributes().entrySet()){
					 sql = "insert into ATTRIBUTE (IDENTITY_ID,NAME, VALUE)"
							+" values (?, ?, ?)";
						PreparedStatement statement1 = connection.prepareStatement(sql);
						statement1.setString(1, entity.getUid());
						statement1.setString(2, entry.getKey());
						statement1.setString(3, entry.getValue());
						
						statement1.executeUpdate();
						statement1.close();
					}
			}
			connection.close();
		} catch (SQLException e) {
			Logger.getLogger(IdentityDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		 
		
	}
	/* (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#delete(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void delete(Identity identity) throws DAOInitializationException{
		connection = Connector.getConnection();
		try {
		
			String sql = "DELETE  FROM ADDRESS_ATTRIBUTE "
					+" WHERE ADDRESS_ATTRIBUTE.address_id "
					+ "in (select address_id from ADDRESS where "
					+ "ADDRESS.identity_id = ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, identity.getUid());
			statement.executeUpdate();
			
			sql = "delete from ADDRESS "
					+" where IDENTITY_ID=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, identity.getUid());
			
			sql = "delete from ATTRIBUTE "
					+" where IDENTITY_ID=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, identity.getUid());
			statement.executeUpdate();
			
			sql = "delete from IDENTITIES "
					+" where IDENTITY_ID=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, identity.getUid());
			statement.executeUpdate();
			statement.close();
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(IdentityDAO.class.getName()).log(Level.SEVERE, null, e);
			
		}
		 
		
	}
	/* (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#readAllIdentities()
	 */
	@Override
	public List<Identity> readAll() throws DAOInitializationException{
		connection = Connector.getConnection();
		Map<String, Identity> identities = new HashMap<String, Identity>();
		try {
			String sql = "select * from IDENTITIES i left join ATTRIBUTE a on "
					+ "i.identity_id=a.identity_id";
			ResultSet result = connection.prepareStatement(sql).executeQuery();
			
			while(result.next()){
				String uid = result.getString("IDENTITY_ID");
				String displayName = result.getString("display_name");
				String email = result.getString("email");
				Date birthday = result.getDate("BIRTHDAY");
				String password = result.getString("PASSWORD");
				boolean isadmin = (result.getInt("ISADMIN")==1)? true:false;
				String name = result.getString("name");
				String value = result.getString("value");
				Identity identity;
					if (identities.containsKey(uid)){
						identity = identities.get(uid);
						if (name != null){
							identity.setAttribute(name, value);
						}
					}
					else{
						identity = new Identity(uid, displayName, 
								email, birthday, password);
						identity.setAdmin(isadmin);
						if (name != null){
							identity.setAttribute(name, value);
						}
						identities.put(identity.getUid(), identity);
					}
					sql = "select * from ADDRESS a left join ADDRESS_ATTRIBUTE aa on "
							+ "a.address_id=a.address_id"
							+ " where a.identity_id=?";
					PreparedStatement p = connection.prepareStatement(sql);
					p.setString(1, uid);
					ResultSet result1 = p.executeQuery();
					Map<String,Address> addresses = new HashMap<String, Address>();
					while(result1.next()){
						String address_id = result1.getString("address_id");
						String street = result1.getString("street");
						String city = result1.getString("city");
						String zipcode = result1.getString("zipcode");
						String aname = result1.getString("name");
						String avalue = result1.getString("value");
						Address address;
						if (addresses.containsKey(address_id)){
							address = addresses.get(address_id);
						}
						else{
							address = new Address(address_id, uid, street, city, zipcode);
						
						}
						address.setAttribute(aname, avalue);
						addresses.put(address_id, address);
						
					}
					for(Address item : addresses.values()){
						identity.setAddress(item);
					}
					
					identities.put(identity.getUid(), identity);
					

			}
			
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(IdentityDAO.class.getName()).log(Level.SEVERE, null, e);
			
		}
		List<Identity> result = new ArrayList<Identity>();
		for (Identity i :identities.values()){
			result.add(i);
		}
		return result;
	}


	@Override
	public Identity find(Object id) throws DAOInitializationException {
		Identity identity = null;
		connection = Connector.getConnection();
		try {
			String sql = "select * from IDENTITIES where IDENTITY_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, Integer.parseInt(id.toString()));
			
			ResultSet result = statement.executeQuery();
			if (result.next()){
				String uid = result.getString("identity_id");
				String displayName = result.getString("display_name");
				String email = result.getString("email");
				identity = new Identity(uid, displayName, email);
			}
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(IdentityDAO.class.getName()).log(Level.SEVERE, null, e);
		
		}
		 
		
		return identity;
	}

	@Override
	public List<Identity> search(String[] keywords, Identity entity) throws DAOInitializationException {
		// TODO Auto-generated method stub
		return null;
	}

}
