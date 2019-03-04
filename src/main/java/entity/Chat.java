package entity;

import api.SqlEntity;
import api.SqlField;
import api.SqlForeignMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SqlEntity(name = Chat.ENTITY_NAME)
public class Chat extends AbstractEntity {

    public static final String ENTITY_NAME = "chat";

    @SqlField(name = Field.CHAT_ID, primaryKey = true)
    public Long chatId;

    @SqlField(name = Field.CHAT_NAME)
    public String chatName;

    @SqlForeignMapping(entityType = Message.class, field = Chat.Field.CHAT_ID, foreignField = Message.Field.MESSAGE_ID, relationName = "chat_message", many = true, orphanRemoval = true)
    public List<Message> messageList;

    @SqlForeignMapping(entityType = Account.class, field = Chat.Field.CHAT_ID, foreignField = Account.Field.ACCOUNT_ID, relationName = "account_chat", many = true, orphanRemoval = true)
    public List<Account> accountList;

    @SqlForeignMapping(entityType = Attribute.class, field = Chat.Field.CHAT_ID, foreignField = Attribute.Field.ATTRIBUTE_ID, relationName = "chat_attribute", many = true, orphanRemoval = true)
    public List<Attribute> attributeList;

    public static final class Field {
        public static final String CHAT_ID = "chat_id";
        public static final String CHAT_NAME = "chat_name";
    }
}
