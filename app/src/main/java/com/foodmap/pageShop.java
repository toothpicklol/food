package com.foodmap;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class pageShop extends AppCompatActivity {


    private static String shopACC;
    private static String userAcc;
    String url="http://114.32.152.202/foodphp/shopcomment.php";
    String info="http://114.32.152.202/foodphp/shopinfo.php";
    String imgU="http://114.32.152.202/foodphp/userinfo.php";
    String insertL="http://114.32.152.202/foodphp/insertLikeShop.php";
    String selectL="http://114.32.152.202/foodphp/selectLikeShop.php";
    String updateL="http://114.32.152.202/foodphp/updateLikeShop.php";
    String pointUrl="http://114.32.152.202/foodphp/shopPointInfo.php";
    String logUrl="http://114.32.152.202/foodphp/insertLog.php";
    String fansU="http://114.32.152.202/foodphp/fansCountShop.php";
    String updateShopInfo="http://114.32.152.202/foodphp/updateShopInfo.php";
    Button btnPost,btnOwner,btnLike,btnOtherUser,btnPointInfo;
    LinearLayout ll,bgU;
    View view,comment,shopText,viewP;
    TextView account,title,text,username,userLV,fan,good;
    ImageView bigHead,headC;
    TextView pointS,commentC,message,shopInfo;
    makeComment[] commentSQL;

    TextView pointBox,listBox,countBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_shop);
        ll = findViewById(R.id.ll_in_sv);
        addListView();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.page_shop, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_report:
                setAlertReport("report");

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private Drawable loadImageFromURL(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable draw = Drawable.createFromStream(is, "src");
            return draw;
        } catch (Exception e) {

            System.out.println("erroooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooor");
            Log.i("loadingImg", e.toString());
            return null;
        }
    }
    public void addListView(){
        makeInfo[] InfoSQL = new makeInfo[1];
        String userInfo=dbcon.userInfo(shopACC,info);
        String[] infoArr=userInfo.split(",");
        InfoSQL[0] = new makeInfo(infoArr[0], infoArr[6], infoArr[1],infoArr[2],infoArr[7],infoArr[3],infoArr[4]+"~"+infoArr[5],infoArr[9],infoArr[8],infoArr[10]);
        String fansC=dbcon.userInfo(shopACC,fansU);


        for (makeInfo point : InfoSQL) {

            comment=LayoutInflater.from(pageShop.this).inflate(R.layout.comment, null);
            bgU =comment.findViewById(R.id.userBg);
            bigHead=comment.findViewById(R.id.bighead);
            userLV=comment.findViewById(R.id.userLV);
            username=comment.findViewById(R.id.username);
            fan=comment.findViewById(R.id.txFans);
            good=comment.findViewById(R.id.txGold);
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
            btnPointInfo=shopText.findViewById(R.id.btn_pointinfo);

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
                    else {
                        setAlertEditShop();
                    }
                }
            });
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

            btnPointInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAlert();
                }
            });

            message.setText(point.message);
            commentC.setText(point.comment);
            pointS.setText(point.point);
            shopInfo.setText("營業時間:"+point.time+"\n"+"地址:"+point.address+"\n"+"電話:"+point.tel);
            fan.setText(fansC);
            good.setText("");




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
            commentSQL[i] = new makeComment(commentArr2[0], commentArr2[1], commentArr2[2],commentArr2[3],imgArr[3],commentArr2[4],commentArr2[5],imgArr[0]);//評論資料



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
            account.setText(point.nick);
            headC.setImageDrawable(loadImageFromURL(point.head));
            title.setText("【評論】"+point.title);
            if(!point.picture.equals("null")){
                text.setBackground(loadImageFromURL(point.picture));


            }
            else{

                text.getLayoutParams().height = 250;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    text.setText(Html.fromHtml(point.text, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    text.setText(Html.fromHtml(point.text));
                }

            }
        }


    }
    public void setAlert(){

        final Dialog dialog = new Dialog(pageShop.this,R.style.MyDialog);
        dialog.setContentView(R.layout.pointbox);//指定自定義layout
        LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.llPoint);
        int height = (int)(getResources().getDisplayMetrics().heightPixels);
        int width = (int)(getResources().getDisplayMetrics().widthPixels);

        String[]object=new String[]{"a","b","c","d","e","f","g","h","i","j","k","l"};

        for(int i=0;i<12;i++){
            viewP = LayoutInflater.from(pageShop.this).inflate(R.layout.point_object, null);


            if(dbcon.shopPointInfo(object[i],shopACC,pointUrl)!=null&&!dbcon.shopPointInfo(object[i],shopACC,pointUrl).equals("")){
                String tmp=dbcon.shopPointInfo(object[i],shopACC,pointUrl);
                String[] pointArr=tmp.split(",");

                System.out.println(dbcon.shopPointInfo(object[i],shopACC,pointUrl));

                System.out.println(pointArr[0]);
                System.out.println(pointArr.length);

                 pointBox=viewP.findViewById(R.id.txPointInfo);
                 listBox=viewP.findViewById(R.id.txPointList);
                 countBox=viewP.findViewById(R.id.txPointCount);
                ll.addView(viewP);

                listBox.setText(pointArr[0]);
                pointBox.setText(pointArr[1]+"分");
                if(Integer.parseInt(pointArr[2])>999){
                    countBox.setText(999+"+則評分");
                }
                else {
                    countBox.setText(pointArr[2]+"則評分");
                }






            }

        }








        dialog.getWindow().setLayout(width, height);

        dialog.show();
    }
    public void setAlertReport(final String type){

        final Dialog dialog = new Dialog(pageShop.this,R.style.MyDialog);
        dialog.setContentView(R.layout.reportbox);//指定自定義layout


        final EditText report=dialog.findViewById(R.id.edReport);
        ImageButton reportBtn=dialog.findViewById(R.id.imgBtnReportSent);
        TextView alert=dialog.findViewById(R.id.txAlert);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String why=report.getText().toString();
                SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date Time = Calendar.getInstance().getTime();
                dbcon.insertLog(userAcc,"shop"+shopACC,why,"report",Format.format(Time),logUrl);
                dialog.cancel();







            }
        });
        if(!type.equals("report")){
            alert.setText("編輯");
        }

        dialog.getWindow().setLayout(800,400);

        dialog.show();
    }
    public void setAlertEditShop(){

        final Dialog dialog = new Dialog(pageShop.this,R.style.MyDialog);
        dialog.setContentView(R.layout.shop_info_box);//指定自定義layout


        final EditText OTime=dialog.findViewById(R.id.edShopOTime);
        final EditText CTime=dialog.findViewById(R.id.edShopCTime);
        final EditText Tel=dialog.findViewById(R.id.edShopTel);
        final EditText Address=dialog.findViewById(R.id.edShopAdd);
        final EditText Info=dialog.findViewById(R.id.edShopInfo);
        ImageButton postBtn=dialog.findViewById(R.id.imgBtnSent);
        final CheckBox cbAdd=dialog.findViewById(R.id.cbAddres);
        final CheckBox cbTel=dialog.findViewById(R.id.cbTel);
        final CheckBox cbOTime=dialog.findViewById(R.id.cbOTime);
        final CheckBox cbCTime=dialog.findViewById(R.id.cbCTime);
        final CheckBox cbInfo=dialog.findViewById(R.id.cbInfo);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                over(cbAdd,Address,"address");
                over(cbCTime,CTime,"closeT");
                over(cbInfo,Info,"info");
                over(cbOTime,OTime,"openT");
                over(cbTel,Tel,"tel");

                dialog.cancel();

            }
        });
        int height = (int)(getResources().getDisplayMetrics().heightPixels);
        int width = (int)(getResources().getDisplayMetrics().widthPixels);


        dialog.getWindow().setLayout(width,height);

        dialog.show();
    }
    public void over(CheckBox a ,EditText b,String type) {
        if (a.isChecked() && b.getText().toString() != null && !b.getText().toString().equals("")) {
            dbcon.updateShopInfo(b.getText().toString(),shopACC,type,updateShopInfo);

        }



        //System.out.println("666666666666666666666666666666666666666666666");
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
    public static void setName(String i,String j){
        shopACC=i;
        userAcc=j;
    }
    private View.OnClickListener commCheck= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();
            pageWatchPost.setPost(commentSQL[id].head,commentSQL[id].title,commentSQL[id].text,commentSQL[id].account,commentSQL[id].postId,commentSQL[id].nick,commentSQL[id].time);
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
