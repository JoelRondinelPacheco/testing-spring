package org.joel.test.springboot.app.sprigboot_test.repositories;

import org.joel.test.springboot.app.sprigboot_test.models.Bank;

import java.util.List;

public interface BankRepository {
    List<Bank> findAll();

    Bank findById(Long id);

    void update(Bank bank);
}
