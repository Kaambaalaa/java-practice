package dao;

import entity.Chat;
import entity.Chat;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ChatDao implements Dao<Chat> {

    private EntityManager em;

    @Override
    public Optional<Chat> findById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Chat> criteria = builder.createQuery(Chat.class);
        Root<Chat> root = criteria.from(Chat.class);
        criteria.where(builder.equal(root.get("chatId"), id));
        return Optional.ofNullable(em.createQuery(criteria).getSingleResult());    }

    @Override
    public List<Chat> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Chat> criteria = builder.createQuery(Chat.class).where();
        Root<Chat> root = criteria.from(Chat.class);
        return em.createQuery(criteria).getResultList();    }

    @Override
    public void save(Chat chat) {
        em.persist(chat);
    }

    @Override
    public void delete(Chat chat) {
        findById(chat.getChatId()).ifPresent(em::remove);
    }

}
