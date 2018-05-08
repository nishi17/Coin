package com.demo.demo_coin;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.demo_coin.fragment.All_Coins;
import com.demo.demo_coin.fragment.Favourtites;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {


    private Context mContext;

    private TabLayout tabLayout;

    private ViewPager pager;
    private Pager adapter;

    private String Tab_allcoins, Tab_favorites;

    public String getTab_allcoins() {
        return Tab_allcoins;
    }

    public void setTab_allcoins(String tab_allcoins) {
        Tab_allcoins = tab_allcoins;
    }

    public String getTab_favorites() {
        return Tab_favorites;
    }

    public void setTab_favorites(String tab_favorites) {
        Tab_favorites = tab_favorites;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        pager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("My Flyer"));
        tabLayout.addTab(tabLayout.newTab().setText("Nearby Flyer"));

        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.white));

        tabLayout.setupWithViewPager(pager);

        adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        pager.setAdapter(adapter);


      /*  Intent alarm = new Intent(mContext, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(mContext, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 10000, pendingIntent);*//* /10 min*//*
        }
*/

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {

    }

    public void mEthodCAllService() {

        String myFav = getTab_favorites();

        Favourtites Favourtites = (Favourtites) MainActivity.this.getSupportFragmentManager().findFragmentByTag(myFav);
        Favourtites.CAllAPI();


    }

    //Freagmnet cretae
    public class Pager extends FragmentPagerAdapter {

        int tabCount;

        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return All_Coins.newInstance(position);
                case 1:
                    return Favourtites.newInstance(position);

                default:
                    return null;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);

            }
            return null;
        }


        /*@Override
        public int getItemPosition(Object object) {
            return POSITION_UNCHANGED;
        }*/

        @Override
        public int getCount() {
            return tabCount;
        }

    }
}

