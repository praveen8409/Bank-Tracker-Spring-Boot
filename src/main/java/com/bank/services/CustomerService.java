package com.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.entities.Account;
import com.bank.entities.Customer;
import com.bank.entities.repositories.AccountRepository;
import com.bank.entities.repositories.CustomerRepository;


@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
	
	public Customer getCustomerById(int customerId) {
		return customerRepository.findByCustomerId(customerId);
	}

	public Customer updateCustomerById(int customerId, Customer customer) {
		// TODO Auto-generated method stub
		Customer cust = customerRepository.findByCustomerId(customerId);
		cust.setCustomerName(customer.getCustomerName());
		cust.setCustomerEmail(customer.getCustomerEmail());
		System.out.println("Updated details are!!" + cust);
		return customerRepository.save(cust);
		
	}
	
	
	public void  deleteCustomerById(int customerId) {
		Customer customer = customerRepository.findByCustomerId(customerId);
		customerRepository.delete(customer);
	}

	public void deleteAllCustomers() {
		
		 customerRepository.deleteAll();
	}

	public void withdraw(int customerId, int accountNumber, int balance) {
		// TODO Auto-generated method stub
		
		Customer customer = customerRepository.findByCustomerId(customerId);
		Account account = accountRepository.findByAccountNumber(accountNumber);
		int customerBalance = customer.getAccount().getBalance();
		System.out.println(customerBalance);
		int minBalance = 500;
		
		if(customerBalance > minBalance && customerBalance >= balance) {
			int newBal = customerBalance - balance;
			
			customer.getAccount().setBalance(newBal);
			customerRepository.save(customer);
			accountRepository.save(account);
		}else {
			System.out.println("Insufficient Balance");
		}
	
	}

	public void credit(int customerId, int accountNumber, int balance) {
		// TODO Auto-generated method stub
		
		Customer customer = customerRepository.findByCustomerId(customerId);
		Account account = accountRepository.findByAccountNumber(accountNumber);
		int customerBalance = customer.getAccount().getBalance();
		System.out.println(customerBalance);	
		
			int newBal = customerBalance + balance;
			
			customer.getAccount().setBalance(newBal);
			customerRepository.save(customer);
			accountRepository.save(account);	
	}

	public void transfer(int fromAccount, int toAccount, int ammount) {
		// TODO Auto-generated method stub
		Account account1 = accountRepository.findByAccountNumber(fromAccount);
		Account account2 = accountRepository.findByAccountNumber(toAccount);
		
		int customer1Balance = account1.getBalance();
		int customer2Balance = account2.getBalance();
		
		int minBalance = 500;
		
		
		if( customer1Balance >= minBalance + ammount) {
			int availableCustomer1Balance = customer1Balance - ammount;
			account1.setBalance(availableCustomer1Balance);
			accountRepository.save(account1);
			
			int availableCustomer2Balance = customer2Balance + ammount;
			account2.setBalance(availableCustomer2Balance);
			accountRepository.save(account2);
		}else {
			System.out.println("Insufficient Balance");
		}
	}
	
	
	

	
	
	
}
