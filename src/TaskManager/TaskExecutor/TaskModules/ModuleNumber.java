package TaskManager.TaskExecutor.TaskModules;

import TaskManager.Objects.ObjectNode;
import TaskManager.TaskExecutor.TaskManagerData;
import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.TaskReference;
import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.ValueType;
import Utilities.Logger;
import Utilities.Logger.logType;

public class ModuleNumber {
    public TaskManagerData task_data;

    public ModuleNumber (TaskManagerData task_data_reference) {
        this.task_data = task_data_reference;
    }
   
    public void add () {
        Double result = 0.0;
        for (ObjectNode param : task_data.getParameters()) {
            result += param.getNumberFromObject();
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 

    public void substract () {
        Double result = 0.0;
        for (int i = 0; i < task_data.getParameters().size(); i++) {
            ObjectNode param = task_data.getParameters().get(i);
            if (i == 0)
                result = result + param.getNumberFromObject();
            else result = result - param.getNumberFromObject();
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 

    public void multiply () {
        Double result = 1.0;
        for (ObjectNode param : task_data.getParameters()) {
            result *= param.getNumberFromObject();
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 

    public void divide () {
        Double result = 0.0;
        for (int i = 0; i < task_data.getParameters().size(); i++) {
            ObjectNode param = task_data.getParameters().get(i);
            if (i == 0)
                result = result + param.getNumberFromObject();
            else result = result / param.getNumberFromObject();
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 

    public void mod () {
        Double result = 0.0;
        for (int i = 0; i < task_data.getParameters().size(); i++) {
            ObjectNode param = task_data.getParameters().get(i);
            if (i == 0)
                result = result + param.getNumberFromObject();
            else result = result % param.getNumberFromObject();
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 
}
