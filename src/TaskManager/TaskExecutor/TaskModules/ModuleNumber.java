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
    
    public static Double stringToDouble (String str) {
        str = str.toLowerCase().trim();
        double value;
        String tmp = "";
        char[] numbers = {'.', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < str.length(); i++) {
            for (char c : numbers) {
                if (str.charAt(i) == c) {
                    tmp = tmp + c;
                    break;
                }
            }
        }
        try {
            value = Double.parseDouble(tmp);
        }
        catch (Exception e) {
            Logger.log(logType.WARNING, "Failed to parse number - " + str);
            return null;
        }
        return value;
    }

    public void add () {
        Double result = 0.0;
        for (ObjectNode param : task_data.getParameters()) {
            String object_type = TaskManagerData.getObjectType(param.object);
            switch (object_type) {
                case "STRING" :
                    result += stringToDouble((String) param.object);
                    break;

                case "DOUBLE" :
                    result += (Double) param.object;
                    break;

                default :
                    Logger.log(logType.WARNING, "Object type cannot be used!");
                    break;
            }
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 

    public void substract () {
        Double result = 0.0;
        for (int i = 0; i < task_data.getParameters().size(); i++) {
            ObjectNode param = task_data.getParameters().get(i);
            String object_type = TaskManagerData.getObjectType(param.object);
            switch (object_type) {
                case "STRING" :
                    if (i == 0)
                        result = result + stringToDouble((String) param.object);
                    else result = result - stringToDouble((String) param.object);
                    break;

                case "DOUBLE" :
                    if (i == 0)
                        result = result + (Double) param.object;
                    else result = result - (Double) param.object;
                    break;

                default :
                    Logger.log(logType.WARNING, "Object type cannot be used!");
                    break;
            }
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 

    public void multiply () {
        Double result = 1.0;
        for (ObjectNode param : task_data.getParameters()) {
            String object_type = TaskManagerData.getObjectType(param.object);
            switch (object_type) {
                case "STRING" :
                    result *= stringToDouble((String) param.object);
                    break;

                case "DOUBLE" :
                    result *= (Double) param.object;
                    break;

                default :
                    Logger.log(logType.WARNING, "Object type cannot be used!");
                    break;
            }
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 

    public void divide () {
        Double result = 0.0;
        for (int i = 0; i < task_data.getParameters().size(); i++) {
            ObjectNode param = task_data.getParameters().get(i);
            String object_type = TaskManagerData.getObjectType(param.object);
            switch (object_type) {
                case "STRING" :
                    if (i == 0)
                        result = result + stringToDouble((String) param.object);
                    else result = result / stringToDouble((String) param.object);
                    break;

                case "DOUBLE" :
                    if (i == 0)
                        result = result + (Double) param.object;
                    else result = result / (Double) param.object;
                    break;

                default :
                    Logger.log(logType.WARNING, "Object type cannot be used!");
                    break;
            }
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 

    public void mod () {
        Double result = 0.0;
        for (int i = 0; i < task_data.getParameters().size(); i++) {
            ObjectNode param = task_data.getParameters().get(i);
            String object_type = TaskManagerData.getObjectType(param.object);
            switch (object_type) {
                case "STRING" :
                    if (i == 0)
                        result = result + stringToDouble((String) param.object);
                    else result = result % stringToDouble((String) param.object);
                    break;

                case "DOUBLE" :
                    if (i == 0)
                        result = result + (Double) param.object;
                    else result = result % (Double) param.object;
                    break;

                default :
                    Logger.log(logType.WARNING, "Object type cannot be used!");
                    break;
            }
        }
        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, result);
    } 
}
