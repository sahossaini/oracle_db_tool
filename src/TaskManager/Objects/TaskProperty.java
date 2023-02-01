package TaskManager.Objects;

import TaskManager.Objects.Enums.TaskName;
import TaskManager.Objects.Enums.TokenType;
import TaskManager.Objects.Enums.ValueType;

public class TaskProperty {
    public TaskName task_name;
    public ValueType task_return_type;
    public int number_of_parameters;
    public TokenType[] parameter_types;

    public TaskProperty (TaskName name, ValueType return_type, int no_params, TokenType[] param_types) {
        task_name = name;
        task_return_type = return_type;
        number_of_parameters = no_params;
        parameter_types = param_types;
    }
}