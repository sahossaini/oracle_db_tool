package TaskManager.Objects;

public class Enums {
    public enum ValueType {
        VOID, STRING, NUMBER, TABLE, EXCEL
    }
        
    public enum TaskNodeStatus {
        NOT_STARTED, RUNNING, PAUSED, ENDED
    }

    public enum TokenType {
        VARIABLE, TASK
    }

    public enum TaskName {
        CONNECT_ORACLE_DB(
            new TaskProperty("CONNECT_ORACLE_DB", 
                            ValueType.STRING, 
                            1, 
                            new TokenType[] {
                                TokenType.VARIABLE
                            }
                )
        ),
        READ_FROM_FILE(
            new TaskProperty("READ_FROM_FILE", 
                            ValueType.STRING, 
                            1, 
                            new TokenType[] {
                                TokenType.VARIABLE
                            }
                )
        ),
        SAVE_TO_FILE(
            new TaskProperty("SAVE_TO_FILE", 
                            ValueType.STRING, 
                            2, 
                            new TokenType[] {
                                TokenType.VARIABLE,
                                TokenType.VARIABLE
                            }
                )
        ),
        SET_VAR(
            new TaskProperty("SET_VAR", 
                            ValueType.STRING, 
                            2, 
                            new TokenType[] {
                                TokenType.VARIABLE,
                                TokenType.VARIABLE
                            }
                )
        ),
        GET_VAR(
            new TaskProperty("GET_VAR", 
                            ValueType.STRING, 
                            1, 
                            new TokenType[] {
                                TokenType.VARIABLE
                            }
                )
        ),
        SET_RETURN(
            new TaskProperty("SET_RETURN", 
                            ValueType.STRING, 
                            2, 
                            new TokenType[] {
                                TokenType.VARIABLE,
                                TokenType.VARIABLE
                            }
                )
        ),
        SET_CACHE(
            new TaskProperty("SET_CACHE", 
                            ValueType.STRING, 
                            2, 
                            new TokenType[] {
                                TokenType.VARIABLE,
                                TokenType.VARIABLE
                            }
                )
        ),
        PRINT(
            new TaskProperty("PRINT", 
                            ValueType.VOID, 
                            1, 
                            new TokenType[] {
                                TokenType.VARIABLE
                            }
                )
        ),
        PAUSE(
            new TaskProperty("PAUSE", 
                            ValueType.STRING, 
                            1, 
                            new TokenType[] {
                                TokenType.VARIABLE
                            }
                )
        ),
        RUN_SERIAL(
            new TaskProperty("RUN_SERIAL", 
                            ValueType.STRING, 
                            -1, 
                            new TokenType[] {
                                TokenType.TASK
                            }
                )
        ),
        RUN_SERIAL_LOOP(
            new TaskProperty("RUN_SERIAL_LOOP", 
                            ValueType.STRING, 
                            -1, 
                            new TokenType[] {
                                TokenType.TASK
                            }
                )
        ),
        RUN_PARALLEL(
            new TaskProperty("RUN_PARALLEL", 
                            ValueType.STRING, 
                            -1, 
                            new TokenType[] {
                                TokenType.TASK
                            }
                )
        ),
        RUN_PARALLEL_CONTINUE(
            new TaskProperty("RUN_PARALLEL_CONTINUE", 
                            ValueType.STRING, 
                            -1, 
                            new TokenType[] {
                                TokenType.TASK
                            }
                )
        );

        public TaskProperty task_property;

        TaskName (TaskProperty task_Property) {
            this.task_property = task_Property;
        }

        @Override
        public final String toString(){
            return task_property.task_name.toUpperCase().trim();
        }
    }
}
