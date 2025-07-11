package com.Bankers.banking_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Bankers.banking_app.dto.AccountDto;
@Service
public interface AccountService {
 AccountDto createAccount(AccountDto accountDto);
 AccountDto getAccountById(Long id);
 AccountDto deposit(Long id, double amount);
 AccountDto withdraw(Long id,double amount);
 List<AccountDto> getAllAccounts();
 String deleteAccount(Long id);
}
