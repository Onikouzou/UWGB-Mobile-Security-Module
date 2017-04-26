package com.example.matt.ignitecs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

// Maps (coarse location)
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

// Maps (fine location)
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;


public class DisplayInformation extends AppCompatActivity
        implements OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener,
        OnMapReadyCallback, FragmentContacts.OnFragmentInteractionListener,
        FragmentCamera.OnFragmentInteractionListener
{

    private GoogleApiClient mGoogleApiClient;
    TextView txtLocCoarse;
    Location mCurrentLocation;
    GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_information);

        /**
         * The following code is for the Google Maps API
         */

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        onStart();

        txtLocCoarse = (TextView) findViewById(R.id.txtLocCoarse);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    } // end onCreate


    // -----------------------
    // Google Maps
    // -----------------------

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // An unresolvable error has occurred and a connection to Google APIs
        // could not be established. Display an error message, or handle
        // the failure silently

        // ...
    }

    @Override
    public void onConnected(Bundle connectionHint)
    {
        try {
            LocationRequest mCoarseLocationRequest = new LocationRequest();
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mCoarseLocationRequest, this);

            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mCurrentLocation != null)
                txtLocCoarse.setText(mCurrentLocation.getLatitude() + ", " + mCurrentLocation.getLongitude());
            else
                txtLocCoarse.setText(0.0 + ", " + 0.0);

            onMapReady(gMap);
        } catch (SecurityException e) {
            // ERROR
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        //your code goes here
    }

    @Override
    public void onLocationChanged(Location loc) {
        txtLocCoarse.setText(loc.getLatitude() + ", " + loc.getLongitude());
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        LatLng gb;
        // 44.5321118, -87.9132922
        if (mCurrentLocation != null) {
            gb = new LatLng(mCurrentLocation.getLatitude(),
                    mCurrentLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(gb)
                    .title("You are here"));
            float zoomLevel = 16;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gb, zoomLevel));
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

} // end class
