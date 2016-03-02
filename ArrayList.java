package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T>, Stack<T>, Queue<T> {
    private class ArrayListIterator implements Iterator<T> {
        private int index;
        private Object[] elements;
        private int nElements;

        public ArrayListIterator(ArrayList<T> list) {
            index = 0;
            elements = list.elements;
            nElements = list.size;
        }

        @Override
        public boolean hasNext() {
            return index < nElements;
        }

        @Override
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

    @Override
    public void add(T element) {
        if (size == elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
        elements[size] = element;
        size++;
    }

    @Override
    public T get(int index){
        @SuppressWarnings("unchecked")
        T element = (T)elements[index];
        return element;
    }

    @Override
    public T pop() {
        if (size == 0) {
            return null;
        }
        size--;
        return get(size);
    }

    @Override
    public T poll() {
        if (size == 0) {
            return null;
        }
        size--;
        T element = get(0);

        // shift all elements 1 spot
        System.arraycopy(elements, 1, elements, 0, size);

        return element;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator(this);
    }

    public static void main(String[] args) {
        System.out.println("ArrayList\n");

        List<Integer> list = new ArrayList<>();
        System.out.println("Filling list with elements: 1,2,3");
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("Printing list elements");
        for (Integer elem : list) {
            System.out.println(elem);
        }
        System.out.println();

        Stack<Integer> stack = new ArrayList<>();
        System.out.println("Filling stack with elements: 1,2,3");
        stack.add(1);
        stack.add(2);
        stack.add(3);
        System.out.println("Emptying stack");
        Integer stackElement;
        while((stackElement = stack.pop()) != null) {
            System.out.println(stackElement);
        }
        System.out.println();

        Queue<Integer> queue = new ArrayList<>();
        System.out.println("Filling queue with elements: 1,2,3");
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println("Emptying queue");
        Integer queueElement;
        while((queueElement = queue.poll()) != null) {
            System.out.println(queueElement);
        }
    }
}