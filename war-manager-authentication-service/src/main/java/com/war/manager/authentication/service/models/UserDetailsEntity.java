package com.war.manager.authentication.service.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
@Entity
public class UserDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userDetailsId;
	@OneToOne(cascade = CascadeType.MERGE)
	private AccountEntity account;
	@OneToOne(cascade = CascadeType.MERGE)
	private UserEntity user;
	private String firstName;
	private String lastName;
	@OneToOne(cascade = CascadeType.MERGE)
	private ContactEntity contact;
	@OneToOne(cascade = CascadeType.MERGE)
	private ContactEntity backupContact;
	
}
