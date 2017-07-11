package fr.epita.iam.services;


import java.util.List;


import fr.epita.iam.exceptions.DAOInitializationException;

/**
 * This interface defines the operations required to update the table 
 * of the database
 * @author Nwadishi Ifeanyi
 *
 * @param <T>: T is a user defined type
 */
public interface DAO<T> {
	/**
	 *  Create a row in the table associated with entity T
	 * @param entity: A class in the datamodel
	 * @throws DAOInitializationException
	 */
	public void create(T entity) throws DAOInitializationException;
	
	/**
	 * Update the value of a row in the table associated with entity T
	 * @param entity
	 * @throws DAOInitializationException
	 */
	public void update(T entity) throws DAOInitializationException;
	
	/**
	 * Delete row in table associate with entity T
	 * @param entity
	 * @throws DAOInitializationException
	 */
	public void delete(T entity) throws DAOInitializationException;
	
	/**
	 * Find a entity in the table associated giving an ID
	 * @param id
	 * @return
	 * @throws DAOInitializationException
	 */
    public T find(final Object id)throws DAOInitializationException;
    
    /**
     * Search entity giving some keywords
     * @param keywords
     * @param entity
     * @return
     * @throws DAOInitializationException
     */
    List<T> search(String[] keywords, T entity)throws DAOInitializationException;
	/**
	 * Read all entities of type T 
	 * @return list of entity of type T
	 * @throws DAOInitializationException
	 */
	List<T> readAll() throws DAOInitializationException;

}