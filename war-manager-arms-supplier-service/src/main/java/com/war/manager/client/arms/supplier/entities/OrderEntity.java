package com.war.manager.client.arms.supplier.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="ORDER_ENTITY")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	@ManyToOne
	private UserEntity providedBy;
	@ManyToOne
	private UserEntity orderedBy;
	private Timestamp orderedDate;
	private OrderStatus orderStatus;
	private double discountOffered;
	private double couponDiscountApplied;
	private double sellingPrice;
	@ManyToOne
	private ProductEntity product;
	@ManyToOne
	private DeliveryEntity delivery;
	
}
