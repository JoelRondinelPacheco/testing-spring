package org.joel.test.springboot.app.sprigboot_test.repositories;

import org.joel.test.springboot.app.sprigboot_test.models.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    Account findById(Long id);

    void update(Account account);

}
