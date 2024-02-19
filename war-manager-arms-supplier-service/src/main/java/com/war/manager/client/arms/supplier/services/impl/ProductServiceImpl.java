package com.war.manager.client.arms.supplier.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.war.manager.client.arms.supplier.entities.ProductEntity;
import com.war.manager.client.arms.supplier.entities.ProductType;
import com.war.manager.client.arms.supplier.repositories.ProductRepository;
import com.war.manager.client.arms.supplier.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public ProductEntity registerNewProduct(ProductEntity product) {
		if(product==null) return null;
		return productRepository.save(product);
	}

	@Override
	public boolean removeExistingProductById(long productId) {
		productRepository.deleteById(productId);
		return productRepository.findById(productId).isEmpty();
	}

	@Override
	public ProductEntity updateProductEntity(ProductEntity product) {
		if(product==null) return null;
		return productRepository.save(product);
	}

	@Override
	public List<ProductEntity> findAllProductsByName(String productName) {
		return productRepository.findAllProductByName(productName);
	}

	@Override
	public List<ProductEntity> findAllProductsContainingName(String containedName) {
		return productRepository.findAllProductsContainingName(containedName);
	}

	@Override
	public List<ProductEntity> findAllProductsByType(ProductType productType) {
		return productRepository.findAllProductsByType(productType);
	}

	@Override
	public List<ProductEntity> findAllProducts() {
		return productRepository.findAll();
	}

}
