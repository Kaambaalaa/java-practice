package service;

import dao.Dao;
import entity.Account;
import entity.Attribute;
import entity.Chat;
import entity.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Slf4j
@AllArgsConstructor
@Service
@DependsOn("flyway")
public class Demo {

    public Dao<Account> accountDao;
    public Dao<Attribute> attributeDao;
    public Dao<Chat> chatDao;
    public Dao<Message> messageDao;

    @PostConstruct
    public void demonstrateDao() {

        chatDao.save(Chat.builder()
                .chatId(1L)
                .chatName("cool_chat")
                .build());
        log.info("Chat saved.");
        Account account = Account.builder()
                .accountId(1L)
                .password("qwerty")
                .email("mail@mail").build();
        Chat chat = chatDao.findById(1L).orElseThrow(() -> new RuntimeException("Smth went wrong here."));
        account.addChat(chat);
        accountDao.save(account);

//        Chat chat = chatDao.save(new Chat(1L, "cool_chat", null, Collections.singletonList(account),
//                Collections.singletonList(new Attribute(1L, Attribute.Type.PUBLIC))));
//
//        Message message = messageDao.save(new Message(1L, "Hello world!",account, chat));

//        System.err.println(chatDao.findAll());
//
//        chatDao.delete(chat);
//        System.err.println(chatDao.findAll());

//        System.out.println(chatDao.findById(1L));
        System.out.println(accountDao.findAll());

        System.out.println(accountDao.findById(1L));

        accountDao.delete(accountDao.findById(1L).get());

    }

}
