package com.foodmap;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Api {
    public static int lv(int A0,int A1,int target,int lv){
        if(target<A0){
            return lv;
        }
        if(target>=A0&&target<A1){
            return lv+1;
        }
        if(target>=A1&&target<A0+A1){
            return lv+2;
        }
        int tmp=A0;
        A0=A1;
        A1=tmp+A1;
        lv++;


        return lv(A0,A1,target,lv);


    }

    public static Drawable loadImageFromURL(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable draw = Drawable.createFromStream(is, "src");
            return draw;
        } catch (Exception e) {
            //TODO handle error
            System.out.println("error");
            Log.i("loadingImg", e.toString());
            return null;
        }
    }
    public static String Time() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date Time = Calendar.getInstance().getTime();


        return Format.format(Time);

    }
}
