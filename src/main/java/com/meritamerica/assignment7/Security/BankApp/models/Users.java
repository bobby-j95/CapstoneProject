package com.meritamerica.assignment7.Security.BankApp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator" )
	@SequenceGenerator(name = "users_generator", sequenceName = "user_seq", initialValue = 2)
	@Column(name = "user_id")
	private int id;
	
	private String username;
	private String password;
	private Boolean active;
	private String roles;//"ROLE_ADMIN,ROLE_ACCOUNT_HOLDER"
	
	
	@OneToOne//(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="user")
	@JoinColumn(name = "account_holder_id")
	@JsonIgnore
	private AccountHolder accountHolder;

	public Users() {
		
	}

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}
	

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	
}
