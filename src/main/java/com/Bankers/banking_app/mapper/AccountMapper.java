package com.Bankers.banking_app.mapper;

import com.Bankers.banking_app.dto.AccountDto;
import com.Bankers.banking_app.model.Account;
//Account Mapper

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto) {
    	 if (accountDto == null) {
    	        throw new IllegalArgumentException("AccountDto cannot be null");
    	    }
        Account account = new Account(
            accountDto.getId(),                    
            accountDto.getAccountHolderName(),    
            accountDto.getBalance()                 
        );
        return account;
    }

    public static AccountDto mapToAccountDto(Account account) {
    	if(account==null) {
    		 throw new IllegalArgumentException("Account cannot be null");
    	}
        AccountDto accountDto = new AccountDto(
            account.getId(),                  
            account.getAccountHolderName(), 
            account.getBalance()                      
        );
        return accountDto;
    }
}
