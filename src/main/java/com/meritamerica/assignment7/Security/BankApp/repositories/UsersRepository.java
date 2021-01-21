package com.meritamerica.assignment7.Security.BankApp.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment7.Security.BankApp.models.Users;


public interface UsersRepository extends JpaRepository<Users, Integer>{

	Optional<Users> findByUsername(String username);
	

}
