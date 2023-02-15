package TaskManager.TaskExecutor.TaskModules;


import TaskManager.Objects.ObjectNode;
import TaskManager.TaskExecutor.GlobalCacheManager;
import TaskManager.TaskExecutor.TaskManagerData;
import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.ValueType;

public class ModuleCore {
    public TaskManagerData task_data;

    public ModuleCore (TaskManagerData task_data_reference) {
        this.task_data = task_data_reference;
    }

    public void set_var () {
        String variable_name = (String) task_data.getParameters().get(1).object; 
        ValueType type = task_data.getParameters().get(0).object_type;
        Object cached_object = task_data.getParameters().get(0).object;
        GlobalCacheManager.addToCache(variable_name, task_data.executing_task_id, type, cached_object);
    }

    public void get_var () {
        String variable_name = (String) task_data.getParameters().get(0).object;
        ObjectNode object = GlobalCacheManager.getFromCache(variable_name);

        if (object != null) {
            task_data.setReturn(task_data.executing_task_id, object.object_type, object.object);
        }
    }

    public void set_array () {
        String array_name = (String) task_data.getParameters().get(1).object; 
        int index = task_data.getParameters().get(2).getDouble().intValue();
        ValueType type = task_data.getParameters().get(0).object_type;
        Object cached_object = task_data.getParameters().get(0).object;
        GlobalCacheManager.addToCache(array_name + "-" + index, task_data.executing_task_id, type, cached_object);
    }

    public void get_array () {
        String array_name = (String) task_data.getParameters().get(0).object;
        int index = task_data.getParameters().get(1).getDouble().intValue();
        ObjectNode object = GlobalCacheManager.getFromCache(array_name + "-" + index);

        if (object != null) {
            task_data.setReturn(task_data.executing_task_id, object.object_type, object.object);
        }
    }

    public void get_array_size () {
        String array_name = (String) task_data.getParameters().get(0).object;
        int size = GlobalCacheManager.countNameStartsWith(array_name + "-");

        task_data.setReturn(task_data.executing_task_id, ValueType.NUMBER, Double.valueOf(size));
    }

    public void compare () {
        Double param1 = task_data.getParameters().get(0).getDouble();
        String operator = (String) task_data.getParameters().get(1).object;
        Double param2 = task_data.getParameters().get(2).getDouble();
        int comparison = Double.compare(param1, param2);
        Boolean result = null;
        if (operator.contains("==")) {
            if (comparison == 0) 
                result = true;
            else result = false;
        }
        else if (operator.contains("!=") || operator.contains("<>")) {
            if (comparison == 0) 
                result = false;
            else result = true;
        }
        else if (operator.contains("<=")) {
            if (comparison == 0 || comparison == -1) 
                result = true;
            else result = false;
        }
        else if (operator.contains(">=")) {
            if (comparison == 0 || comparison == 1) 
                result = true;
            else result = false;
        }
        else if (operator.contains("<")) {
            if (comparison == -1) 
                result = true;
            else result = false;
        }
        else if (operator.contains(">")) {
            if (comparison == 1) 
                result = true;
            else result = false;
        }
        if (result != null) {
            task_data.setReturn(task_data.executing_task_id, ValueType.BOOL, result);
        }
    }

    public void print () {
        System.out.println("==print > " + task_data.getParameters().size());
        for (ObjectNode n :  task_data.getParameters()) {
            System.out.println("var name : " + n.variable_name);
            System.out.println("task_id  : " + n.task_id);
            System.out.println("obj_type : " + n.object_type);
            System.out.println("object   : " + n.object);
        }
    }

    public void pause () {
    }
}
