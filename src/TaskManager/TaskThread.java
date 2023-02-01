package TaskManager;

import java.util.*;
import TaskManager.Objects.*;
import TaskManager.Objects.GlobalCacheNode.GlobalCacheType;
import TaskManager.Objects.ReturnCacheNode.ReturnCacheType;
import TaskManager.Objects.TaskChildNode.TaskChildType;
import Utilities.Utils;

public class TaskThread extends Thread {
    static ArrayList<TaskNode> t; // task_tree
    String start_task_id;
    ReturnCacheNode return_cache; // task_id, type(number/string), value
    
    public TaskThread (String start_task_id, ArrayList<TaskNode> task_tree) {
        t = task_tree;
        this.start_task_id = start_task_id;
        this.return_cache = new ReturnCacheNode();
    }

    void setReturn (String task_id, ReturnCacheType value_type, String value) {
        return_cache = new ReturnCacheNode();
        return_cache.task_id = task_id;
        return_cache.type = value_type;
        return_cache.value = value;
    }

    ReturnCacheNode getReturn () {
        if (return_cache != null) {
            ReturnCacheNode r = new ReturnCacheNode();
            r.task_id = return_cache.task_id;
            r.type = return_cache.type;
            r.value = return_cache.value;
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
        String task_name =  TaskTreeHelper.getTaskName(t, task_id);
        System.out.println(task_name);
        if (task_name != null)
        switch (task_name) {
            case "READ_FROM_FILE":
                read_from_file(task_id);
                break;

            case "SAVE_TO_FILE":
                set_return(task_id);
                break;

            case "SAVE_TO_VARIABLE":
                set_return(task_id);
                break;

            case "SET_RETURN":
                set_return(task_id);
                break;

            case "SET_CACHE":
                set_cache(task_id);
                break;

            case "PRINT":
                print(task_id);
                break;

            case "PAUSE":
                pause(task_id);
                break;

            case "RUN_SERIAL":
                run_serial(task_id);
                break;

            case "RUN_SERIAL_LOOP":
                run_serial(task_id);
                break;
       
            case "RUN_PARALLEL":
                run_parallel(task_id);
                break;
            
            case "RUN_PARALLEL_CONTINUE":
                run_parallel_continue(task_id);
                break;
        
            default:
                break;
        }
        // TaskTreeHelper.printTaskTree(t);
    }

    void read_from_file (String task_id) {
        String file_location = TaskTreeHelper.getChildAt(t, task_id, 0).name;
        try {
            String content = Utils.readFile(file_location);
            System.out.println("reached!!");
            setReturn(task_id, ReturnCacheType.STRING, content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void save_to_variable (String task_id) {
        
    }

    void set_return (String task_id) {
        String value = TaskTreeHelper.getChildAt(t, task_id, 0).name;
        setReturn(task_id, ReturnCacheType.STRING, value);
    }

    void set_cache (String task_id) {
        String var_name = TaskTreeHelper.getChildAt(t, task_id, 0).name;
        String var_value = TaskTreeHelper.getChildAt(t, task_id, 1).name;
        GlobalCacheManager.addToCache(var_name, GlobalCacheType.STRING, var_value);
        GlobalCacheManager.printCache();
    }

    void print (String task_id) {
        TaskChildNode child = TaskTreeHelper.getChildAt(t, task_id, 0);

        if (child.type == TaskChildType.TASK) {
            execute(child.child_id);
            if (child.child_id.equals(checkReturn()))
                System.out.println(">>" + getReturn().value);
        }
        else if (child.type == TaskChildType.VARIABLE) {
            GlobalCacheNode gcn = GlobalCacheManager.getFromCache(child.name);
            if (gcn == null) {
                System.out.println(child.name);
            }
            else {
                System.out.println(gcn.value);
            }
        }
    }

    void pause (String task_id) {
    }

    void run_serial (String task_id) {
        for (String child_task_id : TaskTreeHelper.getChildTaskIds(t, task_id)) {
            System.out.println("Executing :: " + TaskTreeHelper.getTaskName(t, child_task_id) + " " + child_task_id);
            execute(child_task_id);
        }
    }

    void run_parallel (String task_id) {
        ArrayList<TaskThread> threads = new ArrayList<TaskThread>();
        for (String child_task_id : TaskTreeHelper.getChildTaskIds(t, task_id)) {
            TaskThread x = new TaskThread(child_task_id, t);
            threads.add(x);
            System.out.println(child_task_id + " ??");
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
            System.out.println(child_task_id + " ??");
        }
        for (TaskThread x : threads) {
            x.start();
        }
    }
}
