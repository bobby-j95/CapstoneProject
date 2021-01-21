package com.meritamerica.assignment7.Security.BankApp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meritamerica.assignment7.Security.BankApp.models.MyUserDetails;
import com.meritamerica.assignment7.Security.BankApp.models.Users;
import com.meritamerica.assignment7.Security.BankApp.repositories.UsersRepository;

@Service
public class UsersDetailsService implements UserDetailsService  {

	@Autowired
	UsersRepository usersRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> user = usersRepository.findByUsername(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException(
										"Did not find a username " +  username));
		return user.map(MyUserDetails::new).get();
	}
	
	public Optional<Users> loadUserById(int id) {
		return usersRepository.findById(id);
	}
		
	public Users postNewUser(Users user) {
		return usersRepository.save(user);
	}
	
	
	
	
	
	
	
	
	
}
