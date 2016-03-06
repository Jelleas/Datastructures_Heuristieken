package datastructures;

import java.util.Iterator;
import java.util.LinkedList; // cheat import
import java.util.ArrayList; // cheat import
import java.util.List; // cheat import
import java.util.Queue; // cheat import
import java.util.NoSuchElementException;
import java.util.function.Function;

public class GenericTrie<K extends Iterable<? extends Comparable<?>>, V> implements Map<K,V>, Iterable<K> {
    private class GenericTrieIterator implements Iterator<K> {
        private Queue<K> keys;

        public GenericTrieIterator(GenericTrie<K,V> trie) {
            keys = new LinkedList<>();
            collectValues(trie.rootNode);
        }

        private void collectValues(Node node) {
            if (node.isLeaf) {
                keys.add(node.key);
            }
            for (Node nextNode : node.nextNodes) {
                collectValues(nextNode);
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

    private class Node {
        private LinkedList<Node> nextNodes;
        private K key;
        private V value;
        private boolean isLeaf;
        private Comparable item;

        public Node(Comparable item) {
            nextNodes = new LinkedList<>();
            isLeaf = false;
            this.item = item;
        }

        public void add(Iterator<? extends Comparable> iterator, K key, V value) {
            if (!iterator.hasNext()) {
                isLeaf = true;
                this.key = key;
                this.value = value;
                return;
            }

            Comparable nextItem = iterator.next();
            int index = 0;

            for (Node node : nextNodes) {
                if (node.item.equals(nextItem)) {
                    node.add(iterator, key, value);
                    return;
                }
                @SuppressWarnings("unchecked")
                int compareResult = node.item.compareTo(nextItem);
                if (compareResult > 0) {
                    break;
                }

                index++;
            }
            Node nextNode = new Node(nextItem);
            nextNodes.add(index, nextNode);
            nextNode.add(iterator, key, value);
        }

        public V get(Iterator<? extends Comparable> iterator) {
            if (!iterator.hasNext()) {
                if (isLeaf) {
                    return value;
                }
                return null;
            }

            Comparable next = iterator.next();
            int index = indexOf(next);
            if (index != -1) {
                return nextNodes.get(index).get(iterator);
            }

            return null;
        }

        private int indexOf(Comparable item) {
            int index = 0;
            for (Node node : nextNodes) {
                if (node.item.equals(item)) {
                    return index;
                }
                @SuppressWarnings("unchecked")
                int compareResult = node.item.compareTo(item);
                if (compareResult > 0) {
                    break;
                }
                index++;
            }
            return -1;
        }
    }

    private Node rootNode;

    public GenericTrie() {
        rootNode = new Node(null);
    }

    @Override
    public void put(K key, V value) {
        rootNode.add(key.iterator(), key, value);
    }

    @Override
    public V get(K key) {
        return rootNode.get(key.iterator());
    }

    @Override
    public Iterator<K> iterator() {
        return new GenericTrieIterator(this);
    }

    public static void main(String[] args) {
        // small function useful for the specific example below
        Function<String, ArrayList<Character>> stringToCharList = (text) -> {
            ArrayList<Character> list = new ArrayList<>();
            for (Character letter : text.toCharArray()) {
                list.add(letter);
            }
            return list;
        };

        // small function useful for the specific example below
        Function<List<Character>, String> charListToString = (list) -> {
            String text = "";
            for (Character letter : list) {
                text += letter;
            }
            return text;
        };

        System.out.println("GenericTrie\n");

        System.out.println("Filling hashtable with key-value pairs:" +
                " \"hello\":10, \"world!\":20, \"penguin\":41, \"penguin\":42");
        Map<ArrayList<Character>, Integer> trie = new GenericTrie<>();
        trie.put(stringToCharList.apply("hello"), 10);
        trie.put(stringToCharList.apply("world!"), 20);
        trie.put(stringToCharList.apply("penguin"), 41);
        trie.put(stringToCharList.apply("penguin"), 42);
        System.out.println();

        System.out.println("Getting key \"penguin\"");
        System.out.println(trie.get(stringToCharList.apply("penguin")));
        System.out.println("Getting key \"hello\"");
        System.out.println(trie.get(stringToCharList.apply("hello")));
        System.out.println("Getting key \"world!\"");
        System.out.println(trie.get(stringToCharList.apply("world!")));
        System.out.println();

        System.out.println("Iterating over trie and printing all keys:");
        for (ArrayList<Character> key : trie) {
            System.out.println(charListToString.apply(key));
        }
    }
}
