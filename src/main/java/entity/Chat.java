package entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = Chat.ENTITY_NAME)
public class Chat {

    public static final String ENTITY_NAME = "chat";

    @Id
    @Column(name = Field.CHAT_ID)
    public Long chatId;

    @Column(name = Field.CHAT_NAME)
    public String chatName;

    @Builder.Default
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = Chat.ENTITY_NAME, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public List<ChatMessage> chatMessageList = new ArrayList<>();

    @Builder.Default
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = Chat.ENTITY_NAME, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public List<ChatAttribute> chatAttributeList = new ArrayList<>();

    public static final class Field {
        public static final String CHAT_ID = "chat_id";
        public static final String CHAT_NAME = "chat_name";
    }
}
