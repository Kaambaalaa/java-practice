package dao;

import entity.Account;
import lombok.AllArgsConstructor;
import org.hibernate.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AccountDao implements Dao<Account> {

    private EntityManager em;

    @Override
    @Transactional
    public Optional<Account> findById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.where(builder.equal(root.get("accountId"), id));
        return Optional.ofNullable(em.createQuery(criteria).getSingleResult());
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class).where();
        Root<Account> root = criteria.from(Account.class);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    @Transactional
    public void save(Account account) {
        em.persist(account);
    }

    @Override
    @Transactional
    public void delete(Account account) {
        findById(account.getAccountId()).ifPresent(em::remove);
    }
}
