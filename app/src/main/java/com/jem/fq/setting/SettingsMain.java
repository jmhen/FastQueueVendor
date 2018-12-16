package com.jem.fq.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jem.fq.R;
import com.jem.fq.base.TabActivity;
import com.jem.fq.setting.SettingsMenu;

public class SettingsMain extends TabActivity{
    private String user_name;
    Intent Settings;
    Intent Queue;
    Intent PreOrder;
    private TextView storename;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);
        mActivity = this;
        fqTab = findViewById(R.id.fqTab);
        tabIni();
        fqTab.addOnTabSelectedListener(onFqTabSelectedListener);
        storename = findViewById(R.id.name);
        storename.setText(sharedPref.getString(PREF_STORENAME_KEY,""));


    }


    public void viewMenu(View view) {
        Intent goMenu = new Intent(mActivity, SettingsMenu.class);
        startActivity(goMenu);
    }
}
