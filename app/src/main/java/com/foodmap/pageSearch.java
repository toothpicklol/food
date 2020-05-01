package com.foodmap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.URL;

public class pageSearch extends AppCompatActivity {

    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,
             checkBox5,checkBox6,checkBox7,checkBox8,
             checkBox9,checkBox10,checkBox11,checkBox12,
             checkBox13,checkBox14;
    EditText search;
    Button go,shop;

    String tmp=" ";
    LinearLayout ll;
    View view;
    ImageView shopHead;
    TextView address,tel,shopName,point,comment,message,time;

    makeShop[] searchSQL = new makeShop[4];
    int searchL;


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
        shop=findViewById(R.id.btn_Shop);
        shopHead=findViewById(R.id.shopHead);
        address=findViewById(R.id.shopAddress);
        tel=findViewById(R.id.shopTel);
        shopName=findViewById(R.id.shopName);
        point=findViewById(R.id.shopPoint);
        comment=findViewById(R.id.shopCommentCount);
        message=findViewById(R.id.shopMess);
        time=findViewById(R.id.shopTime);



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
        ll = (LinearLayout)findViewById(R.id.ll_in_sv);

    }
    private CompoundButton.OnCheckedChangeListener checkBoxOnCheckedChange =
            new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                { //buttonView 為目前觸發此事件的 CheckBox, isChecked 為此 CheckBox 目前的選取狀態

                    if(isChecked)//等於 buttonView.isChecked()
                    {


                        if(searchL!=search.getText().length()){
                            tmp="";
                        }




                        if(search.getText().length()==0){

                            tmp+="'"+buttonView.getText().toString()+"'";

                        }
                        else
                        {
                            tmp+="AND"+"'"+buttonView.getText().toString()+"'";
                        }
                        search.setText(tmp);

                        searchL=search.getText().length();


                        Toast.makeText(getApplicationContext(),buttonView.getText()+" 被選取", Toast.LENGTH_LONG).show();









                    }

                }
            };
    private Button.OnClickListener goSearch = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            //SELECT Store_Name
            //FROM Store_Information
            //WHERE tag='日式'



            searchSQL[0] = new makeShop("感恩麵店","林森北路","0987974887","2:00","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","8","17","827");
            searchSQL[1] = new makeShop("感麵店","林北路","0987984887","5:00","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","7","87","872");
            searchSQL[2] = new makeShop("感恩麵","森北路","09879874887",":00","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","827","7","8127");
            searchSQL[3] = new makeShop("感恩店","林森路","097984887","25:0","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg","827","817","857");
            ll.removeAllViews();


            int btnId = 0;
            for (makeShop p : searchSQL) {
                view = LayoutInflater.from(pageSearch.this).inflate(R.layout.search_object, null);
                shop = view.findViewById(R.id.btn_Shop);
                shopHead = view.findViewById(R.id.shopHead);
                address = view.findViewById(R.id.shopAddress);
                tel = view.findViewById(R.id.shopTel);
                shopName = view.findViewById(R.id.shopName);
                point = view.findViewById(R.id.shopPoint);
                comment = view.findViewById(R.id.shopCommentCount);
                message = view.findViewById(R.id.shopMess);
                time = view.findViewById(R.id.shopTime);

                shop.setId(btnId);//將按鈕帶入id 以供監聽時辨識使用
                btnId++;
                shop.setOnClickListener(check);

                shopHead.setImageDrawable(loadImageFromURL(p.shopHead));
                address.setText(p.address);
                tel.setText(p.tel);
                shopName.setText(p.shopName);
                point.setText(p.point);
                comment.setText(p.comment);
                message.setText(p.message);
                time.setText(p.time);









                ll.addView(view);


            }
        }
    };

    private Drawable loadImageFromURL(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable draw = Drawable.createFromStream(is, "src");
            return draw;
        } catch (Exception e) {
            //TODO handle error
            System.out.println("erroooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooor");
            Log.i("loadingImg", e.toString());
            return null;
        }
    }
    class makeShop {

        public String shopName,shopHead,address,tel,time,message,point,comment;

        public makeShop( String i, String j,String k,String l,String m,String n,String o,String p) {
            shopName=i;
            address=j;
            tel=k;
            time=l;
            shopHead=m;
            message=n;
            comment=o;
            point=p;



        }


    }
    private View.OnClickListener check= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();

            System.out.println(searchSQL[id].shopName);
            Intent intent = new Intent();
            intent.setClass(pageSearch.this, pageShop.class);
            startActivity(intent);



        }
    };

}
