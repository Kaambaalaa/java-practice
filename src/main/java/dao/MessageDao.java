package dao;

import entity.Message;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MessageDao implements Dao<Message> {

    private EntityManager em;

    @Override
    public Optional<Message> findById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Message> criteria = builder.createQuery(Message.class);
        Root<Message> root = criteria.from(Message.class);
        criteria.where(builder.equal(root.get("messageId"), id));
        return Optional.ofNullable(em.createQuery(criteria).getSingleResult());
    }

    @Override
    public List<Message> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Message> criteria = builder.createQuery(Message.class).where();
        Root<Message> root = criteria.from(Message.class);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public void save(Message chat) {
        em.persist(chat);
    }

    @Override
    public void delete(Message message) {
        findById(message.getMessageId()).ifPresent(em::remove);
    }
}
