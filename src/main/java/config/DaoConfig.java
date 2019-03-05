package config;

import dao.Dao;
import dao.DaoFactory;
import entity.Account;
import entity.Attribute;
import entity.Chat;
import entity.Message;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@ComponentScan
public class DaoConfig {

    @Bean
    public Dao<Account> accountDao(SessionFactory sessionFactory) {
        return DaoFactory.ofAccount(sessionFactory);
    }

    @Bean
    public Dao<Message> messageDao(SessionFactory sessionFactory) {
        return DaoFactory.ofMessage(sessionFactory);
    }

    @Bean
    public Dao<Chat> chatDao(SessionFactory sessionFactory) {
        return DaoFactory.ofChat(sessionFactory);
    }

    @Bean
    public Dao<Attribute> attributeDao(SessionFactory sessionFactory) {
        return DaoFactory.ofAttribute(sessionFactory);
    }

}
