package com.meritamerica.assignment7.Security.BankApp.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment7.Security.BankApp.exceptions.ExceedsCombinedLimitException;
import com.meritamerica.assignment7.Security.BankApp.exceptions.NoSuchResourceFoundException;
import com.meritamerica.assignment7.Security.BankApp.models.AccountHolder;
import com.meritamerica.assignment7.Security.BankApp.models.AccountHoldersContactDetails;
import com.meritamerica.assignment7.Security.BankApp.models.CDAccount;
import com.meritamerica.assignment7.Security.BankApp.models.CDOffering;
import com.meritamerica.assignment7.Security.BankApp.models.CheckingAccount;
import com.meritamerica.assignment7.Security.BankApp.models.SavingsAccount;
import com.meritamerica.assignment7.Security.BankApp.models.Users;
import com.meritamerica.assignment7.Security.BankApp.repositories.AccountHolderRepository;
import com.meritamerica.assignment7.Security.BankApp.repositories.CDAccountRepository;
import com.meritamerica.assignment7.Security.BankApp.repositories.CDOfferingRepository;
import com.meritamerica.assignment7.Security.BankApp.repositories.CheckingAccountRepository;
import com.meritamerica.assignment7.Security.BankApp.repositories.SavingsAccountRepository;
import com.meritamerica.assignment7.Security.BankApp.repositories.UsersRepository;

@Service
public class AccountHolderService {
	
	protected static final double MAX_DEPOSIT_AMOUNT = 250000;
	Logger log = LoggerFactory.getLogger(this.getClass()); 	
	
	@Autowired
	AccountHolderRepository repository;
	
	@Autowired
	CheckingAccountRepository checkingRepository;
	
	@Autowired
	CDAccountRepository cdAccountRepository;
	
	@Autowired 
	CDOfferingRepository cdOfferingRepository;
	
	@Autowired
	SavingsAccountRepository savingsRepository;
	
	@Autowired
	UsersRepository usersRepository;

	@Autowired
	UsersDetailsService userService;
	
	
	public AccountHolder postAccountHolder(AccountHolder accountHolder) {
		AccountHoldersContactDetails contact = accountHolder.getContact();
		Users user = checkUserValid(accountHolder.getUser().getId());
		user.setAccountHolder(accountHolder);
		contact.setAccountHolder(accountHolder);
		return repository.save(accountHolder);
	}
	
	public List<AccountHolder> getAccountHolders(){
		return repository.findAll();
	}
	
	public AccountHolder findById(int id) throws NoSuchResourceFoundException {
		return repository.findById(id).orElseThrow(() -> new NoSuchResourceFoundException("Invalid Account Holder Id"));
	}
	
	public CDOffering findCDOfferingById(int id) throws NoSuchResourceFoundException {
		return cdOfferingRepository.findById(id).orElseThrow(() -> new NoSuchResourceFoundException("Invalid CDOffering Id"));
	}
	
	public CheckingAccount postCheckingAccount(int id, CheckingAccount checkingAccount) throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder ah = findById(id);
		Double balance = checkingAccount.getBalance();
		if (ah.combinedBalance() + balance >= MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}
		else {	
			log.info("Account balance is : " + ah.combinedBalance());
			log.info("Added " + balance);
			checkingAccount.setAccountHolder(ah);
		return checkingRepository.save(checkingAccount);
		}
	}
	
	public SavingsAccount postSavingsAccount(int id, SavingsAccount savingsAccount) throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder ah = findById(id);
		Double balance = savingsAccount.getBalance();	
		if (ah.combinedBalance() + balance >= MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}
		else {
			savingsAccount.setAccountHolder(ah);
			return savingsRepository.save(savingsAccount);
		}
	}

	public CDAccount postCDAccount(int id, @Valid CDAccount cdAccount) throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder ah = findById(id);
		CDOffering cdO = findCDOfferingById(cdAccount.getCdOffering().getId());
		Double balance = cdAccount.getBalance();
		if (ah.combinedBalance() + balance >= MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}else {
			cdAccount.setAccountHolder(ah);
			cdAccount.setCdOffering(cdO);
			cdAccount.setTerm(cdO.getTerm());
			cdAccount.setInterestRate(cdO.getInterestRate());
			return cdAccountRepository.save(cdAccount);
		}
		
	}
	
	public CDOffering postCDOffering(@Valid CDOffering cdOffering) {
		return cdOfferingRepository.save(cdOffering);
	}

	public List<CheckingAccount> getCheckingAccount(int id) throws NoSuchResourceFoundException {
		AccountHolder ah = findById(id);
		return ah.getCheckingAccounts();
	}

	public List<SavingsAccount> getSavingsAccount(int id) throws NoSuchResourceFoundException {
		AccountHolder ah = findById(id);
		return ah.getSavingsAccounts();
	}

	public List<CDAccount> getCDAccounts(int id) throws NoSuchResourceFoundException {
		AccountHolder ah = findById(id);
		return ah.getCdAccounts();
	}
	
	public List<CDOffering> getCDOfferings(){
		return cdOfferingRepository.findAll();
	}
	
	
	public String deleteAccountHolder(int id) {
		String msg = "Account " + id+ " Deleted";
		repository.deleteById(id);
		return msg;
	}
	
	public CheckingAccount postCheckingAccount( CheckingAccount checkingAccount) throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		
		return checkingRepository.save(checkingAccount);
		
	}
	
	
	public Users checkUserValid(int id){
		Optional<Users> user = userService.loadUserById(id);
		try {
			return user.get();
		}catch(NoSuchElementException e) {
			throw e;
		}
	}
	
	
	
	
	/*
	public Boolean exceedsOrNot(double combinedBalance, double balance) throws ExceedsCombinedLimitException {
		if (combinedBalance + balance > MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}
		else {
			return true;
		}
	}
	*/
	
}
