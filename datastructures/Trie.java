package datastructures;

import java.util.Iterator;
import java.util.LinkedList; // cheat import
import java.util.Queue; // cheat import
import java.util.NoSuchElementException;
import java.util.function.Function;

public class Trie<K extends Iterable, V> implements Map<K,V>, Iterable<K> {
    private class TrieIterator implements Iterator<K> {
        private Queue<K> keys;

        public TrieIterator(Trie<K,V> trie) {
            keys = new LinkedList<>();
            collectValues(trie.rootNode);
        }

        private void collectValues(Node node) {
            if (node.leaf != null) {
                keys.add(node.leaf.key);
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

    private class Leaf {
        final K key;
        final V value;
        public Leaf(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class Node {
        private LinkedList<Node> nextNodes;
        private Leaf leaf;
        private Object item;

        public Node(Object item) {
            nextNodes = new LinkedList<>();
            this.item = item;
        }

        public void add(Iterator iterator, K key, V value) {
            if (!iterator.hasNext()) {
                leaf = new Leaf(key, value);
                return;
            }

            Object nextItem = iterator.next();

            for (Node node : nextNodes) {
                if (node.item.equals(nextItem)) {
                    node.add(iterator, key, value);
                    return;
                }
            }

            Node nextNode = new Node(nextItem);
            nextNodes.add(nextNode);
            nextNode.add(iterator, key, value);
        }

        public V get(Iterator iterator) {
            if (!iterator.hasNext()) {
                if (leaf != null) {
                    return leaf.value;
                }
                return null;
            }

            Object next = iterator.next();
            int index = indexOf(next);
            if (index != -1) {
                return nextNodes.get(index).get(iterator);
            }

            return null;
        }

        private int indexOf(Object item) {
            int index = 0;
            for (Node node : nextNodes) {
                if (node.item.equals(item)) {
                    return index;
                }
                index++;
            }
            return -1;
        }
    }

    private Node rootNode;

    public Trie() {
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
        return new TrieIterator(this);
    }

    public static void main(String[] args) {
        // small function useful for the specific example below
        Function<String, Iterable<Character>> stringToCharList = (text) -> {
            ArrayList<Character> list = new ArrayList<>();
            for (Character letter : text.toCharArray()) {
                list.add(letter);
            }
            return list;
        };

        // small function useful for the specific example below
        Function<Iterable<Character>, String> charListToString = (list) -> {
            String text = "";
            for (Character letter : list) {
                text += letter;
            }
            return text;
        };

        System.out.println("Trie\n");

        System.out.println("Filling trie with key-value pairs:" +
                " \"hello\":10, \"world!\":20, \"penguin\":41, \"penguin\":42");
        Map<Iterable<Character>, Integer> trie = new Trie<>();
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
        for (Iterable<Character> key : trie) {
            System.out.println(charListToString.apply(key));
        }
    }
}
