package top.focess.expressionmfc.util;

public class Pair<K,V> {

    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public static <K,V> Pair<K,V> of(K k,V v) {
        return new Pair<>(k,v);
    }

    public K getLeft() {
        return this.getKey();
    }

    public V getRight() {
        return this.getValue();
    }
}
