package fr.epita.iam.services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.datamodel.Attribute;
import fr.epita.iam.exceptions.DAOInitializationException;

/**
 * This class defines the attributes for an address
 * @author Nwadishi Ifeanyi
 *
 */
public class AddressAttributeDAO implements DAO<Attribute> {
	
	private Connection connection;

	@Override
	public void create(Attribute entity) throws DAOInitializationException {
		connection = Connector.getConnection();
		try {
			String sql = "insert into ADDRESS_ATTRIBUTE (ADDRESS_ID, NAME, VALUE)"
					+" values (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.getattributeID());
			statement.setString(2, entity.getName());
			statement.setString(3, entity.getValue());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Connector.releaseConnection(); 
	}

	@Override
	public void update(Attribute entity) throws DAOInitializationException {
		connection = Connector.getConnection();
		try {
			String sql = "update ADDRESS_ATTRIBUTE set ADDRESS_ID=?, "
					+ "NAME=?, "
					+ "VALUE=? "
					+" where ADDRESS_ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.getattributeID());
			statement.setString(2, entity.getName());
			statement.setString(3,  entity.getValue());
			statement.setString(4, entity.getattributeID());
			statement.executeUpdate();
			
			
		} catch (SQLException e) {
			Logger.getLogger(AddressAttributeDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		Connector.releaseConnection();
	}

	@Override
	public void delete(Attribute entity) throws DAOInitializationException {
		connection = Connector.getConnection();
		try {
			String sql = "delete from ADDRESS_ATTRIBUTE "
					+" where ADDRESS_ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.getattributeID());
			statement.executeUpdate();
		
		} catch (SQLException e) {
			Logger.getLogger(AddressAttributeDAO.class.getName()).log(Level.SEVERE, null, e);
			
		}
		Connector.releaseConnection();
	}

	@Override
	public Attribute find(Object id) throws DAOInitializationException {
		Attribute attribute = null;
		connection = Connector.getConnection();
		try {
			String sql = "select * from ADDRESS_ATTRIBUTE where ADDRESS_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, (int)id);
			
			ResultSet result = statement.executeQuery();
			if (result.next()){
				String address_id = result.getString("address_id");
				String name = result.getString("name");
				String value = result.getString("value");
				attribute = new Attribute(address_id, name, value);
			}
			statement.close();
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(AddressAttributeDAO.class.getName()).log(Level.SEVERE, null, e);
			
		}
		return attribute;
	}
	public Map<String, String> find(String id) throws DAOInitializationException {
		Map<String, String> attributes = new HashMap<String, String>();
		connection = Connector.getConnection();
		try {
			String sql = "select * from ADDRESS_ATTRIBUTE where ADDRESS_ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()){
				String name = result.getString("name");
				String value = result.getString("value");
				attributes.put(name, value);
			}
			statement.close();
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(AddressAttributeDAO.class.getName()).log(Level.SEVERE, null, e);
			
		}
		return attributes;
	}
	@Override
	public List<Attribute> search(String[] keywords, Attribute entity) throws DAOInitializationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attribute> readAll() throws DAOInitializationException {
		connection = Connector.getConnection();
		List<Attribute> attributes = new ArrayList<>();
		try {
			String sql = "select * from ADDRESS_ATTRIBUTE";
			ResultSet result = connection.prepareStatement(sql).executeQuery();
			while(result.next()){
				String address_id = result.getString("ADDRESS_ID");
				String name = result.getString("name");
				String value = result.getString("value");
				Attribute attribute = new Attribute(address_id, name, value);
				attributes.add(attribute);
			}
			Connector.releaseConnection();
		} catch (SQLException e) {
			Logger.getLogger(IdentityDAO.class.getName()).log(Level.SEVERE, null, e);
		}
		return attributes;
	}

}
