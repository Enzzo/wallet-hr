package ru.vasilev.wallet.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.vasilev.wallet.domain.Wallet;

import java.util.List;
import java.util.UUID;

@Repository
public class WalletDAO {
    private final SessionFactory sessionFactory;

    public WalletDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Wallet> getAll(int offset, int limit) {
        Query<Wallet> query = getSession().createQuery("SELECT w FROM Wallet w", Wallet.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount(){
        Query<Long> query = getSession().createQuery("select count(*) from Wallet", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Wallet getById(UUID id) {
        Query<Wallet> query = getSession().createQuery("SELECT w FROM Wallet w WHERE w.id = :id", Wallet.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Wallet wallet) {
        getSession().persist(wallet);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Wallet wallet) {
        getSession().delete(wallet);
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}