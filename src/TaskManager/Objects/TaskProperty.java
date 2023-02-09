package TaskManager.Objects;

import TaskManager.Objects.Enums.TokenType;
import TaskManager.Objects.Enums.ValueType;

public class TaskProperty {
    public ValueType task_return_type;
    public int number_of_parameters;
    public TokenType[] parameter_types;

    public TaskProperty (ValueType return_type, int total_params, TokenType[] param_types) {
        this.task_return_type = return_type;
        this.number_of_parameters = total_params;
        this.parameter_types = param_types;
    }
}