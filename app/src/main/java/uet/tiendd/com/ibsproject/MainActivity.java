package uet.tiendd.com.ibsproject;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;

/**
 * Created by duyti on 11/6/2016.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LOCATION";
    private final int CODE_PERMISSIONS = 1;//...
    private IALocationManager mIALocationManager;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Handle if any of the permissions are denied, in grantResults
    }

    private IALocationListener mIALocationListener = new IALocationListener() {

        // Called when the location has changed.
        @Override
        public void onLocationChanged(IALocation location) {
            Toast.makeText(MainActivity.this, "Latitude: " + location.getLatitude()
                    + "\n" + "Longitude: " + location.getLongitude(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] neededPermissions = {
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, neededPermissions, CODE_PERMISSIONS);
        mIALocationManager = IALocationManager.create(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIALocationManager.requestLocationUpdates(IALocationRequest.create(), mIALocationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIALocationManager.removeLocationUpdates(mIALocationListener);
    }

    @Override
    protected void onDestroy() {
        mIALocationManager.destroy();
        super.onDestroy();
    }
}
