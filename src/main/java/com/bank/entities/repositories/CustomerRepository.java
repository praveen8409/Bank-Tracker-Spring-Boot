package com.bank.entities.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.bank.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepositoryImplementation<Customer, Integer> {

	 Customer findByCustomerId(int customerId);

	 Customer findByAccountAccountNumber(int accountNumber);
	  

	

	

}
