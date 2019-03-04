package dao;

import api.SqlField;
import entity.AbstractEntity;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Utils {
    public static <T extends AbstractEntity> Field getIdField(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(SqlField.class) && field.getAnnotation(SqlField.class).primaryKey())
                .findAny().orElseThrow(() -> new RuntimeException("Primary key not found"));
    }

    public static <T extends AbstractEntity> Object getIdFieldValue(T t) {
        try {
            return getIdField(t.getClass()).get(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
