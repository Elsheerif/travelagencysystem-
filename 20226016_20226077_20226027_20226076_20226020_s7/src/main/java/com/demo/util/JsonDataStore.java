package com.demo.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.core.io.ResourceLoader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataStore<T> {
    private final ObjectMapper objectMapper;
    private final String dataFile;
    private final ConcurrentHashMap<String, T> cache;
    private final ReadWriteLock lock;
    private File jsonFile;

    public JsonDataStore(ResourceLoader resourceLoader, String dataFile) {
        this.dataFile = dataFile;
        this.objectMapper = new ObjectMapper();
        this.cache = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
        initializeDataFile();
    }

    private void initializeDataFile() {
        try {

            String resolvedPath = dataFile.replace("classpath:", "");
            if (resolvedPath.startsWith("/")) {
                resolvedPath = resolvedPath.substring(1);
            }


            Path resourceDirectory = Paths.get("src", "main", "resources", resolvedPath).getParent();
            if (!Files.exists(resourceDirectory)) {
                Files.createDirectories(resourceDirectory);
            }


            jsonFile = resourceDirectory.resolve(resolvedPath.substring(resolvedPath.lastIndexOf("/") + 1)).toFile();
            
            if (!jsonFile.exists()) {
                if (jsonFile.createNewFile()) {

                    objectMapper.writeValue(jsonFile, new ArrayList<>());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data file: " + dataFile, e);
        }
    }

    public List<T> readAll(Class<T> clazz) {
        lock.readLock().lock();
        try {
            if (!jsonFile.exists() || jsonFile.length() == 0) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(
                jsonFile,
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to read data from " + dataFile, e);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void save(List<T> items) {
        lock.writeLock().lock();
        try {
            objectMapper.writeValue(jsonFile, items);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write data to " + dataFile, e);
        } finally {
            lock.writeLock().unlock();
        }
    }


    public void saveItem(T item, String id) {
        lock.writeLock().lock();
        try {
            @SuppressWarnings("unchecked")
            List<T> items = readAll((Class<T>) item.getClass());
            items.add(item);
            save(items);
            cache.put(id, item);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public T getById(String id, Class<T> clazz) {
        return cache.computeIfAbsent(id, k -> {
            lock.readLock().lock();
            try {
                List<T> items = readAll(clazz);
                return items.stream()
                    .filter(item -> String.valueOf(item.hashCode()).equals(id))
                    .findFirst()
                    .orElse(null);
            } finally {
                lock.readLock().unlock();
            }
        });
    }

}