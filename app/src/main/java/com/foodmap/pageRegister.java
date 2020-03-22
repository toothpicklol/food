package com.foodmap;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class pageRegister extends AppCompatActivity {

    TextView regAcc,regPass,regPassC,failAcc,failPass,failPass2,isNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_register);

        regAcc = findViewById(R.id.etResAcc);
        regPass =findViewById(R.id.etResPass);
        regPassC=findViewById(R.id.etResPassC);
        failAcc=findViewById(R.id.txFailAcc);
        failPass=findViewById(R.id.txFailPass);
        failPass2=findViewById(R.id.txFailPass2);
        isNull=findViewById(R.id.txNull);



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

    }

    public void confirm(View v) {

        failAcc.setVisibility(View.INVISIBLE);
        failPass.setVisibility(View.INVISIBLE);
        failPass2.setVisibility(View.INVISIBLE);
        isNull.setVisibility(View.INVISIBLE);

        if(regAcc.getText().toString().length()==0|regPass.getText().toString().length()==0|regPassC.getText().toString().length()==0)
        {
            isNull.setVisibility(View.VISIBLE);
        }
        else{
            if(passwordCheck(regPass.getText().toString()))
            {
                if(regPass.getText().toString().equals(regPassC.getText().toString())){


                }
                else
                {
                    failPass2.setVisibility(View.VISIBLE );
                }
            }
            else
            {
                failPass.setVisibility(View.VISIBLE );
            }

        }








    }
    public static boolean passwordCheck(String str) {
        boolean check = false;
        if(str.length() > 7) {
            System.out.println(str.length());

            System.out.println(str);
            boolean isDigit = false;
            boolean isLetter = false;

            for(int i = 0 ; i < str.length(); i++)
            {

                if(Character.isDigit(str.charAt(i)))
                {
                    isDigit = true;
                }
                if(Character.isLetter(str.charAt(i)))
                {

                    isLetter = true;
                }

                if(isDigit && isLetter)
                {
                    break;}
            }



            check = isDigit && isLetter ;
        }
        else{
            return false;
        }
        return check;


    }
}
