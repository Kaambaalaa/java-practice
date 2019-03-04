package config;

import dao.Dao;
import dao.DaoFactory;
import entity.Account;
import entity.Attribute;
import entity.Chat;
import entity.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@EnableTransactionManagement
@Configuration
public class DaoConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public Dao<Account> accountDao() {
        return DaoFactory.ofAccount(em);
    }

    @Bean
    public Dao<Message> messageDao() {
        return DaoFactory.ofMessage(em);
    }

    @Bean
    public Dao<Chat> chatDao() {
        return DaoFactory.ofChat(em);
    }

    @Bean
    public Dao<Attribute> attributeDao() {
        return DaoFactory.ofAttribute(em);
    }

}
