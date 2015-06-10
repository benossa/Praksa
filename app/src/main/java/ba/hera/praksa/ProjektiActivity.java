package ba.hera.praksa;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

public class ProjektiActivity extends Activity  {


    ba.hera.praksa.ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private ArrayList<Projekat> listaprojekata = new ArrayList<>();
    public Projekat temp = new Projekat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projekti);
        try {

            new RetrieveFeedTask().execute("").get();
            if(listDataHeader!=null)
            {
                expListView = (ExpandableListView) findViewById(R.id.lvExp);
                listAdapter = new ba.hera.praksa.ExpandableListAdapter(this, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);
                for (int i=0; i<listAdapter.getGroupCount();i++)
                {
                    expListView.expandGroup(i);
                }
            }
            else
                Toast.makeText(getApplicationContext(), "Lista prazna !", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "OnCreate: " + ex.toString(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_projekti, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void PripremiPodatke()
    {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        //Toast.makeText(getApplicationContext(), listaprojekata.size(), Toast.LENGTH_LONG).show();
        if(listaprojekata.size() > 0)
        for (int i=0;i<listaprojekata.size();i++)
        {
            List<String> Detalji = new ArrayList<String>();

            listDataHeader.add(listaprojekata.get(i).Name);
            Detalji.add("ID: "+listaprojekata.get(i).ID);
            Detalji.add("Evaluated Risks: "+listaprojekata.get(i).EvaluatedRisks);
            Detalji.add("Total Risks: "+listaprojekata.get(i).TotalRisks);
            Detalji.add("Treated Risks: "+listaprojekata.get(i).TreatedRisks);
            listDataChild.put(listDataHeader.get(i), Detalji);
        }
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            String SetServerString = "";
            try {
                try {

                    HttpClient Client = new DefaultHttpClient();
                    // Create URL string
                    String url = "http://heraapps.com:8081/ermVenture/resources/protected/projects";


                    HttpGet get = new HttpGet(url);
                    get.setHeader("Content-Type", "application/json");
                    get.setHeader("jwttoken", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFUk0iLCJpc3MiOiJFUk0iLCJpYXQiOjE0MzM2OTczMjd9.2zd4OlKjW3Yfcd_q2FOoyEGpNrFbf7EuHeIkZ8ponr0");

                                            //jos nije rijeseno pohrana tokena
                    HttpResponse response = Client.execute(get);

                    // provjeri o kojem se kodu radi, 100 informacije, 200 OK, 400 i 500 Error
                    //int status = response.getStatusLine().getStatusCode();
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONArray result = new JSONArray(data);
                    for (int n = 0; n < result.length(); n++)
                    {
                        JSONObject obj = result.getJSONObject(n);   //JSONArray parsiramo u JSONObjekat
                        temp = new Projekat();          //
                        temp.FromJSON(obj);             //JSON objekat parsiramo u projekat
                        listaprojekata.add(temp);       // temp objekat ubacimo na listu
                    }
                    PripremiPodatke();

                } catch (Exception ex) {return "Error:" + ex.toString();}
            } catch (Exception ex) {return "Error:" + ex.toString();}

            /****************/
            return SetServerString;

            /*****************/

        }

        protected void onPostExecute(String response) {
            if (response.contains("Error:")) {
                Toast.makeText(getApplicationContext(), "onPostExecute: "+response, Toast.LENGTH_LONG).show();
            } else {
                try {
                    //Toast.makeText(getApplicationContext(), "Preuzeta lista", Toast.LENGTH_LONG).show();

                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }


}