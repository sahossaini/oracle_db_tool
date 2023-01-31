import Helpers.*;

import java.util.*;

import ConnManager.*;
import TaskManager.*;
import TaskManager.Objects.TaskNode;
import Utilities.Utils;

public class App {
    static void runApp () throws Exception {
        // String cons = Utils.readFile("cons.txt");
        // System.out.println(cons);
        // boolean x = true;
        // int y = 0;
        // while(x) {
        //         DbConn session1 = new DbConn("jdbc:oracle:thin:@//192.168.200.119:1521/orcl", "sys as sysdba", "samiul");
        //         // System.out.println("jdbc:oracle:thin:@//192.168.200.119:1521:devcdb");

        //         Utils.getConnections(Utils.readFile("cons.txt"));

        //         Thread.sleep(6000);

        //         if (y == 5) x = false;
        //         y++;
        // }

        // Utils.getConnections(Utils.readFile("cons.txt"));
        // Json.jsonToStringArrayList(Utils.readFile("cons.txt"));

        // ConnManager connections = new ConnManager();
        // connections.LoadConnections(Utils.readFile("cons.txt"));

        // System.out.println(connections.getProperties("db1"));

        ArrayList<TaskNode> task_tree = new ArrayList<TaskNode>();
        try {
            task_tree = TaskInterpreter.interpret(Utils.readFile("pgm.txt"));
        }
        catch (Exception e) {
            System.out.println("PARSING ERROR !!");
        }

        String root_task_id = TaskTreeHelper.getRootTaskId(task_tree);
        // System.out.println(TaskTreeHelper.getTaskName(task_tree, root_task_id));

        TaskThread x = new TaskThread(root_task_id, task_tree);

        x.start();
    }

    public static void main (String args[]) {
        try {
            runApp ();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
