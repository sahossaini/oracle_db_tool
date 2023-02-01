package TaskManager.Objects;

import TaskManager.Objects.Enums.ValueType;

public class ReturnCacheNode {
    public String task_id;  // Task id of the task the return belongs to
    public ValueType type;     // string, number etc.
    public String value;    // return value
}
