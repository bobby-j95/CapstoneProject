package com.meritamerica.assignment7.Security.BankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment7.Security.BankApp.models.SavingsAccount;


public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Integer> {

}
