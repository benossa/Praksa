package ba.hera.praksa;

/**
 * Created by Benjamin on 10.6.2015.
 */
        import android.app.Activity;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
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
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.ExecutionException;

public class Tab1 extends Fragment
{
    private WebView webview;
    private ArrayList<JSONObject> Listarizika;
    private ArrayList<JSONObject> ListarizikaRTP;
    private ArrayList<JSONObject> ListarizikaRTPIP;

    private int ProjekatID = 23411;
    private String Token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFUk0iLCJpc3MiOiJFUk0iLCJpYXQiOjE0MzM2OTczMjd9.2zd4OlKjW3Yfcd_q2FOoyEGpNrFbf7EuHeIkZ8ponr0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        webview = (WebView) view.findViewById(R.id.webView1);

        UcitajPodatke();

        return view;
    }

    private void UcitajPodatke()
    {
        try
        {
            new RetrieveFeedTask().execute("").get();
        }
        catch (Exception e)
        {
        }
    }
    private void UcitajGrafove()
    {
        String content = "<html>"
                + "  <head>"
                + "    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
                + "    <script type=\"text/javascript\">"
                + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
                + "      google.setOnLoadCallback(drawChart);"
                + "      function drawChart() {"
                + "        var data = google.visualization.arrayToDataTable(["
                + "          ['Rizik', 'Broj Rizika',{ role: \"style\" }],";
                                for(int i=0; i<Listarizika.size();i++)
                                {
                                    try {
                                        content += "['" + (i+1) + "'," + Listarizika.get(i).getString("number") +
                                                    ",' color: " + Listarizika.get(i).getString("color") + "'],";
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            content.substring(0,content.lastIndexOf(",")-1);
                content +=
                  "        ]);"
                + "        var view = new google.visualization.DataView(data);"
                + "        view.setColumns([0, 1,"
                + "          { calc: \"stringify\","
                + "             sourceColumn: 1,  "
                + "             type: \"string\","
                + "             role: \"annotation\" },2]);"
                + "      var options = {"
                + "             title: \"Broj rizika\",width: 350,height: 300,legend: { position: \"none\" }};"

                + "      var chart = new google.visualization.ColumnChart(document.getElementById(\"barchart_values\"));"
                + "                         chart.draw(view, options); }"
                + "    </script>"

                          + "    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
                          + "    <script type=\"text/javascript\">"
                          + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
                          + "      google.setOnLoadCallback(drawChart);"
                          + "      function drawChart() {"
                          + "        var data = google.visualization.arrayToDataTable(["
                          + "          ['Rizik', 'Broj Rizika sa odobrenim planom',{ role: \"style\" }],";
                                    if(ListarizikaRTP.size() > 0) {
                                        for (int i = 0; i < ListarizikaRTP.size(); i++) {
                                            try {
                                                content += "['" + (i + 1) + "'," + ListarizikaRTP.get(i).getString("number") +
                                                        ",' color: " + ListarizikaRTP.get(i).getString("color") + "'],";
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        content.substring(0, content.lastIndexOf(",") - 1);
                                    }
                                else {content += "['0', 0, 'color: #FFFFFF']"; }
                            content +="]);"
                        + "        var view = new google.visualization.DataView(data);"
                        + "        view.setColumns([0, 1,"
                        + "          { calc: \"stringify\","
                        + "             sourceColumn: 1,  "
                        + "             type: \"string\","
                        + "             role: \"annotation\" },2]);"
                        + "      var options = {"
                        + "             title: \"Broj Rizika sa odobrenim planom\",width: 350,height: 300,legend: { position: \"none\" }};"

                        + "      var chart = new google.visualization.ColumnChart(document.getElementById(\"barchart_values2\"));"
                        + "                         chart.draw(view, options); }"
                        + "    </script>"

                        + "    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
                        + "    <script type=\"text/javascript\">"
                        + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
                        + "      google.setOnLoadCallback(drawChart);"
                        + "      function drawChart() {"
                        + "        var data = google.visualization.arrayToDataTable(["
                        + "          ['Rizik', 'Broj Rizika',{ role: \"style\" }],";
                                if(ListarizikaRTPIP.size()>0)
                                {
                                    for (int i = 0; i < ListarizikaRTPIP.size(); i++) {
                                        try {
                                            content += "['" + (i + 1) + "'," + ListarizikaRTPIP.get(i).getString("number") +
                                                    ",' color: " + ListarizikaRTPIP.get(i).getString("color") + "'],";
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    content.substring(0, content.lastIndexOf(",") - 1);
                                }
                                else {content += "['0', 0, 'color: #FFFFFF']"; }
                        content += "]);"
                        + "        var view = new google.visualization.DataView(data);"
                        + "        view.setColumns([0, 1,"
                        + "          { calc: \"stringify\","
                        + "             sourceColumn: 1,  "
                        + "             type: \"string\","
                        + "             role: \"annotation\" },2]);"
                        + "      var options = {"
                        + "             title: \"Broj rizika sa planom u izradi\",width: 350,height: 300,legend: { position: \"none\" }};"

                        + "      var chart = new google.visualization.ColumnChart(document.getElementById(\"barchart_values3\"));"
                        + "                         chart.draw(view, options); }"
                        + "    </script>"
                + "  </head> <body>"
                + "    <div id=\"barchart_values\" style=\"width: 350px; height: 300px;\"></div>"
                + "    <div id=\"barchart_values2\" style=\"width: 350px; height: 300px;\"></div>"
                + "    <div id=\"barchart_values3\" style=\"width: 350px; height: 300px;\"></div>"
                + "  </body> </html>";

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webview.requestFocusFromTouch();
        webview.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "utf-8", null);
        //webview.loadUrl("file:///android_asset/Code.html");

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            String SetServerString = "";
            try {
                try {

                    HttpClient Client = new DefaultHttpClient();
                    // Create URL string
                    String url1 = "http://heraapps.com:8081/ermVenture/resources/protected/totalRisk/" + ProjekatID;
                    String url2 = "http://heraapps.com:8081/ermVenture/resources/protected/activePlan/" + ProjekatID;
                    String url3 = "http://heraapps.com:8081/ermVenture/resources/protected/newPlan/" + ProjekatID;

                    HttpGet get = new HttpGet(url1);
                    get.setHeader("Content-Type", "application/json");
                    get.setHeader("jwttoken",Token );
                    HttpResponse response = Client.execute(get);
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONArray result = new JSONArray(data);
                    Listarizika = new ArrayList<JSONObject>();
                    for (int i=0;i<result.length();i++)
                    {
                        Listarizika.add(result.getJSONObject(i));
                    }

                    get = new HttpGet(url2);
                    get.setHeader("Content-Type", "application/json");
                    get.setHeader("jwttoken",Token );
                    response = Client.execute(get);
                    entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                    result = new JSONArray(data);
                    ListarizikaRTP = new ArrayList<JSONObject>();
                    for (int i=0;i<result.length();i++)
                    {
                        ListarizikaRTP.add(result.getJSONObject(i));
                    }

                    get = new HttpGet(url3);
                    get.setHeader("Content-Type", "application/json");
                    get.setHeader("jwttoken",Token );
                    response = Client.execute(get);
                    entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                    result = new JSONArray(data);
                    ListarizikaRTPIP = new ArrayList<JSONObject>();
                    for (int i=0;i<result.length();i++)
                    {
                        ListarizikaRTPIP.add(result.getJSONObject(i));
                    }

                } catch (Exception ex) {return "Error:" + ex.toString();}
            } catch (Exception ex) {return "Error:" + ex.toString();}

            /****************/
            return SetServerString;
            /*****************/

        }

        protected void onPostExecute(String response)
        {
            UcitajGrafove();
        }
    }

}