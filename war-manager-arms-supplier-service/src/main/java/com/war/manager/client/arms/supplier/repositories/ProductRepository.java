package com.war.manager.client.arms.supplier.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.war.manager.client.arms.supplier.entities.ProductEntity;
import com.war.manager.client.arms.supplier.entities.ProductType;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	
	@Query(value="SELECT * FROM PRODUCT_ENTITY WHERE PRODUCT_NAME=?1", nativeQuery=true)
	List<ProductEntity> findAllProductByName(String productName);

	@Query(value="SELECT * FROM PRODUCT_ENTITY WHERE PRODUCT_TYPE=?1", nativeQuery=true)
	List<ProductEntity> findAllProductsByType(ProductType productType);

	@Query(value="SELECT * FROM PRODUCT_ENTITY WHERE UPPER(PRODUCT_NAME) like '%'||?1||'%'", nativeQuery=true)
	List<ProductEntity> findAllProductsContainingName(String containedName);

}
