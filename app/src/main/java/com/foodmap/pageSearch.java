package com.foodmap;


import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class pageSearch extends AppCompatActivity {


    private static String user;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,
             checkBox5,checkBox6,checkBox7,checkBox8,
             checkBox9,checkBox10,checkBox11,checkBox12,
             checkBox13,checkBox14;
    EditText search;
    Button go,shop;
    String tmp="";
    LinearLayout ll;
    View view;
    ImageView shopHead;
    TextView address,tel,shopName,point,comment,message,time;
    RadioButton radShop,radWriter;
    makeShop[] searchSQL;
    makeUser[] userSQL;
    int searchL,setMode=0;
    String info="http://114.32.152.202/foodphp/shopsearch.php";
    String info2="http://114.32.152.202/foodphp/usersearch.php";



    ImageView headL;
    TextView nameL,accL;
    Button btnLikeL;
    View viewL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_seach);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        checkBox8 = findViewById(R.id.checkBox8);
        checkBox9 = findViewById(R.id.checkBox9);
        checkBox10 = findViewById(R.id.checkBox10);
        checkBox11= findViewById(R.id.checkBox11);
        checkBox12 = findViewById(R.id.checkBox12);
        checkBox13= findViewById(R.id.checkBox13);
        checkBox14 = findViewById(R.id.checkBox14);
        search=findViewById(R.id.etSearch);
        go=findViewById(R.id.btn_search);
        radShop=findViewById(R.id.radBtnShop);
        radWriter=findViewById(R.id.radBtnWriter);

        radShop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setMode=0;
                radShop.setChecked(true);
                radWriter.setChecked(false);
                search.setText("");
                search.setHint("選擇眾多選項,或輸入暱稱,帳號");
            }
        });

        radWriter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setMode=1;
                radShop.setChecked(false);
                radWriter.setChecked(true);
                search.setText("");
                search.setHint("輸入帳號或暱稱");
            }
        });



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
        ll = findViewById(R.id.ll_in_sv);

    }
    private CompoundButton.OnCheckedChangeListener checkBoxOnCheckedChange =
            new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                { //buttonView 為目前觸發此事件的 CheckBox, isChecked 為此 CheckBox 目前的選取狀態

                    if(isChecked){


                        if(searchL!=search.getText().length()){
                            tmp="";
                        }

                        if(search.getText().length()==0){

                            tmp+=buttonView.getText().toString();

                        }
                        else
                        {
                            tmp+=","+buttonView.getText().toString();
                        }
                        search.setText(tmp);

                        searchL=search.getText().length();


                    }

                }
            };

    private Button.OnClickListener goSearch = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {


            if (setMode == 0) {
                String searchShopInfo = "";
                String[] tmpArr = search.getText().toString().split(",");
                for (int i = 0; i < tmpArr.length; i++) {
                    searchShopInfo += dbcon.SearchShop(tmpArr[i], info);
                }
                String[] searchArr = searchShopInfo.split("]");

                List<String> list = new ArrayList<String>();
                for (int j = 0; j < searchArr.length; j++) {
                    if (!list.contains(searchArr[j])) {
                        list.add(searchArr[j]);
                    }
                }
                String[] searchArr3 = new String[list.size()];
                searchArr3 = list.toArray(searchArr3);
                searchSQL = new makeShop[searchArr3.length];


                for (int i = 0; i < searchArr3.length; i++) {
                    if (searchShopInfo.equals("")) {
                        Toast.makeText(getApplicationContext(), " 尚未有店家，快去發掘吧!", Toast.LENGTH_LONG).show();
                        break;
                    }
                    String tmp = searchArr3[i];
                    String[] searchArr2 = tmp.split(",");
                    searchSQL[i] = new makeShop(searchArr2[0], searchArr2[7], searchArr2[3], searchArr2[4] + "~" + searchArr2[5], searchArr2[1], searchArr2[8], searchArr2[9], searchArr2[10], searchArr2[11]);
                    ll.removeAllViews();
                }
                int btnId = 0;
                for (makeShop p : searchSQL) {
                    if (searchShopInfo.equals("")) {
                        break;
                    }
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
                    shopHead.setImageDrawable(Api.loadImageFromURL(p.shopHead));
                    address.setText(p.address);
                    tel.setText(p.tel);
                    shopName.setText(p.shopName);
                    point.setText(p.point);
                    comment.setText(p.comment);
                    message.setText(p.message);
                    time.setText(p.time);

                    ll.addView(view);


                }
                tmp = "";

            }
            else {
                String searchUser =dbcon.userInfo(search.getText().toString(),info2);
                String[] userArr = searchUser.split("]");
                userSQL = new makeUser[userArr.length];
                for (int i = 0; i < userArr.length; i++) {
                    if (searchUser.equals("")) {
                        Toast.makeText(getApplicationContext(), "找不到對應作者!", Toast.LENGTH_LONG).show();
                        break;
                    }
                    String tmp = userArr[i];
                    String[] searchArr2 = tmp.split(",");
                    System.out.println(tmp);
                    userSQL[i] = new makeUser(searchArr2[0], searchArr2[2], searchArr2[1], searchArr2[3], searchArr2[4]);
                    ll.removeAllViews();
                }


                int btnId=0;
                for (makeUser point : userSQL) {
                    if (searchUser.equals("")) {
                        break;
                    }

                    viewL = LayoutInflater.from(pageSearch.this).inflate(R.layout.like_object, null);

                    nameL=viewL.findViewById(R.id.txLikeName);
                    accL=viewL.findViewById(R.id.txLikeAcc);
                    headL=viewL.findViewById(R.id.imgLike);
                    btnLikeL=viewL.findViewById(R.id.btnLike);
                    btnLikeL.setId(btnId);//將按鈕帶入id 以供監聽時辨識使用
                    btnLikeL.setOnClickListener(checkL);
                    btnId++;
                    ll.addView(viewL);



                    accL.setText("LV:"+point.exp+"-"+point.nTitle+"-"+point.name);
                    nameL.setText(point.account);
                    headL.setImageDrawable(Api.loadImageFromURL(point.head));
                }
            }
        }

    };



    private View.OnClickListener check= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();

            Intent intent = new Intent();
            intent.setClass(pageSearch.this, pageShop.class);
            startActivity(intent);
            pageShop.setName(searchSQL[id].acc,user);

        }
    };
    private View.OnClickListener checkL= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();
            Intent intent = new Intent();
            intent.setClass(pageSearch.this, pageUser.class);
            startActivity(intent);
            pageUser.otherUser(userSQL[id].account);

        }
    };
    public static void setName(String i,String j){
        user=i;
    }
    class makeShop {

        public String shopName,shopHead,address,tel,time,message,point,comment,acc;

        public makeShop( String i, String j,String k,String l,String m,String n,String o,String p,String q) {
            shopName=i;
            address=j;
            tel=k;
            time=l;
            shopHead=m;
            message=n;
            comment=o;
            point=p;
            acc=q;



        }


    }
    class makeUser {

        public String account,head,name,exp,nTitle;
        public makeUser( String i, String j,String k,String l,String m) {
            account=i;
            name=j;
            head=k;
            exp=l;
            nTitle=m;

        }


    }




}
