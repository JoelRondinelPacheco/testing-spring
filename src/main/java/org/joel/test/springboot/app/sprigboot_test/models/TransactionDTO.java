package org.joel.test.springboot.app.sprigboot_test.models;

import java.math.BigDecimal;

public class TransactionDTO {

    private long originAccountId;
    private long destinationAccountId;
    private BigDecimal amount;
    private Long bankId;

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public TransactionDTO() {
    }

    public TransactionDTO(long originAccountId, long destinationAccountId, BigDecimal amount) {
        this.originAccountId = originAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
    }

    public long getOriginAccountId() {
        return originAccountId;
    }

    public void setOriginAccountId(long originAccountId) {
        this.originAccountId = originAccountId;
    }

    public long getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
