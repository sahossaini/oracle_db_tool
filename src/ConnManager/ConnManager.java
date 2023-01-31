package ConnManager;

import java.util.ArrayList;

import Helpers.*;

public class ConnManager {
    ArrayList<ArrayList<String>> connections;

    public ConnManager () {
        connections = new ArrayList<ArrayList<String>>();
    }

    public void LoadConnections (String text) {
        connections = Json.jsonToStringArrayList(text);
    }

    public ArrayList<String> getProperties (String connection_name) {
        ArrayList<String> properties = new ArrayList<String>();

        for (ArrayList<String> i : connections) {
            if (i.get(0).equals(connection_name)) {
                properties.add(i.get(1));
                properties.add(i.get(2));
            }
        }
        return properties;
    }

    public String getPropertyValue (String connection_name, String property_name) {
        for (ArrayList<String> i : connections) {
            if (i.get(0).equals(connection_name)) {
                if (i.get(1).equals(property_name)) {
                    return i.get(2);
                }
            }
        }
        return null;
    }
}
