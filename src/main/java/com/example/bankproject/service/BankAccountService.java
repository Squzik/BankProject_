package com.example.bankproject.service;

import com.example.bankproject.store.entity.BankAccount;

import java.util.List;

public interface BankAccountService {
    List<BankAccount> getAllBankAccounts();
    BankAccount createBankAccount(String name, int pin);
    boolean withdrawMoney(Long accountId, int pin, double amount);
    boolean depositMoney(Long accountId, int pin, double amount);
    boolean transferMoney(Long sourceAccountId, int pin, Long targetAccountId, double amount);

}
