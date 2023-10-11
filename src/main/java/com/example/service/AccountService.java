package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService implements AccountInterfaceService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * addAccountService:  method to implement User Rigisteration Service requirements.
     * The username shouldn't be blank or the password less than 4 length.
     * the username shouldn't be exists under db.
     * this method depends on 2 private mehtods all implemented in this class.
     * @param account: an account object to be cheked.
     * @return account object contain account_id if the account meet requirements , null  if the account dosn't meet requirements.
     */
    @Override
    public Account addAccountService(Account account) {
        // check username requirements not blank and password not less than 4 length:
        if(isValidAccount(account)) {
            //check the account requirment not already exist:
            if(!accountExists(account.getUsername())) {
                //save the account and return acccount object contain new generated account id:
                Account savedAccount = accountRepository.save(account);
                return savedAccount;
            }
        }
        //return null if requirements are not met or acccount failed to be saved to DB
        return null;
    }

    /**
     * login:  method to implement User Login Service requirements.
     * The username & password should match one of exiting accounts.
     * @param username: string username.
     * @param password: string password.
     * @return Account object if exist or null if not exist.
     */
    @Override
    public Account login(String username, String password) {
        Optional<Account> optionalaccount = accountRepository.findByUsernameAndPassword(username, password);
        // if the account exist return account object:
        if(optionalaccount.isPresent()){
            return optionalaccount.get();
        }
        // return null if no account with the given user and pass:
        return null;
    }

    /**
     * findById:  method to implement get Account using Its ID.
     * @param id: integer id.
     * @return Optional Account object.
     */
    public Optional<Account> findById(int id) {
        Optional<Account> existAccount = accountRepository.findAccountById(id);
        return existAccount;
    }

    /**
     * isValidAccount: private method to implement username requirements will be invoked with addAccount.
     * The username shouldn't be blank or the password less than 4 length.
     * @param account: an account object to be cheked.
     * @return True if the account meet requirements , False if the account dont.
     */
    private Boolean isValidAccount(Account account){
        return !account.getUsername().isBlank() && account.getPassword().length() >= 4;
    }

     /**
     * accountExists: private method to implement username requirements will be invoked with addAccount.
     * The account username shouldn't be exist under DB.
     * @param username: string username to be checked in the DB.
     * @return True if the account exits , False if not exists.
     */
    private boolean accountExists(String username){
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        return existingAccount.isPresent();
        
    }
}
