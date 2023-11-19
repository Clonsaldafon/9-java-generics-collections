package com.example.task02;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {
    private List<E> elements;
    private Path file;

    public SavedList(File file) throws IOException {
        this.file = file.toPath();

        if (!file.exists()) {
            Files.createFile(this.file);
            elements = new ArrayList<>();
        } else {
            try (ObjectInputStream input = new ObjectInputStream(
                    Files.newInputStream(this.file))) {
                elements = (ArrayList<E>) input.readObject();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public E get(int index) {
        return elements.get(index);
    }

    @Override
    public E set(int index, E element) {
        try (ObjectOutputStream output = new ObjectOutputStream(
                Files.newOutputStream(file))) {
            E setted = elements.set(index, element);
            output.writeObject(elements);
            return setted;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(int index, E element) {
        try (ObjectOutputStream output = new ObjectOutputStream(
                Files.newOutputStream(file))) {
            elements.add(index, element);
            output.writeObject(elements);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public E remove(int index) {
        try (ObjectOutputStream output = new ObjectOutputStream(
                Files.newOutputStream(file))) {
            E removed = elements.remove(index);
            output.writeObject(elements);
            return removed;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
