package entity;
import api.SqlEntity;
import api.SqlField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SqlEntity(name = Attribute.ENTITY_NAME)
public class Attribute extends AbstractEntity {

    public static final String ENTITY_NAME = "attribute";

    @SqlField(name = Field.ATTRIBUTE_ID, primaryKey = true)
    public Long attributeId;

    @SqlField(name = Field.ATTRIBUTE_NAME)
    public Type type;

    public static final class Field {
        public static final String ATTRIBUTE_ID = "attribute_id";
        public static final String ATTRIBUTE_NAME = "attribute_name";
    }

    public enum Type {
        PUBLIC, PRIVATE
    }
}
