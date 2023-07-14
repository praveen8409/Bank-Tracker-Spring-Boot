package com.bank.entities.repositories;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.bank.entities.Account;

@Repository
public interface AccountRepository extends JpaRepositoryImplementation<Account, Integer>{

	

	Account findByAccountNumber(int accountNumber);

	

	

}
