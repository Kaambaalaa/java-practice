package entity;

import api.SqlEntity;
import api.SqlField;
import api.SqlForeignMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SqlEntity(name = Message.ENTITY_NAME)
public class Message extends AbstractEntity {

    public static final String ENTITY_NAME = "message";

    @SqlField(name = Field.MESSAGE_ID, primaryKey = true)
    public Long messageId;

    @SqlField(name = Field.TEXT)
    public String text;

    @SqlForeignMapping(entityType = Account.class, field = Field.MESSAGE_ID, foreignField = Account.Field.ACCOUNT_ID, relationName = "account_message", orphanRemoval = true)
    public Account sender;

    @SqlForeignMapping(entityType = Chat.class, field = Field.MESSAGE_ID, foreignField = Chat.Field.CHAT_ID, relationName = "chat_message", orphanRemoval = true)
    public Chat receiverChat;

    public static final class Field {
        public static final String MESSAGE_ID = "message_id";
        public static final String TEXT = "text";
    }
}
