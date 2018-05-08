package com.demo.demo_coin.sevices;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by flexi_mac4 on 06/05/18.
 */

public class BackgroundMethod extends Service {

    private Context context;
    // Binder given to clients
    private final IBinder binder = new LocalBinder();
    // Registered callbacks


    // Class used for the client Binder.
    public class LocalBinder extends Binder {
        BackgroundMethod getService() {
            // Return this instance of MyService so clients can call public methods
            return BackgroundMethod.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        Log.e("service   ", "servivcee  ");
        stopSelf();

    }
}
