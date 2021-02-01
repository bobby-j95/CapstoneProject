package com.meritamerica.assignment7.Security.BankApp.models;


public abstract class IRAAccount extends BankAccount{
	
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
