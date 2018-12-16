package com.jem.fq.preorder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jem.fq.R;
import com.jem.fq.base.TabActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.Date;

public class PreorderMain extends TabActivity {

    private String user_name;
    private String preorderCount;
    private String preorderDeadlineTime;
    private Date preorderDeadline;

    Button preorderGo;
    Button preorderEndButton1;
    Button preorderEndButton2;
    Button stopPreorder;

    Spinner preorderDeadlineMenu;
    Spinner preorderCountMenu;

    RelativeLayout pre_main;
    LinearLayout pre_prog;
    LinearLayout pre_time;
    LinearLayout pre_count;

    TextView text_preordercount;
    TextView text_timeleft;

    DeadlineCheck currentDeadline;
    CountCheck currentCount;

    Date nextDeadline;
    String nextDeadlineText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preorder_main);
        fqTab = findViewById(R.id.fqTab);
        tabIni();
        fqTab.addOnTabSelectedListener(onFqTabSelectedListener);
        //layouts
        pre_main = findViewById(R.id.preorder_main);
        pre_prog = findViewById(R.id.preorder_inprogress);
        pre_time = findViewById(R.id.preorder_timereached);
        pre_count = findViewById(R.id.preorder_countreached);

        //init visibility
        pre_main.setVisibility(View.VISIBLE);
        pre_prog.setVisibility(View.INVISIBLE);
        pre_time.setVisibility(View.INVISIBLE);
        pre_count.setVisibility(View.INVISIBLE);

        preorderGo = findViewById(R.id.preorder_button);
        preorderEndButton1 = findViewById(R.id.preorder_closeButton_timereached);
        preorderEndButton2 = findViewById(R.id.preorder_closeButton_countreached);
        stopPreorder = findViewById(R.id.preorder_stop_button);

        text_preordercount = (TextView) findViewById(R.id.preorder_ordercount);
        text_timeleft = (TextView) findViewById(R.id.preorder_timeleft);

        nextDeadline = PreorderDeadline.getPreorderDeadline();
        nextDeadlineText = ConvertTime.dateToString(nextDeadline)[1];

        Log.i("Preorder", "Main page init"); /* For Debugging purpose! */

        //spinner1
        preorderCountMenu = findViewById(R.id.preorder_count_menu);
        ArrayAdapter<CharSequence> adapter_preorderCount =
                ArrayAdapter.createFromResource(this, R.array.numOfOrders, android.R.layout.simple_spinner_dropdown_item);
        preorderCountMenu.setAdapter(adapter_preorderCount);

        preorderCountMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Preorder", "preorder count: " + preorderCount);
                preorderCount = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                preorderCount = "10";
            }
        });

        //spinner2
        preorderDeadlineMenu = findViewById(R.id.preorder_deadline_menu);
        String[] deadlineOfOrders = {nextDeadlineText};
        ArrayAdapter<String> adapter_preorderDeadline = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, deadlineOfOrders);
        preorderDeadlineMenu.setAdapter(adapter_preorderDeadline);

        preorderDeadlineMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preorderDeadlineTime = String.valueOf(parent.getItemAtPosition(position));
                preorderDeadline = nextDeadline;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                preorderDeadlineTime = nextDeadlineText;
                preorderDeadline = nextDeadline;
            }
        });

        //database connection
        preorderGo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            String out = submitData.submitPreorder(preorderCount, ConvertTime.dateToString(preorderDeadline)[0], ConvertTime.dateToString(preorderDeadline)[1]);
                            if (out == null){
                                Log.i("Preorder", "Data sent: " +preorderCount + ConvertTime.dateToString(preorderDeadline)[0] + ConvertTime.dateToString(preorderDeadline)[1]);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();

                pre_main.setVisibility(View.INVISIBLE);
                pre_prog.setVisibility(View.VISIBLE);
                pre_time.setVisibility(View.INVISIBLE);
                pre_count.setVisibility(View.INVISIBLE);

                Log.i("Preorder", "In Progress Init");

                currentDeadline = new DeadlineCheck(nextDeadline);
                currentCount = new CountCheck(preorderCount);

                new PreorderAsyncTask().execute();
            }
        });

        preorderEndButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Preorder", "Main page");
                pre_main.setVisibility(View.VISIBLE);
                pre_prog.setVisibility(View.INVISIBLE);
                pre_time.setVisibility(View.INVISIBLE);
                pre_count.setVisibility(View.INVISIBLE);

            }
        });

        preorderEndButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Preorder", "Main page");
                pre_main.setVisibility(View.VISIBLE);
                pre_prog.setVisibility(View.INVISIBLE);
                pre_time.setVisibility(View.INVISIBLE);
                pre_count.setVisibility(View.INVISIBLE);

            }
        });

        stopPreorder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pre_main.setVisibility(View.VISIBLE);
                pre_prog.setVisibility(View.INVISIBLE);
                pre_time.setVisibility(View.INVISIBLE);
                pre_count.setVisibility(View.INVISIBLE);
            }
        });

    }










    public class PreorderAsyncTask extends AsyncTask<Void, String, String> {

        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                while (!currentCount.stopPreorder() && !currentDeadline.stopPreorder()){
                    Log.i("Preorder", "In Progress");
                    //Log.i("Preorder", dateToString(currentDeadline.current())[0]+dateToString(currentDeadline.current())[1]);
                    //Log.i("Preorder", dateToString(currentDeadline.deadline())[0]+dateToString(currentDeadline.deadline())[1]);
                    //Log.i("Preorder", Boolean.toString(currentDeadline.stopPreorder()));
                    publishProgress(Integer.toString(currentCount.getCurrent()), currentDeadline.timeLeft());
                    if(isCancelled()){
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (currentCount.stopPreorder()){
                    Log.i("Preorder", "Limit reached");
                    result = "limit";
                } else if (currentDeadline.stopPreorder()){ //assume currentDeadline.stopPreorder() == true
                    Log.i("Preorder", "Time's up");
                    result = "time";
                } else if (isCancelled()){
                    Log.i("Preorder", "Cancelled");
                    result = "cancelled";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Preorder", "Ended for no reason");
            return result;
        }

        protected void onProgressUpdate(String... params){
            text_preordercount.setText(params[0]);
            text_timeleft.setText(params[1]);
        }

        @Override
        protected void onPostExecute(String param){
            if (param == "cancelled"){
                pre_main.setVisibility(View.VISIBLE);
                pre_prog.setVisibility(View.INVISIBLE);
                pre_time.setVisibility(View.INVISIBLE);
                pre_count.setVisibility(View.INVISIBLE);
            } else if (param == "limit"){
                pre_main.setVisibility(View.INVISIBLE);
                pre_prog.setVisibility(View.INVISIBLE);
                pre_time.setVisibility(View.INVISIBLE);
                pre_count.setVisibility(View.VISIBLE);
            } else if (param == "time"){
                pre_main.setVisibility(View.INVISIBLE);
                pre_prog.setVisibility(View.INVISIBLE);
                pre_time.setVisibility(View.VISIBLE);
                pre_count.setVisibility(View.INVISIBLE);
            } else {
                pre_main.setVisibility(View.VISIBLE);
                pre_prog.setVisibility(View.INVISIBLE);
                pre_time.setVisibility(View.INVISIBLE);
                pre_count.setVisibility(View.INVISIBLE);
            }
        }
    }


}
