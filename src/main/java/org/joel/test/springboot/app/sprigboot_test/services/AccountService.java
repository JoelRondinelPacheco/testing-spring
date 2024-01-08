package org.joel.test.springboot.app.sprigboot_test.services;

import org.joel.test.springboot.app.sprigboot_test.models.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account findById(Long id);

    int reviewAllTransactions(Long bankId);

    BigDecimal reviewBalance(Long accountId);

    void transaction(Long originAccount, Long destinationAccount, BigDecimal amount, Long bankId);
}
