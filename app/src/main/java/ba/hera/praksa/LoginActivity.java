package ba.hera.praksa;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class LoginActivity extends Activity {
    TextView content;


    public void Logiraj(final String username, final String password) {

        //button.cancelPendingInputEvents();
        //button.setText("Logiram...");
        new RetrieveFeedTask().execute("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //v.cancelPendingInputEvents();
                //v.setText("Logiram...");
                Logiraj("ERM", "venture");
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            String SetServerString = "";
            try {
                try {
//                   content = (TextView) findViewById(R.id.textLogin);
                    TextView username = (TextView)findViewById(R.id.ETUsername);
                    TextView password = (TextView)findViewById(R.id.ETPassword);

                    HttpClient Client = new DefaultHttpClient();
                    // Create URL string
                    String URL = "http://heraapps.com:8081/ermVenture/resources/login";

                    try {


                        // Create Request to server and get response

                        HttpPost http = new HttpPost(URL);
                        http.setHeader("Content-Type", "application/json");

                        /*******/
                        JSONObject object = new JSONObject();
                        try {

                            //object.put("name", username.getText());
                            //object.put("password", password.getText());
                            object.put("name", "ERM");
                            object.put("password", "venture");


                        } catch (Exception ex) {

                        }


                        String message = object.toString();
                        http.setEntity(new StringEntity(message, "UTF8"));
/************/

                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        SetServerString = Client.execute(http, responseHandler);
                        //JSONArray result = new JSONArray(SetServerString);
                        //JSONObject object1 = result.getJSONObject(0);


                        //SharedPreferences settings = getSharedPreferences("jwttoken", 0);
                        //SharedPreferences.Editor editor = settings.edit();
                        //editor.putString("jwttoken", object1.getString("token"));
                        //editor.apply();

                        // Show response on activity


                    } catch (Exception ex) {
                        return "Error:" + ex.toString();
                    }
                } catch (Exception ex) {
                    return "Error:" + ex.toString();
                }

                /****************/
                return SetServerString;

                /*****************/



            } catch (Exception e) {
                return "Error:" + e.toString();
            }
        }

        protected void onPostExecute(String response) {
            if (response.contains("Error:"))
            {
                //content.setText("Greska u prijavi !");
                Toast.makeText(getApplicationContext(),"Greska u prijavi, provjerite konekciju.",Toast.LENGTH_LONG).show();
            }
            else {
                //content.setText(response);
                try {
                    Intent intent = new Intent(getApplicationContext(), ProjektiActivity.class);
                    LoginActivity.this.startActivity(intent);
                }
                catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                }
            }
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

































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
                    //ALERT MESSAGE
                    Toast.makeText(getBaseContext(), "Please wait, connecting to server.", Toast.LENGTH_LONG).show();

                    try {

                        // URLEncode user defined data

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
    }
}
