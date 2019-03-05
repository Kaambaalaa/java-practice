package dao;

import entity.Account;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AccountDao implements Dao<Account> {

    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(getSession(sessionFactory).get(Account.class, id));
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        return getSession(sessionFactory).createCriteria(Account.class).list();
    }

    @Override
    @Transactional
    public void save(Account account) {
        getSession(sessionFactory).save(account);
    }

    @Override
    @Transactional
    public void delete(Account account) {
        getSession(sessionFactory).delete(account);
    }

}
