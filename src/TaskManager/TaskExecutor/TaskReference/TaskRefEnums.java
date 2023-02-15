package TaskManager.TaskExecutor.TaskReference;

import TaskManager.Objects.TaskProperty;
import Utilities.Logger;
import Utilities.Logger.logType;

public class TaskRefEnums {
    public enum ValueType { ///REMOVE ARRAY TYPE!
        VOID, ANY, TASK, STRING, DATE, NUMBER, BOOL, TABLE, EXCEL, ARRAY
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
        ADD("ADD", 
            new TaskProperty[] {
                new TaskProperty(ValueType.NUMBER, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        SUBSTRACT("SUBSTRACT", 
            new TaskProperty[] {
                new TaskProperty(ValueType.NUMBER, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        MULTIPLY("MULTIPLY", 
            new TaskProperty[] {
                new TaskProperty(ValueType.NUMBER, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        DIVIDE("DIVIDE", 
            new TaskProperty[] {
                new TaskProperty(ValueType.NUMBER, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        MOD("MOD", 
            new TaskProperty[] {
                new TaskProperty(ValueType.NUMBER, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        COMPARE("COMPARE", 
            new TaskProperty[] {
                new TaskProperty(ValueType.NUMBER, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        IF("IF", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        SET_ARRAY("SET_ARRAY", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        GET_ARRAY("GET_ARRAY", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        GET_ARRAY_SIZE("GET_ARRAY_SIZE", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        CHAR_AT("CHAR_AT", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.VARIABLE})
            }
        ),
        LENGTH("LENGTH", 
            new TaskProperty[] {
                new TaskProperty(ValueType.VOID, -1, new TokenType[] {TokenType.VARIABLE})
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
