package com.meritamerica.assignment7.Security.BankApp.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositTransferTransactions extends Transactions{

	private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	private BankAccount sender;
	private BankAccount destination;
	
	DepositTransferTransactions(double amount, Date date, BankAccount sender, BankAccount destination){
		super(amount, date);
		this.sender = sender;
		this.destination = destination;
	}
	
	public BankAccount getSender() {
		return sender;
	}
	public void setSender(BankAccount sender) {
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
