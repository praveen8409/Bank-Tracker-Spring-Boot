package com.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entities.Account;
import com.bank.entities.repositories.AccountRepository;
import com.bank.entities.repositories.CustomerRepository;

@Service
public class AccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	
	

	public Account saveAccount(Account account) {
		
		return accountRepository.save(account);
		
	}


	public List<Account> getAllAcounts() {
		// TODO Auto-generated method stub
		return accountRepository.findAll();
	}



	public Account getAccount(int accountNumber) {
		// TODO Auto-generated method stub
		return accountRepository.findByAccountNumber(accountNumber);
	}


	public void deleteAcoount(int accountNumber) {
		// TODO Auto-generated method stub
		Account account = accountRepository.findByAccountNumber(accountNumber);
		accountRepository.delete(account);
	}


	

}
