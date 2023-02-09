package TaskManager.Parser;
import Helpers.Misc;
import TaskManager.Objects.TaskNode;

import java.util.ArrayList;
import java.util.Random;


public class TaskInterpreter {
    static ArrayList<ArrayList<String>> variables;
    static ArrayList<ArrayList<String>> functions;

    public static ArrayList<TaskNode> interpret (String task_text) throws Exception {
        // remove all comments - (any line starting with # is a comment)
        String[]lines = task_text.split(System.getProperty("line.separator"));
        String comment_less_task = "";
        boolean is_line_comment = false;
        for(String line : lines){
            is_line_comment = false;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '#') {
                    is_line_comment = true;
                    break;
                }
                else if (line.charAt(i) != ' ') {
                    is_line_comment = false;
                    break;
                }
            }
            if (!is_line_comment) {
                comment_less_task = comment_less_task + line + '\n';
            } 
        }

        task_text = comment_less_task;

        // replace all enters with spaces
        task_text = task_text.replaceAll("\\r?\\n", " ");

        // list out all the variables
        variables = new ArrayList<ArrayList<String>>();
        int var_count = 1;
        while (task_text.contains("\"")) {
            int var_start_position = -1;
            int var_end_position = -1;
            for (int i = 0; i < task_text.length(); i++) {
                char c = task_text.charAt(i);

                if (c == '"') {
                    if (var_start_position == -1) {
                        var_start_position = i;
                    }
                    if (i > 0 && task_text.charAt(i - 1) == '\\') {
                        // ignore
                    }
                    else if (var_end_position == -1 && var_start_position != i) {
                        var_end_position = i;
                    }
                }

                if (var_start_position != -1 && var_end_position != -1) {
                    break;
                }
            }
            String var_id, variable;
            while (true) {
                var_id = getRandomString(10);
                if (!task_text.contains(var_id)) {
                    break;
                }
            }
            var_id = var_count + "#" + var_id;
            var_count++;
            variable = task_text.substring(var_start_position + 1, var_end_position);
            variable = variable.replaceAll("\\\\\"", "\"");
            variable = variable.replaceAll("\\\\n", "\n");
            
            task_text = task_text.substring(0, var_start_position) + "<v" + var_id + "v>" + task_text.substring(var_end_position + 1);

            ArrayList<String> variable_set = new ArrayList<String>();
            variable_set.add(var_id);
            variable_set.add(variable);

            variables.add(variable_set);
        }
        task_text = task_text.replaceAll("( )+", " ");
        task_text = task_text.trim();

        // check for some invalid conditions 
        char[] valid_chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
                            , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
                            , '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
                            , ' ', '-', ',', '(', ')', '_'};

        String tmp = task_text;

        for (ArrayList<String> v : variables) {
            tmp = tmp.replaceFirst("<v" + v.get(0) + "v>", "X");
        }

        for (int i = 0; i < tmp.length(); i++) {
            char c = tmp.charAt(i);
            boolean is_err = true;
            for (int j = 0; j < valid_chars.length; j++) {
                if (c == valid_chars[j]) {
                    is_err = false;
                    break;
                }
            }
            if (is_err) {
                throw new Exception();
            }
        }
        int count_first_b = 0;
        int count_second_b = 0;
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(i) == '(') count_first_b++;
            if (tmp.charAt(i) == ')') count_second_b++;
        }
        
        if (count_first_b != count_second_b || count_first_b + count_second_b == 0) {
            throw new Exception();   
        }
        int ii = 0;
        while (true) {
            int end = task_text.length();

            char c = task_text.charAt(ii);
            if (!(ii >= end - 1)) {
                if (isCapChar(c) || isLowChar(c) || isNumChar(c) || c == '-' || c == '_' || c == '<' || c == '>') {
                    char n = task_text.charAt(ii + 1);
                    if (n == '(' || n == ')' || n == ',') {
                        task_text = task_text.substring(0, ii + 1) + " " + task_text.substring(ii + 1);
                        ii = -1;
                    }
                }
                else if (c == '(') {
                    char n = task_text.charAt(ii + 1);
                    if (isCapChar(n) || isLowChar(n) || isNumChar(n) || n == '-' || n == '_' || n == ')' || n == '(' || n == ',' || c == '<' || c == '>') {
                        task_text = task_text.substring(0, ii + 1) + " " + task_text.substring(ii + 1);
                        ii = -1;
                    }
                }
                else if (c == ')') {
                    char n = task_text.charAt(ii + 1);
                    if (isCapChar(n) || isLowChar(n) || isNumChar(n) || n == '-' || n == '_' || n == ')' || n == '(' || n == ',' || c == '<' || c == '>') {
                        task_text = task_text.substring(0, ii + 1) + " " + task_text.substring(ii + 1);
                        ii = -1;
                    }
                }
                else if (c == ',') {
                    char n = task_text.charAt(ii + 1);
                    if (isCapChar(n) || isLowChar(n) || isNumChar(n) || n == '-' || n == '_' || n == ')' || n == '(' || n == ',' || c == '<' || c == '>') {
                        task_text = task_text.substring(0, ii + 1) + " " + task_text.substring(ii + 1);
                        ii = -1;
                    }
                }
            }

            ii++;
            if (ii >= end) {
                break;
            }
        }
        task_text = task_text.trim();

        int func_no = 0;
        functions = new ArrayList<ArrayList<String>>();
        while (true) {
            boolean func_found = false;
            int start = -1;
            int end = -1;
            for (int i = 0; i < task_text.length(); i++) {
                char c = task_text.charAt(i);

                if (isCapChar(c) || isLowChar(c) || isNumChar(c) || c == '_' || c == '-') {
                    if (i == 0 || task_text.charAt(i - 1) == ' ') {
                        start = i;
                    }
                    else if (task_text.charAt(i + 1) == ' ') {
                        end = i;
                    }
                }
                if (start >= 0 && end >= 0) {
                    func_found = true;
                    break;
                }
            }
            if (func_found) {
                String func_id;
                while (true) {
                    func_id = getRandomString(10);
                    if (!task_text.contains(func_id)) {
                        break;
                    }
                }
                String func = task_text.substring(start, end + 1);
                ArrayList<String> f = new ArrayList<String>();
                func_no++;
                func_id = func_no + "#" + func_id; 
                f.add(func_id);
                f.add(func.trim().toUpperCase());
                functions.add(f);
                task_text = task_text.substring(0, start) + "{f" + func_id + "f}" + task_text.substring(end + 1);
            }
            else {
                break;
            }
        }

        // refresh between spaces 
        while (true) {
            boolean done = true;
            for (int i = 0; i < task_text.length(); i++) {
                char c = task_text.charAt(i);
                if (c == '}' || c == '>') {
                    char x = task_text.charAt(i + 1);
                    if (x != ' ') {
                        task_text = task_text.substring(0, i + 1) + " " + task_text.substring(i + 1);
                        done = false;
                        break;
                    }
                }
                else if (c == '{' || c == '<') {
                    if (i != 0) {
                        char x = task_text.charAt(i - 1);
                        if (x != ' ') {
                            task_text = task_text.substring(0, i) + " " + task_text.substring(i);
                            done = false;
                            break;
                        }
                    }
                }
                else if (c == ',' || c == '(') {
                    char x = task_text.charAt(i - 1);
                    if (x != ' ') {
                        task_text = task_text.substring(0, i) + " " + task_text.substring(i);
                        done = false;
                        break;
                    }
                    x = task_text.charAt(i + 1);
                    if (x != ' ') {
                        task_text = task_text.substring(0, i + 1) + " " + task_text.substring(i + 1);
                        done = false;
                        break;
                    }
                }
            }
            if (done) break;
        }

        // validate couple of rules 
        for (int i = 0; i < task_text.length(); i++) {
            char c = task_text.charAt(i);
            char x;
            if (c == '{') {
                if (i != 0) {
                    x = task_text.charAt(i - 2);
                    if (x != '(' && x != ',') {
                        throw new Exception();
                    }
                }
            }
            else if (c == '}') {
                if (i + 1 >= task_text.length()) {
                    throw new Exception();
                }
                x = task_text.charAt(i + 2);
                if (x != '(') {
                    throw new Exception();
                }
            }
            else if (c == '<') {
                if (i == 0) {
                    throw new Exception();
                }
                x = task_text.charAt(i - 2);
                if (x != '(' && x != ',') {
                    throw new Exception();
                }
            }
            else if (c == '>') {
                if (i + 1 >= task_text.length()) {
                    throw new Exception();
                }
                x = task_text.charAt(i + 2);
                if (x != ')' && x!= ',') {
                    throw new Exception();
                }
            }
            else if (c == '(') {
                if (i == 0) {
                    throw new Exception();
                }
                if (i + 1 >= task_text.length()) {
                    throw new Exception();
                }
                x = task_text.charAt(i - 2);
                if (x != '}')  {
                    throw new Exception();
                }
                x = task_text.charAt(i + 2);
                if (x != '{' && x != '<' && x != ')')  {
                    throw new Exception();
                }
            }
            else if (c == ')') {
                if (i == 0) {
                    throw new Exception();
                }
                x = task_text.charAt(i - 2);
                if (x != '}' && x != '>' && x!= '(' && x!= ')')  {
                    throw new Exception();
                }
                if (i + 1 <= task_text.length()) {
                    if (i + 1 != task_text.length()) { 
                        x = task_text.charAt(i + 2);
                        if (x != ',' && x != ')')  {
                            throw new Exception();
                        }
                    }
                }
            }            
            else if (c == ',') {
                if (i == 0) {
                    throw new Exception();
                }
                if (i + 1 >= task_text.length()) {
                    throw new Exception();
                }
                x = task_text.charAt(i - 2);
                if (x != ')' && x != '>')  {
                    throw new Exception();
                }
                x = task_text.charAt(i + 2);
                if (x != '{' && x != '<')  {
                    throw new Exception();
                }
            }
        }

        TaskTree t = new TaskTree();
        t.makeTree(task_text, variables, functions);

        return t.task_tree;
    }

    static boolean isCapChar (char c) {
        char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i])
                return true;
        }
        return false;
    }

    static boolean isLowChar (char c) {
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i])
                return true;
        }
        return false;
    }

    static boolean isNumChar (char c) {
        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i])
                return true;
        }
        return false;
    }

    static String getRandomString (int length) {
		int leftLimit = 97; 	// letter 'a'
		int rightLimit = 122; 	// letter 'z'
		int targetStringLength = length;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
			(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}
}
