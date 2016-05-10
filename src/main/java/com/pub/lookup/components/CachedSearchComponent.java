package com.pub.lookup.components;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class CachedSearchComponent<T> {
    
    private static final int MAX_CACHE_SIZE = 10;

    public CachedSearchComponent() {
        super();
    }

    private LinkedList<T> cache = new LinkedList<T>();

    public List<T> getCache() {
        return cache;
    }
    
    public void add(T cachedSearch) {
        if(cache.size() == MAX_CACHE_SIZE) {
            cache.removeLast();
        }
        
        if(cache.contains(cachedSearch)) {
            cache.remove(cachedSearch);
        }
        
        cache.addFirst(cachedSearch);
        
    }
    
}
