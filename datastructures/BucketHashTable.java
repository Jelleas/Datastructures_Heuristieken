package datastructures;

import java.util.ArrayList; // Cheat import
import java.util.Queue; // Cheat import
import java.util.LinkedList; // Cheat import
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BucketHashTable<K,V> implements Map<K,V> {
    private class HashTableIterator implements Iterator<K> {
        private Queue<K> keys;

        public HashTableIterator(BucketHashTable<K,V> hashTable) {
            keys = new LinkedList<>();
            for (Bucket bucket : hashTable.buckets) {
                keys.addAll(bucket.keys);
            }
        }

        @Override
        public K next() {
            if (hasNext()) {
                return keys.poll();
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasNext() {
            return !keys.isEmpty();
        }
    }

    private class Bucket {
        ArrayList<K> keys;
        ArrayList<V> values;

        public Bucket() {
            keys = new ArrayList<>();
            values = new ArrayList<>();
        }

        public void put(K key, V value) {
            for (int i = 0; i < keys.size(); i++)
            {
                if (keys.get(i).equals(key)) {
                    values.set(i, value);
                    return;
                }
            }
            keys.add(key);
            values.add(value);
        }

        public V get(K key) {
            for (int i = 0; i < keys.size(); i++)
            {
                if (keys.get(i).equals(key)) {
                    return values.get(i);
                }
            }
            return null;
        }
    }

    private ArrayList<Bucket> buckets;

    public BucketHashTable() {
        buckets = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            buckets.add(new Bucket());
        }
    }

    @Override
    public void put(K key, V value) {
        buckets.get(index(key)).put(key, value);
    }

    @Override
    public V get(K key) {
        return buckets.get(index(key)).get(key);
    }

    private int index(K key) {
        return Math.abs(key.hashCode()) % buckets.size();
    }

    public Iterator<K> iterator() {
        return new HashTableIterator(this);
    }

    public static void main(String[] args) {
        System.out.println("BucketHashTable\n");

        Map<String, Integer> table = new BucketHashTable<>();
        System.out.println("Filling hashtable with key-value pairs:" +
                " \"Hello\":10, \"World!\":20, \"penguin\":41, \"penguin\":42");
        table.put("Hello", 10);
        table.put("World!", 20);
        table.put("penguin", 41);
        table.put("penguin", 42);
        System.out.println();

        System.out.println("Getting key \"penguin\"");
        System.out.println(table.get("penguin"));
        System.out.println("Getting key \"Hello\"");
        System.out.println(table.get("Hello"));
        System.out.println("Getting key \"World!\"");
        System.out.println(table.get("World!"));
        System.out.println();

        System.out.println("Iterating over hashtable and printing all keys:");
        for (String key : table) {
            System.out.println(key);
        }
    }
}
