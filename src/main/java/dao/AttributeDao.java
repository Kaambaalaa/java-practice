package dao;

import entity.Account;
import entity.Attribute;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.AttributeRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AttributeDao implements Dao<Attribute> {

    private AttributeRepository attributeRepository;

    @Override
    @Transactional
    public Optional<Attribute> findById(Long id) {
        return attributeRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    @Override
    @Transactional
    public void delete(Attribute attribute) {
        attributeRepository.delete(attribute);
    }
}
