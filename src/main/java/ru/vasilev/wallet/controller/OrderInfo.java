package ru.vasilev.wallet.controller;

import ru.vasilev.wallet.domain.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderInfo {
    private UUID walletId;
    private OperationType operation;
    private BigDecimal amount;

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}