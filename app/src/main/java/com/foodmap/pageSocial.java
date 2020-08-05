package com.foodmap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class pageSocial extends AppCompatActivity {
    private static String user;
    String url="http://114.32.152.202/foodphp/socianComment.php";

    String imgU="http://114.32.152.202/foodphp/userinfo.php";
    Button btnPost,btnOtherUser;
    LinearLayout ll;
    View view;
    TextView account,title,text;
    ImageView headC;

    makeComment[] commentSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_social);
        ll = findViewById(R.id.ll);
        addList();
    }
    @SuppressLint({"InflateParams", "SetTextI18n"})
    public void addList(){
        String commentS=dbcon.comment(user,url);

        String[] commentArr=commentS.split("]");

        commentSQL = new makeComment[commentArr.length];
        for (int i=0; i<commentArr.length; i++) {
            if(commentS.equals(""))
            {
                Toast.makeText(getApplicationContext()," 尚未有收藏，快去收藏吧!", Toast.LENGTH_LONG).show();
                break;

            }
            //System.out.println(commentArr[i]);
            String tmp=commentArr[i];
            String[] commentArr2=tmp.split(",");
            String img=dbcon.userInfo(commentArr2[0],imgU);
            System.out.println(img);
            String[] imgArr=img.split(",");

            commentSQL[i] = new makeComment(commentArr2[0], commentArr2[1], commentArr2[2],commentArr2[3],imgArr[2],commentArr2[4],commentArr2[5],imgArr[0]);//評論資料



        }
        int btnId=0;
        for (makeComment point : commentSQL) {
            if(commentS.equals(""))
            {
                break;

            }
            view = LayoutInflater.from(pageSocial.this).inflate(R.layout.personal_object, null);
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
            account.setText(point.nick);
            headC.setImageDrawable(Api.loadImageFromURL(point.head));
            title.setText("【評論】"+point.title);
            if(!point.picture.equals("null")){
                text.setBackground(Api.loadImageFromURL(point.picture));
            }
            else{

                text.getLayoutParams().height = 250;
                text.setText(Html.fromHtml(point.text, Html.FROM_HTML_MODE_COMPACT));

            }
        }
    }

    private View.OnClickListener commCheck= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v;
            int id = post.getId();
            pageWatchPost.setPost(commentSQL[id].head,commentSQL[id].title,commentSQL[id].text,commentSQL[id].account,commentSQL[id].postId,commentSQL[id].nick,commentSQL[id].time);
            pageWatchPost.setName(user);
            Intent intent = new Intent();
            intent.setClass(pageSocial.this, pageWatchPost.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener checkOtherUser= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v;
            int id = post.getId();
            if(!commentSQL[id].account.equals(user)){
                pageUser.otherUser(commentSQL[id].account);
                Intent intent = new Intent();
                intent.setClass(pageSocial.this, pageUser.class);
                startActivity(intent);
            }
        }
    };
    public static void setName(String i){
        user=i;
    }
    class makeComment {

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
}
