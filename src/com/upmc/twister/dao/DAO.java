package com.upmc.twister.dao;

public interface DAO {
	/**
	 * The create method persists a new Object to the database The subclass must
	 * define the implementation of this method
	 * 
	 * @param o
	 *            the object to create
	 */
	public abstract void create(Object o) throws Exception;

	/**
	 * The update method updates an Object to the database The subclass must define
	 * the implementation of this method
	 *
	 * @param o
	 *            the object to update
	 */
	public abstract void update(Object o) throws Exception;

	/**
	 * The delete method deletes an Object from the database The subclass must
	 * define the implementation of this method
	 * 
	 * @param o
	 *            the object to delete
	 */
	public abstract void delete(Object o) throws Exception;

	/**
	 * The find method find an Object from the database by its id The subclass must
	 * define the implementation of this method
	 * 
	 * @param id
	 */
	public abstract Object find(int id) throws Exception;
}
