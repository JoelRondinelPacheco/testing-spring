package org.joel.test.springboot.app.sprigboot_test;

import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.repositories.AccountRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//import static org.springframework.test.util.AssertionErrors.*;

@DataJpaTest
public class IntegrationJpaTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    void testFindById() {
        Optional<Account> account = accountRepository.findById(1L);
        assertTrue(account.isPresent());
        assertEquals("Andrés", account.orElseThrow().getPerson());
    }
    @Test
    void testFindByPerson() {
        Optional<Account> account = accountRepository.findByPerson("Andrés");
        assertTrue(account.isPresent());
        assertEquals("Andrés", account.orElseThrow().getPerson());
        assertEquals("1000.00", account.orElseThrow().getBalance().toPlainString());
    }
    @Test
    void testFindByPersonThrowException() {
        Optional<Account> account = accountRepository.findByPerson("Jeol");
        assertThrows(NoSuchElementException.class, () -> {
            account.orElseThrow();
        });
        assertTrue(!account.isPresent());
        assertFalse(account.isPresent());
    }

    @Test
    void testFindAll() {
        List<Account> accounts = accountRepository.findAll();
        assertFalse(accounts.isEmpty());
        assertEquals(2, accounts.size());
    }

    @Test
    void testSave() {
        //Given
        Account pepeAccount = new Account(null, "Pepe", new BigDecimal("3000"));

        //When
        Account account = accountRepository.save(pepeAccount);
        //Account account = accountRepository.findByPerson("Pepe").orElseThrow();
        //Account account = accountRepository.findById(save.getId()).orElseThrow();

        //Then
        assertEquals("Pepe", account.getPerson());
        assertEquals("3000", account.getBalance().toPlainString());
        //assertEquals(3, account.getId());
    }
    @Test
    void testUpdate() {
        //Given
        Account pepeAccount = new Account(null, "Pepe", new BigDecimal("3000"));

        //When
        Account account = accountRepository.save(pepeAccount);
        //Account account = accountRepository.findByPerson("Pepe").orElseThrow();
        //Account account = accountRepository.findById(save.getId()).orElseThrow();

        //Then
        assertEquals("Pepe", account.getPerson());
        assertEquals("3000", account.getBalance().toPlainString());
        //assertEquals(3, account.getId());

        //When
        account.setBalance(new BigDecimal("3800"));
        Account accountUpdated = accountRepository.save(account);

        //When
        assertEquals("Pepe", accountUpdated.getPerson());
        assertEquals("3800", accountUpdated.getBalance().toPlainString());
    }

    @Test
    void testDelete() {
        Account account = accountRepository.findById(2L).orElseThrow();
        assertEquals("John", account.getPerson());

        accountRepository.delete(account);

        assertThrows(NoSuchElementException.class, () -> {
//            accountRepository.findByPerson("John").orElseThrow();
            accountRepository.findById(2L).orElseThrow();
        });

        assertEquals(1, accountRepository.findAll().size());
    }
}
