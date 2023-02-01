package TaskManager.Objects;

public class ReturnCacheNode {
    public enum ReturnCacheType {
        STRING, NUMBER
    }
    public String task_id;  // Task id of the task the return belongs to
    public ReturnCacheType type;     // string, number etc.
    public String value;    // return value
}
