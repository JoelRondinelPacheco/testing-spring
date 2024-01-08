package org.joel.test.springboot.app.sprigboot_test.models;

import org.joel.test.springboot.app.sprigboot_test.exceptions.InsufficientMoneyException;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private Long id;
    private String person;
    private BigDecimal balance;

    public Account() {
    }

    public Account(String person, BigDecimal balance) {
        this.person = person;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void Debit(BigDecimal amount) {
        BigDecimal newBalance = this.balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientMoneyException("Dinero insuficiente en la cuenta");
        }
        this.balance = newBalance;
    }

    public void Credit(BigDecimal amount) {
    this.balance = this.balance.add(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(person, account.person) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, balance);
    }
}
