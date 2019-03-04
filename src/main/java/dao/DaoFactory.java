package dao;

import entity.AbstractEntity;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;

public class DaoFactory {

    public static <E extends AbstractEntity>  Dao<E> of(Class<E> clazz, JdbcTemplate jdbcTemplate) {
        return new Dao<>(clazz, jdbcTemplate);
    }
}
