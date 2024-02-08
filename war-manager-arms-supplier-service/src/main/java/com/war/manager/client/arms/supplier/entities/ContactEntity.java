package com.war.manager.client.arms.supplier.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="CONTACT_ENTITY")
public class ContactEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long contactId;
	private long phoneNumber;
	private int phoneExt;
	private String email;
	private String address;
}
