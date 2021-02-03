package com.meritamerica.assignment7.Security.BankApp.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.annotations.DiscriminatorOptions;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ACCOUNT_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = true)

//@MappedSuperclass
public abstract class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_generator")
	@Column(name = "account_id")
	private int accountNumber;

	@ManyToOne
	@JoinColumn(name = "account_holder_id", nullable = false)
	@JsonIgnore
	private AccountHolder accountHolder;
	
	@OneToMany
	private List<Transactions> transaction;

	@Min(value = 0L, message = "Balance Lower Than 0 Exception")
	private double balance;

	// @DecimalMin(value = "0.0", inclusive = false, message = "Interest must be
	// greater than 0")
	// @DecimalMax(value = "1.0", inclusive = false, message = "Interest rate must
	// be lower than 1")
	private double interestRate;
	
	private String openedOn;

	public BankAccount() {

		openedOn = "1234566";
	}

	public BankAccount(double interestRate) {

		this.balance = 0;
		this.interestRate = interestRate;
		openedOn = "123123141423";
	}

	public BankAccount(double balance, double interestRate) {

		this.balance = balance;
		this.interestRate = interestRate;
		openedOn = "123123141423";
	}

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance += balance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOpenedOn() {
		return openedOn;
	}

	public void setOpenedOn(String openedOn) {
		this.openedOn = openedOn;
	}
	
	public void makeTransaction(@RequestBody @Valid String kindOfTrans, @RequestBody @Valid String otherPlace, double amount, BankAccount acc) {
		if(kindOfTrans.equalsIgnoreCase("Deposit")) {
			if(otherPlace.equalsIgnoreCase("Transfer")) {
				Date dateobj = new Date();
				DepositTransferTransactions t = new DepositTransferTransactions(amount, dateobj, acc, this);
				transaction.add(t);
			} else {
				Date dateobj = new Date();
				DepositOtherTransaction t = new DepositOtherTransaction(amount, dateobj, otherPlace);
				transaction.add(t);
			}
		} else {
			//withdraw
		}
		
	}
	public abstract boolean closeAccount(BankAccount acc);
}
