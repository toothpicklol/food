package com.foodmap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.foodmap.richeditor.RichEditor;

public class pageWatchPost extends AppCompatActivity {
    private static String headS,titleS,textS,accountS,idS,user,shopS,nickS,timeS;
    private RichEditor mEditor;
    String likeUrl="http://114.32.152.202/foodphp/likeCount.php";
    String point="http://114.32.152.202/foodphp/pointInfo.php";
    String messageUrl="http://114.32.152.202/foodphp/message.php";
    String info="http://114.32.152.202/foodphp/userinfo.php";
    String checkLikeUrl="http://114.32.152.202/foodphp/likeCheck.php";
    String insertLike="http://114.32.152.202/foodphp/insertLike.php";
    String insertMessage="http://114.32.152.202/foodphp/insertMessage.php";
    String shopIdUrl="http://114.32.152.202/foodphp/searchShopId.php";
    String logUrl="http://114.32.152.202/foodphp/insertLog.php";
    String deleteLog="http://114.32.152.202/foodphp/delete.php";
    String updateMessage="http://114.32.152.202/foodphp/updateMessage.php";
    View viewM;


    TextView account,title,text,good,bad,total,infoPoint,accountM,timeM,textM;
    ImageView messageOtherHead,postHead;
    ImageButton GP,BP,headM,messageInfo;
    Button otherUser,message;
    makeMessage[] messageSQL;
    ScrollView sc;
    String userInfo = dbcon.userInfo(user, info);
    final String[] infoArr = userInfo.split(",");


    @SuppressLint("SetTextI18n")
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
        mEditor = findViewById(R.id.editor);
        total=findViewById(R.id.txTotalPoint);
        infoPoint=findViewById(R.id.txPointInfo);
        GP=findViewById(R.id.imgBtnGP);
        BP=findViewById(R.id.imgBtnBP);
        otherUser=findViewById(R.id.btnPostUser);
        message=findViewById(R.id.btnMessage);
        messageOtherHead = findViewById(R.id.imgMessageHead);
        sc=findViewById(R.id.sc);

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


        account.setText(nickS);
        title.setText("【評論】"+titleS);
        mEditor.setHtml(textS);
        postHead.setImageDrawable(Api.loadImageFromURL(headS));
        mEditor.setInputEnabled(false);

        if(mEditor.getHtml().length()<20){
            mEditor.getLayoutParams().height=500;

        }






        messageOtherHead.setImageDrawable(Api.loadImageFromURL(infoArr[3]));


        String likeInfo=dbcon.likeCount(idS,likeUrl);
        String[]  like= likeInfo.split("]");
        good.setText(like[0]);
        bad.setText(like[1]);

        String pointInfo=dbcon.pointInfo(idS,point);
        infoPoint.setText(pointInfo);

        String[] pointTMP= pointInfo.split(",");

        StringBuilder tmp= new StringBuilder();
        int j=0;
        for(int i=0;i<pointTMP.length;i++){
            if(pointTMP[i].length()!=0){
                switch (i){
                    case 0:
                        tmp.append("餐廳整潔度");
                        break;
                    case 1:
                        tmp.append("菜單多樣性");
                        break;
                    case 2:
                        tmp.append("「色」香味");
                        break;
                    case 3:
                        tmp.append("色「香」味");
                        break;
                    case 4:
                        tmp.append("色香「味」");
                        break;
                    case 5:
                        tmp.append("餐廳裝潢");
                        break;
                    case 6:
                        tmp.append("服務品質");
                        break;
                    case 7:
                        tmp.append("商品價格");
                        break;
                    case 8:
                        tmp.append("送餐速度");
                        break;
                    case 9:
                        tmp.append("份量");
                        break;
                    case 10:
                        tmp.append("餐廳氣氛");
                        break;
                    case 11:
                        tmp.append("排隊時間");
                        break;
                    case 12:
                        total.setText("餐廳總分:"+pointTMP[i]+"分");
                        break;
                }
                if (i != 12) {
                    tmp.append(":").append(pointTMP[i]).append("分").append("/");
                    j++;
                }
                if(j%3==0){
                    tmp.append("\n");
                }

            }



        }
        infoPoint.setText(tmp.toString());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.page_watch_post, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete:
                if(accountS.equals(user)){
                    delete(titleS+"/"+textS+"/"+timeS,1);
                }

                return true;
            case R.id.action_edit:
                if(accountS.equals(user)){
                    Intent intent = new Intent();
                    intent.setClass(pageWatchPost.this, pageEditor.class);
                    startActivity(intent);
                    pageEditor.setMode(1,titleS,textS,idS,user);
                    finish();

                }

                return true;
            case R.id.action_report:
                setAlertReport("null","report");

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setAlert(){
        viewM = LayoutInflater.from(pageWatchPost.this).inflate(R.layout.message_object, null);
        final Dialog dialog = new Dialog(pageWatchPost.this,R.style.MyDialog);
        dialog.setContentView(R.layout.messagebox);//指定自定義layout
        final int height = (getResources().getDisplayMetrics().heightPixels);
        final int width = (getResources().getDisplayMetrics().widthPixels);
        LinearLayout ll = dialog.findViewById(R.id.llDialog);
        ImageButton userHead=dialog.findViewById(R.id.imgBtnMessageUserHead);
        TextView txCount=dialog.findViewById(R.id.txMessageCount);
        ImageButton messagePost =dialog.findViewById(R.id.imgBtnMessagePost);
        userHead.setImageDrawable(Api.loadImageFromURL(infoArr[3]));
        final EditText text=dialog.findViewById(R.id.edMessage);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getWindow().setLayout(width, height/2);
            }
        });
        messagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shopId=dbcon.searchShopId(idS,shopIdUrl);

                dbcon.insertMessage(idS,user,text.getText().toString(),Api.Time(),shopId,insertMessage);
                text.setText("");
                dialog.cancel();
                setAlert();
            }
        });

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
            messageInfo=viewM.findViewById(R.id.imgBtnMessageInfo);
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
            messageInfo.setId(btnId);
            messageInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PopupMenu popup = new PopupMenu(pageWatchPost.this,v); //you can use image button
                    popup.getMenuInflater().inflate(R.menu.page_watch_post,popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = v.getId();
                            switch (item.getItemId()) {
                                case R.id.action_delete:
                                    if(messageSQL[id].account.equals(user)){
                                        delete(messageSQL[id].text+"/"+messageSQL[id].time,0);
                                    }
                                    return true;
                                case R.id.action_edit:
                                    if(messageSQL[id].account.equals(user)){
                                        String edit=messageSQL[id].account+"/"+idS;
                                        setAlertReport(edit,"edit");
                                    }
                                    return true;
                                case R.id.action_report:

                                    String report=messageSQL[id].account+"/"+idS;
                                    setAlertReport(report,"report");
                                    return true;

                                default:
                                    return true;
                            }
                        }
                    });
                    popup.show();

                }
            });

            btnId++;
            ll.addView(viewM);

            accountM.setText(point.nick);
            textM.setText(point.text);
            timeM.setText(point.time);
            headM.setImageDrawable(Api.loadImageFromURL(point.head));



        }
        dialog.getWindow().setLayout(width, height);
        dialog.show();
    }
    public void setAlertReport(final String x, final String type){

        final Dialog dialog = new Dialog(pageWatchPost.this,R.style.MyDialog);
        dialog.setContentView(R.layout.reportbox);
        final EditText report=dialog.findViewById(R.id.edReport);
        ImageButton reportBtn=dialog.findViewById(R.id.imgBtnReportSent);
        TextView alert=dialog.findViewById(R.id.txAlert);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String why=report.getText().toString();

                if(type.equals("report")){
                    if(!x.equals("null")){
                        dbcon.insertLog(user,x,why,"report",Api.Time(),logUrl);
                    }
                    else {
                        dbcon.insertLog(user,idS,why,"report",Api.Time(),logUrl);
                    }
                }
                else {

                    dbcon.insertLog(user,x,why,"edit/message",Api.Time(),logUrl);
                    dbcon.updateMessage(why,idS,updateMessage);
                    finish();
                }

                dialog.cancel();

            }
        });
        if(!type.equals("report")){
            alert.setText("編輯");
        }
        dialog.getWindow().setLayout(800,400);

        dialog.show();
    }
    public void delete(String x,int y){

        if(y==0){
            dbcon.insertLog(user,idS,x,"mess/delete",Api.Time(),logUrl);
            dbcon.delete("postmessage",user,idS,deleteLog);


        }
        else {
            dbcon.insertLog(user,idS,x,"post/delete",Api.Time(),logUrl);
            dbcon.delete("post",user,idS,deleteLog);
        }
        finish();

    }
    public static void setName(String i){
        user=i;

    }
    public static void setPost(String head,String title,String text,String account,String id,String nick,String time){
        headS=head;
        titleS=title;
        textS=text;
        accountS=account;
        idS=id;
        nickS=nick;
        timeS=time;



    }
    class  makeMessage {

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
