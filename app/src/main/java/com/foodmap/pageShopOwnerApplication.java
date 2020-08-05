package com.foodmap;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;



public class pageShopOwnerApplication extends AppCompatActivity {
    TextView ownerInfo;
    EditText ownerTel,ownerAddress,ownerShopName;
    ImageButton btnOwnerPost;

    private static String shop;
    private static String user;
    String checkUrl="http://114.32.152.202/foodphp/insertCheckOwner.php";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_shop_onwer_application);

        ownerAddress=findViewById(R.id.edOwnerAddress);
        ownerInfo=findViewById(R.id.txOnwerCheck);
        ownerShopName=findViewById(R.id.edOwnerShopName);
        ownerTel=findViewById(R.id.edOwnerTel);
        btnOwnerPost=findViewById(R.id.imgBtnOwnerPost);
        ownerInfo.setText("使用者(您)"+user+"將申請為帳號為<"+shop+">的店主");
        btnOwnerPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkID="check"+pageCreateShop.getRandomString(10);
                dbcon.ownerCheck(checkID,shop,ownerShopName.getText().toString(),user,ownerTel.getText().toString(),ownerAddress.getText().toString(),"null",checkUrl);
                Toast.makeText(getApplicationContext()," 申請成功，請靜候驗證", Toast.LENGTH_LONG).show();
                finish();

            }
        });


    }
    public static void setName(String i,String j){
        shop=j;
        user=i;
    }
}
