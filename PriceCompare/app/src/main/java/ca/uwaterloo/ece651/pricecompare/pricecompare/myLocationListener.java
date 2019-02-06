package ca.uwaterloo.ece651.pricecompare.pricecompare;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class myLocationListener implements LocationListener {
    private double lat, lng;

    myLocationListener() {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null){
            lat = location.getLatitude();
            lng = location.getLongitude();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public double[] getGPS() {
        return new double[]{lat, lng};
    }
}

