package com.foodmap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.foodmap.ui.home.HomeFragment;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class pageCreateShop extends AppCompatActivity {

    public static double x,y;
    public static String tmpAcc=getRandomString(8);

    public  static String url="http://114.32.152.202/foodphp/register.php";
    public  static String bgS=null;
    public  static String headS=null;
    String insertShop="http://114.32.152.202/foodphp/insertShop.php";
    TextView xShop,yShop,shopAcc;
    EditText oTime,cTime,address,tel,nameShop;
    public static String r=dbcon.dbReg(tmpAcc,url);
    ImageView bg,head;
    Button img,create;


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
        create=findViewById(R.id.btnCreatShop);
        img.setOnClickListener(imgCheck);
        create.setOnClickListener(createCheck);
        head=findViewById(R.id.imgShopH);
        bg=findViewById(R.id.imgShopBg);

        xShop.setText(String.valueOf(x));
        yShop.setText(String.valueOf(y));
        shopAcc.setText(r);
        bg.setImageDrawable(loadImageFromURL(bgS));
        head.setImageDrawable(loadImageFromURL(headS));


        System.out.println(x);
        System.out.println(y);


    }
    public static void newXY(double i,double j){

        x=i;
        y=j;


    }
    public static String getRandomString(int length)
    {
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
   public static boolean checkAcc(){

       if (r.equals(tmpAcc)){

           return true;

       }

       else{
           tmpAcc=getRandomString(8);
           checkAcc();
       }
       return false;

   }
    private View.OnClickListener imgCheck= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            pageUpload.setMode();
            Intent intent = new Intent();
            intent.setClass(pageCreateShop.this, pageUpload.class);
            startActivity(intent);
            finish();


        }
    };
    private View.OnClickListener createCheck= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(checkAcc()&&address.getText().toString().length()!=0&&nameShop.getText().toString().length()!=0&&bgS!=null&&headS!=null){
                String[] r=new String[]{shopAcc.getText().toString(),nameShop.getText().toString(),xShop.getText().toString(),yShop.getText().toString(),
                        tel.getText().toString(),oTime.getText().toString(),cTime.getText().toString(),"null",address.getText().toString(),headS,bgS};
                dbcon.insertShop(r,insertShop);

                Intent intent = new Intent();
                intent.setClass(pageCreateShop.this, pageHome.class);
                startActivity(intent);
                finish();


            }



        }
    };
    public static void returnImg(String head,String bg){
        if(head==null&&bg!=null){
            bgS=bg;
        }
        else if(head!=null&&bg==null){
            headS=head;
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
