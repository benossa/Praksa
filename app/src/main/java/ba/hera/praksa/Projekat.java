package ba.hera.praksa;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Benjamin on 7.6.2015.
 */
public class Projekat {
    public String ID;
    public String Name;
    public String EvaluatedRisks;
    public String TotalRisks;
    public String TreatedRisks;

    public Boolean FromJSON(JSONObject obj) //parsiramo iz JSONObject u nase propertie
    {
        if(obj!= null)
        {
            //JSON.parse('true');            // true

            try {
                Name = obj.getString("name");
                ID = obj.getString("id");
                EvaluatedRisks = obj.getString("evaluatedRisks");
                TotalRisks = obj.getString("totalRisks");
                TreatedRisks = obj.getString("treatedRisks");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
