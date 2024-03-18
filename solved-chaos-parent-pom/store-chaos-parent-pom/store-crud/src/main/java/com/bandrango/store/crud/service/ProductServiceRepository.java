package com.bandrango.store.crud.service;

import java.util.List;

import com.bandrango.store.crud.beans.Product;

public interface ProductServiceRepository {
	
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
	public List<Product> findAll();

	/**
	 * Retrieves a list of products based on the name.
	 *
	 * @param name The product name.
	 * @return A Product object that match the specified criteria.
	 */
	public Product findByName(String name);

}