package dao;

import repository.AccountRepository;
import repository.AttributeRepository;
import repository.ChatRepository;
import repository.MessageRepository;


public class DaoFactory {

    public static AccountDao ofAccount(AccountRepository accountRepository) {
        return new AccountDao(accountRepository);
    }

    public static ChatDao ofChat(ChatRepository chatRepository) {
        return new ChatDao(chatRepository);
    }

    public static MessageDao ofMessage(MessageRepository messageRepository) {
        return new MessageDao(messageRepository);
    }

    public static AttributeDao ofAttribute(AttributeRepository attributeRepository) {
        return new AttributeDao(attributeRepository);
    }

}