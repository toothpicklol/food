package com.foodmap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class pageUserInfo extends AppCompatActivity {
    private static String user;
    Button change,changeC,back,changeBg,changeNick,changeNickC;
    View view,viewN;
    LinearLayout ll,llUpPass,llUpNick;
    EditText oldPass,newPass,newPassC,newNick;
    Spinner title;
    String url="http://114.32.152.202/foodphp/title.php";
    String updateN="http://114.32.152.202/foodphp/updateNickTitle.php";
    String updatePass="http://114.32.152.202/foodphp/updatePass.php";
    String info="http://114.32.152.202/foodphp/userinfo.php";
    String emailU="http://114.32.152.202/foodphp/getEmail.php";
    String commentU="http://114.32.152.202/foodphp/getCommentCount.php";
    ImageView head;
    LinearLayout userBg;

    TextView name,email,lv,titleS,acc,commentC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_user_info);
        change=findViewById(R.id.btnPassChange);
        changeC=findViewById(R.id.btnchngePassC);
        changeBg=findViewById(R.id.btnChangeBg);
        changeNick=findViewById(R.id.btnUpdateN);

        change.setOnClickListener(check);
        changeBg.setOnClickListener(checkBg);
        changeNick.setOnClickListener(nick);

        ll=findViewById(R.id.ll);
        llUpPass=findViewById(R.id.ll);
        llUpNick=findViewById(R.id.ll);
        userBg=findViewById(R.id.userBgI);

        oldPass=findViewById(R.id.oldP);
        newPass=findViewById(R.id.newP);
        newPassC=findViewById(R.id.newPC);
        back=findViewById(R.id.btnBack);

        newNick=findViewById(R.id.etNick);
        changeNickC=findViewById(R.id.btnChangeNickC);
        title=findViewById(R.id.spinner);

        name=findViewById(R.id.txNick);
        email=findViewById(R.id.txEmail);
        lv=findViewById(R.id.txLv);
        titleS=findViewById(R.id.txTitle);
        acc=findViewById(R.id.txAcc);
        commentC=findViewById(R.id.txCommentCount);
        head=findViewById(R.id.imgbigheadI);






        //makeInfo[] InfoSQL = new makeInfo[1];
        String userInfo=dbcon.userInfo(user,info);
        String[] infoArr=userInfo.split(",");
        //InfoSQL[0] = new makeInfo(infoArr[0], infoArr[1], infoArr[2],infoArr[3],infoArr[4]);

        acc.setText("帳號:"+user);

        name.setText("暱稱:"+infoArr[0]);
        lv.setText("等級:"+infoArr[1]);
        head.setImageDrawable(loadImageFromURL(infoArr[2]));
        userBg.setBackground(loadImageFromURL(infoArr[3]));
        titleS.setText("稱號:"+infoArr[4]);

        String x=dbcon.userInfo(user,emailU);

        email.setText("Email:"+x);

        String y=dbcon.userInfo(user,commentU);
        commentC.setText("評論數量:"+y);









        view = LayoutInflater.from(pageUserInfo.this).inflate(R.layout.upadateinfo, null);
        viewN = LayoutInflater.from(pageUserInfo.this).inflate(R.layout.update_nick, null);
    }
    private View.OnClickListener check= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ll.removeAllViews();
            llUpPass=view.findViewById(R.id.ll);
            changeC=view.findViewById(R.id.btnchngePassC);
            oldPass=view.findViewById(R.id.oldP);
            newPass=view.findViewById(R.id.newP);
            newPassC=view.findViewById(R.id.newPC);
            back=view.findViewById(R.id.btnBack);
            ll.addView(llUpPass);
            changeC.setOnClickListener(checkPass);
            back.setOnClickListener(checkBack);

        }
    };
    private View.OnClickListener checkPass= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(pageRegister.passwordCheck(newPass.getText().toString())&&newPassC.getText().toString()==newPass.getText().toString()){
                //update密碼
                dbcon.updatePass(user,newPassC.getText().toString(),updatePass);

            }

        }
    };
    private View.OnClickListener checkBack= new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
        }
    };
    private View.OnClickListener checkBg= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(pageUserInfo.this, pageUpload.class);
            startActivity(intent);
            pageUpload.setName(user);

        }
    };
    private View.OnClickListener nick= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ll.removeAllViews();
            llUpNick=viewN.findViewById(R.id.ll);

            newNick=viewN.findViewById(R.id.etNick);
            changeNickC=viewN.findViewById(R.id.btnChangeNickC);
            title=viewN.findViewById(R.id.spinner);
            back=viewN.findViewById(R.id.btnBack);

            String userTitle=dbcon.title(user,url);
            String[] titleArr=userTitle.split("]");
            final List<String> plantsList = new ArrayList<>(Arrays.asList(titleArr));

            // Initializing an ArrayAdapter
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    pageUserInfo.this,R.layout.spinner_item,plantsList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            title.setAdapter(spinnerArrayAdapter);
            ll.addView(llUpNick);
            changeNickC.setOnClickListener(nickC);

        }
    };

    private View.OnClickListener nickC= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String newN=newNick.getText().toString();
            String text = title.getSelectedItem().toString();
            dbcon.updateTitle(user,text,newN,updateN);

            Intent intent = new Intent();
            intent.setClass(pageUserInfo.this, pageHome.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext()," 更改成功", Toast.LENGTH_LONG).show();

        }
    };
    public static void setName(String i){
        user=i;
    }
    class makeInfo {

        public String username,userLV,bigHead,bg,title;
        public makeInfo( String i, String j,String k,String l,String m) {
            username=i;
            userLV=j;
            bigHead=k;
            bg=l;
            title=m;


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


}
