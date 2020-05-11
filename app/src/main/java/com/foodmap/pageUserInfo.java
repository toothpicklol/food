package com.foodmap;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class pageUserInfo extends AppCompatActivity {
    Button change,changeC,back,changeBg;
    View view,accountGet;
    LinearLayout ll,llup;
    EditText oldPass,newPass,newPassC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_user_info);
        change=findViewById(R.id.btnPassChange);
        changeC=findViewById(R.id.btnchngePassC);
        changeBg=findViewById(R.id.btnChangeBg);
        change.setOnClickListener(check);
        changeBg.setOnClickListener(checkBg);

        ll=findViewById(R.id.ll);
        llup=findViewById(R.id.ll);

        oldPass=findViewById(R.id.oldP);
        newPass=findViewById(R.id.newP);
        newPassC=findViewById(R.id.newPC);
        back=findViewById(R.id.btnBack);



        view = LayoutInflater.from(pageUserInfo.this).inflate(R.layout.upadateinfo, null);
        accountGet = LayoutInflater.from(pageUserInfo.this).inflate(R.layout.activity_main, null);
    }
    private View.OnClickListener check= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ll.removeAllViews();
            llup=view.findViewById(R.id.ll);
            changeC=view.findViewById(R.id.btnchngePassC);
            oldPass=view.findViewById(R.id.oldP);
            newPass=view.findViewById(R.id.newP);
            newPassC=view.findViewById(R.id.newPC);
            back=view.findViewById(R.id.btnBack);

            ll.addView(llup);

            changeC.setOnClickListener(checkPass);
            back.setOnClickListener(checkBack);

        }
    };
    private View.OnClickListener checkPass= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(pageRegister.passwordCheck(newPass.getText().toString())&&newPassC.getText().toString()==newPass.getText().toString()){
                //update密碼

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

        }
    };

}
