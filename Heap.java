public interface Heap<T> extends Iterable<T> {
    void add(T element);
    T poll();
}