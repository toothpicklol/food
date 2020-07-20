package com.foodmap;


import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.text.*;
import android.text.style.ImageSpan;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.URL;

public class pagePost extends AppCompatActivity {
    EditText title,text;
    ImageButton imageB;
    public static String postTitle;
    public static String postText;
    String[] img;
    int imgC=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_post);
        title=findViewById(R.id.etPTitle);
        //text=findViewById(R.id.etPText);
        imageB=findViewById(R.id.imageButton);
        imageB.setOnClickListener(insertImage);

        if(postTitle!=null){
            title.setText(postTitle);
        }

        if(postText!=null){
            text.setText(postText);

            for(int i=0;i<text.getText().toString().length();i++)
            {


                String imageURL=text.getText().toString().substring(text.getText().toString().indexOf("[img=",i)+5,text.getText().toString().indexOf("]",i));

                i=text.getText().toString().indexOf("]",i);
                System.out.println(imageURL);
                //String img =new [10];
                //imgC++;
                //addImageInEditText(loadImageFromURL(imageURL));




            }



//            if (text.getText().toString().indexOf("[img=") != -1){
//                String imageURL=text.getText().toString().substring(text.getText().toString().indexOf("[img=")+5,text.getText().toString().indexOf("]"));
//
//                Drawable x=loadImageFromURL(imageURL);
//                addImageInEditText(x);
//                addImageInEditText(x);
//            }






        }
    }
    private void addImageInEditText(Drawable drawable) {

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        int selectionCursorPos = text.getSelectionStart();
        text.getText().insert(selectionCursorPos, " ");
        selectionCursorPos = text.getSelectionStart();
        SpannableStringBuilder builder = new SpannableStringBuilder(text.getText());
        int startPos = selectionCursorPos - " ".length();
        builder.setSpan(new ImageSpan(drawable), startPos, selectionCursorPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(builder);
        text.setSelection(selectionCursorPos);
    }
    private View.OnClickListener insertImage= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            getPostImage();

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
    public static void postTmp(String title,String text){
        postTitle=title;
        postText=text;
    }
    public void getPostImage(){
        pageUpload.setMode(3);
        //pageUpload.tmpPost(title.getText().toString(),text.getText().toString(),);
        pageUpload.setName("toothpicklol");
        Intent intent = new Intent();
        intent.setClass(pagePost.this, pageUpload.class);
        startActivity(intent);
        finish();

    }


}
