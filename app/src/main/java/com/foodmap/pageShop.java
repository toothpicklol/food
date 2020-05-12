package com.foodmap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.URL;
import java.security.acl.Owner;

public class pageShop extends AppCompatActivity {


    private static String pointS,messageS,commentS,textS,nameS;
    Button btnPost,btnOwner,btnLike;
    LinearLayout ll,bgU;

    View view,comment,accountGet,shopText;
    TextView account,title,text,username,userLV;
    ImageView bigHead,headC;
    EditText etAccount;
    TextView point,commentC,message,shopInfo;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_shop);


        accountGet = LayoutInflater.from(pageShop.this).inflate(R.layout.activity_main, null);

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

        point=findViewById(R.id.shopPoint);
        commentC=findViewById(R.id.shopCommentCount);
        message=findViewById(R.id.shopMess);
        shopInfo=findViewById(R.id.shopinfo);
        btnOwner=findViewById(R.id.shopOwner);
        btnLike=findViewById(R.id.btnLike);






        addListView();


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
    public static void  getInfo(String point,String name,String comment,String address,String time,String tel,String message,String imageU){
        pointS=point;
        messageS=message;
        commentS=comment;
        textS="營業時間:"+time+"\n"+"地址:"+address+"\n"+"電話:"+tel;
        nameS=name;

    }

    public void addListView(){

        makeInfo[] InfoSQL = new makeInfo[1];
        InfoSQL[0]= new makeInfo("牛肉麵","87","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");//個人資料

        etAccount=accountGet.findViewById(R.id.etAcc);



        for (makeInfo point : InfoSQL) {

            comment=LayoutInflater.from(pageShop.this).inflate(R.layout.comment, null);
            bgU =comment.findViewById(R.id.userBg);
            bigHead=comment.findViewById(R.id.bighead);
            userLV=comment.findViewById(R.id.userLV);
            username=comment.findViewById(R.id.username);
            ll.addView(comment);

            username.setText(point.username);
            userLV.setText("等級"+point.userLV);
            bigHead.setImageDrawable(loadImageFromURL(point.bigHead));
            bgU.setBackground(loadImageFromURL(point.bg));

        }

        shopText= LayoutInflater.from(pageShop.this).inflate(R.layout.shop_info, null);
        point=shopText.findViewById(R.id.shopPoint);
        commentC=shopText.findViewById(R.id.shopCommentCount);
        message=shopText.findViewById(R.id.shopMess);
        shopInfo=shopText.findViewById(R.id.shopinfo);
        btnOwner=shopText.findViewById(R.id.shopOwner);
        btnLike=shopText.findViewById(R.id.btnLike);

        point.setText(pointS);
        commentC.setText(commentS);
        message.setText(messageS);
        shopInfo.setText(textS);
        btnOwner.setOnClickListener(check);
        btnLike.setOnClickListener(check);




        ll.addView(shopText);





        makeComment[] commentSQL = new makeComment[3];
        commentSQL[0] = new makeComment("25.067", "121.4971", "天龍國","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");//評論資料
        commentSQL[1] = new makeComment("25.068", "121.4972", "南部","null","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");
        commentSQL[2] = new makeComment("25.069", "121.4973", "地府","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");
        int btnId=0;
        for (makeComment point : commentSQL) {
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
            if(point.picture=="null")
            {
                text.setText(point.text);

            }
            else
                text.setBackground(loadImageFromURL(point.picture));
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

        public String username,userLV,bigHead,bg;
        public makeInfo( String i, String j,String k,String l) {
            username=i;
            userLV=j;
            bigHead=k;
            bg=l;


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
}
