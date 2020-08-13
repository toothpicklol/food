package com.foodmap;

import android.content.Intent;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Random;

public class pageCreateShop extends AppCompatActivity {

    public static double x,y;
    public static int lv;
    public static String user;
    String tmpAcc;

    public  static String url="http://114.32.152.202/foodphp/register.php";
    public  static String bgS=null;
    public  static String headS=null;
    String insertShop="http://114.32.152.202/foodphp/insertShop.php";
    TextView xShop,yShop,shopAcc;
    EditText oTime,cTime,address,tel,nameShop;

    ImageView bg,head;
    Button img;
    ImageButton create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_page_create_shop);
        xShop=findViewById(R.id.txShopX);
        yShop=findViewById(R.id.txShopY);
        shopAcc=findViewById(R.id.txShopAcc);
        oTime=findViewById(R.id.etOpen);
        cTime=findViewById(R.id.etClose);
        address=findViewById(R.id.etAddress);
        tel=findViewById(R.id.etTel);
        nameShop=findViewById(R.id.etNameShop);
        img=findViewById(R.id.btnChoseImg);
        create=findViewById(R.id.imgBtnCreate);

        if(lv>5){
            img.setOnClickListener(imgCheck);
            create.setOnClickListener(createCheck);
        }
        else {
            Toast.makeText(getApplicationContext(),"等級不足，請持續發文獲得經驗", Toast.LENGTH_LONG).show();
            finish();
        }

        head=findViewById(R.id.imgShopH);
        bg=findViewById(R.id.imgShopBg);
        xShop.setText(String.valueOf(x));
        yShop.setText(String.valueOf(y));
        tmpAcc=getRandomString(8);
        shopAcc.setText(tmpAcc);
        bg.setImageDrawable(Api.loadImageFromURL(bgS));
        head.setImageDrawable(Api.loadImageFromURL(headS));








    }
    public static void newXY(double i,double j){

        x=i;
        y=j;


    }
    public static void returnImg(String head,String bg){
        if(head==null&&bg!=null){
            bgS=bg;
        }
        else if(head!=null&&bg==null){
            headS=head;
        }



    }
    public static String getRandomString(int length){
        String str="abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sf=new StringBuffer();
        for(int i=0;i<length;i++)
        {
            int number=random.nextInt(62);//0~61
            sf.append(str.charAt(number));


        }
        return sf.toString();
    }
    public static void setName(String i,int j){
        user=i;
        lv=j;
    }

    private View.OnClickListener imgCheck= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            pageUpload.setMode(1);
            Intent intent = new Intent();
            intent.setClass(pageCreateShop.this, pageUpload.class);
            startActivity(intent);

        }
    };
    private View.OnClickListener createCheck= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(address.getText().toString().length()!=0&&nameShop.getText().toString().length()!=0&&bgS!=null&&headS!=null){
                String[] r=new String[]{shopAcc.getText().toString(),nameShop.getText().toString(),xShop.getText().toString(),yShop.getText().toString(),
                        tel.getText().toString(),oTime.getText().toString(),cTime.getText().toString(),"null",address.getText().toString(),headS,bgS};
                dbcon.insertShop(r,insertShop);
                bgS=null;
                headS=null;

                Intent intent = new Intent();
                intent.setClass(pageCreateShop.this, pageHome.class);
                startActivity(intent);
                finish();

            }
        }
    };




}
