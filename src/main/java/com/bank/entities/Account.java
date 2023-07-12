package com.bank.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {

	@Id
	Integer accountNumber;
	int balance;
	String accountType;
	
}
