package com.jem.fq.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected final String SERVER = "http://fastqueue.000webhostapp.com/";
    private final String sharedPrefFile = "sharedPref";
    protected SharedPreferences sharedPref;
    protected Activity asyncActivity;
    public static final String PREF_ID_KEY = "store_id";
    public static final String PREF_USERNAME_KEY = "store_username";
    public static final String PREF_STORENAME_KEY = "store_name";
    public static final String PREF_DESCRIPTION_KEY = "store_description";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);


    }

    protected ProgressDialog getProgressDialog(String msg) {
        ProgressDialog dialog = new ProgressDialog(asyncActivity);
        dialog.setMessage(msg);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }
}
