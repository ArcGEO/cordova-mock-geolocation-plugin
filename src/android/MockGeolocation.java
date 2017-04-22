package sk.arcgeo.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.location.*;
import android.content.*;
import android.widget.*;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

public class MockGeolocation extends CordovaPlugin {
    private Context mContext;
    //private CordovaLocationListener mListener;
    private LocationManager locMgr;

    LocationManager getLocationManager() {
        return locMgr;
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        locMgr = (LocationManager) cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);

        locMgr.addTestProvider (LocationManager.GPS_PROVIDER,
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
                null,System.currentTimeMillis());
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        //Log.i("action", action);
        if (action == null || !action.matches("mock|setMock")) {
            return false;
        }
        if (action.equals("setMock")) {
            double latitude = Double.valueOf(data.getString(0));
            double longitude = Double.valueOf(data.getString(1));
            float accuracy = Float.valueOf(data.getString(2));
            float altitude = Float.valueOf(data.getString(3));
            return setMock(latitude, longitude, accuracy, altitude);
        }
        else {
            return false;
        }
    }

    public boolean setMock(Double latitude, Double longitude, Float accuracy, Float altitude) {

        Location newLocation = new Location(LocationManager.GPS_PROVIDER);

        /*newLocation.setLatitude(-26.902038);  // double
        newLocation.setLongitude(-48.671337);
        newLocation.setAccuracy(1);
        newLocation.setAltitude(0);
        newLocation.setAccuracy(500);*/
        newLocation.setLatitude(latitude);  // double
        newLocation.setLongitude(longitude);
        newLocation.setAccuracy(accuracy);
        newLocation.setAltitude(altitude);
        newLocation.setTime(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }

        locMgr.setTestProviderLocation(LocationManager.GPS_PROVIDER, newLocation);

        return true;
    }
}