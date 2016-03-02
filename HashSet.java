package com.company;

import java.util.Iterator;

public class HashSet<T> implements Set<T> {
    private HashTable<T,Boolean> table;

    public HashSet() {
        table = new BucketHashTable<>();
    }

    public void add(T element) {
        if (!contains(element)) {
            table.put(element, true);
        }
    }

    public Boolean contains(T element) {
        return table.get(element) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return table.iterator();
    }

    public static void main(String[] args) {
        System.out.println("HashSet\n");

        Set<Integer> set = new HashSet<>();
        System.out.println("Filling set with 102,102,2,2,1,3");
        set.add(102);
        set.add(102);
        set.add(2);
        set.add(2);
        set.add(1);
        set.add(3);
        System.out.println();

        System.out.println("Iterating over set and printing all elements:");
        for (Integer element : set) {
            System.out.println(element);
        }
    }
}

