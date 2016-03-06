package datastructures;

public interface Map<K,V> extends Iterable<K> {
    void put(K key, V value);
    V get(K key);
}