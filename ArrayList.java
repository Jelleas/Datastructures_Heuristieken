package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private class ArrayListIterator implements Iterator<T> {
        private int index;
        private Object[] elements;
        private int nElements;

        public ArrayListIterator(ArrayList<T> list) {
            index = 0;
            elements = list.elements;
            nElements = list.size;
        }

        public boolean hasNext() {
            return index < nElements;
        }

        public T next() {
            if (hasNext()) {
                @SuppressWarnings("unchecked")
                T element = (T)elements[index];
                index++;
                return element;
            }
            throw new NoSuchElementException();
        }
    }

    private Object[] elements;
    private int size;

    public ArrayList() {
        elements = new Object[10];
        size = 0;
    }
    public void add(T element) {
        if (size == elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
        elements[size] = element;
        size++;
    }

    public T get(int index){
        @SuppressWarnings("unchecked")
        T element = (T)elements[index];
        return element;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator(this);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        for (Integer x : list) {
            System.out.println(x);
        }
    }
}



