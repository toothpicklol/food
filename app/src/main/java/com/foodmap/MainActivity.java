package com.foodmap;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    TextView account,password,loginfail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = findViewById(R.id.et_acc);
        password =findViewById(R.id.et_regAcc);
        loginfail=findViewById(R.id.txFail);
    }


    public void login(View v) {

        String acc = String.format(account.getText().toString());
        String pass = String.format(password.getText().toString());
        System.out.println(acc);
        if (acc.equals("admin")&&pass.equals("87879487")) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, pageComment.class);
            startActivity(intent);
        }
        else{
            loginfail.setVisibility(View.VISIBLE );

        }
    }
    public void register(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, pageRegister.class);
        startActivity(intent);



    }
}
