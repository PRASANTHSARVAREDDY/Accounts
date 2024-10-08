package com.Bankers.banking_app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bankers.banking_app.dto.AccountDto;
import com.Bankers.banking_app.mapper.AccountMapper;
import com.Bankers.banking_app.model.Account;
import com.Bankers.banking_app.repository.AccountRepository;
import com.Bankers.banking_app.service.AccountService;
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {

		this.accountRepository=accountRepository;
	}
	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account=AccountMapper.mapToAccount(accountDto);
		Account savedAccount=accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}
	@Override
	public AccountDto getAccountById(Long id) {
		Account account =accountRepository.
				findById(id).
				orElseThrow(()->new RuntimeException("Account Does not exists"));
		return AccountMapper.mapToAccountDto(account);
	}
	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account=accountRepository.
				findById(id).
				orElseThrow(()->new RuntimeException("Account Does Not Exists"));
		double total=account.getBalance()+amount;
		account.setBalance(total);
		Account savedAccount=accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);  
	}
	@Override
	public AccountDto withdraw(Long id,double amount) {
		Account account=accountRepository
				.findById(id)
				.orElseThrow(()->new RuntimeException("Account Does not exists"));
		if(account.getBalance()<amount) {
			throw new RuntimeException("Insufficient Amount");
		}
		double total=account.getBalance()-amount;		
		account.setBalance(total);
		Account savedAccount=accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}
	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts =accountRepository.findAll();
		
		return accounts.stream().map((account)->AccountMapper
				.mapToAccountDto(account)).
				collect(Collectors.toList());
	}
	@Override
	public String deleteAccount(Long id) {
		Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account Does not Exists"));
		accountRepository.deleteById(id);
		return "Account Deleted Sucessfully";
	}

}
