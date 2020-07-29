package com.foodmap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.URL;

public class pageUser extends AppCompatActivity {

    private static String user;
    private static String otherUser;
    private static String setMode="";
    Button btUserInfo,btUserLike,btUserMore,btUserCom,btnPost,follow,btnOtherUser;
    LinearLayout ll,bgU;

    View buttonView,view,comment,like;
    TextView account,title,text,username,userLV,fans,gold;
    ImageView bigHead,headC;
    String url="http://114.32.152.202/foodphp/comment.php";
    String info="http://114.32.152.202/foodphp/userinfo.php";
    String insertL="http://114.32.152.202/foodphp/insertLikeUser.php";
    String selectL="http://114.32.152.202/foodphp/selectLikeUser.php";
    String updateL="http://114.32.152.202/foodphp/updateLikeUser.php";
    String fansU="http://114.32.152.202/foodphp/fansCount.php";


    makeComment[] commentSQL;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_user);
        AlertDialog.Builder toolbar;
        buttonView = LayoutInflater.from(pageUser.this).inflate(R.layout.personal_object_button, null);
        like=LayoutInflater.from(pageUser.this).inflate(R.layout.follow_objext, null);

        ll = (LinearLayout)findViewById(R.id.ll_in_sv);

        btUserInfo = (Button)buttonView.findViewById(R.id.btnUserInfo);
        btUserLike = (Button)buttonView.findViewById(R.id.btnUserLike);
        btUserMore = (Button)buttonView.findViewById(R.id.btnUserMore);
        btUserCom = (Button)buttonView.findViewById(R.id.btnUserCom);
        follow=like.findViewById(R.id.btnFollow);
        btnPost =findViewById(R.id.btn_Post);
        fans=findViewById(R.id.txFans);
        gold=findViewById(R.id.txGoldGood);
        addListView();
        setActions();

    }
    @Override
    protected void onResume() {
        super.onResume();
        setMode="";
        // The activity has become visible (it is now "resumed").
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

    public void addListView(){

        if(!setMode.equals("other")) {


            makeInfo[] InfoSQL = new makeInfo[1];
            String userInfo = dbcon.userInfo(user, info);
            String[] infoArr = userInfo.split(",");
            InfoSQL[0] = new makeInfo(infoArr[0], infoArr[1], infoArr[2], infoArr[3], infoArr[4],infoArr[5]);
            String fansC=dbcon.userInfo(user,fansU);



            for (makeInfo point : InfoSQL) {

                comment = LayoutInflater.from(pageUser.this).inflate(R.layout.comment, null);
                bgU = comment.findViewById(R.id.userBg);
                bigHead = comment.findViewById(R.id.bighead);
                userLV = comment.findViewById(R.id.userLV);
                username = comment.findViewById(R.id.username);
                fans = comment.findViewById(R.id.txFans);
                gold = comment.findViewById(R.id.txGoldGood);
                ll.addView(comment);
                username.setText(point.username);
                fans.setText(fansC);
                gold.setText(point.gold);
                userLV.setText("等級" + point.userLV + "-" + point.title);
                bigHead.setImageDrawable(loadImageFromURL(point.bigHead));
                bgU.setBackground(loadImageFromURL(point.bg));


            }

            ll.addView(buttonView);
            String commentS = dbcon.comment(user, url);
            String[] commentArr = commentS.split("]");
            commentSQL = new makeComment[commentArr.length];
            for (int i = 0; i < commentArr.length; i++) {
                if (commentS.equals(user)) {
                    Toast.makeText(getApplicationContext(), " 尚未有評論，快去發文吧!", Toast.LENGTH_LONG).show();
                    break;
                }
                String tmp = commentArr[i];
                String[] commentArr2 = tmp.split(",");


                commentSQL[i] = new makeComment(commentArr2[0], commentArr2[1], commentArr2[2], commentArr2[3], infoArr[3],commentArr2[4],commentArr2[5]);//評論資料

            }


            int btnId = 0;
            for (makeComment point : commentSQL) {
                if (commentS.equals(user)) {
                    break;
                }
                view = LayoutInflater.from(pageUser.this).inflate(R.layout.personal_object, null);


                account = view.findViewById(R.id.txNickLv);
                title = view.findViewById(R.id.txTitle);
                text = view.findViewById(R.id.txText);
                headC = view.findViewById(R.id.userHead);
                btnPost = view.findViewById(R.id.btn_Post);
                btnOtherUser=view.findViewById(R.id.btnPostUser);
                btnOtherUser.setId(btnId);
                btnOtherUser.setOnClickListener(checkOtherUser);


                btnPost.setId(btnId);//將按鈕帶入id 以供監聽時辨識使用
                btnPost.setOnClickListener(check);

                btnId++;

                ll.addView(view);
                account.setText(point.account);
                headC.setImageDrawable(loadImageFromURL(point.head));
                title.setText(point.title);

                if (point.picture .equals("null")) {

                    text.getLayoutParams().height = 100;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        text.setText(Html.fromHtml(point.text, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        text.setText(Html.fromHtml(point.text));
                    }


                } else {
                    text.setBackground(loadImageFromURL(point.picture));


                }


            }
        }
        else{


            makeInfo[] InfoSQL = new makeInfo[1];
            String userInfo = dbcon.userInfo(otherUser, info);
            String[] infoArr = userInfo.split(",");
            InfoSQL[0] = new makeInfo(infoArr[0], infoArr[1], infoArr[2], infoArr[3], infoArr[4],infoArr[5]);
            String fansC=dbcon.userInfo(otherUser,fansU);


            for (makeInfo point : InfoSQL) {

                comment = LayoutInflater.from(pageUser.this).inflate(R.layout.comment, null);
                bgU = comment.findViewById(R.id.userBg);
                bigHead = comment.findViewById(R.id.bighead);
                userLV = comment.findViewById(R.id.userLV);
                username = comment.findViewById(R.id.username);
                fans = comment.findViewById(R.id.txFans);
                gold = comment.findViewById(R.id.txGoldGood);
                ll.addView(comment);

                fans.setText(fansC);
                gold.setText(point.gold);
                username.setText(point.username);
                userLV.setText("等級" + point.userLV + "-" + point.title);
                bigHead.setImageDrawable(loadImageFromURL(point.bigHead));
                bgU.setBackground(loadImageFromURL(point.bg));

            }
            ll.addView(like);
            String commentS = dbcon.comment(otherUser, url);
            String[] commentArr = commentS.split("]");
            commentSQL = new makeComment[commentArr.length];
            for (int i = 0; i < commentArr.length; i++) {
                if (commentS.equals(otherUser)) {
                    Toast.makeText(getApplicationContext(), "該作者尚未有評論", Toast.LENGTH_LONG).show();
                    break;
                }
                String tmp = commentArr[i];
                String[] commentArr2 = tmp.split(",");
                commentSQL[i] = new makeComment(commentArr2[0], commentArr2[1], commentArr2[2], commentArr2[3], infoArr[3],commentArr2[4],commentArr2[5]);//評論資料

            }


            int btnId = 0;
            for (makeComment point : commentSQL) {
                if (commentS.equals(otherUser)) {
                    break;
                }
                view = LayoutInflater.from(pageUser.this).inflate(R.layout.personal_object, null);
                account = view.findViewById(R.id.txNickLv);
                title = view.findViewById(R.id.txTitle);
                text = view.findViewById(R.id.txText);
                headC = view.findViewById(R.id.userHead);
                btnPost = view.findViewById(R.id.btn_Post);
                btnPost.setId(btnId);//將按鈕帶入id 以供監聽時辨識使用
                btnPost.setOnClickListener(check);

                btnId++;

                ll.addView(view);
                account.setText(point.account);
                headC.setImageDrawable(loadImageFromURL(point.head));
                title.setText(point.title);

                if (point.picture .equals("null")) {

                    text.getLayoutParams().height = 150;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        text.setText(Html.fromHtml(point.text, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        text.setText(Html.fromHtml(point.text));
                    }


                } else {
                    text.setBackground(loadImageFromURL(point.picture));


                }


            }
        }





    }
    private void setActions(){
        btUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(pageUser.this, pageUserInfo.class);
                startActivity(intent);
                pageUserInfo.setName(user);

            }
        });
        btUserLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(pageUser.this, pageLike.class);
                startActivity(intent);
                pageLike.setName(user);


                }




        });
        btUserCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageSocial.setName(user);
                Intent intent = new Intent();
                intent.setClass(pageUser.this, pageSocial.class);
                startActivity(intent);

            }
        });
        btUserMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dbcon.insertLikeShop(user,otherUser,selectL).equals(user)){
                    dbcon.insertLikeShop(user,otherUser,insertL);
                    Toast.makeText(getApplicationContext()," 已收藏!", Toast.LENGTH_LONG).show();
                }
                else {
                    dbcon.insertLikeShop(user,otherUser,updateL);
                    Toast.makeText(getApplicationContext()," 取消收藏!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }



    private View.OnClickListener check= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();
            pageWatchPost.setPost(commentSQL[id].head,commentSQL[id].title,commentSQL[id].text,commentSQL[id].account,commentSQL[id].postId);
            pageWatchPost.setName(user);
            Intent intent = new Intent();
            intent.setClass(pageUser.this, pageWatchPost.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener checkOtherUser= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();
            pageUser.otherUser(commentSQL[id].account);




        }
    };

    public static void setName(String i){
        user=i;
    }
    public static void otherUser(String i){
        otherUser=i;
        setMode="other";
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
    class makeInfo {

        public String username,userLV,bigHead,bg,title,gold;
        public makeInfo( String i, String j,String k,String l,String m,String n) {
            username=i;
            userLV=j;
            bigHead=k;
            bg=l;
            title=m;
            gold=n;


        }


    }

}
