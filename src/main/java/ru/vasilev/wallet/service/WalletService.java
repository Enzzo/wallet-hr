package ru.vasilev.wallet.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.vasilev.wallet.domain.OperationType;
import ru.vasilev.wallet.exception.InsufficientFundsException;
import ru.vasilev.wallet.exception.WalletNotFoundException;
import ru.vasilev.wallet.domain.Wallet;
import ru.vasilev.wallet.repository.WalletDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class WalletService {

    private final WalletDAO walletDAO;

    public WalletService(WalletDAO walletDAO){
        this.walletDAO = walletDAO;
    }

    public List<Wallet> getAll(int offset, int limit) {
        return walletDAO.getAll(offset, limit);
    }

    public int getAllCount(){
        return walletDAO.getAllCount();
    }

    @Transactional
    public Wallet create(BigDecimal amount){
        Wallet newWallet = new Wallet();
        newWallet.setBalance(amount);
        walletDAO.saveOrUpdate(newWallet);
        return newWallet;
    }

    @Transactional
    public Wallet edit(UUID id, OperationType operation, BigDecimal amount){
        Wallet newWallet = new Wallet();
        newWallet.setBalance(amount);
        walletDAO.saveOrUpdate(newWallet);
        return newWallet;
    }

    @Transactional
    public Wallet delete(UUID id){
        Wallet task = walletDAO.getById(id);
        if(isNull(task)){
            throw new RuntimeException("Not found");
        }
        walletDAO.delete(task);
        return task;
    }

    public BigDecimal getWalletBalance(UUID id){
        return walletDAO.getById(id).getBalance();
    }
}