package Helpers;

import java.util.ArrayList;
import org.json.simple.*;

public class Json {
    public static ArrayList<ArrayList<String>> jsonToStringArrayList (String json) {
    	JSONObject jsonObject = (JSONObject) JSONValue.parse(json);
		
        ArrayList<ArrayList<String>> connections = new ArrayList<>();

        for (Object i : jsonObject.keySet()) {
            String connection_name = (String) i;
			JSONObject connection_properties = (JSONObject) jsonObject.get((String) i);

            if (connection_properties instanceof JSONObject) {
                for (Object j : connection_properties.keySet()) {
                    String conn_property_name = (String) j;
                    String conn_property_value = (String) connection_properties.get((String) j);
                    
                    ArrayList<String> row = new ArrayList<String>();
                    row.add(connection_name);
                    row.add(conn_property_name);
                    row.add(conn_property_value);

                    connections.add(row);

                    // System.out.println("conn: " + connection_name);
                    // System.out.println("pname: " + conn_property_name);
                    // System.out.println("pvalue: " + conn_property_value);
                }
            }
		}
        return connections;
    }
}
