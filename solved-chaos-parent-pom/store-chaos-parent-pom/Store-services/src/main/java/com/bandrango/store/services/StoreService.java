package com.bandrango.store.services;

import java.util.List;

import com.bandrango.store.crud.beans.Customer;
import com.bandrango.store.crud.beans.Product;
import com.bandrango.store.services.beans.OrderRequest;
import com.bandrango.store.services.beans.OrderResponse;

public interface StoreService {

	/**
	 * Upserts (inserts or updates) the given product into the system.
	 *
	 * @param product The Product object to be upserted.
	 * @return The number of affected rows in the database after the upsert
	 *         operation.
	 */
	public int upsertProduct(Product product);

	/**
	 * Deletes the product with the specified ID from the system.
	 *
	 * @param name The product name.
	 * @return The number of affected rows in the database after the deletion
	 *         operation.
	 */
	public int deleteProduct(String name);

	/**
	 * Retrieves all products from the system.
	 *
	 * @return A list of Product objects representing all products in the
	 *         system.
	 */
	public List<Product> findAllProducts();

	/**
	 * Retrieves a list of products based on the name.
	 *
	 * @param name     The product name.
	 * @return A Product object that match the specified criteria.
	 */
	public Product findProductByName(String name);
	
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
	public List<Customer> findAllCustomers();

	/**
	 * Retrieves a list of Customers based on the id.
	 *
	 * @param id     The name Customer ID.
	 * @return A Customer object that match the specified criteria.
	 */
	public Customer findCustomerById(Long id);
	
	/**
	 * Processes an order based on the provided order request.
	 *
	 * @param orderRequest The order request containing information about the customer and ordered products.
	 * @return An {@link OrderResponse} object representing the response to the order request.
	 */
	public OrderResponse order (OrderRequest orderRequest);
	
}