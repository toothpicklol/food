package com.foodmap.ui.home;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.location.LocationListener;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.foodmap.*;
import com.foodmap.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HomeFragment extends Fragment
        implements OnMapReadyCallback,
        LocationListener, GoogleMap.OnMarkerClickListener{

    private static GoogleMap mMap;
    private static Double nX,nY;
    private static final String TAG = "LocationFragment";
    private static String user;
    private LocationManager mLocationManager;
    Button search;
    EditText searchBar;
    int first=1,length=0;
    String info="http://114.32.152.202/foodphp/userinfo.php";
    String mapShop="http://114.32.152.202/foodphp/mapshop.php";
    GoogleMapV2_MarkPoint[] MysqlPointSet;
    double X,Y;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        search=root.findViewById(R.id.btn_search);

        searchBar=root.findViewById(R.id.etSearch);
        search.setOnClickListener(searchListener);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Toast.makeText(getActivity(), getResources().getString(R.string.map), Toast.LENGTH_SHORT).show();

        mapFragment.getMapAsync(this);


        return root;


    }
    private Button.OnClickListener searchListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), pageSearch.class);
            startActivity(intent);

        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        String commentS=dbcon.mark(String.valueOf(X),String.valueOf(Y),mapShop);
        System.out.println(commentS);

        String[] markArr=commentS.split("]");
        System.out.println(markArr.length);

        if(markArr.length!=1){
            MysqlPointSet = new GoogleMapV2_MarkPoint[markArr.length];
            for (int i=0; i<markArr.length; i++) {
                String tmp=markArr[i];
                String[] commentArr2=tmp.split(",");
                MysqlPointSet[i] = new GoogleMapV2_MarkPoint(Double.parseDouble(commentArr2[0]), Double.parseDouble(commentArr2[1]), commentArr2[2],commentArr2[3],commentArr2[4],commentArr2[5]);//評論資料

            }

            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            for (GoogleMapV2_MarkPoint point : MysqlPointSet) {


                mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title(point.title)
                        .snippet(point.point+"#"+point.account).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromURL(point.head))));


            }
            if(first==1){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(23.5,121.0), 6));
                first++;
            }
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    String name=marker.getSnippet();
                    if(marker.getTitle().equals("你的位置")){

                    }
                    else{
                        String[] nameArr=name.split("#");

                        Intent intent = new Intent(getActivity(), pageShop.class);
                        startActivity(intent);
                        pageShop.setName(nameArr[1],user);
                    }








                }
            });
            mMap.setMyLocationEnabled(true);
        }
        












    }
    public static void selectShop(){


            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                    markerOptions.snippet("點擊這裡創建商店並在按一次右下的按鈕退出模式");
                    mMap.clear();

                    // Animating to the touched position
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    // Placing a marker on the touched position
                    mMap.addMarker(markerOptions);
                }
            });
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    nX = marker.getPosition().latitude;
                    nY = marker.getPosition().longitude;
                    pageCreateShop.newXY(nX, nY);



                }



            });





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

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);
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
        mMap.clear();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("你的位置"));
        X=location.getLatitude();
        Y=location.getLongitude();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 17));
        onMapReady(mMap);

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

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            myBitmap = Bitmap.createScaledBitmap(myBitmap,150,100, true);
        return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        return true;
    }

    class GoogleMapV2_MarkPoint {
        public double latitude, longitude;
        public String title,head,point,account;
        public GoogleMapV2_MarkPoint(double i, double j,String k,String l,String m,String n) {

            latitude=i;
            longitude=j;
            title=k;
            point="總評分:"+l+"/100";
            head=m;
            account=n;


        }
        @Override
        public String toString() {

            return "Point:( "+latitude + " , " + longitude+" )";
        }

    }

    public static void setName(String i){
        user=i;

    }









}
