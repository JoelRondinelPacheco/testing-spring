package org.joel.test.springboot.app.sprigboot_test.services;

import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.models.Bank;
import org.joel.test.springboot.app.sprigboot_test.repositories.AccountRepository;
import org.joel.test.springboot.app.sprigboot_test.repositories.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return this.accountRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public int reviewAllTransactions(Long bankId) {
        Bank bank = this.bankRepository.findById(bankId).orElseThrow();
        return bank.getAllTransactions();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal reviewBalance(Long accountId) {
        Account account = this.accountRepository.findById(accountId).orElseThrow();
        return account.getBalance();
    }

    @Override
    @Transactional
    public void transaction(Long originAccount, Long destinationAccount, BigDecimal amount, Long bankId) {
    Account accountOrigin = this.accountRepository.findById(originAccount).orElseThrow();
    accountOrigin.debit(amount);
    this.accountRepository.save(accountOrigin);

    Account accountDestination = this.accountRepository.findById(destinationAccount).orElseThrow();
    accountDestination.credit(amount);
    this.accountRepository.save(accountDestination);
    List<Bank> banks = this.bankRepository.findAll();
    if (!banks.isEmpty()) {
        System.out.println("Empty");
        System.out.println(banks.size());
    }
    Bank bank = this.bankRepository.findById(bankId).orElseThrow();
    int totalTransactions = bank.getAllTransactions();
    bank.setAllTransactions(++totalTransactions);
    this.bankRepository.save(bank);



    }
}
