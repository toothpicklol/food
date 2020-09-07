package com.foodmap.ui.home_page1;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foodmap.Api;
import com.foodmap.R;
import com.foodmap.dbcon;
import com.foodmap.pageChecker;
import com.foodmap.pageShop;
import com.foodmap.pageUser;
import com.foodmap.ui.home.HomeFragment;
import com.foodmap.ui.slideshow.SlideshowFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class homeoldFragment extends Fragment {

    private static String user;
    Button btn1,btn2,btn3,btn4;
    LinearLayout linearLayout;
    TextView tx1;
    GoogleMapV2_MarkPoint[] MysqlPointSet;
    GoogleMapV1_MarkPoint[] InfoSQL;
    make[] InfoSQL2;
    makeInfo[] InfoSQL1;
    makeInfo1[] InfoSQL3;
    makeComment[] commentSQL;
    makeLng[] makeLng;
    post[] post1;
    String imgU="http://114.32.152.202/foodphp/userinfo.php";
    String url="http://114.32.152.202/foodphp/shopcomment.php";
    String Discountshopidinfo="http://114.32.152.202/foodphp/discountshopidinfo.php";
    String shopnameinfo="http://114.32.152.202/foodphp/shopnameinfo.php";
    String Discountinfo="http://114.32.152.202/foodphp/discountinfo.php";
    String mapdiscount="http://114.32.152.202/foodphp/mapdiscount.php";
    String mapshop="http://114.32.152.202/foodphp/mapshop.php";
    String info="http://114.32.152.202/foodphp/shopinfo.php";
    String postshopAinfo="http://114.32.152.202/foodphp/postshopAinfo.php";
    String shoppostinfo="http://114.32.152.202/foodphp/shoppostinfo.php";
    String allshopAinfo="http://114.32.152.202/foodphp/allshopAinfo.php";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.homeold_fragment, container, false);

        linearLayout=(LinearLayout)root.findViewById(R.id.linearLayout);


        String userInfo0=dbcon.userInfo("asas",allshopAinfo);
        String[] makArr0=userInfo0.split("]");
        for(int b=0;b<makArr0.length;b++) {
            String tmp0 = makArr0[b];
            String userInfo = dbcon.userInfo(tmp0, Discountinfo);
            String[] makArr1 = userInfo.split("]");
            if(!dbcon.userInfo(tmp0, Discountinfo).equals("")) {
                InfoSQL = new GoogleMapV1_MarkPoint[makArr1.length];
                for (int z = 0; z < makArr1.length; z++) {
                    String tmp1 = makArr1[z];
                    String[] infoArr = tmp1.split(",");
                    InfoSQL[z] = new GoogleMapV1_MarkPoint(infoArr[2], infoArr[3], infoArr[4], infoArr[0], infoArr[1], infoArr[5], infoArr[6]);
                    if (Integer.parseInt(InfoSQL[z].num) > 0) {
                        InfoSQL1 = new makeInfo[1];
                        String name = dbcon.userInfo(tmp0, info);
                        String[] infoArr1 = name.split(",");
                        InfoSQL1[0] = new makeInfo(infoArr1[0], infoArr1[6], infoArr1[1], infoArr1[2], infoArr1[7], infoArr1[4], infoArr1[4] + "~" + infoArr1[5], infoArr1[9], infoArr1[8], infoArr1[10]);


                        //String mark=dbcon.userInfo("3Nh4cWNv", mapdiscount);
                        String mark=dbcon.userInfo(tmp0, mapdiscount);
                        final String[] makLng=mark.split(",");

//                        Double latng=Double.parseDouble(tmp[0]);
//                        Double lonng=Double.parseDouble(tmp[1]);

                        Button BTN2=new Button(getActivity());
                        BTN2.setText(InfoSQL1[0].address + "的" + InfoSQL1[0].username + "附近目前還有" + InfoSQL[z].num + "個折價卷，需要折價卷的請在折價卷的100公尺內撿取。");
                        //BTN2.setText(mark);
                        BTN2.setBackgroundColor(Color.parseColor("#00000000"));
                        BTN2.setHeight(350);
                        BTN2.setTextSize(20);
                        BTN2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LatLng lng=new LatLng(Double.parseDouble(makLng[0]),
                                        Double.parseDouble(makLng[1]));
                                HomeFragment fragment=new HomeFragment();
                                FragmentManager fragmentManager=getFragmentManager();
                                FragmentTransaction ft=fragmentManager.beginTransaction();
                                ft.replace(getId(),fragment);
                                ft.commit();
                                HomeFragment.setLng(lng);
                            }
                        });
                        linearLayout.addView(BTN2);
 //                       TextView tx = new TextView(getActivity());
 //                       tx.setText(InfoSQL1[0].address + "的" + InfoSQL1[0].username + "附近目前還有" + InfoSQL[z].num + "個折價卷，需要折價卷的請在折價卷的100公尺內撿取。\n" );
 //                       linearLayout.addView(tx);
                    }
                }
            }
        }

      /*  for(GoogleMapV1_MarkPoint point : InfoSQL){
            for (makeInfo point1 : InfoSQL1){
                if(Integer.parseInt(point.num)>0) {
                    TextView tx = new TextView(getActivity());
                    tx.setText(point1.address + "附近的" + point1.username + "目前還有" + point.num + "個折價卷。");
                    linearLayout.addView(tx);
                }
            }
        }*/








        /*String userInfo2=dbcon.userInfo("asas",postshopAinfo);
        String[] makArr2=userInfo2.split("]");
        for(int c=0;c<makArr2.length;c++) {
            String tmp2 = makArr2[c];
            String commentS = dbcon.userInfo(tmp2, url);
            String[] commentArr = commentS.split("]");
            commentSQL = new makeComment[commentArr.length];
            for (int i = 0; i < commentArr.length; i++) {
                String tmp=commentArr[i];
                String[] commentArr2=tmp.split(",");
                String img = dbcon.userInfo(commentArr2[0], imgU);
                String[] imgArr = img.split(",");
                commentSQL[i] = new makeComment(commentArr2[0], commentArr2[1], commentArr2[2], commentArr2[3], imgArr[2], commentArr2[4], commentArr2[5], imgArr[0]);//評論資料

                InfoSQL1 = new makeInfo[1];
                String name = dbcon.userInfo(tmp2, info);
                String[] infoArr1 = name.split(",");
                InfoSQL1[0] = new makeInfo(infoArr1[0], infoArr1[6], infoArr1[1], infoArr1[2], infoArr1[7], infoArr1[4], infoArr1[4] + "~" + infoArr1[5], infoArr1[9], infoArr1[8], infoArr1[10]);


            }
        }*/

        String userInfo2=dbcon.userInfo("admin",shoppostinfo);
        String[] makArr2=userInfo2.split("]");
        post1=new post[makArr2.length];
        if(makArr2.length!=0) {
            for (int c = 0; c < makArr2.length; c++) {
                String tmp2 = makArr2[c];
                String[] commentArr2 = tmp2.split(",");
                post1[c] = new post(commentArr2[0], commentArr2[1], commentArr2[2], commentArr2[3], commentArr2[4], commentArr2[5]);

                InfoSQL1 = new makeInfo[1];
                String name = dbcon.userInfo(post1[c].shopA, info);
                String[] infoArr1 = name.split(",");
                InfoSQL1[0] = new makeInfo(infoArr1[0], infoArr1[6], infoArr1[1], infoArr1[2], infoArr1[7], infoArr1[4], infoArr1[4] + "~" + infoArr1[5], infoArr1[9], infoArr1[8], infoArr1[10]);

                commentSQL = new makeComment[1];
                String commentS=dbcon.comment(post1[c].shopA,url);
                String[] commentArr=commentS.split("]");
                String tmp=commentArr[0];
                String[] commentArr3=tmp.split(",");
                String img=dbcon.userInfo(commentArr3[0],imgU);
                String[] imgArr=img.split(",");
                commentSQL[0] = new makeComment(commentArr3[0], commentArr3[1], commentArr3[2], commentArr3[3], imgArr[2], commentArr3[4], commentArr3[5], imgArr[0]);//評論資料


                Button BTN1=new Button(getActivity());
                BTN1.setText("【公告】" + post1[c].title);
                BTN1.setAllCaps(false);
                BTN1.setGravity(Gravity.LEFT);
                BTN1.setBackgroundColor(Color.parseColor("#00000000"));
                BTN1.setHeight(200);
                BTN1.setTextSize(20);
                final int finalC = c;
                BTN1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), pageShop.class);
                        startActivity(intent);
                        pageShop.setName(post1[finalC].shopA,user);
                    }
                });
                linearLayout.addView(BTN1);

            }
        }





        return root;
    }

    public Bitmap getBitmapFromURL1(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            myBitmap = Bitmap.createScaledBitmap(myBitmap,600,450, true);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    static class makeComment {
        public String text,title,account,picture,head,postId,time,nick;
        public makeComment( String i, String j,String k,String l,String n,String o,String p,String q) {
            account=i;
            title=j;
            text=k;
            picture=l;
            head=n;
            postId=o;
            time=p;
            nick=q;
        }
    }

    class makeInfo {

        public String username,userLV,bigHead,bg,address,message,tel,time,comment,point;
        public makeInfo( String i, String j,String k,String l,String m,String n,String o,String p,String q,String r) {
            username=i;
            userLV=j;
            bigHead=k;
            bg=l;
            address=m;
            tel=n;
            time=o;
            message=p;
            comment=q;
            point=r;
        }


    }

    static class makeInfo2 {

        public String username,userLV,bigHead,bg,title,gold;
        public makeInfo2( String i, String j,String k,String l,String m,String n) {
            username=i;
            userLV=j;
            bigHead=k;
            bg=l;
            title=m;
            gold=n;


        }


    }

    static class makeLng {

        public double NS,WE;
        public makeLng( double i, double j) {
            NS=i;
            WE=j;

        }


    }

    class post {

        public String title,text,url,time,shopA,postid;
        public post( String i, String j,String k,String l,String m,String n) {
            title=i;
            text=j;
            url=k;
            time=l;
            shopA=m;
            postid=n;
        }


    }

    class makeInfo1 {

        public String username,userLV,bigHead,bg,address,message,tel,time,comment,point;
        public makeInfo1( String i, String j,String k,String l,String m,String n,String o,String p,String q,String r) {
            username=i;
            userLV=j;
            bigHead=k;
            bg=l;
            address=m;
            tel=n;
            time=o;
            message=p;
            comment=q;
            point=r;
        }


    }

    public static void setName(String i){
        user=i;
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

    class make {
        public String name,address,head,bg;
        public make(String i, String j,String k,String l) {

            name=i;
            address=j;
            head=k;
            bg=l;
        }

    }

}
