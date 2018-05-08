package com.demo.demo_coin.fragment;

import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.demo_coin.MainActivity;
import com.demo.demo_coin.R;
import com.demo.demo_coin.retrofot.FavotiteResponse;
import com.demo.demo_coin.retrofot.RestAdapter;
import com.demo.demo_coin.retrofot.RestServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        ((MainActivity) getActivity()).setTab_allcoins(myTag);


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


    public void CAllAPI() {
        if (isConnectingToInternet(context)) {


            final RestServices services = RestAdapter.getExStuffServices();
            Call<FavotiteResponse> call = services.AllCoins();
            call.enqueue(new Callback<FavotiteResponse>() {
                @Override
                public void onResponse(Call<FavotiteResponse> call, Response<FavotiteResponse> response) {


                    int statusCode = response.code();


                    Log.e("status code all coins ", String.valueOf(statusCode));
                    if (statusCode == 200) {


                        try {


                            
                            JSONObject jsonObject = new JSONObject("id");

                            JSONArray namearray = jsonObject.names();

                            Log.e("addas" ,"zbvgdzf");

                        } catch (JSONException e) {

                            Log.e("proble    "   , e.getMessage());
                            e.printStackTrace();
                        }

                        FavotiteResponse favotiteResponse = response.body();
                        adapterAllCoins = new ListViewAdapterAllCoins(context, favotiteResponse);

                        recyclerView.setAdapter(adapterAllCoins);

                    }


                }

                @Override
                public void onFailure(Call<FavotiteResponse> call, Throwable t) {

                    Log.e("exception allcoins ", t.getMessage());
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
        FavotiteResponse favriteobject;


        public ListViewAdapterAllCoins(Context context, FavotiteResponse flyerList) {

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

        }

        @Override
        public int getItemCount() {
            return 0;
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
