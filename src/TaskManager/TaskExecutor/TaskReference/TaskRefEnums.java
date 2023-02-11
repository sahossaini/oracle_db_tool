package TaskManager.TaskExecutor.TaskReference;

import TaskManager.Objects.TaskProperty;

public class TaskRefEnums {
    public enum ValueType {
        VOID, ANY, TASK, STRING, DATE, NUMBER, BOOL, TABLE, EXCEL
    }
        
    public enum TaskNodeStatus {
        NOT_STARTED, RUNNING, PAUSED, ENDED
    }

    public enum TokenType {
        VARIABLE, TASK
    }

    public enum TaskReference {
        CONNECT_ORACLE_DB("CONNECT_ORACLE_DB",
            new TaskProperty[] {
                new TaskProperty(ValueType.STRING, 1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        READ_FROM_FILE("READ_FROM_FILE",
            new TaskProperty[] {
                new TaskProperty(ValueType.STRING, 1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        SAVE_TO_FILE("SAVE_TO_FILE",
            new TaskProperty[] {
                new TaskProperty(ValueType.STRING, 2, new TokenType[] {TokenType.VARIABLE, TokenType.VARIABLE})
            }
        ),
        SET_VAR("SET_VAR",
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, 2, new TokenType[] {TokenType.VARIABLE, TokenType.VARIABLE})
            }
        ),
        GET_VAR("GET_VAR", 
            new TaskProperty[] {
                new TaskProperty(ValueType.STRING, 1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        PRINT("PRINT", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, 1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        PAUSE("PAUSE", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, 1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        RUN_SERIAL("RUN_SERIAL", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.TASK})
            }
        ),
        RUN_SERIAL_LOOP("RUN_SERIAL_LOOP", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.TASK})
            }
        ),
        RUN_PARALLEL("RUN_PARALLEL", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.TASK})
            }
        ),
        RUN_PARALLEL_CONTINUE("RUN_PARALLEL_CONTINUE", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.TASK})
            }
        ),
        ADD_NUMBERS("ADD_NUMBERS", 
            new TaskProperty[] {
                new TaskProperty(ValueType.NUMBER, -1, new TokenType[] {TokenType.VARIABLE})
            }
        );

        String task_name;
        public TaskProperty[] task_properties;

        TaskReference (String task_name, TaskProperty[] task_properties) {
            this.task_name = task_name;
            this.task_properties = task_properties;
        }

        @Override
        public final String toString(){
            return task_name.toUpperCase().trim();
        }

        public static TaskReference getTaskReference (String task_name) {
            for (TaskReference ref : TaskReference.values()) {
                if (ref.toString().equals(task_name.toUpperCase().trim())) {
                    return ref;
                }
            }
            return null;
        }
    }
}