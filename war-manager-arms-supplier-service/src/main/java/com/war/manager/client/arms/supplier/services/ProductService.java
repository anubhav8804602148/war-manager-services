package com.war.manager.client.arms.supplier.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.war.manager.client.arms.supplier.entities.ProductEntity;
import com.war.manager.client.arms.supplier.entities.ProductType;

@Service
public interface ProductService {

	public ProductEntity registerNewProduct(ProductEntity product);
	public boolean removeExistingProductById(long productId);
	public ProductEntity updateProductEntity(ProductEntity product);
	
	/**
	 * @productName = 'product1'
	 * @returns = <p><i>products having name exactly 'product1'</i></p>
	 * 
	 */
	public List<ProductEntity> findAllProductsByName(String productName);
	
	/**
	 * @containedName = 'product1'
	 * @returns = <p><i>products having 'product1' in any case in their name</i></p>
	 * 
	 * e.g. 'Custom Product1', 'Product1', 'product1', 'Product1 Product2'
	 * 
	 */
	public List<ProductEntity> findAllProductsContainingName(String containedName);
	public List<ProductEntity> findAllProductsByType(ProductType productType);
	public List<ProductEntity> findAllProducts();
	
}
