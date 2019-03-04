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
@Table(name = Attribute.ENTITY_NAME)
public class Attribute {

    public static final String ENTITY_NAME = "attribute";

    @Id
    @Column(name = Field.ATTRIBUTE_ID)
    public Long attributeId;

    @Column(name = Field.ATTRIBUTE_NAME)
    public Type type;

    public static final class Field {
        public static final String ATTRIBUTE_ID = "attribute_id";
        public static final String ATTRIBUTE_NAME = "attribute_name";
    }

    public enum Type {
        PUBLIC, PRIVATE
    }
}
