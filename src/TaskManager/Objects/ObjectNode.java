package TaskManager.Objects;

import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.ValueType;

public class ObjectNode {
    public String variable_name;            // the variable name of the var the object belongs to (eg. "x" = "some_text")
    public String task_id;                  // Task id of the task the return belongs to
    public ValueType object_type;           // string, number etc.
    public Object object;                   // value
}
