package TaskManager.TaskExecutor.TaskModules;

import java.util.ArrayList;

import TaskManager.Objects.ObjectNode;
import TaskManager.Objects.Enums.ValueType;
import TaskManager.TaskExecutor.GlobalCacheManager;
import TaskManager.TaskExecutor.TaskManagerData;

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

    public void print () {
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
