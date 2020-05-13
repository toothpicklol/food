package com.foodmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class pageCreateShop extends AppCompatActivity {

    public static double x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_create_shop);
        System.out.println(x);
        System.out.println(y);
    }
    public static void newXY(double i,double j){

        x=i;
        y=j;





    }
}
