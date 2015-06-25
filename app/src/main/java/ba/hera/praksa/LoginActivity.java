package ba.hera.praksa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class LoginActivity extends Activity {


    private String UID, PW;
    private TextView ETUsername;
    private TextView ETPass;
    private CheckBox cbPrijava;

    public void Logiraj() {

        new RetrieveFeedTask().execute("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETUsername = (TextView) findViewById(R.id.ETUsername);
        ETPass = (TextView) findViewById(R.id.ETPassword);
        cbPrijava = (CheckBox) findViewById(R.id.cbZapamtiPrijavu);

        final SharedPreferences settings = getSharedPreferences("Config", 0);

        boolean zl = settings.getBoolean("ZapamtiLogin", false);
        ETUsername.setText(settings.getString("Username", ""));
        ETPass.setText(settings.getString("PW", ""));

        cbPrijava.setChecked(zl);
        if (zl)
        {
            UID = settings.getString("Username", null);
            PW = settings.getString("PW", null);
        }
        else
        {
            ETUsername.setText("");
            ETPass.setText(""); //znaci da ne zeli spasiti podatke
        }


        cbPrijava.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences settings = getSharedPreferences("Config", 0);
                SharedPreferences.Editor editor = settings.edit();
                if (isChecked) {
                    ETUsername = (TextView) findViewById(R.id.ETUsername);
                    ETPass = (TextView) findViewById(R.id.ETPassword);

                    editor.putBoolean("ZapamtiLogin", true);
                    editor.putString("Username", ETUsername.getText().toString());
                    editor.putString("PW", ETPass.getText().toString());
                    Toast.makeText(getApplicationContext(), "Podaci sacuvani", Toast.LENGTH_LONG).show();
                } else
                {
                    editor.putBoolean("ZapamtiLogin", false);
                    editor.putString("Username", null);
                    editor.putString("PW", null);
                    ETUsername.setText("");
                    ETPass.setText("");
                }
                editor.apply();

            }
        });


        Button button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ETUsername != null)
                    UID = ETUsername.getText().toString();

                if (ETPass != null)
                    PW = ETPass.getText().toString();

                if (cbPrijava.isChecked()) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Username", UID);
                    editor.putString("PW", PW);
                    editor.putBoolean("ZapamtiLogin", true);
                    editor.apply();
                }
                Logiraj();
            }
        });
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            String SetServerString = "";
            try {


                HttpClient Client = new DefaultHttpClient();
                String URL = "http://heraapps.com:8081/ermVenture/resources/login";
                HttpPost http = new HttpPost(URL);
                http.setHeader("Content-Type", "application/json");

                /*******/ //parametri kako bi postavili timeout konekcije
                HttpParams httpParameters = new BasicHttpParams();
                int timeoutConnection = 7000;
                int timeoutSocket = 7000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
                http.setParams(httpParameters);
                /*******/
                JSONObject object = new JSONObject();
                object.put("name", UID);
                object.put("password", PW);

                String message = object.toString();
                http.setEntity(new StringEntity(message, "UTF8"));
                HttpResponse response = Client.execute(http);
                HttpEntity entity = response.getEntity();
                SetServerString = EntityUtils.toString(entity);

                //int status = response.getStatusLine().getStatusCode();
                //if (status != 200)
                //    return "Error:";

                JSONObject result = new JSONObject(SetServerString);
                String token = result.getString("token");
                SharedPreferences settings = getSharedPreferences("Config", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("JWToken", token);
                editor.apply();


                /****************/
                return SetServerString;
                /*****************/
            } catch (Exception e) {
                return "Error:" + e.toString();
            }
        }

        protected void onPostExecute(String response)
        {
            if (response.contains("Error:")) {
                Toast.makeText(getApplicationContext(), "Login error. Invalid password, or no server response", Toast.LENGTH_LONG).show();
            } else
            {
                try {
                    Intent intent = new Intent(getApplicationContext(), ProjektiActivity.class);
                    LoginActivity.this.startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "Lista projekata", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

/*
    public class HttpGetAndroidExample extends Activity {

        TextView content;
        EditText name, pass;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            content = (TextView) findViewById(R.id.textLogin);
            name = (EditText) findViewById(R.id.ETUsername);
            pass = (EditText) findViewById(R.id.ETPassword);

            Button saveme = (Button) findViewById(R.id.btn_login);


            saveme.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "Please wait, connecting to server.", Toast.LENGTH_LONG).show();

                    try {

                        String loginValue = URLEncoder.encode(name.getText().toString(), "UTF-8");
                        String passValue = URLEncoder.encode(pass.getText().toString(), "UTF-8");

                        // Create http cliient object to send request to server

                        HttpClient Client = new DefaultHttpClient();

                        // Create URL string

                        //String URL = "http://androidexample.com/media/webservice/httpget.php?user="+loginValue+"&name="+fnameValue+"&email="+emailValue+"&pass="+passValue;
                        String URL = "http://heraapps.com:8081/ermVenture/resources/login";

                        //Log.i("httpget", URL);

                        try {
                            String SetServerString = "";

                            // Create Request to server and get response

                            HttpPost http = new HttpPost(URL);
                            http.setHeader("Content-Type", "application/json");

                            JSONObject object = new JSONObject();
                            try {

                                object.put("name", "ERM");
                                object.put("password", "venture");

                            } catch (Exception ex) {

                            }


                            String message = object.toString();
                            http.setEntity(new StringEntity(message, "UTF8"));


                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = Client.execute(http, responseHandler);

                            // Show response on activity

                            content.setText(SetServerString);
                        } catch (Exception ex) {
                            content.setText("Fail!");
                        }
                    } catch (UnsupportedEncodingException ex) {
                        content.setText("Fail");
                    }
                }
            });
        }
    }*/
}
