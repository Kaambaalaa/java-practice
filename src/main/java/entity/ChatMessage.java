package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString(of = "chatMessagePK")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ChatMessage.ENTITY_NAME)
public class ChatMessage {

    public static final String ENTITY_NAME = "chat_message";

    @EmbeddedId
    private ChatMessagePK chatMessagePK;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @MapsId(Chat.Field.CHAT_ID)
    @JoinColumn(name = Chat.Field.CHAT_ID, foreignKey = @ForeignKey(name = "CONSTRAINT_81"))
    private Chat chat;

    @ManyToOne
    @MapsId(Message.Field.MESSAGE_ID)
    @JoinColumn(name = Message.Field.MESSAGE_ID, foreignKey = @ForeignKey(name = "CONSTRAINT_8"))
    private Message message;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class ChatMessagePK implements Serializable {
        @Column(name = Chat.Field.CHAT_ID)
        private Long chatId;

        @Column(name = Message.Field.MESSAGE_ID)
        private Long messageId;
    }
}
