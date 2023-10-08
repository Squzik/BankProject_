package com.example.bankproject.service.impl;

import com.example.bankproject.service.BankAccountService;
import com.example.bankproject.store.entity.BankAccount;
import com.example.bankproject.store.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private final BankAccountRepository repository;

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return repository.findAll();
    }

    @Override
    public BankAccount createBankAccount(String name, int pin) {
        BankAccount account = new BankAccount();
        account.setName(name);
        account.setPin(pin);
        account.setBalance(0.0);
        return repository.save(account);
    }

    @Override
    public boolean withdrawMoney(Long accountId, int pin, double amount) {
        Optional<BankAccount> optionalAccount = repository.findById(accountId);
        if (optionalAccount.isPresent()) {
            BankAccount account = optionalAccount.get();
            if (account.getPin() == pin && account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                repository.save(account);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean depositMoney(Long accountId, int pin, double amount) {
        Optional<BankAccount> optionalAccount = repository.findById(accountId);
        if (optionalAccount.isPresent()) {
            BankAccount account = optionalAccount.get();
            if (account.getPin() == pin) {
                account.setBalance(account.getBalance() + amount);
                repository.save(account);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean transferMoney(Long sourceAccountId, int pin, Long targetAccountId, double amount) {
        if (withdrawMoney(sourceAccountId, pin, amount)) {
            if (depositMoney(targetAccountId, pin, amount)) {
                return true;
            } else {
                // Возврат денег на исходный счет, если не удалось зачислить на целевой
                depositMoney(sourceAccountId, pin, amount);
            }
        }
        return false;
    }
}
