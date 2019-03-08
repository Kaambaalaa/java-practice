package config;

import dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import repository.AccountRepository;
import repository.AttributeRepository;
import repository.ChatRepository;
import repository.MessageRepository;

@EnableTransactionManagement
@Configuration
@ComponentScan
public class DaoConfig {

    @Bean
    public AccountDao accountDao(AccountRepository accountRepository) {
        return DaoFactory.ofAccount(accountRepository);
    }

    @Bean
    public MessageDao messageDao(MessageRepository messageRepository) {
        return DaoFactory.ofMessage(messageRepository);
    }

    @Bean
    public ChatDao chatDao(ChatRepository chatRepository) {
        return DaoFactory.ofChat(chatRepository);
    }

    @Bean
    public AttributeDao attributeDao(AttributeRepository attributeRepository) {
        return DaoFactory.ofAttribute(attributeRepository);
    }

}
