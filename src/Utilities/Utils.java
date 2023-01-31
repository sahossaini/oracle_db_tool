package Utilities;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.json.simple.*;
import org.json.simple.parser.*;

public class Utils {
    public static String readFile(String file_name) throws Exception {
		return new String(Files.readAllBytes(Paths.get(file_name)));
	}

	public static String[][] getConnections (String json_string) throws Exception {
		Object obj=JSONValue.parse(json_string);  
    	JSONObject jsonObject = (JSONObject) obj;
		System.out.println(jsonObject);
			
        for (Object key : jsonObject.keySet()) {
			//based on you key types
			String keyStr = (String)key;
			Object keyvalue = jsonObject.get(keyStr);
	
			//Print key and value
			System.out.println("key: "+ keyStr + " value: " + keyvalue);
	
			// for nested objects iteration if required
			// if (keyvalue instanceof JSONObject)
			// 	System.out.println((JSONObject)keyvalue);
				
		}
		return null;
	}
}
