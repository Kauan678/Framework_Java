package framework.impl;

import framework.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository<T> implements CrudRepository<T> {
    private final List<T> entities = new ArrayList<>();

    @Override
    public void save(T entity) {
        entities.add(entity);
    }

    @Override
    public T findById(int id) {
        if (id >= 0 && id < entities.size()) {
            return entities.get(id);
        }
        return null;
    }

    @Override
    public void update(T entity) {
        int index = entities.indexOf(entity);
        if (index != -1) {
            entities.set(index, entity);
        }
    }

    @Override
    public void delete(int id) {
        if (id >= 0 && id < entities.size()) {
            entities.remove(id);
        }
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }
}
