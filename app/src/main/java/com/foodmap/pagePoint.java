package com.foodmap;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

public class pagePoint extends AppCompatActivity {
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,
            checkBox5,checkBox6,checkBox7,checkBox8,
            checkBox9,checkBox10,checkBox11,checkBox12;
    EditText editText1,editText2,editText3,editText4,editText5,
       editText6,editText7,editText8,editText9,editText10,editText11,editText12;
    ImageButton pointBtn;
    String pointUrl="http://114.32.152.202/foodphp/updatePoint.php";

    int count=0;
    float total=0;
    private static String user;
    private static String shop;
    private static String postID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_point);

        checkBox1 = (CheckBox)findViewById(R.id.cbPoint);
        checkBox2 = (CheckBox)findViewById(R.id.cbPoint1);
        checkBox3 = (CheckBox)findViewById(R.id.cbPoint2);
        checkBox4 = (CheckBox)findViewById(R.id.cbPoint3);
        checkBox5 = (CheckBox)findViewById(R.id.cbPoint4);
        checkBox6 = (CheckBox)findViewById(R.id.cbPoint5);
        checkBox7 = (CheckBox)findViewById(R.id.cbPoint6);
        checkBox8 = (CheckBox)findViewById(R.id.cbPoint7);
        checkBox9 = (CheckBox)findViewById(R.id.cbPoint8);
        checkBox10 = (CheckBox)findViewById(R.id.cbPoint9);
        checkBox11= (CheckBox)findViewById(R.id.cbPoint10);
        checkBox12 = (CheckBox)findViewById(R.id.cbPoint11);

        editText1=findViewById(R.id.edPoint);
        editText2=findViewById(R.id.edPoint1);
        editText3=findViewById(R.id.edPoint2);
        editText4=findViewById(R.id.edPoint3);
        editText5=findViewById(R.id.edPoint4);
        editText6=findViewById(R.id.edPoint5);
        editText7=findViewById(R.id.edPoint6);
        editText8=findViewById(R.id.edPoint7);
        editText9=findViewById(R.id.edPoint8);
        editText10=findViewById(R.id.edPoint9);
        editText11=findViewById(R.id.edPoint10);
        editText12=findViewById(R.id.edPoint11);


        pointBtn=findViewById(R.id.imgBtnPoint);
        pointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                over(checkBox1,editText1,'a');
                over(checkBox2,editText2,'b');
                over(checkBox3,editText3,'c');
                over(checkBox4,editText4,'d');
                over(checkBox5,editText5,'e');
                over(checkBox6,editText6,'f');
                over(checkBox7,editText7,'g');
                over(checkBox8,editText8,'h');
                over(checkBox9,editText9,'i');
                over(checkBox10,editText10,'j');
                over(checkBox11,editText11,'k');
                over(checkBox12,editText12,'l');
                dbcon.updatePoint(postID,"total",String.valueOf(total/count*1.0),pointUrl);
                finish();


            }
        });


    }
    public void over(CheckBox a ,EditText b,char c){



        if(a.isChecked()==true&&b.getText().toString()!=null&&b.getText().toString()!=""){



                if (Long.valueOf(b.getText().toString()) > 100) {
                    b.setText("100");

                } else if (Long.valueOf(b.getText().toString()) < 0) {
                    b.setText("0");
                }
                dbcon.updatePoint(postID, String.valueOf(c), b.getText().toString(), pointUrl);
                total += Integer.valueOf(b.getText().toString());
                count++;







        }
        else {
            b.setText(null);
        }


    }

    public static void setName(String userI,String shopI,String postIDI){
        user=userI;
        shop=shopI;
        postID=postIDI;


    }
}
