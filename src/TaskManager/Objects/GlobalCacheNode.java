package TaskManager.Objects;

import TaskManager.Objects.Enums.ValueType;

public class GlobalCacheNode {
    public String variable_name;
    public String task_id; 
    public ValueType type;
    public Object cached_object;
}
