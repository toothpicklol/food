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
            hP.addHeader("cookie",Wcook+"...");

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




    public static String  dbReg(String  i ,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

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
    public static String  insertReg(String[]  i ,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i[0]));
            params.add(new BasicNameValuePair("S2",i[1]));
            params.add(new BasicNameValuePair("S3",i[2]));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  insertUser(String  i ,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }

    public static String  SearchShop(String  i ,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }


    public static String  userInfo(String i,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));




            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }

    public static String  comment(String i,String url){
        String[] result;
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= new String[]{EntityUtils.toString(hR.getEntity(), HTTP.UTF_8)};




        }
        catch (Exception e){
            return e.toString();
        }



        return result[0];
    }
    public static String  updateImg(String i,String j,String k,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));


            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  mark(String i,String j,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  insertShop(String[]  i ,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i[0]));
            params.add(new BasicNameValuePair("S2",i[1]));
            params.add(new BasicNameValuePair("S3",i[2]));
            params.add(new BasicNameValuePair("S4",i[3]));
            params.add(new BasicNameValuePair("S5",i[4]));
            params.add(new BasicNameValuePair("S6",i[5]));
            params.add(new BasicNameValuePair("S7",i[6]));
            params.add(new BasicNameValuePair("S8",i[7]));
            params.add(new BasicNameValuePair("S9",i[8]));
            params.add(new BasicNameValuePair("S10",i[9]));
            params.add(new BasicNameValuePair("S11",i[10]));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  title(String i,String url){
        String[] result;
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= new String[]{EntityUtils.toString(hR.getEntity(), HTTP.UTF_8)};
        }
        catch (Exception e){
            return e.toString();
        }



        return result[0];
    }
    public static String  updateTitle(String i,String j,String k,String url){
        String[] result;
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= new String[]{EntityUtils.toString(hR.getEntity(), HTTP.UTF_8)};
        }
        catch (Exception e){
            return e.toString();
        }



        return result[0];
    }
    public static String  updatePass(String i,String j,String url){
        String[] result;
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= new String[]{EntityUtils.toString(hR.getEntity(), HTTP.UTF_8)};
        }
        catch (Exception e){
            return e.toString();
        }



        return result[0];
    }
    public static String  insertLikeShop(String i,String j,String url){
        String[] result;
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= new String[]{EntityUtils.toString(hR.getEntity(), HTTP.UTF_8)};
        }
        catch (Exception e){
            return e.toString();
        }



        return result[0];
    }
    public static String  insertImgLib(String i,String j,String url){
        String[] result;
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= new String[]{EntityUtils.toString(hR.getEntity(), HTTP.UTF_8)};
        }
        catch (Exception e){
            return e.toString();
        }



        return result[0];
    }
    public static String  insertPost(String i,String j,String k,String l,String m,String n,String o,String url){
        String[] result;
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));
            params.add(new BasicNameValuePair("S4",l));
            params.add(new BasicNameValuePair("S5",m));
            params.add(new BasicNameValuePair("S6",n));
            params.add(new BasicNameValuePair("S7",o));


            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= new String[]{EntityUtils.toString(hR.getEntity(), HTTP.UTF_8)};
        }
        catch (Exception e){
            return e.toString();
        }



        return result[0];
    }

    public static String  insertPoint(String  i,String j,String k ,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));


            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }

    public static String  updatePoint(String i,String j,String k ,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));



            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  insertTag(String  i,String j ,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  likeCount(String  i,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));


            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  pointInfo(String  i,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));


            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }

    public static String  messageInfo(String  i,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));


            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  likeCheck(String  i,String j,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));


            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  insertLike(String  i,String j,String k,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));


            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  insertMessage(String  i,String j,String k,String l,String m,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));
            params.add(new BasicNameValuePair("S4",l));
            params.add(new BasicNameValuePair("S5",m));



            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }


    public static String  searchShopId(String  i,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));




            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  shopPointInfo(String  i,String j,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));




            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  insertLog(String  i,String j,String k,String l,String m,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));
            params.add(new BasicNameValuePair("S4",l));
            params.add(new BasicNameValuePair("S5",m));





            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  delete(String  i,String j,String k,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));






            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }

    public static String  updateGold(String i,String j,String url){
        String[] result;
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= new String[]{EntityUtils.toString(hR.getEntity(), HTTP.UTF_8)};
        }
        catch (Exception e){
            return e.toString();
        }



        return result[0];
    }
    public static String  updateDiscount(String i,String j,String k,String url){
        String[] result;
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");
            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= new String[]{EntityUtils.toString(hR.getEntity(), HTTP.UTF_8)};
        }
        catch (Exception e){
            return e.toString();
        }



        return result[0];
    }

    public static String  insertArticle(String[]  i ,String url){
        String result = "空";
        try{
            HttpClient hc=new DefaultHttpClient();
            HttpPost hp=new HttpPost(url);
            hp.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i[0]));
            params.add(new BasicNameValuePair("S2",i[1]));
            params.add(new BasicNameValuePair("S3",i[2]));
            params.add(new BasicNameValuePair("S4",i[3]));
            params.add(new BasicNameValuePair("S5",i[4]));

            hp.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
            HttpResponse hr=hc.execute(hp);
            result = EntityUtils.toString(hr.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }


    public static String  insertDiscount(String[]  i ,String url){
        String result = "空";
        try{
            HttpClient hc=new DefaultHttpClient();
            HttpPost hp=new HttpPost(url);
            hp.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i[0]));
            params.add(new BasicNameValuePair("S2",i[1]));
            params.add(new BasicNameValuePair("S3",i[2]));
            params.add(new BasicNameValuePair("S4",i[3]));
            params.add(new BasicNameValuePair("S5",i[4]));
            params.add(new BasicNameValuePair("S6",i[5]));
            params.add(new BasicNameValuePair("S7",i[6]));
            params.add(new BasicNameValuePair("S8",i[7]));

            hp.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
            HttpResponse hr=hc.execute(hp);
            result = EntityUtils.toString(hr.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  updatePost(String  i,String j,String k,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));






            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  updateMessage(String  i,String j,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  updateShopInfo(String  i,String j,String k,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  ownerCheck(String  i,String j,String k,String l,String m,String n,String o,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            params.add(new BasicNameValuePair("S3",k));
            params.add(new BasicNameValuePair("S4",l));
            params.add(new BasicNameValuePair("S5",m));
            params.add(new BasicNameValuePair("S6",n));
            params.add(new BasicNameValuePair("S7",o));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  selectOwnerCheck(String  i,String j,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  signUpChecker(String  i,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  insertChecker(String  i,String j,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  updateCheckerPass(String  i,String j,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  isChecker(String  i,String j,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  getCheckerPass(String  i,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR=hc.execute(hP);
            result= EntityUtils.toString(hR.getEntity(),HTTP.UTF_8);


        }
        catch (Exception e){
            return e.toString();
        }



        return result;
    }
    public static String  updateChecker(String  i,String j,String url){
        String result = "空";
        try{
            HttpClient hc =new DefaultHttpClient();
            HttpPost  hP=new HttpPost(url);
            hP.addHeader("cookie","...");

            ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1",i));
            params.add(new BasicNameValuePair("S2",j));
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
