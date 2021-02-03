package com.meritamerica.assignment7.Security.BankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment7.Security.BankApp.models.AccountHolder;
import com.meritamerica.assignment7.Security.BankApp.models.DBAChecking;

public interface DBACheckingAccountRepository extends JpaRepository<DBAChecking, Integer> {

}
