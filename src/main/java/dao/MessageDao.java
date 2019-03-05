package dao;

import entity.Message;
import lombok.AllArgsConstructor;
import repository.MessageRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MessageDao implements Dao<Message> {

    private MessageRepository messageRepository;

    @Override
    @Transactional
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public void delete(Message message) {
        messageRepository.delete(message);
    }
}
