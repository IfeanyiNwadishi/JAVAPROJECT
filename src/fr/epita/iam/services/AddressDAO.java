package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.datamodel.Address;
import fr.epita.iam.datamodel.Attribute;
import fr.epita.iam.exceptions.DAOInitializationException;


public class AddressDAO implements DAO<Address> {
	
	
	private Connection connection;
	/**
	 * 
	 */
	public AddressDAO(){
		connection = Connector.getConnection();
	}

	/* (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#create(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void create(Address entity) throws DAOInitializationException{
		
		connection = Connector.getConnection();
		try {
			String sql = "insert into ADDRESS (IDENTITY_ID, STREET, CITY, ZIPCODE)"
					+" values (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql,
											Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, entity.getIdentityID());
			statement.setString(2, entity.getStreet());
			statement.setString(3, entity.getCity());
			statement.setString(4, entity.getZipCode());
			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			AddressAttributeDAO  addressAttrDAO = new AddressAttributeDAO();
			if(result.next())
            {
				String identityID = result.getString(1);
				for (Map.Entry<String, String> entry :entity.getAttributes().entrySet()){
					addressAttrDAO.create(new Attribute(identityID,
							entry.getKey(), entry.getValue()));
				}
            }
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		
		 
		
	}
	/* (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#update(fr.epita.iam.datamodel.Identity)
	 */
	@Override
	public void update(Address entity) throws DAOInitializationException{
		connection = Connector.getConnection();
		try {
			String sql = "update ADDRESS  set STREET=?, "
					+ "CITY=?, "
					+ "ZIPCODE=?, "
					+ "IDENTITY_ID=?"
					+" where ADDRESS_ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.getStreet());
			statement.setString(2, entity.getCity());
			statement.setString(3, entity.getZipCode());
			statement.setString(4, entity.getIdentityID());
			statement.setString(5, entity.getAddressID());
			statement.executeUpdate();
			statement.close();
			sql = "select *  from ADDRESS_ATTRIBUTE  where ADDRESS_ID=?";
			PreparedStatement statement0 = connection.prepareStatement(sql);
			statement0.setString(1, entity.getAddressID());
			ResultSet temp = statement0.executeQuery();
			if(temp.next()){
				for (Map.Entry<String, String> entry : entity.getAttributes().entrySet()){
					 sql = "update ADDRESS_ATTRIBUTE  set  "
							+ "NAME=?, "
							+ "VALUE=?"
							+" where ADDRESS_ID=?";
						PreparedStatement statement1 = connection.prepareStatement(sql);
						statement1.setString(1, entry.getKey());
						statement1.setString(2, entry.getValue());
						statement1.setString(3, entity.getAddressID());
						statement1.executeUpdate();
						statement1.close();
					}
			}
			else{
				for (Map.Entry<String, String> entry : entity.getAttributes().entrySet()){
					 sql = "insert into ADDRESS_ATTRIBUTE (ADDRESS_ID, NAME, VALUE)"
							+" values (?, ?, ?)";
						PreparedStatement statement1 = connection.prepareStatement(sql);
						statement1.setString(1, entity.getAddressID());
						statement1.setString(2, entry.getKey());
						statement1.setString(3, entry.getValue());
						
						statement1.executeUpdate();
						statement1.close();
					}
			}
			connection.close();
		} catch (SQLException e) {
			Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		 
		
	}
	
	@Override
	public void delete(Address entity) throws DAOInitializationException{
		connection = Connector.getConnection();
		try {
			String sql = "delete from ADDRESS "
					+" where ADDRESS_ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.getAddressID());
			statement.executeUpdate();
			statement.close();
			
			sql = "delete from ADDRESS_ATTRIBUTE "
				+" where ADDRESS_ID=?";
			PreparedStatement statement1 = connection.prepareStatement(sql);
			statement1.setString(1, entity.getAddressID());
			statement1.executeUpdate();
			statement1.close();
			
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, e);
			
		}
		 
		
	}
	/* (non-Javadoc)
	 * @see fr.epita.iam.services.DAO#readAllIdentities()
	 */
	@Override
	public List<Address> readAll() throws DAOInitializationException{
		connection = Connector.getConnection();
		Map<String, Address> addresses = new HashMap<String, Address>();
		try {
			String sql = "select * from ADDRESS a left join ADDRESS_ATTRIBUTE aa on "
					+ "a.address_id=aa.address_id";
			ResultSet result = connection.prepareStatement(sql).executeQuery();
			
			while(result.next()){
				String address_id = result.getString("address_id");
				String identity_id = result.getString("identity_id");
				String street = result.getString("street");
				String city = result.getString("city");
				String zipcode = result.getString("zipcode");
				String name = result.getString("name");
				String value = result.getString("value");
				
					if (addresses.containsKey(address_id)){
						Address address = addresses.get(address_id);
						if (name != null){
							address.setAttribute(name, value);
						}
					}
					else{
						Address address = new Address(address_id, identity_id, 
								street, city, zipcode);
						if (name != null){
							address.setAttribute(name, value);
						}
						addresses.put(address.getAddressID(), address);
					}
			
			}
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, e);
			
		}
		List<Address> result = new ArrayList<Address>();
		for (Address i : addresses.values()){
			result.add(i);
		}
		return result;
	}


	@Override
	public Address find(Object id) throws DAOInitializationException {
		Address address = null;
		connection = Connector.getConnection();
		try {
			String sql = "select * from ADDRESS where ADDRESS_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, (int)id);
			
			ResultSet result = statement.executeQuery();
			if (result.next()){
				String address_id = result.getString("ADDRESS_ID");
				String identity_id = result.getString("IDENTITY_ID");
				String street = result.getString("STREET");
				String city = result.getString("CITY");
				String zipcode = result.getString("ZIPCODE");
				address = new Address(address_id,identity_id,
										street, city, zipcode);
			}
			if (address != null){
				sql = "select *  from ADDRESS_ATTRIBUTE  where ADDRESS_ID=?";
				PreparedStatement statement0 = connection.prepareStatement(sql);
				statement0.setInt(1, (int)id);
				ResultSet temp = statement0.executeQuery();
				while(temp.next()){
					String key = result.getString("name");
					String value = result.getString("value");
					address.setAttribute(key, value);
				}
			}
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, e);
		
		}
		return address;
	}

	@Override
	public List<Address> search(String[] keywords, Address entity) throws DAOInitializationException {
		// TODO Auto-generated method stub
		return null;
	}

}
