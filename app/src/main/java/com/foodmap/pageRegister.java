package com.foodmap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class pageRegister extends AppCompatActivity {

    TextView regAcc,regPass,regPassC,fail,email;
    Context context=this;
    WebView webView;
    String url="http://114.32.152.202/foodphp/register.php";
    String insterReg="http://114.32.152.202/foodphp/insterReg.php";
    CookieManager cookieManager;
    String cookieStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_register);

        regAcc = findViewById(R.id.etResAcc);
        regPass =findViewById(R.id.etResPass);
        regPassC=findViewById(R.id.etResPassC);
        fail=findViewById(R.id.txFailAcc);
        email=findViewById(R.id.etEmail);





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

    public void confirm(View v) {
        String r="xxxxx";
        r=dbcon.dbReg(regAcc.getText().toString(),cookieStr,url);

        fail.setVisibility(View.INVISIBLE);




        if (r.equals(regAcc.getText().toString())) {
            if(regAcc.getText().toString().length()==0|regPass.getText().toString().length()==0|regPassC.getText().toString().length()==0|email.getText().toString().length()==0)
            {
                fail.setText("尚未輸入");
                fail.setVisibility(View.VISIBLE);
            }
            else{
                if(passwordCheck(regPass.getText().toString()))
                {
                    if(regPass.getText().toString().equals(regPassC.getText().toString())){
                        String [] in=new String[]{regAcc.getText().toString(),regPass.getText().toString(),email.getText().toString()};
                        dbcon.insertReg(in,cookieStr,insterReg);
                        Intent intent = new Intent();
                        intent.setClass(pageRegister.this, MainActivity.class);
                        startActivity(intent);




                    }
                    else
                    {
                        fail.setText("密碼不相符");
                        fail.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    fail.setText("密碼請由8位英數組成");
                    fail.setVisibility(View.VISIBLE);
                }

           }
        }

        else{

            fail.setText("帳號已被註冊");;
            fail.setVisibility(View.VISIBLE);

        }
    }
    public static boolean passwordCheck(String str) {
        boolean check = false;
        if(str.length() > 7) {
            System.out.println(str.length());


            boolean isDigit = false;
            boolean isLetter = false;

            for(int i = 0 ; i < str.length(); i++)
            {

                if(Character.isDigit(str.charAt(i)))
                {
                    isDigit = true;
                }
                if(Character.isLetter(str.charAt(i)))
                {

                    isLetter = true;
                }

                if(isDigit && isLetter)
                {
                    break;}
            }



            check = isDigit && isLetter ;
        }
        else{
            return false;
        }
        return check;


    }
}
