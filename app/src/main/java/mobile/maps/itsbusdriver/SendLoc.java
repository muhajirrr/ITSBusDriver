package mobile.maps.itsbusdriver;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendLoc extends Service {

    Handler handler = new Handler();
    Runnable runnable;
    Location location;
    double latitude, longitude;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final GPSTracker tracker = new GPSTracker(getBaseContext());

        runnable = new Runnable() {
            @Override
            public void run() {
                location = tracker.getLocation();

                latitude = location.getLatitude();
                longitude = location.getLongitude();

                mDatabase.child("driver1").child("latitude").setValue(latitude);
                mDatabase.child("driver1").child("longitude").setValue(longitude);

                Toast.makeText(getBaseContext(), "Sending location to server\nLat: "+latitude+"\nlng: "+longitude,
                        Toast.LENGTH_SHORT).show();

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
    }
}