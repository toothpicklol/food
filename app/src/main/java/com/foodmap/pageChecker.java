package com.foodmap;

import android.app.Dialog;
import android.graphics.Color;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class pageChecker extends AppCompatActivity {
    public static String user;
    public static int LV;
    Button btnCheckList,btnCheckerSignUP;
    LinearLayout llChecker;
    checkList[] checkSQL;
    TextView status;
    String url="http://114.32.152.202/foodphp/checkList.php";
    String url2="http://114.32.152.202/foodphp/checkerList.php";
    String info="http://114.32.152.202/foodphp/shopinfo.php";
    String insertChecker="http://114.32.152.202/foodphp/insertChecker.php";
    String updateCheckerPass="http://114.32.152.202/foodphp/updateCheckerPass.php";
    String isChecker="http://114.32.152.202/foodphp/isChecker.php";
    String logUrl="http://114.32.152.202/foodphp/insertLog.php";
    String pass="http://114.32.152.202/foodphp/getCheckerPass.php";
    String updateChecker="http://114.32.152.202/foodphp/updateChecker.php";
    String isTrue="http://114.32.152.202/foodphp/isTrue.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_checker);
        btnCheckerSignUP=findViewById(R.id.btnCheckerSignUp);
        btnCheckList=findViewById(R.id.btnCheckList);
        llChecker=findViewById(R.id.llChecker);
        if(LV>5){
            btnCheckList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnCheckList.setBackgroundColor(Color.parseColor("#FFFF8800"));
                    btnCheckerSignUP.setBackgroundColor(Color.parseColor("#FAB731"));
                    checkerSignUp(url2,1);
                }
            });
            btnCheckerSignUP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnCheckerSignUP.setBackgroundColor(Color.parseColor("#FFFF8800"));
                    btnCheckList.setBackgroundColor(Color.parseColor("#FAB731"));
                    checkerSignUp(url,0);
                }
            });
            btnCheckerSignUP.setBackgroundColor(Color.parseColor("#FFFF8800"));
            checkerSignUp(url,0);
        }
        else {
            Toast.makeText(getApplicationContext(),"等級不足，請持續發文獲得經驗", Toast.LENGTH_LONG).show();
            finish();
        }

    }
    public  void checkerSignUp(String urlX,final int mode){
        llChecker.removeAllViews();

        String shopStr= dbcon.signUpChecker(user,urlX);
        String[] shopList=shopStr.split("]");
        System.out.println(shopStr);
        checkSQL = new checkList[shopList.length];
        for(int i=0;i<shopList.length;i++){
            if(shopList[0].equals("")){
                break;
            }
            String[] shopListInfo=shopList[i].split(",");
            checkSQL[i]=new checkList(shopListInfo[0],shopListInfo[1],shopListInfo[2],shopListInfo[3]);
        }

        int btnId = 0;
        for (final checkList point : checkSQL) {
            if(shopList[0].equals("")){
                break;
            }
            View list= LayoutInflater.from(pageChecker.this).inflate(R.layout.checker_object, null);

            TextView shopName = list.findViewById(R.id.txCheckShopName);
            TextView address = list.findViewById(R.id.txCheckShopAddress);
            status =list.findViewById(R.id.txCheckShopStatus);
            ImageView head = list.findViewById(R.id.imgCheckShopHead);
            ImageButton btnAdd = list.findViewById(R.id.imgBtnCheckShopAdd);

            btnAdd.setId(btnId);

            btnId++;
            String userInfo=dbcon.userInfo(point.shopAcc,info);
            String[] infoArr=userInfo.split(",");

            llChecker.addView(list);
            shopName.setText(point.shopName);
            head.setImageDrawable(Api.loadImageFromURL(infoArr[1]));
            address.setText(point.address);
            status.setText("報名中");
            if(dbcon.isChecker(point.checkId,user,isChecker).equals("1")){
                status.setText("中籤");
            }

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mode==0){
                        String result=dbcon.insertChecker(user,point.checkId,insertChecker);
                        if(result.equals("0")){
                            Toast.makeText(getApplicationContext()," 你已報過名~請靜候抽籤!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext()," 報名成功!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        if(dbcon.isChecker(point.checkId,user,isChecker).equals("1")){

                            if(!dbcon.isChecker(point.checkId,user,isTrue).equals("0")){
                                status.setText("已驗證");
                                Toast.makeText(getApplicationContext(),"你已對該店進行過驗證!", Toast.LENGTH_LONG).show();
                            }
                            else {
                                setAlertChecker(point.checkId);
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext()," 報名人數到達會統一抽籤~請稍等!", Toast.LENGTH_LONG).show();
                        }

                    }



                }
            });
        }

    }

    public static String getRandomString(int length){
        String str="0123456789";
        Random random=new Random();
        StringBuffer sf=new StringBuffer();
        for(int i=0;i<length;i++)
        {
            int number=random.nextInt(10);//0~61
            sf.append(str.charAt(number));


        }
        return sf.toString();
    }
    public void setAlertChecker(final String id){

        final Dialog dialog = new Dialog(pageChecker.this,R.style.MyDialog);
        dialog.setContentView(R.layout.checkerbox);//指定自定義layout
        final EditText checkerWhy=dialog.findViewById(R.id.edCheckerWhy);
        Button btnCheckerConfirm=dialog.findViewById(R.id.btnCheckerConfirm);
        final CheckBox cbCheckerTrue=dialog.findViewById(R.id.cbCheckerTrue);
        final CheckBox cbCheckerFalse=dialog.findViewById(R.id.cbCheckerFalse);
        int height = (getResources().getDisplayMetrics().heightPixels);
        int width = (getResources().getDisplayMetrics().widthPixels);

        cbCheckerTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbCheckerFalse.setChecked(false);

            }
        });
        cbCheckerFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbCheckerTrue.setChecked(false);
            }
        });

        btnCheckerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((cbCheckerTrue.isChecked()||cbCheckerFalse.isChecked())&&!checkerWhy.getText().toString().equals("")){

                    if(cbCheckerTrue.isChecked()){




                            dbcon.updateCheckerPass(user+"/"+getRandomString(8),id,updateCheckerPass);
                            dbcon.insertLog(user,id,checkerWhy.getText().toString(),"checkShop",Api.Time(),logUrl);
                            dialog.cancel();
                            setAlertCheckerEdit(id);



                    }




                }
                else {
                    Toast.makeText(getApplicationContext(),"請寫理由!", Toast.LENGTH_LONG).show();

                }
            }
        });

        dialog.getWindow().setLayout(width,height/2);

        dialog.show();
    }
    public void setAlertCheckerEdit(final String id){

        final Dialog dialog = new Dialog(pageChecker.this,R.style.MyDialog);
        dialog.setContentView(R.layout.checkerbox_pass);//指定自定義layout
        final EditText checkerPass=dialog.findViewById(R.id.edCheckerPass);
        Button btnCheckerPass=dialog.findViewById(R.id.btnCheckerPass);

        int height = (int)(getResources().getDisplayMetrics().heightPixels);
        int width = (int)(getResources().getDisplayMetrics().widthPixels);

        btnCheckerPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((user+"/"+checkerPass.getText().toString()).equals(dbcon.getCheckerPass(id,pass))){

                    Toast.makeText(getApplicationContext(),"驗證成功", Toast.LENGTH_LONG).show();
                    dbcon.updateChecker(id,user,updateChecker);
                    dialog.cancel();





                }
                else {
                    Toast.makeText(getApplicationContext(),"驗證失敗或重新驗證一次", Toast.LENGTH_LONG).show();
                }
            }
        });





        dialog.getWindow().setLayout(width,height/2);

        dialog.show();
    }
    class checkList {

        public String shopName,address,shopAcc,checkId;
        public checkList( String i, String j,String k,String l) {
            checkId=i;
            shopAcc=j;
            shopName=k;
            address=l;

        }


    }
    public static void setName(String i,int j){
        user=i;
        LV=j;
    }
}
