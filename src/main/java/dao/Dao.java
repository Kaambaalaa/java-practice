package dao;

import api.SqlEntity;
import api.SqlField;
import api.SqlForeignMapping;
import entity.AbstractEntity;
import entity.Attribute;
import org.apache.commons.collections4.CollectionUtils;
import org.flywaydb.core.internal.util.jdbc.JdbcTemplate;
import org.flywaydb.core.internal.util.jdbc.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Dao<T extends AbstractEntity> {

    private static final Logger log = Logger.getLogger(Dao.class.getName());

    private Class<T> entity;
    private JdbcTemplate jdbcTemplate;
    private Field idField;

    public Dao(Class<T> entity, JdbcTemplate jdbcTemplate) {
        this.entity = entity;
        this.jdbcTemplate = jdbcTemplate;
        idField = Utils.getIdField(entity);
        Arrays.stream(entity.getDeclaredFields()).forEach(field -> field.setAccessible(true));
    }

    private static final String SELECT_BY_FIELD = "select * from %s where %s = %s";
    private static final String SELECT_ALL = "select * from %s";
    private static final String DELETE_BY_FIELD = "DELETE from %s where %s = %s";
    private static final String UPDATE = "UPDATE %s SET %s WHERE %S = %s";
    private static final String INSERT = "INSERT INTO %s (%s) VALUES (%S)";


    public Optional<T> findById(Long id) {
        return findById(id, false);
    }

    private Optional<T> findById(Long id, boolean lazy) {//todo remove lazy with better logic
        log.info("Going to find " + entity.getSimpleName() + " with id: " + id);
        String idFieldName = idField.getAnnotation(SqlField.class).name();
        List<T> result = null;
        try {
            result = jdbcTemplate.query(String.format(SELECT_BY_FIELD, entity.getAnnotation(SqlEntity.class).name(),
                    idFieldName, id), getRowMapper(lazy));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result.size() > 1) {
            throw new RuntimeException("Not unique result.");
        }
        return result.size() == 1 ? Optional.of(result.get(0)) : Optional.empty();
    }

    ;

    public List<T> getAll() {
        try {
            return jdbcTemplate.query(String.format(SELECT_ALL, entity.getAnnotation(SqlEntity.class).name()), getRowMapper(false));
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    ;

    public T save(T t) {
        log.info("Going to save " + entity.getSimpleName() + " : " + t);

        try {
            idField.setAccessible(true);
            if (findById((Long) idField.get(t)).isPresent()) {
                update(t);
            } else {
                insert(t);
            }
            return findById((Long) idField.get(t)).orElseThrow(() -> new RuntimeException("Can`t save object."));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can`t save object.");
    }

    ;

    public T delete(T t) {
        log.info("Going to delete " + entity.getSimpleName() + " : " + t);

        removeRelations(t);
        String idFieldName = idField.getAnnotation(SqlField.class).name();
        try {
            jdbcTemplate.execute(String.format(DELETE_BY_FIELD, entity.getAnnotation(SqlEntity.class).name(), idFieldName, idField.get(t)));
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    ;

    private void insert(T t) {
        StringJoiner keys = new StringJoiner(",");
        StringJoiner values = new StringJoiner(",");

        Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SqlField.class))
                .forEach(field -> {
                    try {
                        keys.add(field.getAnnotation(SqlField.class).name());
                        values.add("'" + field.get(t).toString() + "'");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        try {
            jdbcTemplate.execute(String.format(INSERT, entity.getAnnotation(SqlEntity.class).name(), keys, values));
            createRelations(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void update(T t) {
        removeRelations(t);
        String idFieldName = idField.getAnnotation(SqlField.class).name();
        StringJoiner updateString = new StringJoiner(",");
        Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SqlField.class) && !field.getAnnotation(SqlField.class).primaryKey())
                .forEach(field -> {
                    try {
                        updateString.add(field.getAnnotation(SqlField.class).name() + "='" + field.get(t).toString() + "'");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        try {
            jdbcTemplate.execute(String.format(UPDATE, entity.getAnnotation(SqlEntity.class).name(), updateString, idFieldName, idField.get(t)));
            createRelations(t);
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void removeRelations(T t) {
        Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SqlForeignMapping.class) && field.getAnnotation(SqlForeignMapping.class).orphanRemoval())
                .forEach(field -> {
                    try {
                        jdbcTemplate.execute(String.format(DELETE_BY_FIELD, field.getAnnotation(SqlForeignMapping.class).relationName(),
                                idField.getAnnotation(SqlField.class).name(), idField.get(t)));
                    } catch (SQLException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void createRelations(T t) {

        Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SqlForeignMapping.class))
                .collect(Collectors.groupingBy(field -> field.getAnnotation(SqlForeignMapping.class).relationName()))
//        .entrySet().stream()
                .forEach((key, value) -> {
                    if (value.stream().noneMatch(field -> field.getAnnotation(SqlForeignMapping.class).many())) {

                        StringJoiner keys = new StringJoiner(",");
                        StringJoiner values = new StringJoiner(",");
                        value.stream().forEach(field -> {
                            try {
                                keys.add(field.getAnnotation(SqlForeignMapping.class).foreignField());
                                values.add(Utils.getIdFieldValue((AbstractEntity) field.get(t)).toString());


                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                        keys.add(idField.getAnnotation(SqlField.class).name());
                        try {
                            values.add(idField.get(t).toString());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        try {
                            jdbcTemplate.execute(String.format(INSERT, key, keys, values));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        StringJoiner keys = new StringJoiner(",");
                        StringJoiner values = new StringJoiner(",");
                        value.stream().forEach(field -> {
                            try {
                                CollectionUtils.emptyIfNull(((List<AbstractEntity>) field.get(t)))
                                        .forEach(entity -> {
                                            keys.add(field.getAnnotation(SqlForeignMapping.class).foreignField());
                                            values.add(Utils.getIdFieldValue(entity).toString());
                                            keys.add(idField.getAnnotation(SqlField.class).name());
                                            try {
                                                values.add(idField.get(t).toString());
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }

                                            try {
                                                jdbcTemplate.execute(String.format(INSERT, key, keys, values));
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                        });
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
    }

    private <S extends AbstractEntity> void mapFieldToEntities(T obj, Field field, Long id) throws IllegalAccessException, InstantiationException, SQLException {
        SqlForeignMapping sqlForeignMapping = field.getAnnotation(SqlForeignMapping.class);
        Class<S> mappingType = sqlForeignMapping.entityType();
        Dao<S> mappedDao = new Dao<S>(mappingType, jdbcTemplate);
        List<S> result = jdbcTemplate.queryForList(String.format(SELECT_BY_FIELD, sqlForeignMapping.relationName(),
                sqlForeignMapping.foreignField(), id)).stream()
//                .peek(System.err::println)
                .map(map -> {
                    return mappedDao.findById(Long.valueOf(map.get(sqlForeignMapping.foreignField().toUpperCase())), true).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (sqlForeignMapping.many()) {
            field.set(obj, result);
        } else {
            if (result.size() > 1) {
                throw new RuntimeException("Not unique result.");
            }
            if (result.size() == 1) {
                field.set(obj, result.get(0));
            }
        }
    }

    private Object getField(String fieldName, Class fieldType, ResultSet rs) throws SQLException {
        if (String.class.isAssignableFrom(fieldType)) {
            return rs.getString(fieldName);
        }
        if (Integer.class.isAssignableFrom(fieldType)) {
            return rs.getInt(fieldName);
        }
        if (Long.class.isAssignableFrom(fieldType)) {
            return rs.getLong(fieldName);
        }
        if (Attribute.Type.class.isAssignableFrom(fieldType)) {
            return Attribute.Type.valueOf(rs.getString(fieldName));
        }
        throw new RuntimeException("Type currently not supported.");
    }

    private RowMapper<T> getRowMapper(boolean lazy) {

        return rs -> {
            try {
                T obj = entity.newInstance();
                Arrays.stream(obj.getClass().getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(SqlField.class) || field.isAnnotationPresent(SqlForeignMapping.class))
                        .forEach(field -> {
                            if (field.isAnnotationPresent(SqlField.class)) {
                                SqlField sqlField = field.getAnnotation(SqlField.class);
                                try {
                                    field.set(obj, getField(sqlField.name(), field.getType(), rs));
                                } catch (IllegalAccessException | SQLException e) {
                                    e.printStackTrace();
                                }
                            } else if (!lazy) {
                                try {
                                    mapFieldToEntities(obj, field, (Long) idField.get(obj));
                                } catch (IllegalAccessException | InstantiationException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                return obj;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        };
    }
}
