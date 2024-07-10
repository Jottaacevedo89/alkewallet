package com.javiera.alke.service;

import com.javiera.alke.model.Transaction;
import com.javiera.alke.model.TransactionType;
import com.javiera.alke.model.User;
import com.javiera.alke.repository.TransactionRepository;
import com.javiera.alke.repository.UserRepository;
import com.javiera.alke.exception.InsufficientBalanceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUserId(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId;
        if (principal instanceof UserDetails) {
            userId = ((UserDetails) principal).getUsername();
        } else {
            userId = principal.toString();
        }
        return findByUserId(userId);
    }

    @Override
    public void deposit(double amount) {
        User user = getCurrentUser();
        user.setBalance(user.getBalance() + (int) amount);
        userRepository.save(user);

        // Registrar transacción
        Transaction transaction = new Transaction();
        transaction.setUserId(user.getUserId());
        transaction.setAmount((int) amount);
        transaction.setTransactionType(TransactionType.deposit);
        transaction.setTransactionDate(new Timestamp(System.currentTimeMillis()));
        transaction.setCurrency("CLP");
        transactionRepository.save(transaction);
    }

    @Override
    public void withdraw(double amount) {
        User user = getCurrentUser();
        if (user.getBalance() == 0) {
            throw new InsufficientBalanceException("No se puede realizar el retiro: saldo insuficiente");
        }
        if (user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - (int) amount);
            userRepository.save(user);

            // Registrar transacción
            Transaction transaction = new Transaction();
            transaction.setUserId(user.getUserId());
            transaction.setAmount((int) amount);
            transaction.setTransactionType(TransactionType.withdrawal);
            transaction.setTransactionDate(new Timestamp(System.currentTimeMillis()));
            transaction.setCurrency("CLP");
            transactionRepository.save(transaction);
        } else {
            throw new InsufficientBalanceException("Saldo insuficiente");
        }
    }

    @Override
    public void transfer(String senderId, String recipientId, double amount) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (sender.getBalance() == 0) {
            throw new InsufficientBalanceException("No se puede realizar la transferencia: saldo insuficiente");
        }
        if (sender.getBalance() >= amount) {
            sender.setBalance((int) (sender.getBalance() - amount));
            recipient.setBalance((int) (recipient.getBalance() + amount));
            
            userRepository.save(sender);
            userRepository.save(recipient);

            Transaction senderTransaction = new Transaction();
            senderTransaction.setUserId(sender.getUserId());
            senderTransaction.setAmount((int) amount);
            senderTransaction.setTransactionType(TransactionType.transfer);
            senderTransaction.setTargetUserId(recipient.getUserId());
            senderTransaction.setTransactionDate(new Timestamp(System.currentTimeMillis()));
            senderTransaction.setCurrency("CLP");
            senderTransaction.setNameTargetUserId(recipient.getFirstName() + " " + recipient.getLastName1() + " " + recipient.getLastName2());
            transactionRepository.save(senderTransaction);

            Transaction recipientTransaction = new Transaction();
            recipientTransaction.setUserId(recipient.getUserId());
            recipientTransaction.setAmount((int) amount);
            recipientTransaction.setTransactionType(TransactionType.transfer);
            recipientTransaction.setTargetUserId(sender.getUserId());
            recipientTransaction.setTransactionDate(new Timestamp(System.currentTimeMillis()));
            recipientTransaction.setCurrency("CLP");
            recipientTransaction.setNameTargetUserId(sender.getFirstName() + " " + sender.getLastName1() + " " + sender.getLastName2());
            transactionRepository.save(recipientTransaction);
        } else {
            throw new InsufficientBalanceException("Saldo insuficiente");
        }
    }

    @Override
    public List<Transaction> getTransactions(String userId) {
        return transactionRepository.findByUserId(userId);
    }
}
