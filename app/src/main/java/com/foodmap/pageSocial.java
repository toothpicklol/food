package com.foodmap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.URL;

public class pageSocial extends AppCompatActivity {
    private static String user;
    String url="http://114.32.152.202/foodphp/socianComment.php";

    String imgU="http://114.32.152.202/foodphp/userinfo.php";
    Button btnPost,btnOwner,btnLike,btnOtherUser,btnPointInfo;
    LinearLayout ll,bgU;
    View view,comment,shopText,viewP;
    TextView account,title,text,username,userLV;
    ImageView bigHead,headC;
    TextView pointS,commentC,message,shopInfo;
    makeComment[] commentSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_social);
        ll = findViewById(R.id.ll);
        addList();
    }
    public void addList(){
        String commentS=dbcon.comment(user,url);

        String[] commentArr=commentS.split("]");

        commentSQL = new makeComment[commentArr.length];
        for (int i=0; i<commentArr.length; i++) {
            if(commentS.equals(""))
            {
                Toast.makeText(getApplicationContext()," 尚未有評論，快去收藏吧!", Toast.LENGTH_LONG).show();
                break;

            }
            //System.out.println(commentArr[i]);
            String tmp=commentArr[i];
            String[] commentArr2=tmp.split(",");
            String img=dbcon.userInfo(commentArr2[0],imgU);
            System.out.println(img);
            String[] imgArr=img.split(",");

            commentSQL[i] = new makeComment(commentArr2[0], commentArr2[1], commentArr2[2],commentArr2[3],imgArr[2],commentArr2[4],commentArr2[5]);//評論資料



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
    private View.OnClickListener commCheck= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();
            pageWatchPost.setPost(commentSQL[id].head,commentSQL[id].title,commentSQL[id].text,commentSQL[id].account,commentSQL[id].postId);
            pageWatchPost.setName(user);
            Intent intent = new Intent();
            intent.setClass(pageSocial.this, pageWatchPost.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener checkOtherUser= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
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

        public String text,title,account,picture,head,postId,time;
        public makeComment( String i, String j,String k,String l,String n,String o,String p) {
            account=i;
            title=j;
            text=k;
            picture=l;
            head=n;
            postId=o;
            time=p;
        }


    }
}
