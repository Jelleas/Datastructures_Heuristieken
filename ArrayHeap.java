package com.company;

import java.util.Iterator;
import java.util.ArrayList; // Cheat import
import java.util.NoSuchElementException;

public class ArrayHeap<T extends Comparable<T>> implements Heap<T>, PriorityQueue<T> {
    private class ArrayHeapIterator implements Iterator<T> {
        private ArrayHeap<T> heap;

        public ArrayHeapIterator(ArrayHeap<T> heap) {
            this.heap = new ArrayHeap<>(heap);
        }

        @Override
        public boolean hasNext() {
            return !heap.elements.isEmpty();
        }

        @Override
        public T next() {
            if (hasNext()) {
                return heap.poll();
            }
            throw new NoSuchElementException();
        }
    }

    private ArrayList<T> elements;

    public ArrayHeap() {
        elements = new ArrayList<>();
    }

    public ArrayHeap(ArrayHeap<T> other) {
        this();
        elements.addAll(other.elements);
    }

    @Override
    public void add(T element) {
        elements.add(element);
        upHeap(elements.size() - 1);
    }

    private void upHeap(int index) {
        if (index == 0) {
            return;
        }

        int parentIndex = index % 2 == 0 ? (index - 2) / 2 : (index - 1) / 2;
        T parent = elements.get(parentIndex);
        T child = elements.get(index);

        if (child.compareTo(parent) == 1) {
            elements.set(parentIndex, child);
            elements.set(index, parent);

            upHeap(parentIndex);
        }
    }

    @Override
    public T poll() {
        T element = elements.get(0);
        elements.set(0, elements.get(elements.size() - 1));
        elements.remove(elements.size() - 1);
        downHeap(0);
        return element;
    }

    private void downHeap(int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int largest = index;

        if (left < elements.size() && elements.get(left).compareTo(elements.get(largest)) == 1) {
            largest = left;
        }

        if (right < elements.size() && elements.get(right).compareTo(elements.get(largest)) == 1) {
            largest = right;
        }

        if (largest != index) {
            T temp = elements.get(largest);
            elements.set(largest, elements.get(index));
            elements.set(index, temp);

            downHeap(largest);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayHeapIterator(this);
    }

    public static void main(String[] args) {
        Heap<Integer> heap = new ArrayHeap<>();
        heap.add(1);
        heap.add(2);
        heap.add(42);
        heap.add(3);
        for (Integer x : heap) {
            System.out.println(x);
        }
        for (Integer x : new int[]{0,1}) {
            System.out.println(heap.poll());
        }
        heap.add(27);
        heap.add(0);
        for (Integer x : new int[]{0,1,2,3}) {
            System.out.println(heap.poll());
        }
    }
}

