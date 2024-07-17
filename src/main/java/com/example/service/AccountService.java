package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.BadRequestException;
import com.example.exception.ConflictException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    
    public Account register(Account account) {
        // Check if username is not blank
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            throw new BadRequestException("Username cannot be blank");
        }
        // Check if password is at least 4 characters long
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new BadRequestException("Password must be at least 4 characters long");
        }
        // Check if username already exists
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new ConflictException("Username already exists");
        }
        // Save the account to the database
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
    
}
