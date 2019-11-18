package org.apache.cordova.firebase;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;

public class OnNotificationOpenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        PackageManager pm = context.getPackageManager();

        Intent launchIntent = pm.getLaunchIntentForPackage(context.getPackageName());
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle data = intent.getExtras();

        //if there is some input
        if (remoteInput != null) {
            CharSequence name = remoteInput.getCharSequence("inlineReply");
            data.putString("inlineReply", name.toString());
        } else {
            data.putBoolean("tap", true);
        }

        FirebasePlugin.sendNotification(data, context);

        launchIntent.putExtras(data);
        context.startActivity(launchIntent);
    }
}
