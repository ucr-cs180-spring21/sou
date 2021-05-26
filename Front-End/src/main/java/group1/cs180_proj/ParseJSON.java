package group1.cs180_proj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJSON {
    static public ArrayList<Uber> parseALUber(JSONArray jsonarray){
        ArrayList<Uber> alu = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);

            Uber u = new Uber(jsonobject.getString("date"), jsonobject.getString("time"), jsonobject.getString("state"),
                    jsonobject.getString("pickup"), jsonobject.getString("address"), jsonobject.getString("street"), jsonobject.getString("id"));


            alu.add(u);
        }
        return alu;
    }
}
