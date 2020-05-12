package com.foodmap;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.net.URL;

public class pageLike extends AppCompatActivity {

    LinearLayout shopBox,writerBox;
    makeLike[] likeSQL;
    ImageView head;
    TextView name,acc;
    Button btnLike;
    View view;
    int likeCheck=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_like);
        view = LayoutInflater.from(pageLike.this).inflate(R.layout.like_object, null);
        shopBox=findViewById(R.id.llSB);
        writerBox=findViewById(R.id.llWB);
        name=findViewById(R.id.txLikeName);
        head=findViewById(R.id.imgLike);
        btnLike=findViewById(R.id.btnLike);
        acc=findViewById(R.id.txLikeAcc);




        addListView();



        }
    public void addListView(){

        //String commentS=dbcon.comment(user,cookieStr,url);
//        String[] commentArr=commentS.split("]");
//        likeSQL = new makeLike[commentArr.length];
//        for (int i=0; i<commentArr.length; i++) {
//            if(commentArr.length==1)
//            {
//                break;
//            }
//            String tmp=commentArr[i];
//            String[] commentArr2=tmp.split(",");
//            for (int j=0; j<commentArr2.length; j++) {
//                System.out.println(commentArr2[j]);
//            }

        likeSQL = new makeLike[2];
        likeSQL[0] = new makeLike("a","c ","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");//評論資料
        likeSQL[1] = new makeLike("a", "j","https://storage.googleapis.com/www-cw-com-tw/article/201810/article-5bd182cf13ebb.jpg");//評論資料

        int btnId=0;
        for (makeLike point : likeSQL) {
            view = LayoutInflater.from(pageLike.this).inflate(R.layout.like_object, null);



            shopBox=findViewById(R.id.llSB);
            writerBox=findViewById(R.id.llWB);

            name=view.findViewById(R.id.txLikeName);
            acc=view.findViewById(R.id.txLikeAcc);
            head=view.findViewById(R.id.imgLike);
            btnLike=view.findViewById(R.id.btnLike);


            btnLike.setId(btnId);//將按鈕帶入id 以供監聽時辨識使用
            btnLike.setOnClickListener(check);

            btnId++;

            if(likeCheck==0){
                shopBox.addView(view);
            }
            else if(likeCheck==1){
                writerBox.addView(view);
            }


            name.setText(point.name);
            acc.setText(point.account);
            head.setImageDrawable(loadImageFromURL(point.head));






        }


    }
    private Drawable loadImageFromURL(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable draw = Drawable.createFromStream(is, "src");
            return draw;
        } catch (Exception e) {
            //TODO handle error
            System.out.println("error");
            Log.i("loadingImg", e.toString());
            return null;
        }
    }

    class makeLike {

        public String account,head,name;
        public makeLike( String i, String j,String k) {
            account=i;
            name=j;
            head=k;

        }


    }
    private View.OnClickListener check= new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            Button post =  (Button)v; //在new 出所按下的按鈕
            int id = post.getId();

            System.out.println(likeSQL[id]);



        }
    };
}

