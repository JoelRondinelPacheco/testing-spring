package org.joel.test.springboot.app.sprigboot_test.services;

import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.models.Bank;
import org.joel.test.springboot.app.sprigboot_test.repositories.AccountRepository;
import org.joel.test.springboot.app.sprigboot_test.repositories.BankRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public Account findById(Long id) {
        return this.accountRepository.findById(id).orElseThrow();
    }

    @Override
    public int reviewAllTransactions(Long bankId) {
        Bank bank = this.bankRepository.findById(bankId).orElseThrow();
        return bank.getAllTransactions();
    }

    @Override
    public BigDecimal reviewBalance(Long accountId) {
        Account account = this.accountRepository.findById(accountId).orElseThrow();
        return account.getBalance();
    }

    @Override
    public void transaction(Long originAccount, Long destinationAccount, BigDecimal amount, Long bankId) {

        Account accountOrigin = this.accountRepository.findById(originAccount).orElseThrow();
        accountOrigin.debit(amount);
        this.accountRepository.save(accountOrigin);

        Account accountDestination = this.accountRepository.findById(destinationAccount).orElseThrow();
        accountDestination.credit(amount);
        this.accountRepository.save(accountDestination);

        Bank bank = this.bankRepository.findById(bankId).orElseThrow();
        int totalTransactions = bank.getAllTransactions();
        bank.setAllTransactions(++totalTransactions);
        this.bankRepository.save(bank);


    }
}
