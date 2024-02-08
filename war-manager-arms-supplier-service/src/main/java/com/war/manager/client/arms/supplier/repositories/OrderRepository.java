package com.war.manager.client.arms.supplier.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.war.manager.client.arms.supplier.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
