package mobile.maps.itsbusdriver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendLoc extends Service {

    Handler handler = new Handler();
    Runnable runnable;
    Location location;
    double latitude, longitude;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    String key;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        key = intent.getStringExtra("key");

        // Create push notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setTicker(getString(R.string.app_name))
                        .setSmallIcon(R.mipmap.ic_notification)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("Location tracking activated by driver"+key)
                        .setAutoCancel(false)
                        .setOngoing(true)
                        .setShowWhen(false)
                        .setUsesChronometer(false);

        Intent mIntent = new Intent(this, MapsActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MapsActivity.class);
        stackBuilder.addNextIntent(mIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());

        runnable = new Runnable() {
            @Override
            public void run() {
                GPSTracker tracker = new GPSTracker(getBaseContext());
                location = tracker.getLocation();

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    mDatabase.child("driver"+key).child("latitude").setValue(latitude);
                    mDatabase.child("driver"+key).child("longitude").setValue(longitude);
                }
                handler.postDelayed(this, 500);
            }
        };

        handler.post(runnable);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(runnable);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}