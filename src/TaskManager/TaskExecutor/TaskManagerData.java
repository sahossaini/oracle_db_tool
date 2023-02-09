package TaskManager.TaskExecutor;

import java.util.ArrayList;

import TaskManager.Objects.ObjectNode;
import TaskManager.Objects.Enums.ValueType;

public class TaskManagerData {
    public String start_task_id;
    public String executing_task_id;
    private ObjectNode return_cache;
    private ArrayList<ObjectNode> parameters;

    public TaskManagerData (String start_task_id) {
        this.start_task_id = start_task_id;
        this.executing_task_id = start_task_id;
        this.return_cache = new ObjectNode();
        this.parameters = new ArrayList<ObjectNode>();
    }

    public void setParameters (ArrayList<ObjectNode> params) {
        parameters.clear();
        for (ObjectNode obj : params) {
            parameters.add(obj);
        }
    }

    public ArrayList<ObjectNode> getParameters () {
        ArrayList<ObjectNode> params = new ArrayList<ObjectNode>();
        for (ObjectNode obj : parameters) {
            params.add(obj);
        }
        // parameters.clear();
        return params;
    }

    public void printParameters () {
        for (ObjectNode n :  parameters) {
            System.out.println("var name : " + n.variable_name);
            System.out.println("task_id  : " + n.task_id);
            System.out.println("obj_type : " + n.object_type);
            System.out.println("object   : ");
            System.out.println(n.object);
        }
    }

    public void setReturn (String task_id, ValueType value_type, Object return_cache_object) {
        return_cache.variable_name = null;
        return_cache.task_id = task_id;
        return_cache.object_type = value_type;
        return_cache.object = return_cache_object;
    }

    public void setReturnEmpty () {
        return_cache.variable_name = null;
        return_cache.task_id = null;
        return_cache.object_type = null;
        return_cache.object = null;
    }

    public ObjectNode getReturn () {
        if (return_cache.object != null) {
            ObjectNode r = new ObjectNode();
            r.task_id = return_cache.task_id;
            r.object_type = return_cache.object_type;
            r.object = return_cache.object;
            setReturnEmpty ();
            return r;
        }
        else return null;
    }

    public String checkReturnTaskId () {
        if (return_cache.object != null)
            return return_cache.task_id;
        else return null;
    }

    public void printReturnCache () {
        System.out.println("var name : " + return_cache.variable_name);
        System.out.println("task_id  : " + return_cache.task_id);
        System.out.println("obj_type : " + return_cache.object_type);
        System.out.println("object   : ");
        System.out.println(return_cache.object);
    }
}
