package fr.epita.iam.exceptions;

public class DAOInitializationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage(){
		return "Service cannot connect to the database";
	}
}
