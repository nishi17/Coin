package com.demo.demo_coin.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.demo_coin.MainActivity;
import com.demo.demo_coin.R;
import com.demo.demo_coin.database.DatabaseHandler;
import com.demo.demo_coin.retrofot.DataResponse;
import com.demo.demo_coin.retrofot.FavotiteResponse;
import com.demo.demo_coin.retrofot.INResponse;
import com.demo.demo_coin.retrofot.RestAdapter;
import com.demo.demo_coin.retrofot.RestServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by flexi_mac4 on 06/05/18.
 */

public class Favourtites extends Fragment implements View.OnClickListener {

    private Context context;
    private View view;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private TextView txtView_no_data;
    private ListViewAdapterFavourites adapterFavourites;
    private List<FavotiteResponse> favotiteResponseArrayList;

    public static Favourtites newInstance(int sectionNumber) {

        Favourtites fragment = new Favourtites();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    Handler ha;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favourites, container, false);

        context = getActivity();

        String myTag = getTag();

        ((MainActivity) getActivity()).setTab_favorites(myTag);
        txtView_no_data = (TextView) view.findViewById(R.id.txtView_no_data);

        recyclerView = (RecyclerView) view.findViewById(R.id.RV_favourites);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
    /*    recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);*/
        recyclerView.findViewHolderForAdapterPosition(container.getVerticalScrollbarPosition());
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refreshNearBy);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               /* if (statusCheck()) {
                    refreshLayout.setRefreshing(false);

                } else {
                    refreshLayout.setRefreshing(false);

                }*/
                CAllAPI();
                refreshLayout.setRefreshing(false);
            }
        });

        ha = new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function
                Log.e("service   ", "servivcee  ");

                CAllAPI();

                ha.postDelayed(this, 1000);
            }
        }, 1000);

        CAllAPI();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        //  CAllAPI();

    }


    public void CAllAPI() {
        if (isConnectingToInternet(context)) {


            List<Integer> integerList1 = retriveIdDatabase();

            Log.e("integerlist  ", String.valueOf(integerList1));

            favotiteResponseArrayList = new ArrayList<FavotiteResponse>();
            favotiteResponseArrayList.clear();

            for (int i = 0; i < integerList1.size(); i++) {

                final RestServices services = RestAdapter.getExStuffServices();
                int a = integerList1.get(i);
                String url = "https://api.coinmarketcap.com/v2/ticker/" + a + "//?convert=INR";

                Call<FavotiteResponse> call = services.callApi(url);
                call.enqueue(new Callback<FavotiteResponse>() {
                    @Override
                    public void onResponse(Call<FavotiteResponse> call, Response<FavotiteResponse> response) {


                        int statusCode = response.code();


                        Log.e("status code  ", String.valueOf(statusCode));

                        if (statusCode == 200) {


                            FavotiteResponse favotiteResponse = response.body();


                            favotiteResponseArrayList.add(favotiteResponse);


                            if (favotiteResponseArrayList.size() > 0) {
                                recyclerView.setVisibility(View.VISIBLE);
                                txtView_no_data.setVisibility(View.GONE);
                            } else {
                                txtView_no_data.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }

                            adapterFavourites = new ListViewAdapterFavourites(context, favotiteResponseArrayList);
                            recyclerView.setAdapter(adapterFavourites);


//                        List<DataResponse> dataResponse = new ArrayList<DataResponse>();
//                        dataResponse.add(favotiteResponse.getData());


                            Log.e("favotiteResponseArrayL", String.valueOf(favotiteResponseArrayList.size()));


                        }


                    }

                    @Override
                    public void onFailure(Call<FavotiteResponse> call, Throwable t) {

                        Log.e("exception  ", t.getMessage());
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

            }


        } else {
            Toast.makeText(context, "please connect to internate", Toast.LENGTH_LONG).show();
        }


    }

    private ArrayList<Integer> retriveIdDatabase() {

        ArrayList<Integer> stringList = new ArrayList<>();
        stringList.clear();

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

    @Override
    public void onClick(View view) {

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

    public class ListViewAdapterFavourites extends RecyclerView.Adapter<ListViewAdapterFavourites.MyViewHolder> {

        private Context context;
        private List<FavotiteResponse> favriteobject;


        public ListViewAdapterFavourites(Context context, List<FavotiteResponse> lisst) {


            this.favriteobject = lisst;
            this.context = context;

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lisview_favourtis, parent, false);
            context = parent.getContext();
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


            FavotiteResponse favotiteResponse = favriteobject.get(position);

            DataResponse dataResponse = favotiteResponse.getData();

            INResponse inResponse = dataResponse.getQuotes().getINR();

            String id = String.valueOf(dataResponse.getId());

            holder.tv_id.setText(id);
            holder.tv_name_main.setText(dataResponse.getName());
            holder.tv_symbol.setText("(" + dataResponse.getSymbol() + ")");
            if (inResponse.getPrice() != null)
                holder.tv_price.setText("Price : " + inResponse.getPrice());
            if (inResponse.getMarket_cap() != null)
                holder.tv_market_cap.setText("MarketCap : " + inResponse.getMarket_cap());
            if (inResponse.getVolume_24h() != null)
                holder.tv_volume_24h.setText("Volume 24h:  : " + inResponse.getVolume_24h());


            if (inResponse.getPercent_change_1h() != null) {

                holder.tv_percent_change_1h.setText("1h : " + inResponse.getPercent_change_1h() + "%");

                if (inResponse.getPercent_change_1h() < 0) {
                    holder.tv_percent_change_1h.setTextColor(getResources().getColor(R.color.red));
                } else {
                    holder.tv_percent_change_1h.setTextColor(getResources().getColor(R.color.green));
                }
            } else {
                holder.tv_percent_change_1h.setText("1h :  N/A");
            }


            if (inResponse.getPercent_change_24h() != null) {

                holder.tv_percent_change_24h.setText("24h : " + inResponse.getPercent_change_24h() + "%");

                if (inResponse.getPercent_change_24h() < 0) {
                    holder.tv_percent_change_24h.setTextColor(getResources().getColor(R.color.red));
                } else {
                    holder.tv_percent_change_24h.setTextColor(getResources().getColor(R.color.green));
                }
            } else {
                holder.tv_percent_change_24h.setText("24h :  N/A");
            }


            if (inResponse.getPercent_change_7d() != null) {

                holder.tv_percent_change_7d.setText("7d : " + inResponse.getPercent_change_7d() + "%");

                if (inResponse.getPercent_change_7d() < 0) {
                    holder.tv_percent_change_7d.setTextColor(getResources().getColor(R.color.red));
                } else {
                    holder.tv_percent_change_7d.setTextColor(getResources().getColor(R.color.green));
                }
            } else {
                holder.tv_percent_change_7d.setText("7d :  N/A");
            }



/*


            if (inResponse.getPercent_change_7d() < 0) {
                holder.tv_percent_change_7d.setTextColor(getResources().getColor(R.color.red));
            }
*/

        }

        @Override
        public int getItemCount() {
            Log.e("size in adapter ", String.valueOf(favriteobject.size()));

            return favriteobject.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView iv_image;

            public TextView tv_id, tv_name_main, tv_symbol, tv_price, tv_market_cap, tv_volume_24h, tv_percent_change_1h,
                    tv_percent_change_24h, tv_percent_change_7d;

            public MyViewHolder(View view) {
                super(view);

                tv_id = (TextView) view.findViewById(R.id.tv_id);
                tv_name_main = (TextView) view.findViewById(R.id.tv_name_main);
                tv_symbol = (TextView) view.findViewById(R.id.tv_symbol);
                tv_price = (TextView) view.findViewById(R.id.tv_price);
                tv_market_cap = (TextView) view.findViewById(R.id.tv_market_cap);
                tv_volume_24h = (TextView) view.findViewById(R.id.tv_volume_24h);
                tv_percent_change_1h = (TextView) view.findViewById(R.id.tv_percent_change_1h);
                tv_percent_change_24h = (TextView) view.findViewById(R.id.tv_percent_change_24h);
                tv_percent_change_7d = (TextView) view.findViewById(R.id.tv_percent_change_7d);

                iv_image = (ImageView) view.findViewById(R.id.iv_image);


            }
        }
    }
}
