package com.javiera.alke.service;

import com.javiera.alke.model.User;
import com.javiera.alke.model.Transaction;

import java.util.List;

public interface UserService {
    void save(User user);
    User findByUserId(String userId);
    User getCurrentUser();
    void deposit(double amount);
    void withdraw(double amount);
    void transfer(String senderId, String recipientId, double amount);
    List<Transaction> getTransactions(String userId);
}
