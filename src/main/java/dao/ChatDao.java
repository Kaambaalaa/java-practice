package dao;

import entity.Chat;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ChatDao implements Dao<Chat> {

    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Optional<Chat> findById(Long id) {
        return Optional.ofNullable(getSession(sessionFactory).get(Chat.class, id));
    }

    @Override
    @Transactional
    public List<Chat> findAll() {
        return getSession(sessionFactory).createCriteria(Chat.class).list();
    }

    @Override
    @Transactional
    public void save(Chat chat) {
        getSession(sessionFactory).save(chat);
    }

    @Override
    @Transactional
    public void delete(Chat chat) {
        getSession(sessionFactory).delete(chat);
    }

}
