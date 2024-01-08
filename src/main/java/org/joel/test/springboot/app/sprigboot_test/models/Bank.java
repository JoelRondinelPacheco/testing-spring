package org.joel.test.springboot.app.sprigboot_test.models;

public class Bank {
    private Long id;
    private String name;
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
