package com.jem.fq;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jem.fq.base.TabActivity;
import com.jem.fq.preorder.PreorderMain;
import com.jem.fq.recycler.ListItem;
import com.jem.fq.recycler.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

// TAKE NOTE:
//  CHECK THE DEPENDENCIES IN THE GRADLE (APP) AND ADD ***NECESSARY DEPENDENCIES*** SUCH THAT RECYCLER VIEW WORKS


//TODO: follow 'theme' of the other activities
//TODO: implement the 'buttons' to switch between activities'
//TODO: database -> queue (from database put into the arraylist params)

//ArrayList.clear() old list, and add new arraylist from database everytime you click the button

public class QueueMain extends TabActivity implements AsyncResponse {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ListItem> listItems;
    private int GET_DATA = 0;
    private String SERVER = "https://fastqueue.000webhostapp.com/store/chinese/uncollectedfood.php";

    private static final String URL_DATA = "";

    //SwipeRefreshLayout swipeLayout;

    //DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
    //SwipeRefreshLayout swipeRefreshLayout;


    private static final String Tag = "Main Activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_queue_main);
        fqTab = findViewById(R.id.fqTab);
        tabIni();
        fqTab.addOnTabSelectedListener(onFqTabSelectedListener);
        mRecyclerView = findViewById(R.id.recyclerv_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getJSON(SERVER);

    }

    protected void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            //            ProgressDialog dialog;
            AsyncResponse delegate = null;

            @Override
            protected void onPreExecute() {
//                dialog = getProgressDialog(getString(R.string.loading_store_list));
//                dialog.show();
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
                Log.d("GETJSON", s);
                delegate.processFinish(GET_DATA, s);


            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }

        GetJSON getJSONtask = new GetJSON();
        getJSONtask.delegate = (AsyncResponse) mActivity;
        getJSONtask.execute();
    }


    private void loadRecyclerViewData(String json) {

        try {
            JSONArray array = new JSONArray(json);
            listItems = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {

                JSONObject o = array.getJSONObject(i);
                ListItem item = new ListItem(o.getString("food_name"),o.getString("customer_id"),o.getString("order_id"),o.getString("takeaway"),o.getString("preorder"));
                listItems.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter = new RecyclerViewAdapter(listItems, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void processFinish(int request, String result) {
        if(request == GET_DATA){
            if(result.length()>0){
                loadRecyclerViewData(result);
            }
        }

    }

}