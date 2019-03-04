package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.annotation.Generated;

import javax.persistence.*;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Account.ENTITY_NAME)
public class Account {

    public static final String ENTITY_NAME = "account";

    @Id
    @Column(name = Field.ACCOUNT_ID)
    private Long accountId;

    @Column(name = Field.EMAIL)
    private String email;

    @Column(name = Field.PASSWORD)
    private String password;

    @Builder.Default
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = Account.ENTITY_NAME, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public List<AccountChat> accountChatList = new ArrayList<>();

    public void addChat(Chat chat) {
        accountChatList.add(new AccountChat(this, chat));
    }

    public static final class Field {
        public static final String ACCOUNT_ID = "account_id";
        public static final String EMAIL = "EMAIL";
        public static final String PASSWORD = "PASSWORD";
    }
}
