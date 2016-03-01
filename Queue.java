package com.company;

public interface Queue<T> extends Iterable<T> {
    void add(T element);
    T poll();
}
