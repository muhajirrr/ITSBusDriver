package mobile.maps.itsbusdriver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendLoc extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    mDatabase.child("driver1").child("latitude").setValue(latitude);
                    mDatabase.child("driver1").child("longitude").setValue(longitude);

                    Toast.makeText(context, "Sending location to server\nLat: "+latitude+"\nlng: "+longitude,
                            Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                mDatabase.child("driver1").child("latitude").setValue(latitude);
                mDatabase.child("driver1").child("longitude").setValue(longitude);

                Toast.makeText(context, "Sending location to server\nLat: "+latitude+"\nlng: "+longitude,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}