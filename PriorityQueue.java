public interface PriorityQueue<T> extends Iterable<T> {
    void add(T element);
    T poll();
}
