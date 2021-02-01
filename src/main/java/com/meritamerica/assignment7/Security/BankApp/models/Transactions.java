package com.meritamerica.assignment7.Security.BankApp.models;

import java.text.*;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("TRANSACTION")
public class Transactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_number_generator")
	@Column(name = "transaction_id")
	private int tranNum;
	
	@ManyToOne
	@JoinColumn(name = "account_holder_id", nullable = false)
	@JsonIgnore
	private AccountHolder accountHolder;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	private Date dateobj = new Date();
	private double amount;
	private BankAccount sender;
	private BankAccount destination;
	
	Transactions(double amount, Date date, BankAccount sender, BankAccount destination){
		this.amount = amount;
		this.dateobj = date;
		this.sender = sender;
		this.destination = destination;
	}
	public int getTranNum() {
		return tranNum;
	}
	public void setTranNum(int tranNum) {
		this.tranNum = tranNum;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public Date getDateobj() {
		return dateobj;
	}
	public void setDateobj(Date dateobj) {
		this.dateobj = dateobj;
	}
	public String outputTransaction() {
		return "From: " + getSender() + " To: " + getDestination() + " On: " + df.format(dateobj.getTime());
	}
}
