package TaskManager.TaskExecutor;

import java.util.*;

import TaskManager.Objects.ObjectNode;
import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.ValueType;;

public class GlobalCacheManager {
    public static List<ObjectNode> cache
            = Collections.synchronizedList(new ArrayList<ObjectNode>()); // name, type, value

    public GlobalCacheManager () {
    }

    public static int getIdx (String name) {
        // printGlobalCache();
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).variable_name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static ObjectNode getFromCache (String name) {
        int idx = getIdx(name);
        if (idx != -1) {
            return cache.get(idx);
        }
        return null;
    }

    public static void addToCache (String variable_name, String task_id, ValueType object_type, Object cached_object) {
        int idx = getIdx(variable_name);
        if (idx != -1) {
            updateCache (variable_name, task_id, object_type, cached_object);
        }
        else {
            ObjectNode row = new ObjectNode();
            row.variable_name = variable_name;
            row.task_id = task_id;
            row.object_type = object_type;
            row.object = cached_object;
            cache.add(row);
        }
    }

    public static void updateCache (String variable_name, String task_id, ValueType type, Object cached_object) {
        int idx = getIdx(variable_name);
        if (idx != -1) {
            ObjectNode row = new ObjectNode();
            row.variable_name = cache.get(idx).variable_name;
            row.task_id = task_id;
            row.object_type = type;
            row.object = cached_object;
            cache.set(idx, row);
        }
    }

    public static void remove (String name) {
        int idx = getIdx(name);
        if (idx != -1)
            cache.remove(idx);
    }

    public static void printGlobalCache () {
        System.out.println("Total cached : " + cache.size());
        for (ObjectNode row : cache) {
            System.out.println("  name : " + row.variable_name);
            System.out.println("  task_id : " + row.task_id);
            System.out.println("  type : " + row.object_type);
            System.out.println("  value : " + row.object);
            System.out.println(">>");
        }
    }
}
