package TaskManager;

public class TaskSyntaxChecker {
    public TaskSyntaxChecker () {
        String[][][] tasks = {
            {
                {"run_serial"},
                {"void"},
                {"no_limit"},
                {"func"}
            },
            {
                {"run_parallel"},
                {"void"},
                {"no_limit"},
                {"func"}
            },
            {
                {"pause"},
                {"void"},
                {"2"},
                {
                    "var",
                    "var"
                }
            },
            {
                {"print"},
                {"void"},
                {"1"},
                {"var"}
            }
        };
    }
}
