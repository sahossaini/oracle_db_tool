package TaskManager.TaskExecutor;

import java.util.*;

import TaskManager.Objects.*;
import TaskManager.Parser.TaskTree;
import TaskManager.Parser.TaskTreeHelper;
import TaskManager.TaskExecutor.TaskModules.ModuleCore;
import TaskManager.TaskExecutor.TaskModules.ModuleNumber;
import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.*;
import Utilities.Various;

public class TaskManagerThread extends Thread {
    static ArrayList<TaskNode> t; // task_tree
    String start_task_id;
    String currently_running_task_id;
    TaskManagerData task_data;
    ArrayList<ObjectNode> parameters;

    ModuleCore module_core;
    ModuleNumber module_number;

    public TaskManagerThread (String start_task_id, ArrayList<TaskNode> task_tree) {
        t = task_tree;
        this.start_task_id = start_task_id;
        this.task_data = new TaskManagerData(start_task_id);

        this.module_core = new ModuleCore(task_data);
        this.module_number = new ModuleNumber(task_data);
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
        task_data.executing_task_id = task_id;

        TaskReference task_name = TaskTreeHelper.getTaskNode(t, task_id).task;

        // System.out.println("running " + task_id + " :: " + task_name.toString());
        switch (task_name) {
            case RUN_SERIAL :
                run_serial(task_id);
                break;

            case RUN_SERIAL_LOOP :
                run_serial_loop(task_id);
                break;
    
            case RUN_PARALLEL :
                run_parallel(task_id);
                break;
            
            case RUN_PARALLEL_CONTINUE :
                run_parallel_continue(task_id);
                break;
            
            case IF :
                if_else(task_id);
                break;
            
            default : {
                task_data.setParameters(getAllParamsExec(task_id));
                switch (task_name) {
                    case CONNECT_ORACLE_DB :
                        // connect_oracle_db(task_id);
                        break;
    
                    case READ_FROM_FILE :
                        read_from_file(task_id);
                        break;
        
                    case SAVE_TO_FILE :
                        // set_return(task_id);
                        break;
        
                    case SET_VAR :
                        module_core.set_var();
                        break;
        
                    case GET_VAR :
                        module_core.get_var();
                        break;
        
                    case PRINT :
                        module_core.print();
                        break;
        
                    case PAUSE :
                        module_core.pause();
                        break;
                    
                    case ADD :
                        module_number.add();
                        break;

                    case SUBSTRACT :
                        module_number.substract();
                        break;

                    case MULTIPLY :
                        module_number.multiply();
                        break;

                    case DIVIDE :
                        module_number.divide();
                        break;

                    case MOD :
                        module_number.mod();
                        break;
                    
                    case COMPARE :
                        module_core.compare();
                        break;

                    default:
                        break;
                }
            }
            break;
        }

        // TaskTreeHelper.printTaskTree(t);
        // task_data.printReturnCache();
        // System.out.println("ending " + task_id + " :: " + task_name.toString());
    }

    /** crucial function */
    ArrayList<ObjectNode> getAllParamsExec (String task_id) {
        ArrayList<TaskChildNode> children = TaskTreeHelper.getChildren(t, task_id);
        ArrayList<ObjectNode> parameters = new ArrayList<ObjectNode>();

        for (TaskChildNode child : children) {
            ObjectNode parameter = getParamExec(child);
            if (parameter != null) {
                parameters.add(parameter);
            }
        }
        return parameters;
    }

    ObjectNode getParamExec (TaskChildNode child) {
        if (child.type == TokenType.TASK) {
            execute (child.child_id);
            // if (task_data.checkReturnTaskId().equals(TaskTreeHelper.getParentId(t, currently_running_task_id))) {
            // System.out.println("x : " + child.child_id + " y : " + task_data.checkReturnTaskId());
            // if (task_data.checkReturnTaskId().equals(child.child_id)
                // || TaskTreeHelper.isParentOfChild(t, child.child_id, task_data.checkReturnTaskId())) {
                // System.out.println("ret cache--");
                // task_data.printReturnCache();
                return task_data.getReturn();
            // }
        }
        else if (child.type == TokenType.VARIABLE) {
            ObjectNode object = new ObjectNode();
            object.object_type = ValueType.STRING;
            object.object = (String) child.name;
            object.variable_name = null;
            return object;
        }
        return null;
    }
    
    void read_from_file (String task_id) {
        String file_location = TaskTreeHelper.getChildAt(t, task_id, 0).name;
        try {
            String content = Various.readFile(file_location);
            task_data.setReturn(task_id, ValueType.STRING, content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void run_serial (String task_id) {
        for (String child_task_id : TaskTreeHelper.getChildTaskIds(t, task_id)) {
            execute(child_task_id);
        }
    }

    void run_serial_loop (String task_id) {
        boolean end_condition = true;
        TaskNode task = TaskTreeHelper.getTaskNode(t, task_id);
        int num_of_children = task.children.size();
        TaskChildNode child_end_condition = TaskTreeHelper.getChildAt(t, task_id, num_of_children - 1);
        while (end_condition) {
            for (int i = 0; i < task.children.size() - 1; i++) {
                TaskChildNode child = TaskTreeHelper.getChildAt(t, task_id, i);
                if (child.type == TokenType.TASK) {
                    execute(child.child_id);
                }
            }
            ObjectNode obj_condition = getParamExec(child_end_condition);
            if ((Boolean) obj_condition.object) {
                end_condition = true;
            }
            else if (!((Boolean) obj_condition.object)) {
                end_condition = false;
            }
        }
    }

    void run_parallel (String task_id) {
        ArrayList<TaskManagerThread> threads = new ArrayList<TaskManagerThread>();
        for (String child_task_id : TaskTreeHelper.getChildTaskIds(t, task_id)) {
            TaskManagerThread x = new TaskManagerThread(child_task_id, t);
            threads.add(x);
        }
        for (TaskManagerThread x : threads) {
            x.start();
        }
        while (true) {
            try {
                Thread.sleep(50);
                boolean is_task_alive = false; 
                for (TaskManagerThread x : threads) {
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
        ArrayList<TaskManagerThread> threads = new ArrayList<TaskManagerThread>();
        for (String child_task_id : TaskTreeHelper.getChildTaskIds(t, task_id)) {
            TaskManagerThread x = new TaskManagerThread(child_task_id, t);
            threads.add(x);
        }
        for (TaskManagerThread x : threads) {
            x.start();
        }
    }

    void if_else (String task_id) {
        TaskChildNode condition = TaskTreeHelper.getChildAt(t, task_id, 0);
        TaskChildNode if_true = TaskTreeHelper.getChildAt(t, task_id, 1);
        TaskChildNode if_false = TaskTreeHelper.getChildAt(t, task_id, 2);

        ObjectNode obj_condition = getParamExec(condition);
        if ((Boolean)obj_condition.object) {
            execute(if_true.child_id);
        }
        else if (!((Boolean)obj_condition.object)) {
            execute(if_false.child_id);
        }

    }
}
