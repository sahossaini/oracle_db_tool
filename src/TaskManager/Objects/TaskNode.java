package TaskManager.Objects;

import java.util.ArrayList;

public class TaskNode {    
    public enum TaskNodeStatus {
        NOT_STARTED, RUNNING, PAUSED, ENDED
    }
    public String task_id;
    public String task_name;
    public String child_text;
    public TaskNodeStatus task_status; // notstarted, running, paused, ended
    public String parent_task_id;
    public String parent_task_name;
    public ArrayList<TaskChildNode> children; // task/variable_id, type (variable / function), task/variable_name
}

