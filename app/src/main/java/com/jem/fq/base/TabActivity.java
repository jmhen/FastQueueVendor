package com.jem.fq.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.jem.fq.QueueMain;
import com.jem.fq.preorder.PreorderMain;
import com.jem.fq.setting.SettingsMain;



public class TabActivity extends BaseActivity {
    protected TabLayout fqTab;
    protected Activity mActivity;
    protected TabLayout.OnTabSelectedListener onFqTabSelectedListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        onFqTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int idx = tab.getPosition();
                Intent proceed = new Intent();
                switch (idx) {
                    case 0:
                        proceed.setClass(mActivity, QueueMain.class);
                        startActivity(proceed);
                        break;
                    case 1:
                        proceed.setClass(mActivity, PreorderMain.class);
                        startActivity(proceed);
                        break;
                    case 2:
                        proceed.setClass(mActivity, SettingsMain.class);
                        startActivity(proceed);
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }


    protected void tabIni(){
        if(mActivity.getClass().equals(QueueMain.class)) {
            fqTab.getTabAt(0).select();

        }
        else if(mActivity.getClass().equals(PreorderMain.class)) {
            fqTab.getTabAt(1).select();

        }
        else if(mActivity.getClass().equals(SettingsMain.class)) {
            fqTab.getTabAt(2).select();

        }

    }


}
