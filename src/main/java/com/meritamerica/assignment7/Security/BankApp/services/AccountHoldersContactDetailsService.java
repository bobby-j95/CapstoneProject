package com.meritamerica.assignment7.Security.BankApp.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.meritamerica.assignment7.Security.BankApp.models.AccountHoldersContactDetails;
import com.meritamerica.assignment7.Security.BankApp.repositories.AccountHoldersContactDetailsRepository;



public class AccountHoldersContactDetailsService {

	@Autowired
	 private AccountHoldersContactDetailsRepository repository;
	
	public AccountHoldersContactDetails addContactDetails(AccountHoldersContactDetails contact) {
		return repository.save(contact);
	}
}
