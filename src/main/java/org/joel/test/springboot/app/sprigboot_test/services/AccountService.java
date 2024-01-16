package org.joel.test.springboot.app.sprigboot_test.services;

import org.joel.test.springboot.app.sprigboot_test.models.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account save(Account account);

    Account findById(Long id);

    void deleteById(Long id);

    int reviewAllTransactions(Long bankId);

    BigDecimal reviewBalance(Long accountId);

    void transaction(Long originAccount, Long destinationAccount, BigDecimal amount, Long bankId);
}
