package com.foodmap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.URL;

public class pageShop extends AppCompatActivity {


    private static String shopACC;
    private static String userAcc;
    String url="http://114.32.152.202/foodphp/shopcomment.php";
    String info="http://114.32.152.202/foodphp/shopinfo.php";
    String imgU="http://114.32.152.202/foodphp/userinfo.php";
    String insertL="http://114.32.152.202/foodphp/insertLikeShop.php";
    String selectL="http://114.32.152.202/foodphp/selectLikeShop.php";
    String updateL="http://114.32.152.202/foodphp/updateLikeShop.php";
    Button btnPost,btnOwner,btnLike,btnOtherUser;
    LinearLayout ll,bgU;
    View view,comment,shopText;
    TextView account,title,text,username,userLV;
    ImageView bigHead,headC;
    TextView pointS,commentC,message,shopInfo;
    makeComment[] commentSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_shop);
        ll = (LinearLayout)findViewById(R.id.ll_in_sv);
        btnPost =findViewById(R.id.btn_Post);
        pointS=findViewById(R.id.shopPoint);
        commentC=findViewById(R.id.shopCommentCount);
        message=findViewById(R.id.shopMess);
        shopInfo=findViewById(R.id.shopinfo);
        btnOwner=findViewById(R.id.shopOwner);
        btnLike=findViewById(R.id.btnLike);

        addListView();
        setActions();
        FloatingActionButton fab = findViewById(R.id.fabPost);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(pageShop.this, pageEditor.class);
                startActivity(intent);
                pageEditor.setName(userAcc,shopACC);
                finish();


            }
        });


    }
    private Drawable loadImageFromURL(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable draw = Drawable.createFromStream(is, "src");
            return draw;
        } catch (Exception e) {
            //TODO handle error
            System.out.println("erroooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooor");
            Log.i("loadingImg", e.toString());
            return null;
        }
    }
    public void addListView(){
        makeInfo[] InfoSQL = new makeInfo[1];
        String userInfo=dbcon.userInfo(shopACC,info);
        String[] infoArr=userInfo.split(",");
        InfoSQL[0] = new makeInfo(infoArr[0], infoArr[6], infoArr[1],infoArr[2],infoArr[7],infoArr[4],infoArr[4]+"~"+infoArr[5],infoArr[9],infoArr[8],infoArr[10]);

        for (makeInfo point : InfoSQL) {

            comment=LayoutInflater.from(pageShop.this).inflate(R.layout.comment, null);
            bgU =comment.findViewById(R.id.userBg);
            bigHead=comment.findViewById(R.id.bighead);
            userLV=comment.findViewById(R.id.userLV);
            username=comment.findViewById(R.id.username);
            ll.addView(comment);
            username.setText(point.username);
            userLV.setText(point.userLV);
            bigHead.setImageDrawable(loadImageFromURL(point.bigHead));
            bgU.setBackground(loadImageFromURL(point.bg));
            shopText= LayoutInflater.from(pageShop.this).inflate(R.layout.shop_info, null);
            pointS=shopText.findViewById(R.id.shopPoint);
            commentC=shopText.findViewById(R.id.shopCommentCount);
            message=shopText.findViewById(R.id.shopMess);
            shopInfo=shopText.findViewById(R.id.shopinfo);
            btnOwner=shopText.findViewById(R.id.shopOwner);
            btnLike=shopText.findViewById(R.id.btnLike);
            btnOwner.setOnClickListener(check);
            btnLike.setOnClickListener(check);

            message.setText(point.message);
            commentC.setText(point.comment);
            pointS.setText(point.point);
            shopInfo.setText("營業時間:"+point.time+"\n"+"地址:"+point.address+"\n"+"電話:"+point.tel);




            ll.addView(shopText);



        }
        String commentS=dbcon.comment(shopACC,url);
        String[] commentArr=commentS.split("]");
        commentSQL = new makeComment[commentArr.length];



        for (int i=0; i<commentArr.length; i++) {
            if(commentS.equals(shopACC))
            {
                Toast.makeText(getApplicationContext()," 尚未有評論，快去發文吧!", Toast.LENGTH_LONG).show();
                break;

            }
            String tmp=commentArr[i];
            String[] commentArr2=tmp.split(",");
            String img=dbcon.userInfo(commentArr2[0],imgU);
            String[] imgArr=img.split(",");
            commentSQL[i] = new makeComment(commentArr2[0], commentArr2[1], commentArr2[2],commentArr2[3],imgArr[3],commentArr2[4]);//評論資料



        }
        int btnId=0;
        for (makeComment point : commentSQL) {
            if(commentS.equals(shopACC))
            {
                break;

            }
            view = LayoutInflater.from(pageShop.this).inflate(R.layout.personal_object, null);
            account=view.findViewById(R.id.txNickLv);
            title=view.findViewById(R.id.txTitle);
            text=view.findViewById(R.id.txText);
            headC=view.findViewById(R.id.userHead);
            btnPost=view.findViewById(R.id.btn_Post);
            btnPost.setId(btnId);//將按鈕帶入id 以供監聽時辨識使用
            btnOtherUser=view.findViewById(R.id.btnPostUser);
            btnOtherUser.setId(btnId);
            btnOtherUser.setOnClickListener(checkOtherUser);
            btnId++;
            btnPost.setOnClickListener(commCheck);
            ll.addView(view);
            account.setText(point.account);
            headC.setImageDrawable(loadImageFromURL(point.head));
            title.setText(point.title);
            if(!point.picture.equals("null")){
                text.setBackground(loadImageFromURL(point.picture));


            }
            else{

                text.getLayoutParams().height = 100;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    text.setText(Html.fromHtml(point.text, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    text.setText(Html.fromHtml(point.text));
                }

            }
        }


    }

    class makeComment {

        public String text,title,account,picture,head,postId;
        public makeComment( String i, String j,String k,String l,String n,String o) {
            account=i;
            title=j;
            text=k;
            picture=l;
            head=n;
            postId=o;
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
    private View.OnClickListener check= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(pageShop.this, pageShop.class);
            startActivity(intent);
        }
    };
    public static void setName(String i,String j){
        shopACC=i;
        userAcc=j;
    }

    private void setActions(){
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbcon.insertLikeShop(userAcc,shopACC,selectL).equals(userAcc)){
                    dbcon.insertLikeShop(userAcc,shopACC,insertL);
                    Toast.makeText(getApplicationContext()," 已收藏!", Toast.LENGTH_LONG).show();
                }
                else {
                    dbcon.insertLikeShop(userAcc,shopACC,updateL);
                    Toast.makeText(getApplicationContext()," 取消收藏!", Toast.LENGTH_LONG).show();
                }




            }
        });
        btnOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userInfo=dbcon.userInfo(shopACC,info);
                String[] infoArr=userInfo.split(",");
                String[] infoArr2=infoArr[11].split("]");
                if(!infoArr2[0].equals(userAcc)){
                    Uri web = Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLScjv-fExVswZw1KFeUdongUCM66KnEYuqFM_yHhIAX6hfHJQg/viewform?usp=sf_link");
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, web);
                    startActivity(webIntent);
                }



            }
        });


    }


    private View.OnClickListener commCheck= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();

            pageWatchPost.setPost(commentSQL[id].head,commentSQL[id].title,commentSQL[id].text,commentSQL[id].account,commentSQL[id].postId);
            pageWatchPost.setName(userAcc);
            Intent intent = new Intent();
            intent.setClass(pageShop.this, pageWatchPost.class);
            startActivity(intent);




        }
    };
    private View.OnClickListener checkOtherUser= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();
            if(!commentSQL[id].account.equals(userAcc)){
                pageUser.otherUser(commentSQL[id].account);
                Intent intent = new Intent();
                intent.setClass(pageShop.this, pageUser.class);
                startActivity(intent);
            }





        }
    };

}
