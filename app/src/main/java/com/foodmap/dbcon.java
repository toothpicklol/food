package com.foodmap;

import android.net.UrlQuerySanitizer;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class dbcon {
    public static String  dbstring(String  i ,String j ,String Wcook,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie",Wcook+";expires=Thu,31-Dec-37 23:55:55 GMT;path=/");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("un01",i));
            params.add(new BasicNameValuePair("up01",j));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }

    public static String  dbReg(String  i ,String Wcook,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie",Wcook+";expires=Thu,31-Dec-37 23:55:55 GMT;path=/");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("un01",i));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }


}
