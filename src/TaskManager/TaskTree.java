package TaskManager;

import java.util.*;

import TaskManager.Objects.TaskChildNode;
import TaskManager.Objects.TaskNode;

public class TaskTree {
    public TaskNode parent;
    ArrayList<Integer> stack = new ArrayList<Integer>();
    public ArrayList<TaskNode> task_tree = new ArrayList<TaskNode>();

    void push (int item) {
        stack.add(item);
    }

    int pop () {
        int idx = stack.size() - 1;
        int item = stack.get(idx);
        stack.remove(idx);
        return item;
    }

    public void makeTree (String task_text, ArrayList<ArrayList<String>> variables, ArrayList<ArrayList<String>> functions) throws Exception {
        int fb = 0, sb = 0;
        for (int i = 0; i < task_text.length(); i++) {
            char c = task_text.charAt(i);
            if (c == '(') {
                fb++;
                push(i);
            }
            else if (c == ')') {
                sb++;
                int idx = pop() + 1;
                String task_id = "";
                int x = idx - 3;
                while (true) {  
                    x--;
                    if (task_text.charAt(x) != '{') {
                        task_id = task_text.charAt(x) + task_id; 
                    }
                    else {
                        task_id = task_id.trim();
                        break;
                    }
                }                
                String child_task_text = task_text.substring(idx, i);
                task_id = task_id.substring(1, task_id.length() - 1);
                int lvl = 0;
                Boolean rec = false;
                String tmp = "";
                ArrayList<String> tmp_children = new ArrayList<String>();
                for (int j = 0; j < child_task_text.length(); j++) {
                    char d = child_task_text.charAt(j);
                    if (d == '{' || d == '<') {
                        rec = true;
                    }
                    else if (d == '}' || d == '>') {
                        rec = false;
                    }
                    else if (d == '(') {
                        lvl++;
                    }
                    else if (d == ')') {
                        lvl--;
                    }
                    if (rec) {
                        if (d != '<' && d != '>' && d != '{' && d != '}') {
                            if (lvl == 0) {
                                tmp = tmp + d;
                            }
                        }
                    }
                    else {
                        if (tmp.length() > 0)
                            tmp_children.add(tmp);
                        tmp = "";
                    }
                }

                TaskNode t = new TaskNode();
                t.child_text = child_task_text;
                t.task_id = task_id;

                for (ArrayList<String> a : functions) {
                    if (a.get(0).equals(task_id)) {
                        t.task_name = a.get(1);
                        break;
                    }
                }

                ArrayList<TaskChildNode> children = new  ArrayList<TaskChildNode>();

                for (String a : tmp_children) {
                    TaskChildNode child = new TaskChildNode();
                    String child_name = "", child_type = "";
                    if (a.charAt(0) == 'v') {
                        child_type = "var";
                        a = a.substring(1, a.length() - 1);
                        for (ArrayList<String> z : variables) {
                            if (z.get(0).equals(a)) {
                                child_name = z.get(1);
                                break;
                            }
                        }
                    }
                    else if (a.charAt(0) == 'f') {
                        child_type = "func";
                        a = a.substring(1, a.length() - 1);
                        for (ArrayList<String> z : functions) {
                            if (z.get(0).equals(a)) {
                                child_name = z.get(1);
                                break;
                            }
                        }
                    }
                    child.child_id = a;
                    child.type = child_type;
                    child.name = child_name;
                    children.add(child);
                }
                t.children = children;
                t.task_status = "notstarted";
                
                task_tree.add(t);
                if (sb > fb) {
                    throw new Exception();
                }
            }
        }
        // resolve parent ids
        for (TaskNode n : task_tree) {
            for (TaskNode y : task_tree) {
                boolean found = false;
                for (TaskChildNode z : y.children) {
                    if (z.child_id.equals(n.task_id)) {
                        n.parent_task_id = y.task_id;
                        n.parent_task_name = y.task_name;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
        }
        for (TaskNode n : task_tree) {
            if (n.parent_task_id == null) {
                n.parent_task_id = "root";
                n.parent_task_name = "root";
            }
        }
    }
}