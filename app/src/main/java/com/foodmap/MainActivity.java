package com.foodmap;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.StrictMode;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {

    TextView account,password,loginfail;
    Context context=this;
    WebView webView;
    String url="http://114.32.152.202/foodphp/login.php";
    CookieManager cookieManager;
    String cookieStr;
    private GoogleMap mMap;
    private LocationManager mloc;
    private final int ReQust_PERMISSIOIN_FOR_ACCESS_FINE_LOCATION=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = findViewById(R.id.etAcc);
        password =findViewById(R.id.etPass);
        loginfail=findViewById(R.id.txFail);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        Wcookie(context);
        mloc=(LocationManager)getSystemService(LOCATION_SERVICE);

        checkLocationPermissionAndEnableIt(true);



    }

    private void Wcookie(Context context) {
        webView=new WebView(context);
        CookieSyncManager.createInstance(context);
        cookieManager=CookieManager.getInstance();

        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished (WebView view, String url){
                super.onPageFinished(view, url);
                cookieManager.setAcceptCookie(true);
                cookieStr=cookieManager.getCookie(url);

            }
        });
        webView.loadUrl(url);
        webView.clearCache(true);
        webView.clearHistory();

        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();

    }


    public void login(View v) {
        String r="xxxxx";

        String acc = String.format(account.getText().toString());

        pageUser.setName(acc);
        pageShop.setName(acc);
        pageHome.setName(acc);


        r=dbcon.dbstring(account.getText().toString(),password.getText().toString(),cookieStr,url);
        loginfail.setText(r);



        //System.out.println(r);
        if (r.equals(acc)) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, pageHome.class);
            startActivity(intent);
            loginfail.setVisibility(View.INVISIBLE );
            Toast.makeText(getApplicationContext()," 登入成功", Toast.LENGTH_LONG).show();

        }
        else{
            loginfail.setVisibility(View.VISIBLE );

        }
    }
    public void register(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, pageRegister.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext()," 註冊成功", Toast.LENGTH_LONG).show();



    }
    public void onReQust_PERMISSIOIN_FOR_ACCESS_FINE_LOCATION(int requestCode, @NonNull int[]grantResults){
        if(requestCode==ReQust_PERMISSIOIN_FOR_ACCESS_FINE_LOCATION){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                checkLocationPermissionAndEnableIt(true);
                return;
            }
        }
    }
    private void checkLocationPermissionAndEnableIt(boolean on){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder altDlgBuilder =new AlertDialog.Builder(MainActivity.this);

                altDlgBuilder.setTitle("提示");
                altDlgBuilder.setMessage("APP需要開啟定位功能");
                altDlgBuilder.setIcon(android.R.drawable.ic_dialog_info);
                altDlgBuilder.setCancelable(false);
                altDlgBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION}, ReQust_PERMISSIOIN_FOR_ACCESS_FINE_LOCATION);
                    }
                });
                altDlgBuilder.show();
                return;



            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ReQust_PERMISSIOIN_FOR_ACCESS_FINE_LOCATION);
                return;
            }
        }



    }
    private void checkStorge(boolean on){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder altDlgBuilder =new AlertDialog.Builder(MainActivity.this);

                altDlgBuilder.setTitle("提示");
                altDlgBuilder.setMessage("APP需要開啟定位功能");
                altDlgBuilder.setIcon(android.R.drawable.ic_dialog_info);
                altDlgBuilder.setCancelable(false);
                altDlgBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION}, ReQust_PERMISSIOIN_FOR_ACCESS_FINE_LOCATION);
                    }
                });
                altDlgBuilder.show();
                return;



            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ReQust_PERMISSIOIN_FOR_ACCESS_FINE_LOCATION);
                return;
            }
        }



    }
}
