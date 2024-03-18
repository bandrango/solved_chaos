package com.bandrango.store.crud.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bandrango.store.crud.beans.Customer;

public interface CustomerDao {

	/**
	 * Upserts (inserts or updates) the given Customer into the system.
	 *
	 * @param Customer The Customer object to be upserted.
	 * @return The number of affected rows in the database after the upsert
	 *         operation.
	 * @Transactional(readOnly = false)
	 */
	@Transactional(readOnly = false)
	public int upsertCustomer(Customer Customer);

	/**
	 * Deletes the Customer with the specified ID from the system.
	 *
	 * @param id The ID of the Customer to be deleted.
	 * @return The number of affected rows in the database after the deletion
	 *         operation.
	 * @Transactional(readOnly = false)
	 */
	@Transactional(readOnly = false)
	public int deleteCustomer(Long id);

	/**
	 * Retrieves all Customers from the system.
	 *
	 * @return A list of Customer objects representing all Customers in the
	 *         system.
	 * @Transactional(readOnly = true)
	 */
	@Transactional(readOnly = true)
	public List<Customer> findAll();

	/**
	 * Retrieves a list of Customers based on the id.
	 *
	 * @param id     The name Customer ID.
	 * @return A list of Customer objects that match the specified criteria.
	 */
	@Transactional(readOnly = true)
	public Customer findById(Long id);

}