package TaskManager.Objects;

public class Enums {
    public enum ValueType {
        VOID, STRING, NUMBER
    }
        
    public enum TaskNodeStatus {
        NOT_STARTED, RUNNING, PAUSED, ENDED
    }

    public enum TokenType {
        VARIABLE, TASK
    }

    public enum TaskName {
        READ_FROM_FILE("READ_FROM_FILE"),
        SAVE_TO_FILE("SAVE_TO_FILE"),
        SAVE_TO_VARIABLE("SAVE_TO_VARIABLE"),
        SET_RETURN("SET_RETURN"),
        SET_CACHE("SET_CACHE"),
        PRINT("PRINT"),
        PAUSE("PAUSE"),
        RUN_SERIAL("RUN_SERIAL"),
        RUN_SERIAL_LOOP("RUN_SERIAL_LOOP"),
        RUN_PARALLEL("RUN_PARALLEL"),
        RUN_PARALLEL_CONTINUE("RUN_PARALLEL_CONTINUE");

        String name;
        TaskName (String name) {
            this.name = name;
        }

        @Override
        public final String toString(){
            return name.toUpperCase().trim();
        }
    }
}
