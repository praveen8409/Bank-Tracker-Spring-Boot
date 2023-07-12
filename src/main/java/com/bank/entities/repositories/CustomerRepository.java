package com.bank.entities.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.bank.entities.Customer;

public interface CustomerRepository extends JpaRepositoryImplementation<Customer, Integer> {

	Customer findByCustomerId(int customerId);

	

	

}
