package com.meritamerica.assignment7.Security.BankApp.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("CHECKING")
public abstract class CheckingAccount extends BankAccount {

	private static final double INTEREST_RATE = .0001;

	public CheckingAccount(double balance) {
		super(balance, INTEREST_RATE);
	}

	public CheckingAccount() {
		super(INTEREST_RATE);
	}
	
	@Override
	public boolean closeAccount(BankAccount acc) {
		// TODO Auto-generated method stub
		return false;
	}

}
