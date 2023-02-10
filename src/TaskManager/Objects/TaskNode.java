package TaskManager.Objects;

import java.util.ArrayList;

import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.TaskNodeStatus;
import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.TaskReference;

public class TaskNode {
    public String task_id;
    public String task_name;
    public TaskReference task;
    public String child_text;
    public TaskNodeStatus task_status; // notstarted, running, paused, ended
    public String parent_task_id;
    public String parent_task_name;
    public ArrayList<TaskChildNode> children; // task/variable_id, type (variable / function), task/variable_name
}

