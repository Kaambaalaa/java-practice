package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> findById(Long id);

    List<T> findAll();

    void save(T t);

    void delete(T t);

}
