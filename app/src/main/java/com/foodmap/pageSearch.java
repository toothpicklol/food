package com.foodmap;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class pageSearch extends AppCompatActivity {

    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,
             checkBox5,checkBox6,checkBox7,checkBox8,
             checkBox9,checkBox10,checkBox11,checkBox12,
             checkBox13,checkBox14;
    EditText search;
    Button go;

    String tmp=" ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_seach);

        checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox)findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox)findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox)findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox)findViewById(R.id.checkBox6);
        checkBox7 = (CheckBox)findViewById(R.id.checkBox7);
        checkBox8 = (CheckBox)findViewById(R.id.checkBox8);
        checkBox9 = (CheckBox)findViewById(R.id.checkBox9);
        checkBox10 = (CheckBox)findViewById(R.id.checkBox10);
        checkBox11= (CheckBox)findViewById(R.id.checkBox11);
        checkBox12 = (CheckBox)findViewById(R.id.checkBox12);
        checkBox13= (CheckBox)findViewById(R.id.checkBox13);
        checkBox14 = (CheckBox)findViewById(R.id.checkBox14);
        search=findViewById(R.id.etSearch);
        go=findViewById(R.id.btn_search);


        checkBox1.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox2.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox3.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox4.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox5.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox6.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox7.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox8.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox9.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox10.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox11.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox12.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox13.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        checkBox14.setOnCheckedChangeListener(checkBoxOnCheckedChange);
        go.setOnClickListener(goSearch);
    }
    private CompoundButton.OnCheckedChangeListener checkBoxOnCheckedChange =
            new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                { //buttonView 為目前觸發此事件的 CheckBox, isChecked 為此 CheckBox 目前的選取狀態
                    if(isChecked)//等於 buttonView.isChecked()
                    {
                        int count=0;
                        if(count!=0)
                        {
                            tmp+="'"+buttonView.getText().toString()+"'";

                        }
                        else {
                            tmp+="AND"+"'"+buttonView.getText().toString()+"'";
                            search.setText(tmp);

                            Toast.makeText(getApplicationContext(),buttonView.getText()+" 被選取", Toast.LENGTH_LONG).show();

                        }


                    }

                }
            };
    private Button.OnClickListener goSearch = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            //SELECT Store_Name
            //FROM Store_Information
            //WHERE tag='日式'



        }
    };

}
