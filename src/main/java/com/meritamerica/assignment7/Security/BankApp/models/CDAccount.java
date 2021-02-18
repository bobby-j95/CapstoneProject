package com.meritamerica.assignment7.Security.BankApp.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
@DiscriminatorValue("CD_ACCOUNT")
public class CDAccount extends BankAccount {

	private int term;

	@ManyToOne
	@JoinColumn(name = "offerings_id")
	private CDOffering cdOffering;

	public CDOffering getCdOffering() {
		return cdOffering;
	}

	public void setCdOffering(CDOffering cdOffering) {
		this.cdOffering = cdOffering;
	}

	public CDAccount() {
		super();
	}

	public CDAccount(double balance, double interestRate, int term) {
		super(balance, interestRate);
		this.term = term;

	}

	public int getTerm() {

		return this.term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double futureValue() {
		return (super.getBalance() * Math.pow(1.0 + cdOffering.getInterestRate(), cdOffering.getTerm()));
	}

	/*
	// compares the best value for CDAccount by its offerings and outputs that
	// offering
	public CDOffering getBestCDOffering(double depositAmount) {
		CDOffering temp = cdOffering[0];
		for (int x = 1; x < cdOffering.length; x++) {
			if (futureValue(depositAmount, cdOffering[x - 1].getInterestRate(),
					cdOffering[x - 1].getTerm()) < futureValue(depositAmount, cdOffering[x].getInterestRate(),
							cdOffering[x].getTerm())) {
				temp = cdOffering[x];
			}
		}
		return temp;
	}
	
	// compares the second best value for CDAccount by its offerings and outputs
	// that offering
	static CDOffering getSecondBestCDOffering(double depositAmount) {
		CDOffering temp = cdOffering[0];
		CDOffering temp2 = cdOffering[0];
		for (int x = 1; x < cdOffering.length; x++) {
			if (futureValue(depositAmount, cdOffering[x - 1].getInterestRate(),
				cdOffering[x - 1].getTerm()) < futureValue(depositAmount, cdOffering[x].getInterestRate(),
				cdOffering[x].getTerm())) {
				temp2 = temp;
				temp = cdOffering[x];
			}
		}
		return temp2;
	}
	 */
	
	@Override
	public boolean closeAccount(BankAccount acc) {
		// TODO Auto-generated method stub
		return false;
	}

}
