package com.foodmap.ui.home;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.LocationListener;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.foodmap.R;
import com.foodmap.pageUser;;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashMap;



public class HomeFragment extends Fragment
        implements OnMapReadyCallback,
        LocationListener {

    private GoogleMap mMap;
    private static final String TAG = "LocationFragment";
    private LocationManager mLocationManager;
    Button search,more;
    EditText searchBar;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        search=root.findViewById(R.id.btn_search);
        more=root.findViewById(R.id.btn_more);
        searchBar=root.findViewById(R.id.etSearch);
        search.setOnClickListener(searchListener);
        more.setOnClickListener(moreListener);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        return root;


    }
    private Button.OnClickListener searchListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            String x=searchBar.getText().toString();

        }
    };
    private Button.OnClickListener moreListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent intent = new Intent(getActivity(), pageUser.class);
              startActivity(intent);

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {






        GoogleMapV2_MarkPoint[] MysqlPointSet = new GoogleMapV2_MarkPoint[3];
        MysqlPointSet[0] = new GoogleMapV2_MarkPoint(25.067, 121.4971, "天龍國", "5");
        MysqlPointSet[1] = new GoogleMapV2_MarkPoint(25.068, 121.4972, "南部", "5");
        MysqlPointSet[2] = new GoogleMapV2_MarkPoint(25.069, 121.4973, "地府", "5");

        mMap = googleMap;
        for (GoogleMapV2_MarkPoint point : MysqlPointSet) {

            mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title(point.title)
                    .snippet(point.address));

        }
        //地圖單位0.001=100M
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "onResume");


        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            //mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

            return;
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }

    private int checkSelfPermission(String accessFineLocation) {
        return 0;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){

    }


    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, String.valueOf(location.getLatitude()));
        Log.i(TAG, String.valueOf(location.getLongitude()));
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("你的位置"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 15));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("878787");


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }




    class GoogleMapV2_MarkPoint {
        public double latitude, longitude;
        public String title,address;
        public GoogleMapV2_MarkPoint(double i, double j,String k,String l) {

            latitude=i;
            longitude=j;
            title=k;
            address=l;
        }
        @Override
        public String toString() {

            return "Point:( "+latitude + " , " + longitude+" )";
        }

    }









}