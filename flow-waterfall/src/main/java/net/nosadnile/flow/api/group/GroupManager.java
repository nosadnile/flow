package net.nosadnile.flow.api.group;

import net.nosadnile.flow.api.utils.ElementWithId;
import net.nosadnile.flow.api.utils.ElementWithName;
import net.nosadnile.flow.api.utils.ElementWithUUID;
import net.nosadnile.flow.api.utils.ElementWithUniqueId;

import java.util.HashMap;
import java.util.Map;

public class GroupManager<K, V> {
    private Map<K, V> elements;

    public GroupManager() {
        elements = new HashMap<>();
    }

    public void add(K key, V value) {
        elements.put(key, value);
    }

    public void add(ElementWithName<K> element) {
        elements.put(element.getName(), (V) element);
    }

    public void add(ElementWithId<K> element) {
        elements.put(element.getId(), (V) element);
    }

    public void add(ElementWithUniqueId<K> element) {
        elements.put(element.getUniqueId(), (V) element);
    }

    public void add(ElementWithUUID<K> element) {
        elements.put(element.getUUID(), (V) element);
    }

    public void remove(K key) {
        elements.remove(key);
    }

    public void remove(V value, boolean removeAll) {
        for (Map.Entry<K, V> entry : elements.entrySet()) {
            if (entry.getValue() == value) {
                elements.remove(entry.getKey());
                if (!removeAll) return;
            }
        }
    }

    public void remove(ElementWithName<K> element) {
        elements.remove(element.getName());
    }

    public void remove(ElementWithId<K> element) {
        elements.remove(element.getId());
    }

    public void remove(ElementWithUUID<K> element) {
        elements.remove(element.getUUID());
    }

    public void remove(ElementWithUniqueId<K> element) {
        elements.remove(element.getUniqueId());
    }

    public V get(K key) {
        return elements.get(key);
    }

    public void replace(K key, V newValue) {
        elements.replace(key, newValue);
    }

    public boolean containsValue(V value) {
        return elements.containsValue(value);
    }

    public boolean containsKey(K key) {
        return elements.containsKey(key);
    }

    public boolean contains(ElementWithName<K> element) {
        return elements.containsKey(element.getName());
    }

    public boolean contains(ElementWithId<K> element) {
        return elements.containsKey(element.getId());
    }

    public boolean contains(ElementWithUUID<K> element) {
        return elements.containsKey(element.getUUID());
    }

    public boolean contains(ElementWithUniqueId<K> element) {
        return elements.containsKey(element.getUniqueId());
    }
}
