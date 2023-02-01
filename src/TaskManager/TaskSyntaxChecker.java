package TaskManager;

import TaskManager.Objects.TaskProperty;
import TaskManager.Objects.Enums.TaskName;
import TaskManager.Objects.Enums.TokenType;
import TaskManager.Objects.Enums.ValueType;

public class TaskSyntaxChecker {
    public static TaskProperty[] task_properties = {
            new TaskProperty(TaskName.READ_FROM_FILE, ValueType.STRING, 1, new TokenType[] {TokenType.VARIABLE}),
            new TaskProperty(TaskName.SAVE_TO_FILE, ValueType.STRING, 2, new TokenType[] {TokenType.VARIABLE, TokenType.VARIABLE}),
            new TaskProperty(TaskName.SAVE_TO_VARIABLE, ValueType.VOID, 2, new TokenType[] {TokenType.VARIABLE, TokenType.VARIABLE}),
            new TaskProperty(TaskName.SET_RETURN, ValueType.STRING, 2, new TokenType[] {TokenType.VARIABLE, TokenType.VARIABLE}),
            new TaskProperty(TaskName.SET_CACHE, ValueType.STRING, 2, new TokenType[] {TokenType.VARIABLE, TokenType.VARIABLE}),
            new TaskProperty(TaskName.PRINT, ValueType.VOID, 1, new TokenType[] {TokenType.VARIABLE}),
            new TaskProperty(TaskName.PAUSE, ValueType.STRING, 1, new TokenType[] {TokenType.VARIABLE}),
            new TaskProperty(TaskName.RUN_SERIAL, ValueType.VOID, -1, new TokenType[] {TokenType.TASK}),
            new TaskProperty(TaskName.RUN_SERIAL_LOOP, ValueType.VOID, -1, new TokenType[] {TokenType.TASK}),
            new TaskProperty(TaskName.RUN_PARALLEL, ValueType.VOID, -1, new TokenType[] {TokenType.TASK}),
            new TaskProperty(TaskName.RUN_PARALLEL_CONTINUE, ValueType.VOID, -1, new TokenType[] {TokenType.TASK}),
        };
   
    
    public static int getNumberOfParameters (TaskName task_name) {
        for (TaskProperty t : task_properties) {
            if (t.task_name == task_name) {
                return t.number_of_parameters;
            }
        }
        return 0;
    }

    public static ValueType getReturnType (TaskName task_name) {
        for (TaskProperty t : task_properties) {
            if (t.task_name == task_name) {
                return t.task_return_type;
            }
        }
        return null;
    }
}
