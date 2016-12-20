package uet.tiendd.com.ibsproject;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;

/**
 * Created by duyti on 11/6/2016.
 */
public class MapsActivity extends FragmentActivity implements IALocationListener {
    private GoogleMap mMap;
    private Marker mMarker;
    private IALocationManager mIALocationManager;
    private final int CODE_PERMISSIONS = 1;//...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        String[] neededPermissions = {
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, neededPermissions, CODE_PERMISSIONS);
        mIALocationManager = IALocationManager.create(this);
    }

    @Override
    public void onLocationChanged(IALocation location) {
        if(mMarker != null) {
            mMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        Toast.makeText(MapsActivity.this, "Latitude: " + location.getLatitude()
                + "\n" + "Longitude: " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        mIALocationManager.requestLocationUpdates(IALocationRequest.create(), this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIALocationManager.removeLocationUpdates(this);
    }

    @Override
    protected void onDestroy() {
        mIALocationManager.destroy();
        super.onDestroy();
    }
    // ...
}