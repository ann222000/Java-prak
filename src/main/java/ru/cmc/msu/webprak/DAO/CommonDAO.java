package ru.cmc.msu.webprak.DAO;

import ru.cmc.msu.webprak.entities.CommonEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CommonDAO<T extends CommonEntity<ID>, ID> {
    T getById(ID id);

    Collection<T> getAll();

    void save(T entity);

    void saveCollection(Collection<T> entities);

    <V> List<T> searchByFilter(Map<String, V> filter);

    void delete(T entity);

    void deleteById(ID id);

    void update(T entity);
}
