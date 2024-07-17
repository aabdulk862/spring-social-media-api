package com.example.service;

import com.example.entity.Account;
import com.example.exception.BadRequestException;
import com.example.exception.ConflictException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
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
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new ConflictException("Username already exists");
        }
        
        // Save the account to the database
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        Account account = accountRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
        return account;
    }
}
