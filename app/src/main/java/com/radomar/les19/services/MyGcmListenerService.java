package com.radomar.les19.services;

import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.radomar.les19.MainActivity;
import com.radomar.les19.R;
import com.radomar.les19.db.DatabaseHelper;


/**
 * Created by Radomar on 9/09/2015
 */
public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String _from, Bundle _data) {
        Log.d(MainActivity.TAG, "Got message: " + _data.getString("message"));


        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.saveNotification(_data.getString("title"), _data.getString("subtitle"), _data.getString("message"));

        showNotification(getApplicationContext(), _data);

    }

    public void showNotification(Context _context, final Bundle _bundle) {
        String message = _bundle.getString("message");
        String title = _bundle.getString("title");
        String subtitle = _bundle.getString("subtitle");
        String tickerText = _bundle.getString("tickerText");
        int vibrate = Integer.valueOf(_bundle.getString("vibrate"));
        int sound = Integer.valueOf(_bundle.getString("sound"));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(message)
                .setTicker(tickerText)
                .setAutoCancel(true);

        if (vibrate == 1) builder.setVibrate(new long[]{0, 100, 200, 300});
        if (sound == 1) builder.setSound(Uri.parse("android.resource://" + _context.getPackageName() + "/" + R.raw.smb_jump_small));

        NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1234, builder.build());
    }

}
