package datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T>, Stack<T>, Queue<T> {
    private class LinkedListIterator implements Iterator<T> {
        private Link cursor;

        public LinkedListIterator(LinkedList<T> list) {
            cursor = list.begin;
        }

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
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

    @Override
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

    @Override
    public T get(int index) {
        int i = 0;
        for (T element : this) { // eat your own dog food
            if (i == index) {
                return element;
            }
            i++;
        }
        return null;
    }

    @Override
    public T pop() {
        if (end == null) {
            return null;
        }
        T element = end.value;
        end = end.previous;

        if (end == null) {
            begin = null;
        }

        return element;
    }

    @Override
    public T poll() {
        if (begin == null) {
            return null;
        }
        T element = begin.value;
        begin = begin.next;

        if (begin == null) {
            end = null;
        }

        return element;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator(this);
    }

    public static void main(String[] args) {
        System.out.println("LinkedList\n");

        List<Integer> list = new LinkedList<>();
        System.out.println("Filling list with elements: 1,2,3");
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("Printing list elements");
        for (Integer elem : list) {
            System.out.println(elem);
        }
        System.out.println();

        Stack<Integer> stack = new LinkedList<>();
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

        Queue<Integer> queue = new LinkedList<>();
        System.out.println("Filling queue with elements: 1,2,3");
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println("Emptying queue");
        Integer queueElement;
        while((queueElement = queue.poll()) != null) {
            System.out.println(queueElement);
        }

        System.out.println(list.get(2));
    }
}

