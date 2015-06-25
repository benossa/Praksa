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

public class Tab3 extends Fragment {

    private String SelectedProjekatID;
    private String Token;
    private WebView webview;
    private JSONObject Aspect1;
    private JSONObject Aspect2;
    private JSONObject Aspect3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        webview = (WebView) view.findViewById(R.id.webView3);
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

    public void UcitajGraf() {
        try {
            String sadrzaj = "<html><head>"
                    + "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
                    + "<script type=\"text/javascript\">"
                    + "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
                    + "google.setOnLoadCallback(drawTable);"
                    + "function drawTable() {"
                    + "var data = new google.visualization.arrayToDataTable(["
                    + "['Risk',";
            for (int i = 0; i < 11; i++) {
                sadrzaj += "'" + (i + 1) + "',{ role: 'style' }, { role: 'annotation' }, ";
            }
            sadrzaj += "],";
/* ----------------------------- ASPECT 1 ---------------------------------------------*/
                sadrzaj += "['" + Aspect1.getString("name") + "',";
                for (int k=0; k<11;k++)
                {
                    sadrzaj += Aspect1.getString("col"+(k+1)) + ",'";
                    sadrzaj += Aspect1.getString("aspectColor") + "','";
                    sadrzaj += Aspect1.getString("col"+(k+1)) + "',";
                }
                sadrzaj += "],";
/* ----------------------------- ASPECT 2 ---------------------------------------------*/
                sadrzaj += "['" + Aspect2.getString("name") + "',";
                for (int k=0; k<11;k++)
                {
                    sadrzaj += Aspect2.getString("col"+(k+1)) + ",'";
                    sadrzaj += Aspect2.getString("aspectColor") + "','";
                    sadrzaj += Aspect2.getString("col"+(k+1)) + "',";
                }
                sadrzaj += "],";
    /* ----------------------------- ASPECT 3 ---------------------------------------------*/
                sadrzaj += "['" + Aspect3.getString("name") + "',";
                for (int k=0; k<11;k++)
                {
                    sadrzaj += Aspect3.getString("col"+(k+1)) + ",'";
                    sadrzaj += Aspect3.getString("aspectColor") + "','";
                    sadrzaj += Aspect3.getString("col"+(k+1)) + "',";
                }
                sadrzaj += "],";

        sadrzaj += "]); var options = {width: 400,height: 500, bar: { groupWidth: '40%' },legend: { position: \"none\" },"
                + "hAxis: { textPosition: 'none' }, isStacked: 'relative',annotations: {textStyle: {fontSize: 16,highContrast:true}}};"
                + "var chart = new google.visualization.BarChart(document.getElementById(\"barchart_values\"));"
                + "chart.draw(data, options);}"
                + "</script>"
                + "</head><body>"
                + "<div align=\"center\" id=\"barchart_values\"></div>"
                + "</body></html>";


        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webview.requestFocusFromTouch();
        webview.loadDataWithBaseURL("file:///android_asset/", sadrzaj, "text/html", "utf-8", null);
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    public void UcitajGraf2()
    {
        try {
            String sadrzaj = "<html><head>"
                    + "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
                    + "<script type=\"text/javascript\">"
                    + "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
                    + "google.setOnLoadCallback(drawTable);"
                    + "function drawTable() {"
                    + "var data = new google.visualization.arrayToDataTable(["
                    + "['Risk',"
                    + "'" + Aspect1.getString("name") + "',{ role: 'style' }, { role: 'annotation' },"
                    + "'" + Aspect2.getString("name")+"',{ role: 'style' }, { role: 'annotation' },"
                    + "'" + Aspect3.getString("name")+ "',{ role: 'style' } , { role: 'annotation' }],";
                    for (int k=0; k<11;k++)
                    {
                        sadrzaj += "['" + (k+1) + "', ";
                        sadrzaj += Aspect1.getString("col"+(k+1)) + ",'";
                        sadrzaj += Aspect1.getString("aspectColor") + "','";
                        sadrzaj += Aspect1.getString("col"+(k+1)) + "',";

                        sadrzaj += Aspect2.getString("col"+(k+1)) + ",'";
                        sadrzaj += Aspect2.getString("aspectColor") + "','";
                        sadrzaj += Aspect2.getString("col"+(k+1)) + "',";

                        sadrzaj += Aspect3.getString("col"+(k+1)) + ",'";
                        sadrzaj += Aspect3.getString("aspectColor") + "','";
                        sadrzaj += Aspect3.getString("col"+(k+1)) + "'],";
                    }

            sadrzaj += "]); var options = {width: 350,height: 800, bar: { groupWidth: '90%' },"
                    + "hAxis: { textPosition: 'none' }, " +
                    "isStacked: 'relative'," +
                    "annotations: {textStyle: {fontSize: 15,highContrast:true}}," +
                    " colors: ['"+Aspect1.getString("aspectColor")+"','" + Aspect2.getString("aspectColor")+"','"+Aspect3.getString("aspectColor")+"'],"+
                    "legend: { position: 'top', maxLines: 1 }" +
                    "};"
                    + "var chart = new google.visualization.BarChart(document.getElementById(\"barchart_values\"));"
                    + "chart.draw(data, options);}"
                    + "</script>"
                    + "</head><body>"
                    + "<div align=\"center\" id=\"barchart_values\"></div>"
                    + "</body></html>";


            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);
            webview.requestFocusFromTouch();
            webview.loadDataWithBaseURL("file:///android_asset/", sadrzaj, "text/html", "utf-8", null);
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        private Exception exception;

        protected String doInBackground(String... urls)
        {
            String SetServerString = "";
            try
            {
                HttpClient Client = new DefaultHttpClient();
                String urlAspects = "http://heraapps.com:8081/ermVenture/resources/protected/risk/" + SelectedProjekatID;
                HttpGet get = new HttpGet(urlAspects);
                get.setHeader("Content-Type", "application/json");
                get.setHeader("jwttoken",Token );
                HttpResponse response = Client.execute(get);
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                JSONArray result = new JSONArray(data);
                Aspect1 = new JSONObject(); Aspect1 = result.getJSONObject(0);
                Aspect2 = new JSONObject(); Aspect2 = result.getJSONObject(1);
                Aspect3 = new JSONObject(); Aspect3 = result.getJSONObject(2);
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
                UcitajGraf2();
        }
    }
}
