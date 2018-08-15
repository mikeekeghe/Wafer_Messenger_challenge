package com.mike_ekeghe.wafer_messenger_challenge;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.mike_ekeghe.wafer_messenger_challenge.utilities.CountryListAdapter;
import com.mike_ekeghe.wafer_messenger_challenge.utilities.CustomAdapter;
import com.mike_ekeghe.wafer_messenger_challenge.utilities.Item;
import com.mike_ekeghe.wafer_messenger_challenge.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MAIN";

    ArrayList<String> country_name = new ArrayList<>();
    ArrayList<String> arrL_currency = new ArrayList<>();
    ArrayList<String> language = new ArrayList<>();
    private ArrayList<Item> cartList;
    private String globalSearchResult, myOwnDisplayName;
    private ArrayList<String> lang_array;
    private CountryListAdapter mAdapter;
    private ConstraintLayout coordinatorLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        cartList = new ArrayList<>();
        mAdapter = new CountryListAdapter(this, cartList);
        Log.d(TAG, "before makewaferSearchQuery");

        makewaferUsFetchQuery();

        Log.d(TAG, "after makewaferSearchQuery");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

              ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);

    }

    private void makewaferUsFetchQuery() {
        final URL waferSearchUrl = NetworkUtils.buildCountryListUrl();
       Log.d(TAG, "waferSearchUrl is: " + waferSearchUrl.toString());
        // COMPLETED (4) Create a new waferQueryTask and call its execute method, passing in the url to query
       // new waferQueryTask().execute(waferSearchUrl);
        new Thread(new Runnable() {
            public void run() {
                new waferQueryTask().execute(waferSearchUrl);
            }
        }).start();
    }

    private void loadResultView() {
        // get the reference of RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        String strResult = cleanStr(globalSearchResultMethod());


        strResult= " {\"Countries\": " + strResult + "}";
        Log.d(TAG,"strResult_2 is :"+ strResult);

            // get JSONObject from JSON file
        JSONObject obj = null;
        try {
            obj = new JSONObject(strResult);

        // fetch JSONArray named Countrys
            JSONArray CountrysArray = obj.getJSONArray("Countries");
            // implement for loop for getting Countrys list data
            for (int i = 0; i < CountrysArray.length(); i++) {
                // create a JSONObject for fetching single Country data
                JSONObject srDetail = CountrysArray.getJSONObject(i);
                // fetch email and name and store it in arraylist

                country_name.add(srDetail.getString("name"));
                //Log.d(TAG, "country_name is: " + country_name);
      /*          String c = obj.getString("Countries");
                JSONArray jArray = new JSONArray(c);*/
                String strArrCur = srDetail.getString("currencies");
                JSONArray codeArray = new JSONArray(strArrCur);
                arrL_currency.add( codeArray.getJSONObject(0).getString("name"));
                Log.d(TAG, "CURRENCY strCur is: " + arrL_currency);
                String strArrLan = srDetail.getString("languages");
                JSONArray lnArray = new JSONArray(strArrLan);
                language.add( lnArray.getJSONObject(0).getString("name"));
                Log.d(TAG, "LANGUAGE strCur is: " + language);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, country_name,
                arrL_currency,
                language
        );
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

    }

    private String globalSearchResultMethod() {
        return globalSearchResult;
    }

    private String cleanStr(String globalSearchResult) {
        globalSearchResult = globalSearchResult.replaceAll("[^\\x20-\\x7e]", "");
        globalSearchResult = globalSearchResult.replace("&lsquo;", "");
        globalSearchResult = globalSearchResult.replace("&amp;", "");
        globalSearchResult = globalSearchResult.replace("&rsquo;", "");
        globalSearchResult = globalSearchResult.replace("&bull;", "");
        globalSearchResult = globalSearchResult.replace("&#39;", "");
        globalSearchResult = globalSearchResult.replace("&nbsp;", "");
        globalSearchResult = globalSearchResult.replace("&quot;", "");

        return globalSearchResult;
    }

    public class waferQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String waferSearchResults = null;
            try {
                waferSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                waferSearchResults = cleanStr(waferSearchResults);

                Log.d(TAG, "waferSearchResults is : " + waferSearchResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return waferSearchResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results
        @Override
        protected void onPostExecute(String waferSearchResults) {
            if (waferSearchResults != null && !waferSearchResults.equals("")) {
                Log.d(TAG, "waferSearchResults is :" + waferSearchResults);
                globalSearchResult = waferSearchResults;
                loadResultView();
            }
        }
    }


    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CustomAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = cartList.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

        }
    }
}
