package framework.impl;

import framework.CrudRepository;
import framework.SerializableEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InFileRepository<T extends SerializableEntity<T>> implements CrudRepository<T> {
    private final String fileName;
    private final T prototype;

    public InFileRepository(String fileName, T prototype) {
        this.fileName = fileName;
        this.prototype = prototype;
    }

    @Override
    public void save(T entity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(entity.serialize());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T findById(int id) {
        List<T> entities = findAll();
        if (id >= 0 && id < entities.size()) {
            return entities.get(id);
        }
        return null;
    }

    @Override
    public void update(T entity) {
        List<T> entities = findAll();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).equals(entity)) {
                entities.set(i, entity);
                saveAll(entities);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        List<T> entities = findAll();
        if (id >= 0 && id < entities.size()) {
            entities.remove(id);
            saveAll(entities);
        }
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entities.add(prototype.deserialize(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }

    private void saveAll(List<T> entities) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (T entity : entities) {
                writer.write(entity.serialize());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}