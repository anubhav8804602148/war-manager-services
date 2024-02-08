package com.war.manager.client.arms.supplier.entities;

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
@Table(name="DELIVERY_ENTITY")
public class DeliveryEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long deliveryId;
	private long deliveryMode;
	@ManyToOne
	private UserEntity deliveryAgent;
	@ManyToOne
	private UserEntity deliveryOwner;
	private double deliveryCost;
	@ManyToOne
	private ContactEntity deliveryFromContact;
	@ManyToOne
	private ContactEntity deliveryToContact;
	private OrderStatus deliveryStatus;
}
