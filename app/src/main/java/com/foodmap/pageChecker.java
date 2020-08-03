package com.foodmap;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class pageChecker extends AppCompatActivity {
    Button btnCheckList,btnCheckerSignUP;
    LinearLayout llChecker;
    checkList[] checkSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_checker);
        btnCheckerSignUP=findViewById(R.id.btnCheckerSignUp);
        btnCheckList=findViewById(R.id.btnCheckList);
        llChecker=findViewById(R.id.llChecker);
        View list= LayoutInflater.from(pageChecker.this).inflate(R.layout.checker_object, null);
        btnCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        btnCheckerSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
    class checkList {

        public String shopName,head,address;
        public checkList( String i, String j,String k) {
            shopName=i;
            head=j;
            address=k;

        }


    }
}
