package fr.epita.iam.exceptions;

public class ConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage(){
		return "Did not find configuration properties";
	}

}
