package com.foodmap;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.foodmap.richeditor.RichEditor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class pageEditor extends AppCompatActivity {

    private RichEditor mEditor;
    private static String user;
    private static String id;
    private static String shop;
    private static int mode;
    private static String firstImg=null;
    private TextView mPreview;
    public static String postTitle;
    public static String postText;
    EditText title;
    ImageButton postBtn;
    String postUrl="http://114.32.152.202/foodphp/insertPost.php";
    String pointUrl="http://114.32.152.202/foodphp/insertPoint.php";
    String logUrl="http://114.32.152.202/foodphp/insertLog.php";
    String updatePostUrl="http://114.32.152.202/foodphp/updatePost.php";


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_editor);
        Toolbar toolbar = findViewById(R.id.postToolbar);
        setSupportActionBar(toolbar);
        postBtn=findViewById(R.id.imgBtnPost);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date Time = Calendar.getInstance().getTime();
                SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(mode==1){
                    dbcon.insertLog(user,id,title.getText().toString()+"/"+mEditor.getHtml(),"post/update",Format.format(Time),logUrl);
                    dbcon.updatePost(title.getText().toString(),mEditor.getHtml(),id,updatePostUrl);
                    finish();
                }
                else {

                    if(firstImg==null){
                        firstImg="null";
                    }
                    if(title.getText().toString().length()>=5&&mEditor.getHtml()!=null){
                        String postID="post"+pageCreateShop.getRandomString(10);
                        dbcon.insertPost(user,title.getText().toString(),mEditor.getHtml(),firstImg,Format.format(Time).toString(),shop,postID,postUrl);
                        dbcon.insertPoint(user,postID,shop,pointUrl);

                        pagePoint.setName(user,shop,postID);
                        Intent intent = new Intent();
                        intent.setClass(pageEditor.this, pagePoint.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext()," 標題字數需大於5字、內文不可為空", Toast.LENGTH_LONG).show();
                    }
                }



            }
        });
        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.BLACK);
        title=findViewById(R.id.etPTitle);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("內文");



        if(postTitle!=null){
            title.setText(postTitle);
        }

        if(postText!=null){
            mEditor.setHtml(postText);

        }

        mPreview = (TextView) findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });


        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBold();
            }
        });



        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });
        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });
        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });
        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });
        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                pageUpload.setMode(3);
                pageUpload.tmpPost(title.getText().toString(),mEditor.getHtml(),firstImg);
                pageUpload.setName(user);
                Intent intent = new Intent();
                intent.setClass(pageEditor.this, pageUpload.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

            }
        });

    }
    public static void postTmp(String title,String text,String firstImgI){
        postTitle=title;
        postText=text;
        if(firstImgI!=null){
            firstImg=firstImgI;
        }


    }
    public static void setName(String userI,String shopI){
        user=userI;
        shop=shopI;


    }

    public static void setMode(int i,String j,String k,String l,String m){
        mode=i;
        postTitle=j;
        postText=k;
        id=l;
        user=m;
    }

}
