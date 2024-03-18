package com.bandrango.store.crud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bandrango.store.crud.beans.Product;
import com.bandrango.store.crud.dao.ProductDao;
import com.bandrango.store.crud.service.ProductServiceRepository;

@Component
public class ProductServiceRepositoryImpl implements ProductServiceRepository {

	@Autowired
	private ProductDao productDao;

	@Override
	public int upsertProduct(Product product) {
		return productDao.upsertProduct(product);
	}

	@Override
	public int deleteProduct(String name) {
		return productDao.deleteProduct(name);
	}

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}

	@Override
	public Product findByName(String name) {
		return productDao.findByName(name);
	}

}