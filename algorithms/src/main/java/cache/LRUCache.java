package cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 2017/6/30.
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V>{
    //定义缓存的容量
    private int capacity;
    private static final long serialVersionUID = 1L;
    //带参数的构造器
    public LRUCache(int capacity){
        //调用LinkedHashMap的构造器，传入以下参数
        super(capacity);
        this.capacity=capacity;
    }

    @Override
    public V get(Object key) {
        V value = get(key);
        if (value != null) return value;
        /* do query the object */
        V v = null;
        put((K)key, v);
        return v;
    }

    //实现LRU的关键方法，如果map里面的元素个数大于了缓存最大容量，则删除链表的顶端元素
    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest){
        System.out.println(eldest.getKey() + "=" + eldest.getValue());
        return size() > capacity;
    }

    public static void main(String [] args) {
        LRUCache<String, String> t = new LRUCache<>(8);
        System.out.println(t.get("1"));
        System.out.println(t.get("1"));
    }
}
