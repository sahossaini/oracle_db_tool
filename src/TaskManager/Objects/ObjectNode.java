package TaskManager.Objects;

import TaskManager.TaskExecutor.TaskReference.TaskRefEnums.ValueType;
import Utilities.Logger;
import Utilities.Logger.logType;

public class ObjectNode {
    public String variable_name;            // the variable name of the var the object belongs to (eg. "x" = "some_text")
    public String task_id;                  // Task id of the task the return belongs to
    public ValueType object_type;           // string, number etc.
    public Object object;                   // value

    public String getObjectType () {
        if (object != null) 
            return object.getClass().getSimpleName().toUpperCase();
        else return null;
    } 

    public static Double stringToDouble (String str) {
        str = str.toLowerCase().trim();
        double value;
        String tmp = "";
        char[] numbers = {'.', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < str.length(); i++) {
            for (char c : numbers) {
                if (str.charAt(i) == c) {
                    tmp = tmp + c;
                    break;
                }
            }
        }
        try {
            value = Double.parseDouble(tmp);
        }
        catch (Exception e) {
            Logger.log(logType.WARNING, "Failed to parse number - " + str);
            return null;
        }
        return value;
    }

    // implement string to boolean (like 1 is true, True is true etc)

    public Double getDouble () {
        switch (getObjectType ()) {
            case "STRING" :
                return stringToDouble((String) object);

            case "DOUBLE" :
                return (Double) object;

            default :
                Logger.log(logType.WARNING, "Object type cannot be used!");
                break;
        }
        return null;
    }

    public Integer getInt () {
        return getDouble().intValue();
    }

    public String getString () {
        switch (getObjectType ()) {
            case "STRING" :
                return object.toString();

            case "DOUBLE" :
                return Double.toString((Double) object);

            default :
                Logger.log(logType.WARNING, "Object type cannot be used!");
                break;
        }
        return null;
    }
}
