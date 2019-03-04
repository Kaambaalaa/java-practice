package dao;

import entity.Attribute;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AttributeDao implements Dao<Attribute> {

    private EntityManager em;

    @Override
    public Optional<Attribute> findById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Attribute> criteria = builder.createQuery(Attribute.class);
        Root<Attribute> root = criteria.from(Attribute.class);
        criteria.where(builder.equal(root.get("attributeId"), id));
        return Optional.ofNullable(em.createQuery(criteria).getSingleResult());
    }

    @Override
    public List<Attribute> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Attribute> criteria = builder.createQuery(Attribute.class).where();
        Root<Attribute> root = criteria.from(Attribute.class);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public void save(Attribute attribute) {
        em.persist(attribute);
    }

    @Override
    public void delete(Attribute attribute) {
        findById(attribute.getAttributeId()).ifPresent(em::remove);

    }
}
