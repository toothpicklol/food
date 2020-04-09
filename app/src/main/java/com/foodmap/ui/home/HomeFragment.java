package com.foodmap.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.foodmap.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import static androidx.core.content.ContextCompat.*;

public class HomeFragment extends Fragment implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        return root;
        


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        double x=25.068483;
        double y=121.497072;
        double z=0.001;


        HashMap<Double,Double> editMap = new HashMap();
//        for(int i=1;i<=10;i++){
//            x+=0.001;
//            y+=0.001;
//            editMap.put(x,y);
//        }
        GoogleMapV2_MarkPoint[] MysqlPointSet = new GoogleMapV2_MarkPoint[3];
        MysqlPointSet[0]= new GoogleMapV2_MarkPoint(25.067, 121.4971, "天龍國", "5");
        MysqlPointSet[1]= new GoogleMapV2_MarkPoint(25.068, 121.4972, "南部", "5");
        MysqlPointSet[2]= new GoogleMapV2_MarkPoint(25.069, 121.4973, "地府", "5");

        mMap = googleMap;
        for (GoogleMapV2_MarkPoint point : MysqlPointSet) {

            mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude,point.longitude)).title(point.title)
                    .snippet(point.address));

        }

            LatLng sydney = new LatLng(x,y);
            LatLng sydne2 = new LatLng(x+0.01,y+0.01);
            mMap = googleMap;
            mMap.addMarker(new MarkerOptions().position(sydney).title("taipei"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(x,y),18));




    }

    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

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
            System.out.println(i);
            System.out.println(j);
            System.out.println(k);
            System.out.println(l);
            latitude=i;
            longitude=j;
            title=k;
            address=l;
        }
        @Override
        public String toString() {
            System.out.println("8858");
            return "Point:( "+latitude + " , " + longitude+" )";
        }

    }



}