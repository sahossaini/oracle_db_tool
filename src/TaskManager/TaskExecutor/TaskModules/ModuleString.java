package TaskManager.TaskExecutor.TaskModules;

import TaskManager.Objects.ObjectNode;
import TaskManager.TaskExecutor.TaskManagerData;
import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.ValueType;

public class ModuleString {
    public TaskManagerData task_data;

    public ModuleString (TaskManagerData task_data_reference) {
        this.task_data = task_data_reference;
    }

    public void char_at () {
        String string = (String) task_data.getParameters().get(0).object;
        int idx = task_data.getParameters().get(1).getNumberFromObject().intValue();

        string = String.valueOf(string.charAt(idx));
        
        task_data.setReturn(task_data.executing_task_id, ValueType.STRING, string);        
    }

    public void length () {
        String string = (String) task_data.getParameters().get(0).object;

        Double length = (double) string.length();
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, length); 
    }
}
