package com.yi.truecaller.number.locator.name.finder.Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class GPS_Tracker extends Service implements LocationListener {
    private static final long MIN_TIME_BW_UPDATES = 60000;
    private final Context context;
    private boolean getloction = false;
    private boolean gpsenabled = false;
    private double latitude;
    private Location location;
    protected LocationManager locationmanager;
    private double longitude;
    protected boolean networkenabled = false;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLocationChanged(Location location2) {
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public GPS_Tracker(Context context2) {
        this.context = context2;
        getLocation();
    }

    public Location getLocation() {
        try {
            LocationManager locationManager = (LocationManager) this.context.getSystemService(LOCATION_SERVICE);
            this.locationmanager = locationManager;
            this.gpsenabled = locationManager.isProviderEnabled("gps");
            boolean isProviderEnabled = this.locationmanager.isProviderEnabled("network");
            this.networkenabled = isProviderEnabled;
            if (!this.gpsenabled && !isProviderEnabled) {
                return this.location;
            }
            this.getloction = true;
            if (isProviderEnabled) {
                if (ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") != 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    return null;
                }
                this.locationmanager.requestLocationUpdates("network", 0, 0.0f, this);
                Log.d("Constant", "Constant");
                LocationManager locationManager2 = this.locationmanager;
                if (locationManager2 != null) {
                    Location lastKnownLocation = locationManager2.getLastKnownLocation("network");
                    this.location = lastKnownLocation;
                    if (lastKnownLocation != null) {
                        this.latitude = lastKnownLocation.getLatitude();
                        this.longitude = this.location.getLongitude();
                    }
                }
            }
            if (this.gpsenabled && this.location == null) {
                this.locationmanager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, 10.0f, this);
                Log.d("GPS Enabled", "GPS Enabled");
                LocationManager locationManager3 = this.locationmanager;
                if (locationManager3 != null) {
                    Location lastKnownLocation2 = locationManager3.getLastKnownLocation("gps");
                    this.location = lastKnownLocation2;
                    if (lastKnownLocation2 != null) {
                        this.latitude = lastKnownLocation2.getLatitude();
                        this.longitude = this.location.getLongitude();
                    }
                }
            }
            return this.location;
        } catch (Exception e) {
            e.printStackTrace();
            return this.location;
        }
    }

    public double getLatitude() {
        Location location2 = this.location;
        if (location2 != null) {
            this.latitude = location2.getLatitude();
        }
        return this.latitude;
    }

    public double getLongitude() {
        Location location2 = this.location;
        if (location2 != null) {
            this.longitude = location2.getLongitude();
        }
        return this.longitude;
    }

    public boolean canGetLocation() {
        return this.getloction;
    }

    public boolean isgpsenabled() {
        return this.gpsenabled;
    }
}
