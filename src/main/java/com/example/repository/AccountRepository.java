package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account , Integer> {

    //FindByUserName will be used while registering new User
    Optional<Account> findByUsername(String username);

    //Custom Query to perform Login:
    @Query("SELECT a FROM Account a WHERE a.username = :username AND a.password = :password")
    Optional<Account> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    //Custom Query to Perform Get Account by Its ID:
    @Query("SELECT a FROM Account a WHERE a.account_id = :account_id")
    Optional<Account> findAccountById(@Param("account_id") int account_id);
}
