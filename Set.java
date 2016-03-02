package com.company;

public interface Set<T> extends Iterable<T> {
    void add(T element);
    Boolean contains(T element);
}