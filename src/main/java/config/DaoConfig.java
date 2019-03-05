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
import repository.AccountRepository;
import repository.AttributeRepository;
import repository.ChatRepository;
import repository.MessageRepository;

import javax.transaction.Transactional;

@EnableTransactionManagement
@Configuration
@ComponentScan
public class DaoConfig {

    @Bean
    public Dao<Account> accountDao(AccountRepository accountRepository) {
        return DaoFactory.ofAccount(accountRepository);
    }

    @Bean
    public Dao<Message> messageDao(MessageRepository messageRepository) {
        return DaoFactory.ofMessage(messageRepository);
    }

    @Bean
    public Dao<Chat> chatDao(ChatRepository chatRepository) {
        return DaoFactory.ofChat(chatRepository);
    }

    @Bean
    public Dao<Attribute> attributeDao(AttributeRepository attributeRepository) {
        return DaoFactory.ofAttribute(attributeRepository);
    }

}
