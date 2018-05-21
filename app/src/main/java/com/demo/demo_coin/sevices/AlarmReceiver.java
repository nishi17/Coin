
package com.demo.demo_coin.sevices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by flexi_mac4 on 06/05/18. by nishi
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundMethod.class);
        context.startService(background);
    }
}
