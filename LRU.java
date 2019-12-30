package com.xiang;

import java.util.HashMap;

public class LRU<K, V> {

	
	private int currentSize;//当前的大�?
    private int capcity;//总容�?
    private HashMap<K, Node> caches;//�?有的node节点
    private Node first;//头节�?
    private Node last;//尾节�?
 
    public LRU(int size) {
        currentSize = 0;
        this.capcity = size;
        caches = new HashMap<K, Node>(size);
    }
 
    /**
     * 放入元素
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        Node node = caches.get(key);
        //如果新元�?
        if (node == null) {
            //如果超过元素容纳�?
            if (caches.size() >= capcity) {
                //移除�?后一个节�?
                caches.remove(last.key);
                removeLast();
            }
            //创建新节�?
            node = new Node(key,value);
        }
        //已经存在的元素覆盖旧�?
        node.value = value;
        //把元素移动到首部
        moveToHead(node);
        caches.put(key, node);
    }
 
    /**
     * 通过key获取元素
     * @param key
     * @return
     */
    public Object get(K key) {
        Node node = caches.get(key);
        if (node == null) {
            return null;
        }
        //把访问的节点移动到首�?
        moveToHead(node);
        return node.value;
    }
 
    /**
     * 根据key移除节点
     * @param key
     * @return
     */
    public Object remove(K key) {
        Node node = caches.get(key);
        if (node != null) {
            if (node.pre != null) {
                node.pre.next = node.next;
            }
            if (node.next != null) {
                node.next.pre = node.pre;
            }
            if (node == first) {
                first = node.next;
            }
            if (node == last) {
                last = node.pre;
            }
        }
        return caches.remove(key);
    }
 
    /**
     * 清除�?有节�?
     */
    public void clear() {
        first = null;
        last = null;
        caches.clear();
    }
 
    /**
     * 把当前节点移动到首部
     * @param node
     */
    private void moveToHead(Node node) {
        if (first == node) {
            return;
        }
        if (node.next != null) {
            node.next.pre = node.pre;
        }
        if (node.pre != null) {
            node.pre.next = node.next;
        }
        if (node == last) {
            last = last.pre;
        }
        if (first == null || last == null) {
            first = last = node;
            return;
        }
        node.next = first;
        first.pre = node;
        first = node;
        first.pre = null;
    }
 
    /**
     * 移除�?后一个节�?
     */
    private void removeLast() {
        if (last != null) {
            last = last.pre;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
        }
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node node = first;
        while (node != null) {
            sb.append(String.format("%s:%s ", node.key, node.value));
            node = node.next;
        }
        return sb.toString();
    }
     
 
    public static void main(String[] args) {
        LRU<Integer, String> lru = new LRU<Integer, String>(5);
        lru.put(1, "a");
        lru.put(2, "b");
        lru.put(3, "c");
        lru.put(4,"d");
        lru.put(5,"e");
        System.out.println("原始链表�?:"+lru.toString());
 
        lru.get(4);
        System.out.println("获取key�?4的元素之后的链表:"+lru.toString());
 
        lru.put(6,"f");
        System.out.println("新添加一个key�?6之后的链�?:"+lru.toString());
 
        lru.remove(3);
        System.out.println("移除key=3的之后的链表:"+lru.toString());
    }

}
class Node {
    //�?
    Object key;
    //�?
    Object value;
    //上一个节�?
    Node pre;
    //下一个节�?
    Node next;
 
    public Node(Object key, Object value) {
        this.key = key;
        this.value = value;
    }
}
