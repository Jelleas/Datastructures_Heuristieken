package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
    private class LinkedListIterator implements Iterator<T> {
        private Link cursor;

        public LinkedListIterator(LinkedList<T> list) {
            cursor = list.begin;
        }

        public boolean hasNext() {
            return cursor != null;
        }

        public T next() {
            if (hasNext()) {
                T element = cursor.value;
                cursor = cursor.next;
                return element;
            }
            throw new NoSuchElementException();
        }
    }

    private class Link {
        Link previous, next;
        T value;

        public Link(T value) {
            this.value = value;
        }
    }

    private Link begin, end;

    public void add(T element) {
        Link link = new Link(element);
        if (begin == null) {
            begin = link;
        } else {
            end.next = link;
            link.previous = end;
        }
        end = link;
    }

    public T get(int index) {
        int i = 0;
        for (T element : this) { // <- eat your own dog food
            if (i == index) {
                return element;
            }
            i++;
        }
        return null;
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator(this);
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

