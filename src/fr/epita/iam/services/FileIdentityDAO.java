/**
 * 
 */
package fr.epita.iam.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DAOInitializationException;

/**
 * @author Nwadishi Ifeanyi
 *
 */

public class FileIdentityDAO implements DAO<Identity>{

	private PrintWriter writer;
	private Scanner scanner;

	/**
	 * TODO:create, read, update , delete
	 * TODO: Generate uid for identity and store it in a file 
	 */

	public FileIdentityDAO(String fileName) {
		// TODO remove that hardcoded filename
		this.writer = initWriter(new File(fileName));
		try {
			this.scanner = new Scanner(new File("tests.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(Identity identity) {
		
		writer.println("---------IDENTITY:BEGIN-------");
		writer.println(identity.getDisplayName());
		writer.println(identity.getEmail());
		writer.println(identity.getUid());
		writer.println("---------IDENTITY:END---------");
		writer.flush();

	}
	@Override
	public void create(Identity identity) throws DAOInitializationException {
		write(identity);
	}
	
	@Override
	public List<Identity> readAll()throws DAOInitializationException{
		List<Identity> readIdentities = new ArrayList<Identity>();


		while (scanner.hasNextLine()) {
			// delimiter
			scanner.nextLine();
			String displayName = scanner.nextLine();
			String email = scanner.nextLine();
			String uid = scanner.nextLine();
			// delimiter
			scanner.nextLine();
			Identity readIdentity = new Identity(uid, displayName, email);
			readIdentities.add(readIdentity);
		}
		
		return readIdentities;

	}

	/**
	 * @param file
	 */
	private static PrintWriter initWriter(File file) {
		if (!file.exists()) {
			try {
				// "risky" operations
				File parentFile = file.getParentFile();
				if (parentFile != null) {
					parentFile.mkdirs();
				}
				file.createNewFile();
			} catch (IOException e) {
				// do something to handle the exceptional case
				e.printStackTrace();
			} finally {
				// always executed
			}
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer;
	}

	@Override
	public void delete(Identity identity) throws DAOInitializationException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(Identity identity) throws DAOInitializationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Identity find(Object id) throws DAOInitializationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Identity> search(String[] keywords, Identity entity) throws DAOInitializationException {
		// TODO Auto-generated method stub
		return null;
	}


}
