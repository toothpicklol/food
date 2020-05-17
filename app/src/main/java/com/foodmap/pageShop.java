package com.foodmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.URL;
import java.security.acl.Owner;

public class pageShop extends AppCompatActivity {


    private static String shopACC;
    private static String userAcc;
    String url="http://114.32.152.202/foodphp/shopcomment.php";
    String info="http://114.32.152.202/foodphp/shopinfo.php";
    String imgU="http://114.32.152.202/foodphp/userinfo.php";

    String insertL="http://114.32.152.202/foodphp/insertLikeShop.php";
    String selectL="http://114.32.152.202/foodphp/selectLikeShop.php";
    String updateL="http://114.32.152.202/foodphp/updateLikeShop.php";

    Button btnPost,btnOwner,btnLike;
    LinearLayout ll,bgU;

    View view,comment,shopText;
    TextView account,title,text,username,userLV;
    ImageView bigHead,headC;
    EditText etAccount;
    TextView pointS,commentC,message,shopInfo;
    makeComment[] commentSQL;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_shop);
        ll = (LinearLayout)findViewById(R.id.ll_in_sv);
        account=findViewById(R.id.textView1);
        title=findViewById(R.id.textView2);
        text=findViewById(R.id.textView3);
        bgU =findViewById(R.id.userBg);
        bigHead=findViewById(R.id.bighead);
        headC =findViewById(R.id.shopHead);
        userLV=findViewById(R.id.userLV);
        username=findViewById(R.id.username);
        btnPost =findViewById(R.id.btn_Post);
        etAccount=findViewById(R.id.etAcc);
        pointS=findViewById(R.id.shopPoint);
        commentC=findViewById(R.id.shopCommentCount);
        message=findViewById(R.id.shopMess);
        shopInfo=findViewById(R.id.shopinfo);
        btnOwner=findViewById(R.id.shopOwner);
        btnLike=findViewById(R.id.btnLike);

        addListView();
        setActions();


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
            commentSQL[i] = new makeComment(commentArr2[0], commentArr2[1], commentArr2[2],commentArr2[3],imgArr[3]);//評論資料


        }
        int btnId=0;
        for (makeComment point : commentSQL) {
            if(commentS.equals(shopACC))
            {
                break;

            }
            view = LayoutInflater.from(pageShop.this).inflate(R.layout.personal_object, null);
            account=view.findViewById(R.id.textView1);
            title=view.findViewById(R.id.textView2);
            text=view.findViewById(R.id.textView3);
            headC=view.findViewById(R.id.shopHead);
            btnPost=view.findViewById(R.id.btn_Post);
            btnPost.setId(btnId);//將按鈕帶入id 以供監聽時辨識使用
            btnId++;
            ll.addView(view);
            account.setText(point.account);
            headC.setImageDrawable(loadImageFromURL(point.head));
            title.setText(point.title);
            if(loadImageFromURL(point.picture)!=null){
                text.setBackground(loadImageFromURL(point.picture));


            }
            else{
                text.getLayoutParams().height = 100;
                text.setText(point.text);
            }
        }


    }

    class makeComment {

        public String text,title,account,picture,head;
        public makeComment( String i, String j,String k,String l,String n) {
            account=i;
            title=j;
            text=k;
            picture=l;
            head=n;
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

            }
        });


    }
    public static void setShopName(String i){
        shopACC=i;
    }

}
