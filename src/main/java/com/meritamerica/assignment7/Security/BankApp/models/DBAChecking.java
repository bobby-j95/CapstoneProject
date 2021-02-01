package com.meritamerica.assignment7.Security.BankApp.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DBA_CHECKING")
public class DBAChecking extends BankAccount{


	private static final double INTEREST_RATE = .0001;

	public DBAChecking(double balance) {
		super(balance, INTEREST_RATE);
	}

	public DBAChecking() {
		super(INTEREST_RATE);
	}

	@Override
	public boolean closeAccount(BankAccount acc) {
		if(getBalance() > 0) {
			acc.setBalance(this.getBalance()*.8);
			this.setBalance(-this.getBalance());
			return true;
		}else {
			return false;
		}
	}

	

}
