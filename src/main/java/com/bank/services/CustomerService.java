package com.bank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	
	
}
