package com.foodmap;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.*;
import android.webkit.WebView;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.foodmap.richeditor.RichEditor;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class pageWatchPost extends AppCompatActivity {
    private static String headS,titleS,textS,accountS,idS,user,shop;
    private RichEditor mEditor;
    String likeUrl="http://114.32.152.202/foodphp/likeCount.php";
    String point="http://114.32.152.202/foodphp/pointInfo.php";
    String messageUrl="http://114.32.152.202/foodphp/message.php";
    String info="http://114.32.152.202/foodphp/userinfo.php";
    String checkLikeUrl="http://114.32.152.202/foodphp/likeCheck.php";
    String insertLike="http://114.32.152.202/foodphp/insertLike.php";
    String insertMessage="http://114.32.152.202/foodphp/insertMessage.php";
    String shopIdUrl="http://114.32.152.202/foodphp/searchShopId.php";
    View viewM;


    TextView account,title,text,good,bad,total,infoPoint,accountM,timeM,textM;
    ImageView messageOtherHead,postHead;
    ImageButton GP,BP,headM;
    Button otherUser,message;
    makeMessage[] messageSQL;
    String userInfo = dbcon.userInfo(user, info);
    final String[] infoArr = userInfo.split(",");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_watch_post);

        account = findViewById(R.id.txNickLv);
        title = findViewById(R.id.txTitle);
        text = findViewById(R.id.txText);
        postHead=findViewById(R.id.imgPostHead);
        good=findViewById(R.id.txGP);
        bad=findViewById(R.id.txBP);
        mEditor = (RichEditor) findViewById(R.id.editor);
        total=findViewById(R.id.txTotalPoint);
        infoPoint=findViewById(R.id.txPointInfo);
        GP=findViewById(R.id.imgBtnGP);
        BP=findViewById(R.id.imgBtnBP);
        otherUser=findViewById(R.id.btnPostUser);
        message=findViewById(R.id.btnMessage);
        messageOtherHead = findViewById(R.id.imgMessageHead);

        GP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!dbcon.likeCheck(idS,user,checkLikeUrl).equals("1")){

                    dbcon.insertLike(user,idS,"isLike",insertLike);
                    int count=Integer.parseInt(good.getText().toString())+1;
                    good.setText(Integer.toString(count));
                }
                else {
                    Toast.makeText(getApplicationContext(), "你已經按過讚或噓了!", Toast.LENGTH_LONG).show();
                }
            }
        });
        BP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(dbcon.likeCheck(idS,user,checkLikeUrl));

                if(!dbcon.likeCheck(idS,user,checkLikeUrl).equals("1")){

                    dbcon.insertLike(user,idS,"disLike",insertLike);
                    int count=Integer.parseInt(bad.getText().toString())+1;
                    bad.setText(Integer.toString(count));
                }
                else {
                    Toast.makeText(getApplicationContext(), "你已經按過讚或噓了!", Toast.LENGTH_LONG).show();
                }

            }
        });
        otherUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!accountS.equals(user)){
                    pageUser.otherUser(accountS);
                    Intent intent = new Intent();
                    intent.setClass(pageWatchPost.this, pageUser.class);
                    startActivity(intent);
                }
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlert();
            }
        });


        account.setText(accountS);
        title.setText(titleS);
        mEditor.setHtml(textS);
        postHead.setImageDrawable(loadImageFromURL(headS));
        mEditor.setInputEnabled(false);
        messageOtherHead.setImageDrawable(loadImageFromURL(infoArr[3]));

        String likeInfo=dbcon.likeCount(idS,likeUrl);
        String[]  like= likeInfo.split("]");
        good.setText(like[0]);
        bad.setText(like[1]);

        String pointInfo=dbcon.pointInfo(idS,point);
        infoPoint.setText(pointInfo);

        String[] pointTMP= pointInfo.split(",");

        String tmp="";
        int j=0;
        for(int i=0;i<pointTMP.length;i++){
            if(pointTMP[i].length()!=0){
                switch (i){
                    case 0:
                        tmp+="餐廳整潔度";
                        break;
                    case 1:
                        tmp+="菜單多樣性";
                        break;
                    case 2:
                        tmp+="「色」香味";
                        break;
                    case 3:
                        tmp+="色「香」味";
                        break;
                    case 4:
                        tmp+="色香「味」";
                        break;
                    case 5:
                        tmp+="餐廳裝潢";
                        break;
                    case 6:
                        tmp+="服務品質";
                        break;
                    case 7:
                        tmp+="商品價格";
                        break;
                    case 8:
                        tmp+="送餐速度";
                        break;
                    case 9:
                        tmp+="份量";
                        break;
                    case 10:
                        tmp+="餐廳氣氛";
                        break;
                    case 11:
                        tmp+="排隊時間";
                        break;
                    case 12:
                        total.setText("餐廳總分:"+pointTMP[i]+"分");
                        break;
                }
                if (i != 12) {
                    tmp+=":"+pointTMP[i]+"分"+"/";
                    j++;
                }
                if(j%3==0){
                    tmp+="\n";
                }

            }



        }
        infoPoint.setText(tmp);


    }
    public void setAlert(){
        viewM = LayoutInflater.from(pageWatchPost.this).inflate(R.layout.message_object, null);
        final Dialog dialog = new Dialog(pageWatchPost.this,R.style.MyDialog);
        dialog.setContentView(R.layout.messagebox);//指定自定義layout

        LinearLayout ll = (LinearLayout)dialog.findViewById(R.id.llDialog);
        ImageButton userHead=dialog.findViewById(R.id.imgBtnMessageUserHead);
        TextView txCount=dialog.findViewById(R.id.txMessageCount);
        ImageButton messagePost =dialog.findViewById(R.id.imgBtnMessagePost);
        userHead.setImageDrawable(loadImageFromURL(infoArr[3]));
        final EditText text=dialog.findViewById(R.id.edMessage);
        messagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shopId=dbcon.searchShopId(idS,shopIdUrl);
                Date Time = Calendar.getInstance().getTime();
                dbcon.insertMessage(idS,user,text.getText().toString(),Time.toString(),shopId,insertMessage);
                text.setText("");
                dialog.cancel();
                setAlert();
            }
        });
        int height = (int)(getResources().getDisplayMetrics().heightPixels);
        int width = (int)(getResources().getDisplayMetrics().widthPixels);

        int btnId = 0;

        String messageS = dbcon.messageInfo(idS, messageUrl);
        String[] messageArr = messageS.split("]");
        messageSQL = new makeMessage[messageArr.length];
        for (int i = 0; i < messageArr.length; i++) {
            if (messageS.equals(idS)) {
                break;
            }


            String tmp = messageArr[i];
            String[] messageArr2 = tmp.split(",");

            String userInfo1 = dbcon.userInfo(messageArr2[0], info);
            String[] infoArr1 = userInfo1.split(",");

            messageSQL[i] = new makeMessage(infoArr1[0], messageArr2[1], infoArr1[3], messageArr2[2],messageArr2[0]);

        }
        if(messageSQL[0]!=null){
            txCount.setText("留言("+messageSQL.length+")");
        }



        for (makeMessage point : messageSQL) {
            if (messageS.equals(idS)) {
                break;
            }
            viewM = LayoutInflater.from(pageWatchPost.this).inflate(R.layout.message_object, null);
            accountM = viewM.findViewById(R.id.txMessageAcc);

            textM = viewM.findViewById(R.id.txMessageText);
            headM = viewM.findViewById(R.id.imgBtnMessageHead);
            timeM=viewM.findViewById(R.id.txMessageTime);

            headM.setId(btnId);//將按鈕帶入id 以供監聽時辨識使用
            headM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageButton post =  (ImageButton)v; //在new 出所按下的按鈕
                    int id = post.getId();
                    if(!user.equals(messageSQL[id].account)){
                        pageUser.otherUser(messageSQL[id].account);
                        Intent intent = new Intent();
                        intent.setClass(pageWatchPost.this, pageUser.class);
                        startActivity(intent);
                    }

                }
            });

            btnId++;
            ll.addView(viewM);

            accountM.setText(point.nick);
            textM.setText(point.text);
            timeM.setText(point.time);
            headM.setImageDrawable(loadImageFromURL(point.head));



        }


        dialog.getWindow().setLayout(width, height);

        dialog.show();
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
    public static void setName(String i){
        user=i;

    }
    public static void setPost(String head,String title,String text,String account,String id){
        headS=head;
        titleS=title;
        textS=text;
        accountS=account;
        idS=id;

    }
    class makeMessage {

        public String text,account,head,time,nick;
        public makeMessage( String i, String j,String k,String l,String m) {
            nick=i;
            text=j;
            head=k;
            time=l;
            account=m;

        }


    }



}
