package com.bank.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entities.Account;
import com.bank.entities.Customer;
import com.bank.entities.repositories.AccountRepository;
import com.bank.entities.repositories.CustomerRepository;
import com.bank.exceptions.AccountNotFoundException;
import com.bank.exceptions.CustomerNotFoundException;
import com.bank.services.AccountService;
import com.bank.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerRepository customerRepository;
	
	
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createAccount(@Valid @RequestBody Account account){
		System.out.println(account);
		
		Account acc = accountService.saveAccount(account);
		return new ResponseEntity<Account>(acc, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/accountList")
	public ResponseEntity<List<Account>> getAllACcounts(){
		if(accountService.getAllAcounts().isEmpty()) {
			throw new AccountNotFoundException();
		}
		
		return new ResponseEntity<List<Account>>(accountService.getAllAcounts(), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getAccount/{accountNumber}")
	public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable("accountNumber") int accountNumber) {
		if(accountService.getAccount(accountNumber) == null) {
			throw new AccountNotFoundException();
		}
		Account acc = accountService.getAccount(accountNumber);
		 
		return new ResponseEntity<Account>(acc, HttpStatus.FOUND);
				
	}
	
	 @GetMapping("/getCustomer/{accountNumber}")
	    public ResponseEntity<Customer> getCustomerByAccountNumber(@PathVariable("accountNumber") int accountNumber){
	        Customer customer = customerRepository.findByAccountAccountNumber(accountNumber);
	        if (customer == null) {
	            throw new CustomerNotFoundException();
	        }
	        return new ResponseEntity<Customer>(customer, HttpStatus.FOUND);
	    }
	
	
	
	@DeleteMapping("/delete/{accountNumber}")
	public ResponseEntity<String> deleteAccountByAccountNumber(@PathVariable ("accountNumber")int accountNumber){
		accountService.deleteAcoount(accountNumber);
		return new ResponseEntity<String>("Acoount Deleted Successfully", HttpStatus.OK);
	}
	


	
	

}
