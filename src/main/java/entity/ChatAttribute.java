package entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString(of = "chatAttributePK")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ChatAttribute.ENTITY_NAME)
public class ChatAttribute {

    public static final String ENTITY_NAME = "chat_attribute";

    @EmbeddedId
    private ChatAttributePK chatAttributePK;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @MapsId(Chat.Field.CHAT_ID)
    @JoinColumn(name = Chat.Field.CHAT_ID, foreignKey = @ForeignKey(name = "CONSTRAINT_4f"))
    private Chat chat;

    @ManyToOne
    @MapsId(Attribute.Field.ATTRIBUTE_ID)
    @JoinColumn(name = Attribute.Field.ATTRIBUTE_ID, foreignKey = @ForeignKey(name = "CONSTRAINT_4"))
    private Attribute attribute;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class ChatAttributePK implements Serializable {
        @Column(name = Chat.Field.CHAT_ID)
        private Long chatId;

        @Column(name = Attribute.Field.ATTRIBUTE_ID)
        private Long attributeId;
    }
}
