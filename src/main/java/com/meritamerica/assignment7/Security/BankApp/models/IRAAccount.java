package com.meritamerica.assignment7.Security.BankApp.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("IRAACCOUNT")
public abstract class IRAAccount extends BankAccount{
	
	@Override
	public boolean closeAccount(BankAccount acc) {
		double negValue = -this.getBalance();
		if(getBalance() > 0) {
			acc.setBalance(this.getBalance()*.8);
			this.setBalance(negValue);
			return true;
		}else {
			return false;
		}
		
	}
	
}
