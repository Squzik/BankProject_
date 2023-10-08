package com.example.bankproject.rest;

import com.example.bankproject.service.BankAccountService;
import com.example.bankproject.store.entity.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank-accounts")
@RequiredArgsConstructor
public class BankAccountController {

    @Autowired
    private final BankAccountService service;

    @GetMapping("/")
    public List<BankAccount> getAllBankAccounts() {
        return service.getAllBankAccounts();
    }

    @PostMapping("/")
    public BankAccount createBankAccount(@RequestParam String name, @RequestParam int pin) {
        return service.createBankAccount(name, pin);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawMoney(@RequestParam Long accountId, @RequestParam int pin, @RequestParam double amount) {
        if (service.withdrawMoney(accountId, pin, amount)) {
            return ResponseEntity.ok("Money withdrawn successfully.");
        } else {
            return ResponseEntity.badRequest().body("Withdrawal failed. Please check account ID and PIN.");
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> depositMoney(@RequestParam Long accountId, @RequestParam int pin, @RequestParam double amount) {
        if (service.depositMoney(accountId, pin, amount)) {
            return ResponseEntity.ok("Money deposited successfully.");
        } else {
            return ResponseEntity.badRequest().body("Deposit failed. Please check account ID and PIN.");
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestParam Long sourceAccountId, @RequestParam int pin, @RequestParam Long targetAccountId, @RequestParam double amount) {
        if (service.transferMoney(sourceAccountId, pin, targetAccountId, amount)) {
            return ResponseEntity.ok("Money transferred successfully.");
        } else {
            return ResponseEntity.badRequest().body("Transfer failed. Please check account IDs and PIN.");
        }
    }
}
