package com.foodmap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.URL;

public class pageLike extends AppCompatActivity {

    private static String user;
    LinearLayout shopBox,writerBox;
    makeLike[] likeSQL;
    makeLikeU[] likeSQLU;
    ImageView head;
    TextView name,acc;
    Button btnLike;
    View view;
    int likeCheck=0;
    String userLikeU="http://114.32.152.202/foodphp/LikeUser.php";
    String shopLike="http://114.32.152.202/foodphp/LikeShop.php";
    String userInfo="http://114.32.152.202/foodphp/userinfo.php";
    String shopInfo="http://114.32.152.202/foodphp/shopinfo.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_like);

        addListView();
        }
    public void addListView(){

        String userLike = dbcon.userInfo(user, shopLike);
        String[] likeShopArr = userLike.split("]");
        String shopLikeInfo="";
        for(int i=0;i<likeShopArr.length;i++){
            shopLikeInfo+=dbcon.userInfo(likeShopArr[i],shopInfo);
        }
        String[] shopLikeInfoArr=shopLikeInfo.split("]");
        likeSQL = new makeLike[shopLikeInfoArr.length];
        for (int i = 0; i < shopLikeInfoArr.length; i++) {
            if (shopLikeInfo.equals("")) {
                Toast.makeText(getApplicationContext(), " 尚未有收藏!", Toast.LENGTH_LONG).show();
                break;
            }
            String[]  shopLikeInfoArr2= shopLikeInfoArr[i].split(",");
            likeSQL[i] = new makeLike(likeShopArr[i], shopLikeInfoArr2[0], shopLikeInfoArr2[1],"", "");//評論資料
        }
        int btnId=0;
        for (makeLike point : likeSQL) {
            if (shopLikeInfo.equals("")) {

                break;
            }

            view = LayoutInflater.from(pageLike.this).inflate(R.layout.like_object, null);
            shopBox=findViewById(R.id.llSB);
            writerBox=findViewById(R.id.llWB);
            name=view.findViewById(R.id.txLikeName);
            acc=view.findViewById(R.id.txLikeAcc);
            head=view.findViewById(R.id.imgLike);
            btnLike=view.findViewById(R.id.btnLike);
            btnLike.setId(btnId);//將按鈕帶入id 以供監聽時辨識使用
            btnLike.setOnClickListener(check);
            btnId++;
            name.setText(point.account);
            acc.setText(point.name);
            head.setImageDrawable(loadImageFromURL(point.head));
            shopBox.addView(view);
        }

        userLike = dbcon.userInfo(user, userLikeU);
        String[] likeUserArr = userLike.split("]");
        String userLikeInfo="";
        for(int i=0;i<likeUserArr.length;i++){
            userLikeInfo+=dbcon.userInfo(likeUserArr[i],userInfo);
        }
        String[] userLikeInfoArr=userLikeInfo.split("]");
        likeSQLU = new makeLikeU[userLikeInfoArr.length];
        for (int i = 0; i < userLikeInfoArr.length; i++) {
            if (userLikeInfo.equals("")) {
                Toast.makeText(getApplicationContext(), " 尚未有收藏!", Toast.LENGTH_LONG).show();
                break;
            }
            String[]  userLikeInfoArr2= userLikeInfoArr[i].split(",");
            likeSQLU[i] = new makeLikeU(likeUserArr[0], userLikeInfoArr2[0], userLikeInfoArr2[2], userLikeInfoArr2[1],userLikeInfoArr2[4]);//評論資料
        }

        int btnId2=0;
        for (makeLikeU point : likeSQLU) {
            if (userLikeInfo.equals("")) {

                break;
            }
            view = LayoutInflater.from(pageLike.this).inflate(R.layout.like_object, null);
            shopBox=findViewById(R.id.llSB);
            writerBox=findViewById(R.id.llWB);
            name=view.findViewById(R.id.txLikeName);
            acc=view.findViewById(R.id.txLikeAcc);
            head=view.findViewById(R.id.imgLike);
            btnLike=view.findViewById(R.id.btnLike);
            btnLike.setId(btnId2);//將按鈕帶入id 以供監聽時辨識使用
            btnLike.setOnClickListener(check2);
            btnId2++;
            name.setText(point.account);
            acc.setText(point.name);
            head.setImageDrawable(loadImageFromURL(point.head));
            writerBox.addView(view);
        }




    }
    private Drawable loadImageFromURL(String url) {
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

    class makeLike {

        public String account,head,name,exp,nTitle;
        public makeLike( String i, String j,String k,String l,String m) {
            account=i;
            name=j;
            head=k;
            exp=l;
            nTitle=m;

        }


    }
    class makeLikeU {

        public String account,head,name,exp,nTitle;
        public makeLikeU( String i, String j,String k,String l,String m) {
            account=i;
            name=j;
            head=k;
            exp=l;
            nTitle=m;
        }


    }
    private View.OnClickListener check= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();

            Intent intent = new Intent();
            intent.setClass(pageLike.this, pageShop.class);
            startActivity(intent);
            pageShop.setName(likeSQL[id].account,user);


        }
    };
    private View.OnClickListener check2= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();
            Intent intent = new Intent();
            intent.setClass(pageLike.this, pageUser.class);
            startActivity(intent);
            pageUser.otherUser(likeSQLU[id].account);
            

        }
    };
    public static void setName(String i){
        user=i;

    }
}

