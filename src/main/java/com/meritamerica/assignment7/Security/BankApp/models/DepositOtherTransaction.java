package com.meritamerica.assignment7.Security.BankApp.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositOtherTransaction extends Transactions{

	private String sender;
	private BankAccount destination;
	private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

	DepositOtherTransaction(double amount, Date date, String sender){
		super(amount, date);
		this.setSender(sender);
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public BankAccount getDestination() {
		return destination;
	}
	public void setDestination(BankAccount destination) {
		this.destination = destination;
	}
	
	@Override
	public String outputTransaction() {
		return "From: " + getSender() + " To: " + getDestination() + " On: " + df.format(getDateobj().getTime());
	}
}
