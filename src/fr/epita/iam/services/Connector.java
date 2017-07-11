package fr.epita.iam.services;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.epita.iam.exceptions.ConfigException;
import fr.epita.iam.exceptions.DAOInitializationException;

public class Connector  {
	private static  Connection connection;
	private static ResourceBundle props;
    
	private Connector(){
		getConnection();
	}
	public static ResourceBundle getConnectionProperties()throws ConfigException{
	    props = ResourceBundle.getBundle("fr.epita.iam.config.config");
		if (props == null){
			throw new ConfigException();
		}
		return props;
	}
	
	public static void releaseConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public static Connection getConnection() throws DAOInitializationException{
		
		try {
			//read connection information from file
			connection.getSchema();
			
		} catch (Exception e) {
			try {
				props = getConnectionProperties();
				connection = DriverManager.getConnection(
						props.getString("connectionString"),
						props.getString("user"), 
						props.getString("password"));
			} catch (ConfigException|SQLException ex) {
				DAOInitializationException die = new DAOInitializationException();
				die.initCause(ex);
				throw die;
			}
			Logger.getLogger(Connector.class.getName()).log(Level.CONFIG, null, e);
		}
		return connection;
		
	}
}
