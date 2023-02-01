package TaskManager.Objects;

public class TaskChildNode {
    public enum TaskChildType {
        VARIABLE, TASK
    }
    public String child_id;             // id of task / variable
    public TaskChildType type;          // var / func
    public String name;                 // task / variable name 
}
