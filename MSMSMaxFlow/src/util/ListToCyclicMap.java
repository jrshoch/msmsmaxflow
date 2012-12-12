package util;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class ListToCyclicMap {

    private ListToCyclicMap() {
        // Utility class
    }
    
    public static <E> Map<E, E> getForwardsCyclicMap(List<E> list) {
        int size = list.size();
        Map<E, E> result = Maps.newHashMap();
        for (int i = 0; i < list.size() - 1; i++) {
            result.put(list.get(i), list.get(i + 1));
        }
        E first = list.get(0);
        E last = list.get(size - 1);
        result.put(last, first);
        return result;
    }
    
    public static <E> Map<E, E> getBackwardsCyclicMap(List<E> list) {
        int size = list.size();
        Map<E, E> result = Maps.newHashMap();
        for (int i = 1; i < size; i++) {
            result.put(list.get(i), list.get(i - 1));
        }
        E first = list.get(0);
        E last = list.get(size - 1);
        result.put(first, last); 
        return result;
    }
}
