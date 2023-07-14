package com.bank.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entities.Account;
import com.bank.entities.Customer;
import com.bank.entities.repositories.AccountRepository;
import com.bank.entities.repositories.CustomerRepository;
import com.bank.exceptions.AccountNotFoundException;
import com.bank.exceptions.CustomerNotFoundException;
import com.bank.exceptions.IdNotFoundException;
import com.bank.services.CustomerService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	
	
	// creating a new customer
	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer){
		System.out.println(customer); 
		customerService.saveCustomer(customer);
		return new ResponseEntity<>(customer, HttpStatus.CREATED);
	}
	
	// Get all Customer from database
	@GetMapping("/customerList")
	public ResponseEntity<List<Customer>> getAllCustomers() throws CustomerNotFoundException{
		if(customerService.getAllCustomers().isEmpty()) {
			throw new CustomerNotFoundException();
		}
		
		return new ResponseEntity<List<Customer>>(customerService.getAllCustomers(), HttpStatus.ACCEPTED);
	}
	
		// Get Customer by its Id
	@GetMapping("/getCustomerById/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") int customerId) throws IdNotFoundException{
		if(customerService.getCustomerById(customerId) == null) {
			throw new IdNotFoundException();
			
		}
		Customer customer = customerService.getCustomerById(customerId);
		return new ResponseEntity<Customer>(customer, HttpStatus.ACCEPTED);
	}
	
	// Update Customer details by its Id
	@PutMapping("/updateById/{customerId}")
	public ResponseEntity<?> updateCustomerById(@PathVariable("customerId") int customerId, @RequestBody Customer customer) throws IdNotFoundException{
		if(customerService.getCustomerById(customerId) == null) {
			throw new IdNotFoundException();	
		}
		customerService.updateCustomerById(customerId, customer);
		
		return new ResponseEntity<String>("Details Updated Successfully!!", HttpStatus.OK);
		
	}
	
	// Delete customer from database by its id
	@DeleteMapping("/deleteById/{customerId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") int customerId) throws IdNotFoundException{
		if(customerService.getCustomerById(customerId) == null) {
			throw new IdNotFoundException();	
		}
		
		customerService.deleteCustomerById(customerId);
		return new ResponseEntity<String>("Customer Deleted Successfully!!", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteAllCustomers")
	public ResponseEntity<?> deleteAllCustomers() throws CustomerNotFoundException{
		if(customerService.getAllCustomers().isEmpty()) {
			throw new CustomerNotFoundException();
		}
		 customerService.deleteAllCustomers();
		
		return new ResponseEntity<String>("Deleted all Customers Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/withdraw/{customerId}")
	public ResponseEntity<?> withdraw(@Valid @PathVariable("customerId") int customerId, @RequestBody ObjectNode obj) throws AccountNotFoundException{
		System.out.println("Object :" + obj);
		
		String res = "";
		int minBalance = 500;
		
		int accountNumber = obj.get("accountNumber").asInt();
		int balance = obj.get("balance").asInt();
		
		Account account = accountRepository.findByAccountNumber(accountNumber);
		
		if(! accountRepository.existsById(accountNumber)) throw new AccountNotFoundException();
		
		double customerBalance = account.getBalance();
		if(customerBalance > minBalance && customerBalance >= balance) {
			res = "Withdrawn Successfully";
			customerService.withdraw(customerId, accountNumber, balance);
		}
		else {
			res = "Insufficient Balance";
		}
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
		
	}
	
	@PostMapping("/credit/{customerId}")
	public ResponseEntity<?> credit(@PathVariable ("customerId") int customerId, @RequestBody ObjectNode obj) throws AccountNotFoundException{
		System.out.println("Object" + obj);
		
		int accountNumber = obj.get("accountNumber").asInt();
		int balance = obj.get("balance").asInt();
		Account account = accountRepository.findByAccountNumber(accountNumber);
		
		if(! accountRepository.existsById(accountNumber)) throw new AccountNotFoundException();
		
		customerService.credit(customerId, accountNumber, balance);
		
		return new ResponseEntity<String>("Credit Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/transfer/fund/{fromAccount}/{toAccount}/{ammount}")
	public ResponseEntity<?> transferFund(@PathVariable("fromAccount") int fromAccount, @PathVariable("toAccount") int toAccount, @PathVariable("ammount") int ammount) throws AccountNotFoundException{
		
		Account account1 = accountRepository.findByAccountNumber(fromAccount);
		if(! accountRepository.existsById(fromAccount)) throw new AccountNotFoundException();
		
		Account account2 = accountRepository.findByAccountNumber(fromAccount);
		if(! accountRepository.existsById(toAccount)) throw new AccountNotFoundException();
		
		double customer1Balance = account1.getBalance();
		
		String res = "";
		int minBal = 500;
		
		if( customer1Balance >= ammount + minBal) {
			res = "Transfer Successfully";
			customerService.transfer(fromAccount, toAccount, ammount);
		}else {
			res = "Insufficient Balance";
		}
		
		return new ResponseEntity<String>(res ,HttpStatus.OK);
		
	}
}
