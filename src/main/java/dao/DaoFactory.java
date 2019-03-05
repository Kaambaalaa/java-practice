package dao;

import entity.Account;
import entity.Attribute;
import entity.Chat;
import entity.Message;
import org.hibernate.SessionFactory;
import repository.AccountRepository;
import repository.AttributeRepository;
import repository.ChatRepository;
import repository.MessageRepository;

import javax.persistence.EntityManager;


public class DaoFactory {

    public static Dao<Account> ofAccount(AccountRepository accountRepository) {
        return new AccountDao(accountRepository);
    }

    public static Dao<Chat> ofChat(ChatRepository chatRepository) {
        return new ChatDao(chatRepository);
    }

    public static Dao<Message> ofMessage(MessageRepository messageRepository) {
        return new MessageDao(messageRepository);
    }

    public static Dao<Attribute> ofAttribute(AttributeRepository attributeRepository) {
        return new AttributeDao(attributeRepository);
    }

}