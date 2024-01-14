package org.joel.test.springboot.app.sprigboot_test.models;

import jakarta.persistence.*;

@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "all_transactions")
    private int allTransactions;

    public Bank() {
    }

    public Bank(Long id, String name, int allTransactions) {
        this.id = id;
        this.name = name;
        this.allTransactions = allTransactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAllTransactions() {
        return allTransactions;
    }

    public void setAllTransactions(int allTransactions) {
        this.allTransactions = allTransactions;
    }
}
