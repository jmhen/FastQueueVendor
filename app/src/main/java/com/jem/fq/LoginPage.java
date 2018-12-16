package com.jem.fq;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jem.fq.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginPage extends BaseActivity implements AsyncResponse {
    private String TAG =  LoginPage.class.getName(); // good way to put value to TAG
    private RecyclerView mRecyclerView;

    private static final int storeCount = 10;
    private final String WEBSERVICE_LOGIN ="store/login.php";
    private String server = SERVER + WEBSERVICE_LOGIN;
    private int GET_DATA = 0;
    private String loginID;
    private TextView loginError;
    private Intent queueMain;
    private Activity mActivity;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_login);



        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userID = findViewById(R.id.storeID);
                loginID = userID.getText().toString();
                loginError = findViewById(R.id.loginError);
                // GRAB JSON ID
                getJSON(server);
            }
        });
    }

    @Override
    public void processFinish(int request, String result) {
        if(request == GET_DATA){
            if(result.length()>0){
                try {
                    fillDetails(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(!sharedPref.getString(PREF_ID_KEY,"").isEmpty()) {
                    queueMain = new Intent(LoginPage.this, QueueMain.class);
                    LoginPage.this.startActivity(queueMain);
                    LoginPage.this.finish();
                }
                else{ loginError.setVisibility(View.VISIBLE);}

            }
        }else {
                Toast.makeText(this, R.string.login_no_internet, Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * This method changes JSON string into JSONArray[ ], before converted into JSONObject{ }.
     * @param json this needs to be processed prior to this method is called
     * @throws JSONException
     */
    private void fillDetails(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject storeObj = jsonArray.getJSONObject(i);
            Log.d("ID: ",i+" = "+ storeObj.getString("store_username" ));
            if (storeObj.getString("store_username").equals(loginID)) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(PREF_ID_KEY,storeObj.getString("store_id"));
                editor.putString(PREF_STORENAME_KEY,storeObj.getString("store_name"));
                editor.putString(PREF_DESCRIPTION_KEY,storeObj.getString("store_desc"));
                editor.putString(PREF_USERNAME_KEY,storeObj.getString("store_username"));
                editor.commit();

                Log.d("My Store Name: ", sharedPref.getString(PREF_STORENAME_KEY,""));
                break;
            }
        }
    }

    protected void getJSON(final String urlWebService){
        class GetJSON extends AsyncTask<Void, Void, String> {
            AsyncResponse delegate = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(LoginPage.this, s, Toast.LENGTH_SHORT).show();
                Log.d("GetJSON", s);
                delegate.processFinish(GET_DATA,s);
               // this.cancel(true);
            }

            @Override
            protected String doInBackground(Void... voids) {
                // this parses json into a readable string
                try {
                    URL url = new URL(urlWebService);
                    Log.d("DisplayUrl: ", urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null){
                        sb.append(json + "\n");
                        Log.d("Displayjson: ",json);
                    }
                    // Log.d("DisplaySB: ", sb.toString().trim());
                    return sb.toString().trim();
                } catch (Exception e){
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.delegate = (AsyncResponse) mActivity;
        getJSON.execute();
    }


}
