package com.foodmap;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView account,password,loginfail;
    Context context=this;
    WebView webView;
    String url="http://114.32.152.202/foodphp/login.php";
    CookieManager cookieManager;
    String cookieStr;

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
        String pass = String.format(password.getText().toString());

        r=dbcon.dbstring(account.getText().toString(),password.getText().toString(),cookieStr,url);
        loginfail.setText(r);



        System.out.println(r);
        if (r.equals(acc)) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, pageHome.class);
            startActivity(intent);
            loginfail.setVisibility(View.INVISIBLE );
        }
        else{
            loginfail.setVisibility(View.VISIBLE );

        }
    }
    public void register(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, pageRegister.class);
        startActivity(intent);



    }
}
