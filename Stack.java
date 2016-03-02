public interface Stack<T> extends Iterable<T> {
    void add(T element);
    T pop();
}