package ba.hera.praksa;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class ProjektiActivity extends ExpandableListActivity {


    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private ArrayList<Projekat> listaprojekata = new ArrayList<>();
    public Projekat temp = new Projekat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_projekti);
        //FillList();
        try {
            new RetrieveFeedTask().execute("");

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void FillList2() {

        ExpandableListView expandableList = getExpandableListView();
        //ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.ExpListaProjekata);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);


        setGroupParents();
//benjo test
        // Create the Adapter
        MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);
        //adapter.setInflater(LayoutInflater.from(expandableList.getContext()), this);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

        // Set the Adapter to expandableList
        expandableList.setAdapter(adapter);

        expandableList.setOnChildClickListener(this);
    }

    public void setGroupParents() {

        ArrayList<String> child = new ArrayList<String>();
        for (int i=0; i<listaprojekata.size();i++)
        {
            child = new ArrayList<String>();
            parentItems.add(listaprojekata.get(i).Name);
            child.add("ID: "+listaprojekata.get(i).ID);
            child.add("Evaluated Risks: "+listaprojekata.get(i).EvaluatedRisks);
            child.add("Total Risks: "+listaprojekata.get(i).TotalRisks);
            child.add("Treated Risks: "+listaprojekata.get(i).TreatedRisks);
            childItems.add(child);
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
                    int status = response.getStatusLine().getStatusCode();
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
                    Toast.makeText(getApplicationContext(), "Preuzeta lista", Toast.LENGTH_LONG).show();
                    FillList2();    //kada pokupi objekte onda ih ucitaje na listu

                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

}