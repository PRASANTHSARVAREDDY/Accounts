package com.Bankers.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bankers.banking_app.model.Account;

public interface AccountRepository extends JpaRepository<Account,Long>{

}
