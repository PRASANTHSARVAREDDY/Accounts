package com.Bankers.banking_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Table(name="account")
@Entity
public class Account {
public Account(Long id, String accountHolderName, double balance) {
		super();
		this.id = id;
		this.accountHolderName = accountHolderName;
		this.balance = balance;
	}

public Account() {
	super();
}

@Id // To identify this as a primary key in database
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
@Column(name="account_holder_name")
private String accountHolderName;
@Column(name="Balance")
private double balance;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getAccountHolderName() {
	return accountHolderName;
}
public void setAccountHolderName(String accountHolderName) {
	this.accountHolderName = accountHolderName;
}
public double getBalance() {
	return balance;
}
public void setBalance(double balance) {
	this.balance = balance;
}


}
