package com.foodmap;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.foodmap.R;
import com.foodmap.ui.home.HomeFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class pageUser extends AppCompatActivity {

    Button newList;
    LinearLayout ll,bg;
    ArrayList<HashMap> objectList;
    View buttonView,view,comment;
    int post =10;//
    TextView account,title,text,username,userLV;
    ImageView bigHead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_user);
        AlertDialog.Builder toolbar;
        buttonView = LayoutInflater.from(pageUser.this).inflate(R.layout.personal_object_button, null);
        ll = (LinearLayout)findViewById(R.id.ll_in_sv);

        newList = (Button)buttonView.findViewById(R.id.info_dialog_new);

        account=findViewById(R.id.textView1);
        title=findViewById(R.id.textView2);
        text=findViewById(R.id.textView3);

        bg =findViewById(R.id.userBg);
        bigHead=findViewById(R.id.bighead);
        userLV=findViewById(R.id.userLV);
        username=findViewById(R.id.username);



        addListView();
        setActions();

    }


    public void addListView(){

        makeInfo[] InfoSQL = new makeInfo[1];
        InfoSQL[0]= new makeInfo("低能","99","0"," 1");//個人資料
        for (makeInfo point : InfoSQL) {

            comment=LayoutInflater.from(pageUser.this).inflate(R.layout.comment, null);
            bg =comment.findViewById(R.id.userBg);
            bigHead=comment.findViewById(R.id.bighead);
            userLV=comment.findViewById(R.id.userLV);
            username=comment.findViewById(R.id.username);
            ll.addView(comment);

            username.setText(point.username);
            userLV.setText("等級"+point.userLV);

        }




        makeComment[] commentSQL = new makeComment[3];
        commentSQL[0] = new makeComment("25.067", "121.4971", "天龍國", "5");//評論資料
        commentSQL[1] = new makeComment("25.068", "121.4972", "南部", "5");
        commentSQL[2] = new makeComment("25.069", "121.4973", "地府", "5");

        for (makeComment point : commentSQL) {
            view = LayoutInflater.from(pageUser.this).inflate(R.layout.personal_object, null);


            account=view.findViewById(R.id.textView1);
            title=view.findViewById(R.id.textView2);
            text=view.findViewById(R.id.textView3);



            ll.addView(view);
            account.setText(point.account);
            title.setText(point.title);
            text.setText(point.text);



        }

//        objectList = new ArrayList<HashMap>();
//
//        ll.removeAllViews();
//        ll.addView(comment);
//
//
//        ll.addView(buttonView);

    }
    private void setActions(){
        newList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post++;
                addListView();
            }
        });
    }

    class makeComment {

        public String text,title,account,picture;
        public makeComment( String i, String j,String k,String l) {
            account=i;
            title=j;
            text=k;
            picture=l;


        }


    }
    class makeInfo {

        public String username,userLV,bigHead,bg;
        public makeInfo( String i, String j,String k,String l) {
            username=i;
            userLV=j;
            bigHead=k;
            bg=l;


        }


    }
}
