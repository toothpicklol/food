package com.foodmap.ui.home;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.location.LocationListener;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.foodmap.*;
import com.foodmap.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class HomeFragment extends Fragment
        implements OnMapReadyCallback,
        LocationListener, GoogleMap.OnMarkerClickListener,GoogleMap.OnCameraMoveStartedListener,GoogleMap.OnCameraIdleListener
,GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraMoveCanceledListener{

    private static LatLng lng;
    //static int x=1;
    private int count = 1;
    private static GoogleMap mMap;
    private static Double nX,nY;
    private static Double cX,cY;
    private static final String TAG = "LocationFragment";
    private static String user;
    private LocationManager mLocationManager;
    Button search;
    EditText searchBar;
    int first=1;
    String info="http://114.32.152.202/foodphp/userinfo.php";
    String mapShop="http://114.32.152.202/foodphp/mapshop.php";
    GoogleMapV2_MarkPoint[] MysqlPointSet;
    GoogleMapV1_MarkPoint[] InfoSQL;
    View markView;
    double X,Y;

    private static String shop_id;
    Button btn1;
    TextView LV_limit,gold_limit;
    private static Double ax,by;
    String Discountinfo="http://114.32.152.202/foodphp/discountinfo.php";
    String updateDiscount="http://114.32.152.202/foodphp/updateDiscount.php";
    String insertArticle="http://114.32.152.202/foodphp/insertArticle.php";
    String fansU="http://114.32.152.202/foodphp/fansCount.php";
    String Gold="http://114.32.152.202/foodphp/updateGold.php";
    String Articleinfo="http://114.32.152.202/foodphp/Articleinfo.php";
    private Marker m1,m2;
    String[] makArr3;
    public static String BGS=null;
    article_img[] InfoSQL_img;


    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        markView = inflater.inflate(R.layout.map_mark, null);

        search=root.findViewById(R.id.btn_search);
        searchBar=root.findViewById(R.id.etSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), pageSearch.class);
                startActivity(intent);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Toast.makeText(getActivity(), getResources().getString(R.string.map), Toast.LENGTH_SHORT).show();
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        return root;
    }

    public double newx(double x) {
        double newx = (double) (Math.random() * (((x + 0.0005) - (x - 0.0005))) + (x - 0.0005));
        return newx;
    }

    public double newy(double y) {
        double newy = (double) (Math.random() * (((y + 0.0005) - (y - 0.0005))) + (y - 0.0005));
        return newy;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("touchx",0);
        editor.commit();

        mMap = googleMap;
        mMap.clear();
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setOnCameraMoveStartedListener(this);
        if(first==0){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(X,Y), 13));
            first++;
        }
        mMap.setMyLocationEnabled(true);

        //animToOnClick(lng);

        addMark(mMap);



    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mLocationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

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
        //mMap.clear();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("你的位置"));
        X=location.getLatitude();
        Y=location.getLongitude();

        SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("Location_x", (float) X);
        editor.putFloat("Location_y", (float) Y);
        editor.commit();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 17));
        onMapReady(mMap);

    }

    public Bitmap getBitmapFromURL1(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            myBitmap = Bitmap.createScaledBitmap(myBitmap,75,50, true);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
    @Override
    public void onCameraMoveStarted(int reason) {

        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            cX = mMap.getCameraPosition().target.latitude;
            cY = mMap.getCameraPosition().target.longitude;
            addMark(mMap);
        }
        else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            //Toast.makeText(getActivity(), "The user tapped something on the map.",Toast.LENGTH_SHORT).show();
        }
        else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            //Toast.makeText(getActivity(), "The app moved the camera.", Toast.LENGTH_SHORT).show();
        }
    }

    public double getDistance(LatLng start,LatLng end){
        double lat1=(Math.PI/180)*start.latitude;
        double lat2=(Math.PI/180)*end.latitude;

        double lon1=(Math.PI/180)*start.longitude;
        double lon2=(Math.PI/180)*end.longitude;

        double R = 6371;

        double d = Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;

        return  d;

    }

    public Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        return true;
    }
    @Override
    public void onCameraIdle() {
        Toast.makeText(getActivity(), "The camera has stopped moving.",
                Toast.LENGTH_SHORT).show();


    }
    @Override
    public void onCameraMove() {
        Toast.makeText(getActivity(), "The camera is moving.",
                Toast.LENGTH_SHORT).show();

    }
    @Override

    public void onCameraMoveCanceled() {
        Toast.makeText(getActivity(), "Camera movement canceled.",
                Toast.LENGTH_SHORT).show();

    }

    public void addMark(GoogleMap mMap){

        SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        TextView markShopName = (TextView) markView.findViewById(R.id.num_txt);
        ImageView markSopHead=markView.findViewById(R.id.imgShopHead);

        String markS=dbcon.mark(String.valueOf(cX),String.valueOf(cY),mapShop);
        System.out.println(markS);
        String[] markArr=markS.split("]");
        if(!markS.equals("")){
            MysqlPointSet = new GoogleMapV2_MarkPoint[markArr.length];
            for (int i=0; i<markArr.length; i++) {
                String tmp=markArr[i];
                String[] commentArr2=tmp.split(",");
                MysqlPointSet[i] = new GoogleMapV2_MarkPoint(Double.parseDouble(commentArr2[0]), Double.parseDouble(commentArr2[1]), commentArr2[2],commentArr2[3],commentArr2[4],commentArr2[5]);//評論資料
            }

            for (GoogleMapV2_MarkPoint point : MysqlPointSet) {
                markShopName.setText(point.title);
                markSopHead.setImageDrawable(loadImageFromURL(point.head));
                m2 = mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title(point.title)
                        .snippet(point.point + "#" + point.account).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), markView))));
                m2.setTag(0);
                String userInfo=dbcon.userInfo(point.account,Discountinfo);
                if(dbcon.userInfo(point.account,Discountinfo).length()!=0) {
                    String[] makArr1 = userInfo.split("]");
                    InfoSQL = new GoogleMapV1_MarkPoint[makArr1.length];
                    for (int z = 0; z < makArr1.length; z++) {
                        String tmp = makArr1[z];
                        String[] infoArr = tmp.split(",");
                        if (infoArr[0] != "") {
                            InfoSQL[z] = new GoogleMapV1_MarkPoint(infoArr[2], infoArr[3], infoArr[4], infoArr[0], infoArr[1], infoArr[5],infoArr[6]);
                        }
                    }
                    for (GoogleMapV1_MarkPoint point1 : InfoSQL) {
                        String ID=point1.tickrtID;
                        //  final String discount = sp.getString("discount", "");
                        for(int i=0;i<Integer.parseInt(point1.num);i++) {
                            m1 = mMap.addMarker(new MarkerOptions().position(new LatLng(newx(point.latitude), newy(point.longitude))).title(point.title + "/" +point1.Name).snippet("折價卷說明:"+point1.description).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromURL1(point1.img))));
                            m1.setTag(1);
                            m1.setTitle(point.title + "#" + point1.Name + "/" + ID);
                            if(i>0){
                                m1.setVisible(false);
                            }
                        }
                    }
                }
            }

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Integer click0=(Integer)m2.getTag();
                    String name=marker.getSnippet();
                    if(click0 == marker.getTag()) {
                        String[] nameArr = name.split("#");
                        Intent intent = new Intent(getActivity(), pageShop.class);
                        startActivity(intent);
                        pageShop.setName(nameArr[1], user);
                    }

                    //int touch=0;
                    Integer click = (Integer) m1.getTag();
                    int touch_2=1;

                    SharedPreferences sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    int user_LV=sp.getInt("userLV",0);
                    //int user_gold=sp.getInt("gold",0);

                    double X = sp.getFloat("Location_x", 0);
                    double Y = sp.getFloat("Location_y", 0);
                    LatLng lt1 = new LatLng(X, Y); //目前位置

                    LatLng lt2 = marker.getPosition();
                    if (getDistance(lt1,lt2) <= 0.1) {
                        if (click == marker.getTag()) {

                            for (GoogleMapV2_MarkPoint point : MysqlPointSet) {

                                String userInfo=dbcon.userInfo(point.account,Discountinfo);
                                if(dbcon.userInfo(point.account,Discountinfo).length()!=0) {
                                    makArr3 = userInfo.split("]");
                                    InfoSQL = new GoogleMapV1_MarkPoint[makArr3.length];
                                    for (int z = 0; z < makArr3.length; z++) {
                                        String tmp = makArr3[z];
                                        String[] infoArr = tmp.split(",");
                                        InfoSQL[z] = new GoogleMapV1_MarkPoint(infoArr[2], infoArr[3], infoArr[4], infoArr[0], infoArr[1], infoArr[5],infoArr[6]);
                                        String Id=InfoSQL[z].tickrtID;
                                        String id=marker.getTitle();
                                        int C=id.indexOf("/");
                                        String id1=id.substring(C+1);
                                        if(Id.equals(id1)){

                                            String userInfo4=dbcon.userInfo(user,Articleinfo);
                                            String[] makArr4 = userInfo4.split("]");
                                            if(makArr4.length<18) {

                                                // for (GoogleMapV1_MarkPoint point2 : InfoSQL) {
                                                if (Integer.parseInt(InfoSQL[z].num) > 0) {
                                                    String userInfo2 = dbcon.userInfo(user, info);
                                                    String[] infoArr3 = userInfo2.split(",");
                                                    if (Integer.parseInt(infoArr3[1]) >= Integer.parseInt(InfoSQL[z].Levellimit)) {
                                                        makeInfo[] InfoSQL1 = new makeInfo[1];
                                                        String userInfo0 = dbcon.userInfo(user, info);
                                                        String[] infoArr1 = userInfo0.split(",");
                                                        InfoSQL1[0] = new makeInfo(infoArr1[0], infoArr1[1], infoArr1[2], infoArr1[3], infoArr1[4], infoArr1[5]);
                                                        String fansC = dbcon.userInfo(user, fansU);
                                                        if (Integer.parseInt(InfoSQL1[0].gold) >= Integer.parseInt(InfoSQL[z].coinlimit)) {
                                                            int Cold_limit = Integer.parseInt(InfoSQL[z].coinlimit);
                                                            int Cold_new = Integer.parseInt(InfoSQL1[0].gold) - Cold_limit;
                                                            dbcon.updateGold(user, String.valueOf(Cold_new), Gold);


                                                            marker.setVisible(false);

                                                            String userInfo3 = dbcon.userInfo(user, Articleinfo);
                                                            String[] makArr3 = userInfo3.split("]");
                                                            InfoSQL_img = new article_img[makArr3.length];
                                                            if (userInfo3.equals("")) {

                                                                String a = String.valueOf(user);
                                                                String b = String.valueOf(point.account);
                                                                String c = String.valueOf(InfoSQL[z].tickrtID);
                                                                String d = InfoSQL[z].img;
                                                                String e = String.valueOf(1);

                                                                String[] r = new String[]{a, b, c, d, e};
                                                                dbcon.insertArticle(r, insertArticle);
                                                            } else {
                                                                String tmp3 = makArr3[makArr3.length - 1];
                                                                String[] infoArr2 = tmp3.split(",");
                                                                InfoSQL_img[makArr3.length - 1] = new article_img(infoArr2[0], infoArr2[1], infoArr2[2], Integer.parseInt(infoArr2[3]));

                                                                String a = String.valueOf(user);
                                                                String b = String.valueOf(point.account);
                                                                String c = String.valueOf(InfoSQL[z].tickrtID);
                                                                String d = InfoSQL[z].img;
                                                                String e = String.valueOf(InfoSQL_img[makArr3.length - 1].ticket_num + 1);

                                                                String[] r = new String[]{a, b, c, d, e};
                                                                dbcon.insertArticle(r, insertArticle);
                                                            }
                                                            int x = 1;
                                                            int New_num = (Integer.parseInt(InfoSQL[z].num)) - x;
                                                            dbcon.updateDiscount(String.valueOf(New_num), InfoSQL[z].tickrtID, "num", updateDiscount);
                                                        } else {
                                                            Toast.makeText(getActivity(), "金幣不足",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getActivity(), "等級不足",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                            else{
                                                Toast.makeText(getActivity(), "物品欄的折價卷數量已滿，請先使用折價卷再領",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "距離太遠，請走近一點再領喔",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }



    public static void animToOnClick(LatLng lg){
        //將攝影機移動到日月潭
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lg.latitude,lg.longitude),17));
    }

    public static void setLng(LatLng i){
        lng=i;

    }


    private Drawable loadImageFromURL(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable draw = Drawable.createFromStream(is, "src");
            return draw;
        } catch (Exception e) {

            System.out.println("erroor");
            Log.i("loadingImg", e.toString());
            return null;
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
                pageHome.set(1);




            }



        });






    }
    public static void setName(String i){
        user=i;

    }
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
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
    class article_img {
        public String shopid,tickrtid,img;
        public int ticket_num;
        public article_img(String i, String j,String k,int l) {

            shopid=i;
            tickrtid=j;
            img=k;
            ticket_num=l;


        }

    }
    class GoogleMapV1_MarkPoint {
        public String num, Levellimit,coinlimit,description;
        public String tickrtID,Name,img;
        public GoogleMapV1_MarkPoint(String i, String j,String k,String l,String m,String n,String o) {

            num=i;
            Levellimit=j;
            coinlimit=k;
            tickrtID=l;
            Name=m;
            img=n;
            description=o;


        }

    }
    class makeInfo {

        public String username,userLV,bigHead,bg,title,gold;
        public makeInfo( String i, String j,String k,String l,String m,String n) {
            username=i;
            userLV=j;
            bigHead=k;
            bg=l;
            title=m;
            gold=n;


        }


    }












}
