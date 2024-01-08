package org.joel.test.springboot.app.sprigboot_test.services;

import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.models.Bank;
import org.joel.test.springboot.app.sprigboot_test.repositories.AccountRepository;
import org.joel.test.springboot.app.sprigboot_test.repositories.BankRepository;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public Account findById(Long id) {
        return this.accountRepository.findById(id);
    }

    @Override
    public int reviewAllTransactions(Long bankId) {
        Bank bank = this.bankRepository.findById(bankId);
        return bank.getAllTransactions();
    }

    @Override
    public BigDecimal reviewBalance(Long accountId) {
        Account account = this.accountRepository.findById(accountId);
        return account.getBalance();
    }

    @Override
    public void transaction(Long originAccount, Long destinationAccount, BigDecimal amount, Long bankId) {
        Bank bank = this.bankRepository.findById(bankId);
        int totalTransactions = bank.getAllTransactions();
        bank.setAllTransactions(++totalTransactions);
        this.bankRepository.update(bank);

        Account accountOrigin = this.accountRepository.findById(originAccount);
        accountOrigin.debit(amount);
        ;

        Account accountDestination = this.accountRepository.findById(destinationAccount);
        accountDestination.credit(amount);
        this.accountRepository.update(accountDestination);
    }
}
