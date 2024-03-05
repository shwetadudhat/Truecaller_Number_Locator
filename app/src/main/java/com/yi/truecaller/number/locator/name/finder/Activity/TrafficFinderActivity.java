package com.yi.truecaller.number.locator.name.finder.Activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.yi.truecaller.number.locator.name.finder.R;
import com.yi.truecaller.number.locator.name.finder.Utils.Constant;
import com.yi.truecaller.number.locator.name.finder.Utils.Permission_Utils;

public class TrafficFinderActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback
{

    private CheckBox cb_buildingscheckbox;
    private CheckBox cb_indoorcheckbox;
    private CheckBox cb_mylocationcheckbox;
    private CheckBox cb_trafficcheckbox;
    public GoogleMap googleMap;
    private boolean showpermissiondialog = false;
    public Spinner spinner;

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.trafficfinder_activity);

        spinner = (Spinner) findViewById(R.id.layers_spinner);
        ArrayAdapter<CharSequence> createFromResource = ArrayAdapter.createFromResource(this, R.array.layers_array, 17367048);
        createFromResource.setDropDownViewResource(17367049);
        this.spinner.setAdapter(createFromResource);
        this.spinner.setOnItemSelectedListener(this);
        this.cb_trafficcheckbox = (CheckBox) findViewById(R.id.traffic);
        this.cb_mylocationcheckbox = (CheckBox) findViewById(R.id.my_location);
        this.cb_buildingscheckbox = (CheckBox) findViewById(R.id.buildings);
        this.cb_indoorcheckbox = (CheckBox) findViewById(R.id.indoor);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }
    public void onMapReady(GoogleMap googleMap2) {
        this.googleMap = googleMap2;
        updateMapType();
        updateTraffic();
        updateMyLocation();
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.googleMap.setMyLocationEnabled(true);
            this.googleMap.setOnMyLocationChangeListener(myLocationChangeListener());
        }
        String str = (String) this.spinner.getSelectedItem();
        if (str.equals(getString(R.string.normal))) {
            this.googleMap.setMapType(1);
        } else if (str.equals(getString(R.string.hybrid))) {
            this.googleMap.setMapType(4);
        } else if (str.equals(getString(R.string.satellite))) {
            this.googleMap.setMapType(2);
        } else if (str.equals(getString(R.string.terrain))) {
            this.googleMap.setMapType(3);
        } else {
            Log.i("TAG", "Error setting layer with name " + str);
        }
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener() {
        return new GoogleMap.OnMyLocationChangeListener() {
            public void onMyLocationChange(Location location) {
                TrafficFinderActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16.0f));
            }
        };
    }

    private boolean checkReady() {
        if (this.googleMap != null) {
            return true;
        }
        Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
        return false;
    }

    public void onTrafficToggled(View view) {
        updateTraffic();
    }

    private void updateTraffic() {
        if (checkReady()) {
            this.googleMap.setTrafficEnabled(this.cb_trafficcheckbox.isChecked());
        }
    }

    public void onMyLocationToggled(View view) {
        updateMyLocation();
    }

    private void updateMyLocation() {
        if (!checkReady()) {
            return;
        }
        if (this.cb_mylocationcheckbox.isChecked()) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                this.googleMap.setMyLocationEnabled(true);
                return;
            }
            this.cb_mylocationcheckbox.setChecked(false);
            Permission_Utils.requestPermission(this, 1, "android.permission.ACCESS_FINE_LOCATION", false);
        } else if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.googleMap.setMyLocationEnabled(false);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 1) {
            return;
        }
        if (!Permission_Utils.isPermissionGranted(strArr, iArr, "android.permission.ACCESS_FINE_LOCATION")) {
            this.showpermissiondialog = true;
        } else if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.googleMap.setMyLocationEnabled(true);
            this.cb_mylocationcheckbox.setChecked(true);
        } else {
            Constant.isNetworkAvailable(getApplicationContext());
            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        }
    }

    public void onResumeFragments() {
        super.onResumeFragments();
        if (this.showpermissiondialog) {
            Permission_Utils.PermissionDeniedDialog.newInstance(false).show(getSupportFragmentManager(), "dialog");
            this.showpermissiondialog = false;
        }
    }

    public void onBuildingsToggled(View view) {
        updateBuildings();
    }

    private void updateBuildings() {
        if (checkReady()) {
            this.googleMap.setBuildingsEnabled(this.cb_buildingscheckbox.isChecked());
        }
    }

    public void onIndoorToggled(View view) {
        updateIndoor();
    }

    private void updateIndoor() {
        if (checkReady()) {
            this.googleMap.setIndoorEnabled(this.cb_indoorcheckbox.isChecked());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        updateMapType();
    }

    private void updateMapType() {
        if (this.googleMap != null) {
            String str = (String) this.spinner.getSelectedItem();
            if (str.equals(getString(R.string.normal))) {
                this.googleMap.setMapType(1);
            } else if (str.equals(getString(R.string.none_map))) {
                this.googleMap.setMapType(0);
            } else if (str.equals(getString(R.string.satellite))) {
                this.googleMap.setMapType(2);
            } else if (str.equals(getString(R.string.hybrid))) {
                this.googleMap.setMapType(4);
            } else if (str.equals(getString(R.string.terrain))) {
                this.googleMap.setMapType(3);
            } else {
                Log.i("TAG", "Error setting layer with name " + str);
            }
        }
    }

}
