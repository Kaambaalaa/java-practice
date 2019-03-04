package dao;

import entity.Account;
import entity.Attribute;
import entity.Chat;
import entity.Message;

import javax.persistence.EntityManager;


public class DaoFactory {

    private EntityManager em;

    public static Dao<Account> ofAccount(EntityManager em) {
        return new AccountDao(em);
    }

    public static Dao<Chat> ofChat(EntityManager em) {
        return new ChatDao(em);
    }

    public static Dao<Message> ofMessage(EntityManager em) {
        return new MessageDao(em);
    }

    public static Dao<Attribute> ofAttribute(EntityManager em) {
        return new AttributeDao(em);
    }

}