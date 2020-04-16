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

public class pageShop extends AppCompatActivity {

    Button btUserInfo,btUserLike,btUserMore,btUserCom;
    LinearLayout ll,bg;

    View buttonView,view,comment,accountGet;
    TextView account,title,text,username,userLV;
    ImageView bigHead;
    EditText etAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_user);
        AlertDialog.Builder toolbar;
        buttonView = LayoutInflater.from(pageShop.this).inflate(R.layout.personal_object_button, null);
        accountGet = LayoutInflater.from(pageShop.this).inflate(R.layout.activity_main, null);


        ll = (LinearLayout)findViewById(R.id.ll_in_sv);

        btUserInfo = (Button)buttonView.findViewById(R.id.btnUserInfo);
        btUserLike = (Button)buttonView.findViewById(R.id.btnUserLike);
        btUserMore = (Button)buttonView.findViewById(R.id.btnUserMore);
        btUserCom = (Button)buttonView.findViewById(R.id.btnUserCom);

        account=findViewById(R.id.textView1);
        title=findViewById(R.id.textView2);
        text=findViewById(R.id.textView3);

        bg =findViewById(R.id.userBg);
        bigHead=findViewById(R.id.bighead);
        userLV=findViewById(R.id.userLV);
        username=findViewById(R.id.username);

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
            Log.i("loadingImg", e.toString());
            return null;
        }
    }


    public void addListView(){

        makeInfo[] InfoSQL = new makeInfo[1];
        InfoSQL[0]= new makeInfo("低能","99","0"," http://cdn.inside.com.tw/wp-content/uploads/2012/05/Chrome.jpg");//個人資料

        etAccount=accountGet.findViewById(R.id.etAcc);
        String user=etAccount.getText().toString();


        for (makeInfo point : InfoSQL) {

            comment=LayoutInflater.from(pageShop.this).inflate(R.layout.comment, null);
            bg =comment.findViewById(R.id.userBg);
            bigHead=comment.findViewById(R.id.bighead);
            userLV=comment.findViewById(R.id.userLV);
            username=comment.findViewById(R.id.username);
            ll.addView(comment);

            username.setText(point.username);
            userLV.setText("等級"+point.userLV);
            bigHead.setImageDrawable(loadImageFromURL(point.bigHead));

        }
        //ll.addView(buttonView);



        makeComment[] commentSQL = new makeComment[3];
        commentSQL[0] = new makeComment("25.067", "121.4971", "天龍國", "5");//評論資料
        commentSQL[1] = new makeComment("25.068", "121.4972", "南部", "5");
        commentSQL[2] = new makeComment("25.069", "121.4973", "地府", "5");

        for (makeComment point : commentSQL) {
            view = LayoutInflater.from(pageShop.this).inflate(R.layout.personal_object, null);


            account=view.findViewById(R.id.textView1);
            title=view.findViewById(R.id.textView2);
            text=view.findViewById(R.id.textView3);



            ll.addView(view);
            account.setText(point.account);
            title.setText(point.title);
            text.setText(point.text);



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
    }

    class makeComment {

        public String text,title,account,picture;
        public makeComment( String i, String j,String k,String l) {
            account=i;
            title=j;
            text=k;
            picture=l;


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
