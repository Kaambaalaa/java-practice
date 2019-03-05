package dao;

import entity.Message;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MessageDao implements Dao<Message> {

    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Optional<Message> findById(Long id) {
        return Optional.ofNullable(getSession(sessionFactory).get(Message.class, id));
    }

    @Override
    @Transactional
    public List<Message> findAll() {
        return getSession(sessionFactory).createCriteria(Message.class).list();
    }

    @Override
    @Transactional
    public void save(Message chat) {
        getSession(sessionFactory).save(chat);
    }

    @Override
    @Transactional
    public void delete(Message message) {
        getSession(sessionFactory).delete(message);
    }
}
