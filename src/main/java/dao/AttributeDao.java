package dao;

import entity.Account;
import entity.Attribute;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AttributeDao implements Dao<Attribute> {

    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Optional<Attribute> findById(Long id) {
        return Optional.ofNullable(getSession(sessionFactory).get(Attribute.class, id));
    }

    @Override
    @Transactional
    public List<Attribute> findAll() {
        return getSession(sessionFactory).createCriteria(Attribute.class).list();

    }

    @Override
    @Transactional
    public void save(Attribute attribute) {
        getSession(sessionFactory).save(attribute);
    }

    @Override
    @Transactional
    public void delete(Attribute attribute) {
        getSession(sessionFactory).delete(attribute);

    }
}
