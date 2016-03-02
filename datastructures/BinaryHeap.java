package datastructures;

import java.util.Iterator;
import java.util.ArrayList; // Cheat import
import java.util.NoSuchElementException;

public class BinaryHeap<T extends Comparable<T>> implements Heap<T>, PriorityQueue<T> {
    private class BinaryHeapIterator implements Iterator<T> {
        private BinaryHeap<T> heap;

        public BinaryHeapIterator(BinaryHeap<T> heap) {
            this.heap = new BinaryHeap<>(heap);
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

    public BinaryHeap() {
        elements = new ArrayList<>();
    }

    public BinaryHeap(BinaryHeap<T> other) {
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
        if (elements.isEmpty()) {
            return null;
        }

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
        return new BinaryHeapIterator(this);
    }

    public static void main(String[] args) {
        System.out.println("BinaryHeap\n");

        Heap<Integer> heap = new BinaryHeap<>();
        System.out.println("Filling heap with elements: 2,0,4,42,6");
        heap.add(2);
        heap.add(0);
        heap.add(4);
        heap.add(42);
        heap.add(6);
        System.out.println();

        System.out.println("Polling two times");
        System.out.println(heap.poll());
        System.out.println(heap.poll());
        System.out.println();

        System.out.println("Adding 27,3,5");
        heap.add(27);
        heap.add(3);
        heap.add(5);
        System.out.println();

        System.out.println("Emptying heap");
        Integer element;
        while ((element = heap.poll()) != null) {
            System.out.println(element);
        }
    }
}

