package entity;
import api.SqlEntity;
import api.SqlField;
import api.SqlForeignMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SqlEntity(name = Account.ENTITY_NAME)
public class Account extends AbstractEntity {

    public static final String ENTITY_NAME = "account";

    @SqlField(name = Field.ACCOUNT_ID, primaryKey = true)
    public Long accountId;

    @SqlField(name = Field.EMAIL)
    public String email;

    @SqlField(name = Field.PASSWORD)
    public String password;

    @SqlForeignMapping(entityType = Chat.class,field = Field.ACCOUNT_ID, foreignField = Chat.Field.CHAT_ID, relationName = "account_chat", many = true, orphanRemoval = true)
    public List<Chat> chatList;

    public static final class Field {
        public static final String ACCOUNT_ID = "account_id";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }
}
