package dao;

import entity.Account;
import entity.Attribute;
import entity.Chat;
import entity.Message;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;


public class DaoFactory {

    private SessionFactory sessionFactory;

    public static Dao<Account> ofAccount(SessionFactory sessionFactory) {
        return new AccountDao(sessionFactory);
    }

    public static Dao<Chat> ofChat(SessionFactory sessionFactory) {
        return new ChatDao(sessionFactory);
    }

    public static Dao<Message> ofMessage(SessionFactory sessionFactory) {
        return new MessageDao(sessionFactory);
    }

    public static Dao<Attribute> ofAttribute(SessionFactory sessionFactory) {
        return new AttributeDao(sessionFactory);
    }

}