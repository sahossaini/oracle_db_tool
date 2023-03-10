package TaskManager.Parser;

import java.util.*;

import TaskManager.Objects.TaskChildNode;
import TaskManager.Objects.TaskNode;
import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.TokenType;

/**
 * TaskTreeHelper
 */
public class TaskTreeHelper {    
    public static String getRootTaskId (ArrayList<TaskNode> task_tree) {
        for (TaskNode n : task_tree) {
            if (n.parent_task_id.equals("root")) {
                return n.task_id;
            }
        }
        return null;
    }

    public static String getTaskName (ArrayList<TaskNode> task_tree, String task_id) {
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                return n.task_name;
            }
        }
        return null;
    }

    public static TaskNode getTaskNode (ArrayList<TaskNode> task_tree, String task_id) {
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                return n;
            }
        }
        return null;
    }

    public static ArrayList<String> getChildTaskIds (ArrayList<TaskNode> task_tree, String task_id) {
        ArrayList<String> child_task_ids = new ArrayList<String>();
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                for (TaskChildNode t : n.children) {
                    if (t.type == TokenType.TASK) {
                        child_task_ids.add(t.child_id);
                    }
                }
            }
        }
        return child_task_ids;
    }

    public static int getChildTaskCount (ArrayList<TaskNode> task_tree, String task_id) {
        int cnt = 0;
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                for (TaskChildNode t : n.children) {
                    if (t.type == TokenType.TASK) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    public static ArrayList<String> getChildVariableIds (ArrayList<TaskNode> task_tree, String task_id) {
        ArrayList<String> child_var_ids = new ArrayList<String>();
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                for (TaskChildNode t : n.children) {
                    if (t.type == TokenType.VARIABLE) {
                        child_var_ids.add(t.child_id);
                    }
                }
            }
        }
        return child_var_ids;
    }

    public static int getChildVariableCount (ArrayList<TaskNode> task_tree, String task_id) {
        int cnt = 0;
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                for (TaskChildNode t : n.children) {
                    if (t.type == TokenType.VARIABLE) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    public static TaskChildNode getChildAt (ArrayList<TaskNode> task_tree, String task_id, int child_index) {
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                return n.children.get(child_index);
            }
        }
        return null;
    }

    public static boolean isParentOfChild (ArrayList<TaskNode> task_tree, String parent_task_id, String child_task_id) {
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(parent_task_id)) {
                for (TaskChildNode c : n.children) {
                    if (c.child_id.equals(child_task_id)) 
                        return true;
                }
                return false;
            }
        }
        return false;
    }

    public static ArrayList<TaskChildNode> getChildren (ArrayList<TaskNode> task_tree, String task_id) {
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                return n.children;
            }
        }
        return null;
    }

    public static int getChildrenCount (ArrayList<TaskNode> task_tree, String task_id) {
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                return n.children.size();
            }
        }
        return 0;
    }

    public static String getParentId (ArrayList<TaskNode> task_tree, String task_id) {
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                return n.parent_task_id;
            }
        }
        return null;
    }

    // public static String getType (String id) {
        // if (id.startsWith())
    // }

    public static void printTaskTree (ArrayList<TaskNode> task_tree) {
        for (TaskNode n : task_tree) {
            System.out.println("task_id : " + n.task_id);
            System.out.println("task_name : " + n.task_name);
            System.out.println("task_reference : " + n.task);
            System.out.println("task_status : " + n.task_status);
            System.out.println("parent_task_id : " + n.parent_task_id);
            System.out.println("parent_task_name : " + n.parent_task_name);
            System.out.println("child_text : " + n.child_text);
            for (TaskChildNode x : n.children) {
                System.out.println("     " + "id: " + x.child_id + "  type: " + x.type + "  name: " + x.name);
            }
            System.out.println("--------------");
        }
    }

    public static void printTask (ArrayList<TaskNode> task_tree, String task_id) {
        for (TaskNode n : task_tree) {
            if (n.task_id.equals(task_id)) {
                System.out.println("task_id : " + n.task_id);
                System.out.println("task_name : " + n.task_name);
                System.out.println("task_reference : " + n.task);
                System.out.println("task_status : " + n.task_status);
                System.out.println("parent_task_id : " + n.parent_task_id);
                System.out.println("parent_task_name : " + n.parent_task_name);
                System.out.println("child_text : " + n.child_text);
                for (TaskChildNode x : n.children) {
                    System.out.println("     " + "id: " + x.child_id + "  type: " + x.type + "  name: " + x.name);
                }
                System.out.println("--------------");
            }
        }
    }
}