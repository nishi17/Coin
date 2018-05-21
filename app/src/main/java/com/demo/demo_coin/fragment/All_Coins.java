package com.demo.demo_coin.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.demo_coin.MainActivity;
import com.demo.demo_coin.R;
import com.demo.demo_coin.database.DatabaseHandler;
import com.demo.demo_coin.retrofot.DataResponse;
import com.demo.demo_coin.retrofot.RestAdapter;
import com.demo.demo_coin.retrofot.RestServices;
import com.demo.demo_coin.retrofot.USDresponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by flexi_mac4 on 06/05/18.
 */

public class All_Coins extends Fragment implements View.OnClickListener {

    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListViewAdapterAllCoins adapterAllCoins;
    private TextView txtView_no_data;

    public static All_Coins newInstance(int sectionNumber) {
        All_Coins fragment = new All_Coins();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_allcoins, container, false);
        context = getActivity();
        String myTag = getTag();
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        ((MainActivity) getActivity()).setTab_allcoins(myTag);

        txtView_no_data = (TextView) view.findViewById(R.id.txtView);
        recyclerView = (RecyclerView) view.findViewById(R.id.RV_allcoins);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.findViewHolderForAdapterPosition(container.getVerticalScrollbarPosition());
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refreshNearBy);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*if (statusCheck()) {
                    refreshLayout.setRefreshing(false);

                } else {
                    refreshLayout.setRefreshing(false);

                }*/
                CAllAPI();
                refreshLayout.setRefreshing(false);
            }
        });

       /* final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function
                Log.e("service   ", "servivcee  ");
                CAllAPI();
                ha.postDelayed(this, 10000);
            }
        }, 10000);*/

        CAllAPI();
        return view;

    }

    List<DataResponse> dataResponse;

    public void CAllAPI() {
        if (isConnectingToInternet(context)) {


            final RestServices services = RestAdapter.getExStuffServices();
            Call<JsonObject> call = services.AllCoins();
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    int statusCode = response.code();


                    // Log.e("status code all coins ", String.valueOf(statusCode));
                    if (statusCode == 200) {


                        try {


                            JsonObject json = response.body();

                            String s = new Gson().toJson(json);

                            JSONObject jsonObject1 = new JSONObject(s);


                            JSONObject jsonObject = jsonObject1.getJSONObject("data");

                            //   JSONArray namearray = jsonObject.names();
                            dataResponse = new ArrayList<DataResponse>();

                            Iterator<String> iter = jsonObject.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();

                                Object value = jsonObject.get(key);

                                String s1 = value.toString();

                                //    Log.e("valueeee all ", new Gson().toJson(value));


                                Gson gson1 = new Gson();
                                DataResponse categoryByCustomer = gson1.fromJson(s1, DataResponse.class);


                                dataResponse.add(categoryByCustomer);

                            }

                            //  Log.e("dataResponse size", String.valueOf(dataResponse.size()));

                        } catch (Exception e) {

                            //  Log.e("proble    ", e.getMessage());
                            e.printStackTrace();
                        }


                        if (dataResponse.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            txtView_no_data.setVisibility(View.GONE);
                        } else {
                            txtView_no_data.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }


                        adapterAllCoins = new ListViewAdapterAllCoins(context, dataResponse);

                        recyclerView.setAdapter(adapterAllCoins);

                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    //  Log.e("exception allcoins ", t.getMessage());
                    Toast.makeText(context, "all coins" + t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });


        } else {
            Toast.makeText(context, "please connect to internate", Toast.LENGTH_LONG).show();
        }


    }


    public static boolean isConnectingToInternet(Context mContext) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }

            }

        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    class ListViewAdapterAllCoins extends RecyclerView.Adapter<ListViewAdapterAllCoins.MyViewHolder> {

        private Context context;
        List<DataResponse> favriteobject;


        public ListViewAdapterAllCoins(Context context, List<DataResponse> flyerList) {

            this.context = context;
            this.favriteobject = flyerList;


        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lisview_all_coins, parent, false);
            context = parent.getContext();
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ListViewAdapterAllCoins.MyViewHolder holder, int position) {


            final DataResponse dataResponse = favriteobject.get(position);


            USDresponse usd = dataResponse.getQuotes().getUSD();

            final String id = String.valueOf(dataResponse.getId());

            holder.tv_id.setText(id);
            holder.tv_name_main.setText(dataResponse.getName());
            holder.tv_symbol.setText("(" + dataResponse.getSymbol() + ")");
            holder.tv_price.setText("Price : $ " + usd.getPrice());
            holder.tv_market_cap.setText("MarketCap : $ " + usd.getMarket_cap());
            holder.tv_percent_change_24h.setText("24h : " + usd.getPercent_change_24h() + " %");

            if (usd.getPercent_change_24h() != null) {

                if (usd.getPercent_change_24h() < 0) {
                    holder.tv_percent_change_24h.setTextColor(getResources().getColor(R.color.red));
                } else {
                    holder.tv_percent_change_24h.setTextColor(getResources().getColor(R.color.green));
                }

            } else {
                holder.tv_percent_change_24h.setText("24h :  N/A");
            }

            ArrayList<Integer> integerList1 = retriveIdDatabase();

            Iterator itr = integerList1.iterator();
            while (itr.hasNext()) {

                int a = (int) itr.next();

                if (a == dataResponse.getId()) {

                    holder.checkbox_meat.setChecked(true);

                }

            }


            holder.checkbox_meat.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View view) {
                    if (((CheckBox) view).isChecked()) {
                        Toast.makeText(context, "favourites:)", Toast.LENGTH_SHORT).show();

                        if (!isExist(id))
                            insertIddatabase(id, dataResponse.getName());

                    } else {
                        Toast.makeText(context, "Bro,uncheck:)", Toast.LENGTH_SHORT).show();

                        removeIdIndatabse(id);
                    }
                }
            });
        }

        private boolean isExist(String id) {
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            String query = " SELECT " + DatabaseHandler.C_COIN_ID + " FROM "
                    + DatabaseHandler.TABLE_COIN + " WHERE " +
                    DatabaseHandler.C_COIN_ID + " = '" + id + "';";

            SQLiteDatabase helper = databaseHandler.getReadableDatabase();

            Cursor cursor = helper.rawQuery(query, null);

            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            } else {
                cursor.close();
                return true;
            }

        }

        private ArrayList<Integer> retriveIdDatabase() {

            ArrayList<Integer> stringList = new ArrayList<>();

            DatabaseHandler databaseHandler = new DatabaseHandler(context);

            SQLiteDatabase database = databaseHandler.getReadableDatabase();

            String[] Colums = {DatabaseHandler.C_COIN_ID,
                    DatabaseHandler.C_NAME};

            Cursor cursor = database.query
                    (DatabaseHandler.TABLE_COIN, Colums, null, null, null, null, null);

            if (cursor.getCount() >= 0) {
                if (cursor.moveToFirst()) {
                    do {

                        String id = cursor.getString
                                (cursor.getColumnIndex(DatabaseHandler.C_COIN_ID));

                        String name = cursor.getString
                                (cursor.getColumnIndex(DatabaseHandler.C_NAME));

                        stringList.add(Integer.valueOf(id));
                    } while (cursor.moveToNext());

                } else {

                }

            }

            return stringList;
        }


        private void removeIdIndatabse(String id) {
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            SQLiteDatabase helper = databaseHandler.getWritableDatabase();

            helper.execSQL("DELETE FROM " + DatabaseHandler.TABLE_COIN + " WHERE " + DatabaseHandler.C_COIN_ID + "= '" + id + "'");
        }

        private void insertIddatabase(String id, String name) {

            try {

                DatabaseHandler databaseHandler = new DatabaseHandler(context);
                SQLiteDatabase helper = databaseHandler.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHandler.C_COIN_ID, id);
                contentValues.put(DatabaseHandler.C_NAME, name);

                helper.insert(DatabaseHandler.TABLE_COIN, null, contentValues);


            } catch (SQLException e) {

            }


        }

        @Override
        public int getItemCount() {
            return favriteobject.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView iv_image;

            public TextView tv_id, tv_name_main, tv_symbol, tv_percent_change_24h, tv_price, tv_market_cap;

            public CheckBox checkbox_meat;


            public MyViewHolder(View view) {
                super(view);


                tv_id = (TextView) view.findViewById(R.id.tv_id);

                tv_name_main = (TextView) view.findViewById(R.id.tv_name_main);


                tv_symbol = (TextView) view.findViewById(R.id.tv_symbol);
                tv_percent_change_24h = (TextView) view.findViewById(R.id.tv_percent_change_24h);

                tv_price = (TextView) view.findViewById(R.id.tv_price);
                tv_market_cap = (TextView) view.findViewById(R.id.tv_market_cap);

                iv_image = (ImageView) view.findViewById(R.id.iv_image);

                checkbox_meat = (CheckBox) view.findViewById(R.id.checkbox_meat);

                //cardView = (CardView) view.findViewById(R.id.card);

            }
        }


        public void onCheckboxClicked(View view) {
            // Is the view now checked?
            boolean checked = ((CheckBox) view).isChecked();

            // Check which checkbox was clicked
            switch (view.getId()) {
                case R.id.checkbox_meat:
                    if (checked) {

                    }
                    // Put some meat on the sandwich
                    else
                        // Remove the meat
                        break;

            }


        }


    }


}
