package com.Bankers.banking_app.controllerTest;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.Bankers.banking_app.controller.AccountController;
import com.Bankers.banking_app.dto.AccountDto;
import com.Bankers.banking_app.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private AccountDto accountDto;

    @BeforeEach
    void setup() {
        accountDto = new AccountDto(25L, "Prasanth", 5000);
    }

    @Test
    public void testAddAccount() throws Exception {
        when(accountService.createAccount(any(AccountDto.class))).thenReturn(accountDto);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(accountDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(accountDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountHolderName", is(accountDto.getAccountHolderName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance", is(accountDto.getBalance())));
    }

    @Test
    public void testGetAccountById() throws Exception {
        when(accountService.getAccountById(25L)).thenReturn(accountDto);

        mockMvc.perform(get("/api/accounts/{id}", 25L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(accountDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountHolderName", is(accountDto.getAccountHolderName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance", is(accountDto.getBalance())));
    }

    @Test
    public void testDeposit() throws Exception {
        Map<String, Double> request = new HashMap<>();
        request.put("amount", 1000.0);
        when(accountService.deposit(25L, 1000.0)).thenReturn(accountDto);

        mockMvc.perform(put("/api/accounts/{id}/deposit", 25L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance", is(accountDto.getBalance())));
    }

    @Test
    public void testWithdraw() throws Exception {
        Map<String, Integer> request = new HashMap<>();
        request.put("amount", 500);
        when(accountService.withdraw(25L, 500)).thenReturn(accountDto);

        mockMvc.perform(put("/api/accounts/{id}/withdraw", 25L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance", is(accountDto.getBalance())));
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        List<AccountDto> accounts = List.of(accountDto);
        when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(accountDto.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].accountHolderName", is(accountDto.getAccountHolderName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].balance", is(accountDto.getBalance())));
    }

    @Test
    public void testDeleteAccount() throws Exception {
        when(accountService.deleteAccount(25L)).thenReturn("Account deleted successfully");

        mockMvc.perform(delete("/api/accounts/{id}", 25L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Account deleted successfully"));
    }
}
