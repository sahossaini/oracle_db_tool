package TaskManager;

import java.util.*;

import TaskManager.Objects.GlobalCacheNode;
import TaskManager.Objects.Enums.ValueType;;

public class GlobalCacheManager {
    public static List<GlobalCacheNode> cache; // name, type, value

    public GlobalCacheManager () {
        cache = Collections.synchronizedList(new ArrayList<GlobalCacheNode>());
    }

    public static int getIdx (String name) {
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static GlobalCacheNode getFromCache (String name) {
        int idx = getIdx(name);
        if (idx != -1) {
            return cache.get(idx);
        }
        return null;
    }

    public static void addToCache (String name, ValueType type, String value) {
        int idx = getIdx(name);
        if (idx != -1) {
            updateCache (name, value);
        }
        else {
            GlobalCacheNode row = new GlobalCacheNode();
            row.name = name;
            row.type = type;
            row.value = value;
            cache.add(row);
        }
    }

    public static void updateCache (String name, String update_value) {
        int idx = getIdx(name);
        if (idx != -1) {
            GlobalCacheNode row = new GlobalCacheNode();
            row.name = cache.get(idx).name;
            row.type = cache.get(idx).type;
            row.value = update_value;
            cache.set(idx, row);
        }
    }

    public static void remove (String name) {
        int idx = getIdx(name);
        if (idx != -1)
            cache.remove(idx);
    }

    public static void printCache () {
        for (GlobalCacheNode row : cache) {
            System.out.println("  name : " + row.name);
            System.out.println("  type : " + row.type);
            System.out.println("  value : " + row.value);
            System.out.println(">>");
        }
    }
}
