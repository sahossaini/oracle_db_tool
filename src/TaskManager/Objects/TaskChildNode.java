package TaskManager.Objects;

import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.TokenType;

public class TaskChildNode {
    public String child_id;             // id of task / variable
    public TokenType type;       // var / task
    public String name;                 // task / variable name 
}
