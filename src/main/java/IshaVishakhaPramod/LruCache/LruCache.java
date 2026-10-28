package IshaVishakhaPramod.LruCache;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LruCache<K,V> {
    private final static class Node<K,V> {
        final K key;
        V value;
        Node<K,V> prev, next;
        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }
    final private int capacity;
    private final Map<K, Node<K,V>> map;
    private final Node<K,V> head = new Node<>(null,null);
    private final Node<K,V> tail = new Node<>(null,null);

    public LruCache(int capacity){
        if (capacity<=0) throw new IllegalArgumentException("Capacity must be positive value");
        this.capacity = capacity;
        this.map = new HashMap<>((int) Math.ceil(capacity*1.34));
        head.next = tail;
        tail.prev = head;
    }
    public int size(){
        return capacity;
    }
    private void addToFront(Node<K, V> node) {
        Node<K,V> temp = head.next;
        node.next = temp;
        node.prev = head;
        head.next = node;
        temp.prev = node;
    }
    private void removeNode(Node<K,V> node){
        Node<K,V> oldPrev = node.prev;
        Node<K,V> oldNext = node.next;
        oldPrev.next = oldNext;
        oldNext.prev = oldPrev;
    }
    private void moveToFront(Node<K,V> node){
        removeNode(node);
        addToFront(node);
    }
    private void removeLru(){
        Node<K,V> lru = tail.prev;
        removeNode(lru);
        map.remove(lru.key);
    }
    public V get(K key){
        Objects.requireNonNull(key, "Key must not be null");
        Node<K,V> node = map.get(key);
        if (node == null){
            return null;
        }
        moveToFront(node);
        return node.value;
    }
    public void put(K key, V value){
        Objects.requireNonNull(key, "key cannot be null");
        Objects.requireNonNull(value, "value cannot be null");
        Node<K,V> node = map.get(key);
        if (node != null){
            node.value = value;
            moveToFront(node);
        }else{
            if (map.size() == capacity){
                removeLru();
            }
            node = new Node<>(key,value);
            addToFront(node);
            map.put(key,node);
        }

    }



}
