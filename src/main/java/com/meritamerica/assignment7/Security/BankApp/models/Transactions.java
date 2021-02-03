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
public abstract class Transactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_number_generator")
	@Column(name = "transaction_id")
	private int tranNum;
	
	
	private Date dateobj = new Date();
	private double amount;
	
	Transactions(double amount, Date date){
		this.amount = amount;
		this.dateobj = date;
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
	public Date getDateobj() {
		return dateobj;
	}
	public void setDateobj(Date dateobj) {
		this.dateobj = dateobj;
	}
	public abstract String outputTransaction();
}
