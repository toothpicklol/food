package com.foodmap;

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
import java.util.ArrayList;
import java.util.HashMap;

public class pageUser extends AppCompatActivity {

    Button btUserInfo,btUserLike,btUserMore,btUserCom,btnPost;
    LinearLayout ll,bgU;

    View buttonView,view,comment,accountGet;
    TextView account,title,text,username,userLV;
    ImageView bigHead,headC;
    EditText etAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_user);
        AlertDialog.Builder toolbar;
        buttonView = LayoutInflater.from(pageUser.this).inflate(R.layout.personal_object_button, null);
        accountGet = LayoutInflater.from(pageUser.this).inflate(R.layout.activity_main, null);


        ll = (LinearLayout)findViewById(R.id.ll_in_sv);

        btUserInfo = (Button)buttonView.findViewById(R.id.btnUserInfo);
        btUserLike = (Button)buttonView.findViewById(R.id.btnUserLike);
        btUserMore = (Button)buttonView.findViewById(R.id.btnUserMore);
        btUserCom = (Button)buttonView.findViewById(R.id.btnUserCom);

        account=findViewById(R.id.textView1);
        title=findViewById(R.id.textView2);
        text=findViewById(R.id.textView3);

        bgU =findViewById(R.id.userBg);
        bigHead=findViewById(R.id.bighead);
        headC =findViewById(R.id.headComment);
        userLV=findViewById(R.id.userLV);
        username=findViewById(R.id.username);
        btnPost =findViewById(R.id.btnPost);

        etAccount=findViewById(R.id.etAcc);





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
        InfoSQL[0]= new makeInfo("低能","99","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");//個人資料

        etAccount=accountGet.findViewById(R.id.etAcc);
        String user=etAccount.getText().toString();


        for (makeInfo point : InfoSQL) {

            comment=LayoutInflater.from(pageUser.this).inflate(R.layout.comment, null);
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
        ll.addView(buttonView);



        makeComment[] commentSQL = new makeComment[3];
        commentSQL[0] = new makeComment("25.067", "121.4971", "天龍國","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");//評論資料
        commentSQL[1] = new makeComment("25.068", "121.4972", "南部","null","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");
        commentSQL[2] = new makeComment("25.069", "121.4973", "地府","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");
        int btnId=0;
        for (makeComment point : commentSQL) {
            view = LayoutInflater.from(pageUser.this).inflate(R.layout.personal_object, null);


            account=view.findViewById(R.id.textView1);
            title=view.findViewById(R.id.textView2);
            text=view.findViewById(R.id.textView3);
            headC=view.findViewById(R.id.headComment);
            btnPost=view.findViewById(R.id.btnPost);



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

//        objectList = new ArrayList<HashMap>();
//
//        ll.removeAllViews();
//        ll.addView(comment);
//
//
//

    }
    private void setActions(){
        btUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btUserLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btUserCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btUserMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("666666666666666666666666666666666666666666");

            }
        });
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
}
