package com.bandrango.store.crud.service;

import java.util.List;

import com.bandrango.store.crud.beans.Customer;

public interface CustomerServiceRepository {

	/**
	 * Upserts (inserts or updates) the given Customer into the system.
	 *
	 * @param Customer The Customer object to be upserted.
	 * @return The number of affected rows in the database after the upsert
	 *         operation.
	 */
	public int upsertCustomer(Customer Customer);

	/**
	 * Deletes the Customer with the specified ID from the system.
	 *
	 * @param id The ID of the Customer to be deleted.
	 * @return The number of affected rows in the database after the deletion
	 *         operation.
	 */
	public int deleteCustomer(Long id);

	/**
	 * Retrieves all Customers from the system.
	 *
	 * @return A list of Customer objects representing all Customers in the
	 *         system.
	 */
	public List<Customer> findAll();

	/**
	 * Retrieves a list of Customers based on the id.
	 *
	 * @param id     The name Customer ID.
	 * @return A Customer object that match the specified criteria.
	 */
	public Customer findById(Long id);

}