package entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString(of = "accountChatPK")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = AccountChat.ENTITY_NAME)
public class AccountChat {

    public static final String ENTITY_NAME = "account_chat";

    @EmbeddedId
    private AccountChatPK accountChatPK;

    @ManyToOne(cascade = {CascadeType.ALL})
    @MapsId(Account.Field.ACCOUNT_ID)
    @JoinColumn(name = Account.Field.ACCOUNT_ID, foreignKey = @ForeignKey(name = "CONSTRAINT_C"))
    private Account account;

    @ManyToOne
    @MapsId(Chat.Field.CHAT_ID)
    @JoinColumn(name = Chat.Field.CHAT_ID, foreignKey = @ForeignKey(name = "CONSTRAINT_CC"))
    private Chat chat;

    public AccountChat(@NonNull Account account,@NonNull Chat chat) {
        this.accountChatPK = new AccountChatPK(account.getAccountId(), chat.getChatId());
        this.account = account;
        this.chat = chat;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class AccountChatPK implements Serializable {
        @Column(name = Account.Field.ACCOUNT_ID)
        private Long accountId;

        @Column(name = Chat.Field.CHAT_ID)
        private Long chatId;
    }
}
