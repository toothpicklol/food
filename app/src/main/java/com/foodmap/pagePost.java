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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_post);
        title=findViewById(R.id.etPTitle);
        text=findViewById(R.id.etPText);
        imageB=findViewById(R.id.imageButton);
        imageB.setOnClickListener(insertImage);

        if(postTitle!=null){
            title.setText(postTitle);
        }

        if(postText!=null){
            text.setText(postText);
        }





    }
    private void addImageInEditText(Drawable drawable) {

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        int selectionCursorPos = text.getSelectionStart();
        text.getText().insert(selectionCursorPos, ".");
        selectionCursorPos = text.getSelectionStart();
        SpannableStringBuilder builder = new SpannableStringBuilder(text.getText());
        int startPos = selectionCursorPos - ".".length();
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
    public  void add(){
        buildImageSpan(text);
        Drawable d =getDrawable(R.drawable.logo1);
        ImageSpan imageSpan = new ImageSpan(d);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text.getText());
        String imgId = "[img=1]";

        int selStart = text.getSelectionStart();

// current selection is replaceв with imageId
        builder.replace(text.getSelectionStart(), text.getSelectionEnd(), imgId);

// This adds a span to display image where the imageId is. If you do builder.toString() - the string will contain imageId where the imageSpan is.
// you can use it later - if you want to find location of imageSpan in text;
        builder.setSpan(imageSpan, selStart, selStart + imgId.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(builder);
    }
    public void buildImageSpan(EditText et){
//        et.setText("6");
//        String tmp =et.getText().toString();
//        int phCount=1;
//        for(int i=1;i<=phCount;i++){
//            SpannableString ss = new SpannableString(tmp);
//
//            Drawable d =getDrawable(R.drawable.logo1);
//            d.setBounds(0,0,d.getIntrinsicWidth(), d.getIntrinsicHeight());
//            ImageSpan span = new ImageSpan(d);
//            ss.setSpan(span,0,tmp.length(),0);
//
//            SpannableString sd = new SpannableString(tmp);
//            sd.setSpan(span,0,tmp.length(),0);
//            et.setText(TextUtils.concat(et.getText().toString()+"\n",ss,sd));
//        }





    }
//    SpannableString mSpan1 = new SpannableString("1");
//    /*
//     * this is add bitmap on edit text
//     */
//    private void displayBitmapOnText(Bitmap thumbnailBitmap) {
//        if(thumbnailBitmap == null)
//            return;
//        int start = text.getSelectionStart();
//        mSpan1.setSpan(new ImageSpan(thumbnailBitmap) , mSpan1.length() - 1, mSpan1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////    mSpan1.toString();
//        if(text != null) {
//            Editable et = text.getText();
//            et.insert(start, mSpan1);
//            text.setText(et);
//            text.setSelection(start,mSpan1.length());
//        }
//        text.setLineSpacing(10f, 1f);
//    }
//    /*
//     * this is delete bitmap on edit text
//     * from end to start
//     */
//    private void deleteEditTextSpan() {
//        Spanned s = text.getEditableText();
//        ImageSpan[] imageSpan = s.getSpans(0, s.length(), ImageSpan.class);
//        for (int i = imageSpan.length - 1; i >= 0; i--) {
//            if(i == imageSpan.length - 1) {
//                int start = s.getSpanStart(imageSpan[i]);
//                int end = s.getSpanEnd(imageSpan[i]);
//                Editable et = text.getText();
//                et.delete(start, end);
//            }
//        }
//        text.invalidate();
//    }
//    private void addImageBetweentext(Drawable drawable) {
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        int selectionCursor = text.getSelectionStart();
//        text.getText().insert(selectionCursor, ".");
//        selectionCursor = text.getSelectionStart();
//
//        SpannableStringBuilder builder = new SpannableStringBuilder(text.getText());
//        builder.setSpan(new ImageSpan(drawable), selectionCursor - ".".length(), selectionCursor,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        text.setText(builder);
//        text.setSelection(selectionCursor);
//    }
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
        pageUpload.tmpPost(title.getText().toString(),text.getText().toString());

        pageUpload.setName("toothpicklol");
        Intent intent = new Intent();
        intent.setClass(pagePost.this, pageUpload.class);
        startActivity(intent);
        finish();

    }


}
