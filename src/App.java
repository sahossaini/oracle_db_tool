import Helpers.*;

import java.util.*;

import ConnManager.*;
import DataSource.Excel.ExcelManager;
import TaskManager.*;
import TaskManager.Objects.TaskNode;
import TaskManager.Parser.TaskInterpreter;
import TaskManager.Parser.TaskTreeHelper;
import TaskManager.TaskExecutor.GlobalCacheManager;
import TaskManager.TaskExecutor.TaskManagerThread;
import TaskManager.TaskExecutor.TaskModules.ModuleNumber;
import Utilities.Various;

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
            task_tree = TaskInterpreter.interpret(Various.readFile("pgm3.txt"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("PARSING ERROR!");
        }
        
        String root_task_id = TaskTreeHelper.getRootTaskId(task_tree);
        System.out.println(TaskTreeHelper.getTaskName(task_tree, root_task_id));

        TaskManagerThread x = new TaskManagerThread(root_task_id, task_tree);

        x.start();
    }

    public static void excelTest () throws Exception {
        ExcelManager x = new ExcelManager();
        x.readExcel("test_sheet.xls");
    }

    public static void misc () throws Exception {
        System.out.println(ModuleNumber.stringToDouble("2-200"));
    }

    public static void main (String args[]) {
        try {            
            runApp ();
            // excelTest();
            // misc();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
