package dao;

import entity.Chat;
import lombok.AllArgsConstructor;
import repository.ChatRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ChatDao implements Dao<Chat> {

    private ChatRepository chatRepository;

    @Override
    @Transactional
    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
//    @Transactional
    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void delete(Chat chat) {
        chatRepository.delete(chat);
    }

}
