package com.war.manager.authentication.service.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accountId;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<RoleEntity> roles;
	private boolean locked;
	private boolean expired;
	private boolean disabled;
	private boolean passwordExpired;
	private AccountFlag flag;
}
