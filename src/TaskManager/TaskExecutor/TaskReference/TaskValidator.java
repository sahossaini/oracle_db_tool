package TaskManager.TaskExecutor.TaskReference;

import java.util.ArrayList;

import TaskManager.Objects.TaskNode;
import TaskManager.Objects.TaskProperty;
import TaskManager.Parser.TaskTreeHelper;
import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.TaskReference;
import Utilities.Logger;
import Utilities.Logger.logType;

public class TaskValidator {
    public static ArrayList<TaskNode> validateTaskTree (ArrayList<TaskNode> task_tree) {
        /**check if all the parsed tasks are valid names */
        for (int i = 0; i < task_tree.size(); i++) {
            TaskNode task_node = task_tree.get(i);
            task_node.task = TaskReference.getTaskReference(task_node.task_name);
            if (task_node.task == null) {
                Logger.log(logType.ERROR, "Invalid Task name found - " + task_node.task_name);
            }
            else {
                task_tree.set(i, task_node);
            }
        }

        /**check number of parameters */
        for (int i = 0; i < task_tree.size(); i++) {
            TaskNode task_node = task_tree.get(i);
            
            TaskProperty[] task_prop = task_node.task.task_properties;

            boolean paramNumberMatched = false;
            for (TaskProperty tp : task_prop) { 
                if (tp.number_of_parameters == -1) {
                    paramNumberMatched = true; /**apply logic */
                }
                else if (tp.number_of_parameters == -2) {
                    paramNumberMatched = true;
                }
                else if (tp.number_of_parameters == task_node.children.size()) {
                    paramNumberMatched = true;
                }
                if (paramNumberMatched) {

                }
            }
        }
        return task_tree;
    }

    public static boolean validateTask (TaskReference task_name) {
        switch (task_name) {
            case SET_VAR : {
                    break;
                }
            case ADD : {
                break;
            }
        }
        return false;
    }
}
