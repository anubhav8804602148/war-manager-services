package com.war.manager.client.arms.supplier.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.war.manager.client.arms.supplier.entities.ProductEntity;
import com.war.manager.client.arms.supplier.entities.ProductType;
import com.war.manager.client.arms.supplier.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	ProductService productService;

	@GetMapping("/getAllProducts")
	public List<ProductEntity> getAllProducts() {
		return productService.findAllProducts();
	}

	@PostMapping("/registerNewProduct")
	public ProductEntity registerNewProduct(@RequestBody ProductEntity product) {
		return productService.registerNewProduct(product);
	}

	@DeleteMapping("/removeExistingProductById/{productId}")
	public boolean removeExistingProductById(@PathVariable("productId") long productId) {
		return productService.removeExistingProductById(productId);
	}

	@PutMapping("/updateProductEntity")
	public ProductEntity updateProductEntity(@RequestBody ProductEntity product) {
		return productService.updateProductEntity(product);
	}

	@GetMapping("/findAllProductsByName")
	public List<ProductEntity> findAllProductsByName(@RequestParam("productName") String productName) {
		return productService.findAllProductsByName(productName);
	}

	@GetMapping("/findAllProductsContainingName")
	public List<ProductEntity> findAllProductsContainingName(@RequestParam("productName") String productName) {
		return productService.findAllProductsContainingName(productName);
	}

	@GetMapping("/findAllProductsByType")
	public List<ProductEntity> findAllProductsByType(@RequestParam("productName") String productType) {
		return productService.findAllProductsByType(ProductType.valueOf(productType));
	}

}
