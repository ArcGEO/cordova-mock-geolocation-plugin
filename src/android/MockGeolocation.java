package sk.arcgeo.plugin;

import android.Manifest;
import android.content.*;
import android.location.*;
import android.os.Build;
import android.os.SystemClock;
import android.support.*;
import android.util.Log;
import android.widget.*;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class MockGeolocation extends CordovaPlugin {
    private Context mContext;
    //private CordovaLocationListener mListener;
    private LocationManager locMgr;
    private static final String TAG = "CordovaMockGeolocationPlugin";

    LocationManager getLocationManager() {
        return locMgr;
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        locMgr = (LocationManager) cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);

        locMgr.addTestProvider(LocationManager.GPS_PROVIDER,
                "requiresNetwork" == "",
                "requiresSatellite" == "",
                "requiresCell" == "",
                "hasMonetaryCost" == "",
                "supportsAltitude" == "",
                "supportsSpeed" == "",
                "supportsBearing" == "",
                android.location.Criteria.POWER_LOW,
                android.location.Criteria.ACCURACY_FINE);

        locMgr.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);

        locMgr.setTestProviderStatus(LocationManager.GPS_PROVIDER,
                LocationProvider.AVAILABLE,
                null, System.currentTimeMillis());

        if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            cordova.requestPermissions(this, 0, permissions);
        }
    }

    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) throws JSONException {
        Log.v(TAG, "Action: " + action);

        if (action == null || !action.matches("mock|setMock")) {
            return false;
        }
        if (action.equals("setMock")) {
            final double latitude = Double.valueOf(data.getString(0));
            final double longitude = Double.valueOf(data.getString(1));
            final float accuracy = Float.valueOf(data.getString(2));
            final float altitude = Float.valueOf(data.getString(3));

            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    setMock(latitude, longitude, accuracy, altitude);
                    callbackContext.success(); // Thread-safe.
                }
            });

            return true;
        } else {
            return false;
        }
    }

    public void setMock(Double latitude, Double longitude, Float accuracy, Float altitude) {

        Location newLocation = new Location(LocationManager.GPS_PROVIDER);

        newLocation.setLatitude(latitude);
        newLocation.setLongitude(longitude);
        newLocation.setAccuracy(accuracy);
        newLocation.setAltitude(altitude);
        newLocation.setTime(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }

        Log.v(TAG, "Location to mock " + newLocation.toString());

        try {
            locMgr.setTestProviderLocation(LocationManager.GPS_PROVIDER, newLocation);
        } catch (SecurityException e) {
            Log.e(TAG, "Exception: " + Log.getStackTraceString(e));
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Exception: " + Log.getStackTraceString(e));
        } finally {
            Log.v(TAG, "LastLocation: " + locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }
        Log.v(TAG, "LastLocation: " + locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER));
    }
}