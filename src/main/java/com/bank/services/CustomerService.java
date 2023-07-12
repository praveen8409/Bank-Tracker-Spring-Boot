package com.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.entities.Customer;
import com.bank.entities.repositories.CustomerRepository;


@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
}
