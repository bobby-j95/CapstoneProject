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
import com.meritamerica.assignment7.Security.BankApp.models.*;
import com.meritamerica.assignment7.Security.BankApp.repositories.*;

@Service
public class AccountHolderService {
	
	protected static final double MAX_DEPOSIT_AMOUNT = 250000;
	Logger log = LoggerFactory.getLogger(this.getClass()); 	
	
	@Autowired
	private AccountHolderRepository repository;
	
	@Autowired
	private CheckingAccountRepository checkingRepository;
	
	@Autowired
	private DBACheckingAccountRepository dbaCheckingRepository;
	
	@Autowired
	private CDAccountRepository cdAccountRepository;
	
	@Autowired 
	private CDOfferingRepository cdOfferingRepository;
	
	@Autowired
	private SavingsAccountRepository savingsRepository;
	
	@Autowired
	private IRAAccountRepository iraRepository;
	
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UsersDetailsService userService;
	
	
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
	
	public DBAChecking postDBACheckingAccount(int id, DBAChecking dbaCheckingAccount) throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder ah = findById(id);
		Double balance = dbaCheckingAccount.getBalance();
		if (ah.combinedBalance() + balance >= MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}
		else {	
			log.info("Account balance is : " + ah.combinedBalance());
			log.info("Added " + balance);
			dbaCheckingAccount.setAccountHolder(ah);
		return dbaCheckingRepository.save(dbaCheckingAccount);
		}
	}
	
	public IRAAccount postIRAAccount(int id, IRAAccount iraAccount) throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder ah = findById(id);
		Double balance = iraAccount.getBalance();
		if (ah.combinedBalance() + balance >= MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}
		else {	
			log.info("Account balance is : " + ah.combinedBalance());
			log.info("Added " + balance);
			iraAccount.setAccountHolder(ah);
		return iraRepository.save(iraAccount);
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
	
	public List<DBAChecking> getDBACheckingAccount(int id) throws NoSuchResourceFoundException {
		AccountHolder ah = findById(id);
		return ah.getDBACheckingAccounts();
	}
	
	public List<IRAAccount> getIRAAccount(int id) throws NoSuchResourceFoundException {
		AccountHolder ah = findById(id);
		return ah.getIRAAccounts();
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
	
	public String deleteCheckingAccount(int id) {
		String msg = "Checking Account " + id+ " Deleted";
		checkingRepository.deleteById(id);
		return msg;
	}
	
	public String deleteDBACheckingAccount(int id) {
		String msg = "DBA Checking Account " + id+ " Deleted";
		dbaCheckingRepository.deleteById(id);
		return msg;
	}
	
	public String deleteSavingsAccount(int id) {
		String msg = "Savings Account " + id+ " Deleted";
		savingsRepository.deleteById(id);
		return msg;
	}
	
	public String deleteIRAAccount(int id) {
		String msg = "IRA Account " + id+ " Deleted";
		iraRepository.deleteById(id);
		return msg;
	}
	
	public String deleteCDAccount(int id) {
		String msg = "CD Account " + id+ " Deleted";
		cdAccountRepository.deleteById(id);
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
