package TaskManager.Executor;

import java.util.*;

import TaskManager.Objects.*;
import TaskManager.Objects.Enums.*;
import TaskManager.Parser.TaskTreeHelper;
import Utilities.Utils;

public class TaskThread extends Thread {
    static ArrayList<TaskNode> t; // task_tree
    String start_task_id;
    String currently_running_task_id;
    ReturnCacheNode return_cache; // task_id, type(number/string), value
    
    public TaskThread (String start_task_id, ArrayList<TaskNode> task_tree) {
        t = task_tree;
        this.start_task_id = start_task_id;
        this.return_cache = new ReturnCacheNode();
    }

    void setReturn (String task_id, ValueType value_type, Object return_cache_object) {
        return_cache = new ReturnCacheNode();
        return_cache.task_id = task_id;
        return_cache.type = value_type;
        return_cache.return_cache_object = return_cache_object;
    }

    ReturnCacheNode getReturn () {
        if (return_cache != null) {
            ReturnCacheNode r = new ReturnCacheNode();
            r.task_id = return_cache.task_id;
            r.type = return_cache.type;
            r.return_cache_object = return_cache.return_cache_object;
            return_cache = null;
            return r;
        }
        else return null;
    }

    String checkReturn () {
        if (return_cache != null)
            return return_cache.task_id;
        else return null;
    }
    
    public void run ()
    {
        try {
            System.out.println(
                "Thread " + Thread.currentThread().getId()
                + " is running- " + start_task_id);
            execute(start_task_id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void execute (String task_id) {
        currently_running_task_id = task_id;
        String t_name =  TaskTreeHelper.getTaskName(t, task_id);

        TaskName task_name = null;
        if (t_name != null) {
            for (TaskName x : TaskName.values()) {
                if (x.toString().equals(t_name)) {
                    task_name = x;
                    break;
                }
            }
        }

        switch (task_name) {
            case RUN_SERIAL :
                run_serial(task_id);
                break;

            case RUN_SERIAL_LOOP :
            
                break;
    
            case RUN_PARALLEL :
                run_parallel(task_id);
                break;
            
            case RUN_PARALLEL_CONTINUE :
                run_parallel_continue(task_id);
                break;
        
            case CONNECT_ORACLE_DB :
                // connect_oracle_db(task_id);
                break;

            case READ_FROM_FILE :
                read_from_file(task_id);
                break;

            case SAVE_TO_FILE :
                set_return(task_id);
                break;

            case SET_VAR :
                set_var(task_id);
                break;

            case SET_RETURN :
                set_return(task_id);
                break;

            case SET_CACHE :
                set_cache(task_id);
                break;

            case PRINT :
                print(task_id);
                break;

            case PAUSE :
                pause(task_id);
                break;
        
            default:
                break;
        }

        // TaskTreeHelper.printTaskTree(t);
    }

    /** crucial function */
    ArrayList<Object> getAllParametersResolvingAllExecutions (String task_id) {
        ArrayList<TaskChildNode> children = TaskTreeHelper.getChildren(t, task_id);
        ArrayList<Object> parameters = new ArrayList<Object>();

        for (TaskChildNode child : children) {
            Object parameter = getParameterResolvingExecution(child);
            if (parameter != null) {
                parameters.add(parameter);
            }
        }
        return parameters;
    }

    Object getParameterResolvingExecution (TaskChildNode child) {
        if (child.type == TokenType.TASK) {
            execute (child.child_id);
            if (checkReturn().equals(child.child_id)) {
                
            }
        }
        else if (child.type == TokenType.VARIABLE) {
            GlobalCacheNode gcn = GlobalCacheManager.getFromCache(child.name);
            if (gcn == null) {
                return child.name;
            }
            else {
                return gcn.cached_object;
            }
        }
        return null;
    }
    
    void read_from_file (String task_id) {
        String file_location = TaskTreeHelper.getChildAt(t, task_id, 0).name;
        try {
            String content = Utils.readFile(file_location);
            setReturn(task_id, ValueType.STRING, content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void set_var (String task_id) {
        int children_count = TaskTreeHelper.getChildrenCount(t, task_id);
        if (children_count == 3) { 
            String flag = (String) TaskTreeHelper.getChildAt(t, task_id, 2).name;
            if (flag.trim().toUpperCase().equals("REFRESH")) {
                ArrayList<TaskChildNode> children = TaskTreeHelper.getChildren(t, task_id);
                Object cached_object = getParameterResolvingExecution(children.get(0));
                String variable_name =  children.get(1).name;
                GlobalCacheManager.addToCache(variable_name, currently_running_task_id, ValueType.STRING, cached_object);
            }
        }
        else if (children_count == 2) {
            ArrayList<Object> parameters = getAllParametersResolvingAllExecutions(task_id);                
            String variable_name = (String) parameters.get(1); 
            Object cached_object = parameters.get(0);
            GlobalCacheManager.addToCache(variable_name, currently_running_task_id, ValueType.STRING, cached_object);
        }

    }

    void set_return (String task_id) {
        String value = TaskTreeHelper.getChildAt(t, task_id, 0).name;
        setReturn(task_id, ValueType.STRING, value);
    }

    void set_cache (String task_id) {
        // String var_name = TaskTreeHelper.getChildAt(t, task_id, 0).name;
        // String var_value = TaskTreeHelper.getChildAt(t, task_id, 1).name;
        // GlobalCacheManager.addToCache(var_name, ValueType.STRING, var_value);
        // GlobalCacheManager.printCache();
    }

    void print (String task_id) {
        ArrayList<Object> parameters = getAllParametersResolvingAllExecutions(task_id);
        System.out.println(parameters);
    }

    void pause (String task_id) {
    }

    void run_serial (String task_id) {
        for (String child_task_id : TaskTreeHelper.getChildTaskIds(t, task_id)) {
            execute(child_task_id);
        }
    }

    void run_parallel (String task_id) {
        ArrayList<TaskThread> threads = new ArrayList<TaskThread>();
        for (String child_task_id : TaskTreeHelper.getChildTaskIds(t, task_id)) {
            TaskThread x = new TaskThread(child_task_id, t);
            threads.add(x);
        }
        for (TaskThread x : threads) {
            x.start();
        }
        while (true) {
            try {
                Thread.sleep(50);
                boolean is_task_alive = false; 
                for (TaskThread x : threads) {
                    if (x.isAlive()) {
                        is_task_alive = true;
                    }
                }
                if (!is_task_alive) break;
            }
            catch (Exception e) {
                
            }
        }
    }

    void run_parallel_continue (String task_id) {
        ArrayList<TaskThread> threads = new ArrayList<TaskThread>();
        for (String child_task_id : TaskTreeHelper.getChildTaskIds(t, task_id)) {
            TaskThread x = new TaskThread(child_task_id, t);
            threads.add(x);
        }
        for (TaskThread x : threads) {
            x.start();
        }
    }
}