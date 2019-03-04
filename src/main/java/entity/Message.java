package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity
@Table(name = Message.ENTITY_NAME)
public class Message {

    public static final String ENTITY_NAME = "message";

    @Id
    @Column(name = Field.MESSAGE_ID)
    public Long messageId;

    @Column(name = Message.Field.TEXT)
    public String text;

//    public Account sender;
//
//    public Chat receiverChat;

    public static final class Field {
        public static final String MESSAGE_ID = "message_id";
        public static final String TEXT = "text";
    }
}
