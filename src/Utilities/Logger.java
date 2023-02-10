package Utilities;

public class Logger {
    public enum logType {
        WARNING, ERROR
    }

    public static boolean write_to_file;

    public static synchronized void log (logType log_type, String log) {
        if (log_type == logType.ERROR)
            System.out.println("ERROR :: ");
        else if (log_type == logType.WARNING)
            System.out.println("WARNING :: ");

        System.out.println(log);
    }
}
