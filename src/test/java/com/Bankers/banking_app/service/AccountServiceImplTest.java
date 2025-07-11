package com.Bankers.banking_app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.Bankers.banking_app.dto.AccountDto;
import com.Bankers.banking_app.model.Account;
import com.Bankers.banking_app.repository.AccountRepository;
import com.Bankers.banking_app.service.impl.AccountServiceImpl;

public class AccountServiceImplTest {
	@Mock
	private AccountRepository accountRepository;
	@InjectMocks
	private AccountServiceImpl accountServiceImpl;
	AutoCloseable autoCloseable;
	Account account;
	AccountDto accountDto;
	@BeforeEach
	void setup() {
		autoCloseable=MockitoAnnotations.openMocks(this);
		account =new Account(1L,"Prasanth",5000);
		accountDto=new AccountDto(1L,"Prasanth",5000);
	}
	@AfterEach
	void tearDown() throws Exception{
		autoCloseable.close();
	}
	@Test
	public void testCreateAccount() {
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		AccountDto createdAccount=accountServiceImpl.createAccount(accountDto);
		assertThat(createdAccount.getId()).isEqualTo(account.getId());
}
	@Test
	public void testAccountAccountById() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		AccountDto foundAccount=accountServiceImpl.getAccountById(1L);
		assertThat(foundAccount.getId()).isEqualTo(account.getId());
	}
	@Test
	public void testDeposit(){
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		AccountDto updatedAccount=accountServiceImpl.deposit(1L,1000);
		assertThat(updatedAccount.getId()).isEqualTo(account.getId());
		assertThat(updatedAccount.getBalance()).isEqualTo(6000);
	}
	@Test
	public void testWithdraw() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		AccountDto updatedAccount=accountServiceImpl.withdraw(1L,1000);
		assertThat(updatedAccount.getId()).isEqualTo(1L);
		assertThat(updatedAccount.getBalance()).isEqualTo(4000);
	}
	@Test
	public void testDeleteAccount() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		String response=accountServiceImpl.deleteAccount(1L);
		assertThat(response).isEqualTo("Account Deleted Sucessfully");
	}
	@Test
	public void testGetAllAccounts() {
	    List<Account> accountsList = new ArrayList<>();
	    accountsList.add(account);
	    when(accountRepository.findAll()).thenReturn(accountsList); 

	    List<AccountDto> accounts = accountServiceImpl.getAllAccounts();

	    assertThat(accounts).hasSize(1);
	    assertThat(accounts.get(0).getId()).isEqualTo(account.getId());
	    verify(accountRepository, times(1)).findAll();
	}
	@Test
	public void testWithdrawInsufficientFunds() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		 Exception exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
	            accountServiceImpl.withdraw(1L, 6000);
	        });
		 assertThat(exception.getMessage()).isEqualTo("Insufficient Amount");
	}
	@Test
	public void testGetAccountByIdNotFound() {
		when(accountRepository.findById(null)).thenReturn(Optional.empty());
		 Exception exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
	            accountServiceImpl.getAccountById(1L);
	        });
		assertThat(exception.getMessage()).isEqualTo("Account Does not exists");
	}
}
