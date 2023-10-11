package com.example.service;

import com.example.entity.Account;

/*
 * this AccountInterfaceService interface will be Abstraction of AccountService Class , Although i will be using 
 * accountRepository when implmenting the below methods,the reason why i used this approach 
 * beside its best practice , i tried to implement AccountRepository directly in AccountService
 * but i will be implementing unnessarly needed functions and this against SOLID principles.
 * @version 1.0 10-09-2023
 * @author Abdalrhman Alhassan
 */

public interface AccountInterfaceService {
    
    Account addAccountService(Account account);
    
    Account login(String username, String password);
}
