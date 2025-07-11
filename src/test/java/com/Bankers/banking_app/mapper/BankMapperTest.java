package com.Bankers.banking_app.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.Bankers.banking_app.dto.AccountDto;
import com.Bankers.banking_app.model.Account;

public class BankMapperTest {
	@Test
	public void testMapToAccount() {
		AccountDto accountDto=new AccountDto(1L,"Prasanth",1000.0);
		Account account=AccountMapper.mapToAccount(accountDto);
		assertThat(account).isNotNull();
		assertThat(account.getId()).isEqualTo(1L);
		assertThat(account.getAccountHolderName()).isEqualTo("Prasanth");
	}
	@Test
	public void testMapToAccountDto() {
		Account account=new Account(1L,"Sarva",2000.0);
		AccountDto accountDto=AccountMapper.mapToAccountDto(account);
		assertThat(accountDto).isNotNull();
		assertThat(accountDto.getId()).isEqualTo(1L);
		assertThat(accountDto.getAccountHolderName()).isEqualTo("Sarva");
	}
}
