package com.bank.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entities.Customer;
import com.bank.exceptions.CustomerNotFoundException;
import com.bank.services.CustomerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer){
		System.out.println(customer); 
		customerService.saveCustomer(customer);
		return new ResponseEntity<>(customer, HttpStatus.CREATED);
	}
	
	@GetMapping("/customerList")
	public ResponseEntity<List<Customer>> getAllCustomers() throws CustomerNotFoundException{
		if(customerService.getAllCustomers().isEmpty()) {
			throw new CustomerNotFoundException();
		}
		
		return new ResponseEntity<List<Customer>>(customerService.getAllCustomers(), HttpStatus.ACCEPTED);
	}
}
