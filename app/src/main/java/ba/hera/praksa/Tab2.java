package ba.hera.praksa;
/**
 * Created by Benjamin on 10.6.2015.
 */
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Tab2 extends Fragment {

    private ArrayList<JSONObject> ListaNovac;
    private ArrayList<JSONObject> ListaKvalitet;
    private ArrayList<JSONObject> ListaVrijeme;

    private String SelectedProjekatID;
    private String Token;
    private WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        webview = (WebView) view.findViewById(R.id.webView2);
        SharedPreferences settings = this.getActivity().getSharedPreferences("Config", 0);
        Token = settings.getString("JWToken", ".");
        SelectedProjekatID = settings.getString("SelectedProjekatID",".");
        try
        {
            new RetrieveFeedTask().execute("").get();
        }
        catch (Exception e)
        {
        }
        return view;

    }

    private void UcitajGrafove()
    {
        String sadrzaj = "<html><head>"
                + "<style>"
                + " .google-visualization-table-td {"
                + " text-align: center !important;}"
                + "</style>"
                + "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
                + "<script type=\"text/javascript\">"
                + "google.load(\"visualization\", \"1\", {packages:[\"table\"]});"
                + "google.setOnLoadCallback(drawTable);"
                + "function drawTable() {"
                + "var data = new google.visualization.DataTable();"
                + "data.addColumn('string', '1');"
                + "data.addColumn('string', '2');"
                + "data.addColumn('string', '3');"
                + "data.addColumn('string', '4');"
                + "data.addColumn('string', '5');"
                + "data.addRows(5);";
                int ind = 0; //index za listu
                for (int i=0; i < 5 ; i++)
                {
                    for (int j=0; j < 5; j ++)
                    {
                        try
                        {
                            sadrzaj += "data.setCell(" + i + " , " + j + " , '" + ListaNovac.get(ind).getString("number") + "');";
                            sadrzaj += "data.setProperty(" + i + "," + j + ",'style', 'background-color: " + ListaNovac.get(ind).getString("color") + ";');";
                            ind += 1;
                        }
                        catch (Exception ex) {}
                    }
                }
                sadrzaj += "var table = new google.visualization.Table(document.getElementById('table_div1'));"
                        + "table.draw(data, {showRowNumber: true, allowHtml: true, width: 250, alternatingRowStyle: false});}"
                        + "</script>"
     /* ------------------------------------------------------------------------------------------------------------------------------*/
                        + "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
                        + "<script type=\"text/javascript\">"
                        + "google.load(\"visualization\", \"1\", {packages:[\"table\"]});"
                        + "google.setOnLoadCallback(drawTable);"
                        + "function drawTable() {"
                        + "var data = new google.visualization.DataTable();"
                        + "data.addColumn('string', '1');"
                        + "data.addColumn('string', '2');"
                        + "data.addColumn('string', '3');"
                        + "data.addColumn('string', '4');"
                        + "data.addColumn('string', '5');"
                        + "data.addRows(5);";
                        ind = 0; //index za listu
                        for (int i=0; i < 5 ; i++)
                        {
                            for (int j=0; j < 5; j ++)
                            {
                                try
                                {
                                    sadrzaj += "data.setCell(" + i + " , " + j + " , '" + ListaKvalitet.get(ind).getString("number") + "');";
                                    sadrzaj += "data.setProperty(" + i + "," + j + ",'style', 'background-color: " + ListaKvalitet.get(ind).getString("color") + ";');";
                                    ind += 1;
                                }
                                catch (Exception ex) {}
                            }
                        }
                        sadrzaj += "var table = new google.visualization.Table(document.getElementById('table_div2'));"
                        + "table.draw(data, {showRowNumber: true, allowHtml: true, width: 250, alternatingRowStyle: false});}"
                        + "</script>"
                /* ------------------------------------------------------------------------------------------------------------------------------*/
                        + "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
                        + "<script type=\"text/javascript\">"
                        + "google.load(\"visualization\", \"1\", {packages:[\"table\"]});"
                        + "google.setOnLoadCallback(drawTable);"
                        + "function drawTable() {"
                        + "var data = new google.visualization.DataTable();"
                        + "data.addColumn('string', '1');"
                        + "data.addColumn('string', '2');"
                        + "data.addColumn('string', '3');"
                        + "data.addColumn('string', '4');"
                        + "data.addColumn('string', '5');"
                        + "data.addRows(5);";
                        ind = 0; //index za listu
                        for (int i=0; i < 5 ; i++)
                        {
                            for (int j=0; j < 5; j ++)
                            {
                                try
                                {
                                    sadrzaj += "data.setCell(" + i + " , " + j + " , '" + ListaVrijeme.get(ind).getString("number") + "');";
                                    sadrzaj += "data.setProperty(" + i + "," + j + ",'style', 'background-color: " + ListaVrijeme.get(ind).getString("color") + ";');";
                                    ind += 1;
                                }
                                catch (Exception ex) {}
                            }
                        }
                        sadrzaj += "var table = new google.visualization.Table(document.getElementById('table_div3'));"
                        + "table.draw(data, {showRowNumber: true, allowHtml: true, width: 250, alternatingRowStyle: false});}"
                        + "</script>"
                        /* ------------------------------------------------------------------------------------------------------------------------------*/
                        + "</head><body>"
                        + "<p><b>Money</b>.</p>"
                        + "<div align=\"center\" id=\"table_div1\"></div>"
                         + "<p><b>Quality</b>.</p>"
                         + "<div align=\"center\" id=\"table_div2\"></div>"
                         + "<p><b>Time</b>.</p>"
                        + "<div align=\"center\" id=\"table_div3\"></div>"
                        + "</body></html>";


        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webview.requestFocusFromTouch();
        webview.loadDataWithBaseURL("file:///android_asset/", sadrzaj, "text/html", "utf-8", null);
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        private Exception exception;

        protected String doInBackground(String... urls)
        {
            String SetServerString = "";
            try
            {
                HttpClient Client = new DefaultHttpClient();
                String urlAspects = "http://heraapps.com:8081/ermVenture/resources/protected/aspects/" + SelectedProjekatID;
                HttpGet get = new HttpGet(urlAspects);
                get.setHeader("Content-Type", "application/json");
                get.setHeader("jwttoken",Token );
                HttpResponse response = Client.execute(get);
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                JSONArray result = new JSONArray(data);

                /*    Pokupimo ID od svih aspekata i sada ide preuzimanje matrica za sve aspekte      */
                String url1 = "http://heraapps.com:8081/ermVenture/resources/protected/matrix/" + SelectedProjekatID + "/" + result.getJSONObject(0).getString("id"); //novac
                String url2 = "http://heraapps.com:8081/ermVenture/resources/protected/matrix/" + SelectedProjekatID + "/" + result.getJSONObject(1).getString("id"); //kvalitet
                String url3 = "http://heraapps.com:8081/ermVenture/resources/protected/matrix/" + SelectedProjekatID + "/" + result.getJSONObject(2).getString("id"); //vrijeme

                /* NOVAC*/
                HttpGet get1 = new HttpGet(url1);
                get1.setHeader("Content-Type","application/json");
                get1.setHeader("jwttoken",Token );
                HttpResponse response1 = Client.execute(get1);
                HttpEntity entity1 = response1.getEntity();
                String data1 = EntityUtils.toString(entity1);
                JSONArray result1 = new JSONArray(data1);

                /* KVALITET */
                HttpGet get2 = new HttpGet(url2);
                get2.setHeader("Content-Type","application/json");
                get2.setHeader("jwttoken",Token );
                HttpResponse response2 = Client.execute(get2);
                HttpEntity entity2 = response2.getEntity();
                String data2 = EntityUtils.toString(entity2);
                JSONArray result2 = new JSONArray(data2);


                /* VRIJEME */
                HttpGet get3 = new HttpGet(url3);
                get3.setHeader("Content-Type","application/json");
                get3.setHeader("jwttoken",Token );
                HttpResponse response3 = Client.execute(get3);
                HttpEntity entity3 = response3.getEntity();
                String data3 = EntityUtils.toString(entity3);
                JSONArray result3 = new JSONArray(data3);

                ListaNovac = new ArrayList<JSONObject>();
                ListaKvalitet = new ArrayList<JSONObject>();
                ListaVrijeme = new ArrayList<JSONObject>();

                for (int i=0; i < result1.length() ;i++)
                {
                    ListaNovac.add(result1.getJSONObject(i));
                    ListaKvalitet.add(result2.getJSONObject(i));
                    ListaVrijeme.add(result3.getJSONObject(i));
                }
                /* KRAJ*/

            }
            catch (Exception ex)
            {
                return "Error:" + ex.toString();
            }
            return SetServerString;
        }

        protected void onPostExecute(String response)
        {
            if(response.contains("Error"))
            {
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            }
            else
            UcitajGrafove();
        }
    }
}

