package com.foodmap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodmap.ui.home.HomeFragment;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;


public class articleActivity extends AppCompatActivity {

    private static String user;
    public static LinearLayout scroll;
    public LinearLayout linear_discount1;
    ImageView[] imageViews;
    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;
    ImageView img11,img12,img13,img14,img15,img16,img17,img18,img19;
    String Articleinfo="http://114.32.152.202/foodphp/Articleinfo.php";
    String delete="http://114.32.152.202/foodphp/delete.php";
    String deleteArticle="http://114.32.152.202/foodphp/deleteArticle.php";
    String logUrl="http://114.32.152.202/foodphp/insertLog.php";
    article_img[] InfoSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        img1=(ImageView)findViewById(R.id.imageView);
        img2=(ImageView)findViewById(R.id.imageView2);
        img3=(ImageView)findViewById(R.id.imageView3);
        img4=(ImageView)findViewById(R.id.imageView4);
        img5=(ImageView)findViewById(R.id.imageView5);
        img6=(ImageView)findViewById(R.id.imageView6);
        img7=(ImageView)findViewById(R.id.imageView7);
        img8=(ImageView)findViewById(R.id.imageView8);
        img9=(ImageView)findViewById(R.id.imageView9);

        img11=(ImageView)findViewById(R.id.imageView11);
        img12=(ImageView)findViewById(R.id.imageView12);
        img13=(ImageView)findViewById(R.id.imageView13);
        img14=(ImageView)findViewById(R.id.imageView14);
        img15=(ImageView)findViewById(R.id.imageView15);
        img16=(ImageView)findViewById(R.id.imageView16);
        img17=(ImageView)findViewById(R.id.imageView17);
        img18=(ImageView)findViewById(R.id.imageView18);
        img19=(ImageView)findViewById(R.id.imageView19);

        img1.setImageBitmap(stringToBitmap(""));
        img2.setImageBitmap(stringToBitmap(""));
        img3.setImageBitmap(stringToBitmap(""));
        img4.setImageBitmap(stringToBitmap(""));
        img5.setImageBitmap(stringToBitmap(""));
        img6.setImageBitmap(stringToBitmap(""));
        img7.setImageBitmap(stringToBitmap(""));
        img8.setImageBitmap(stringToBitmap(""));
        img9.setImageBitmap(stringToBitmap(""));

        img11.setImageBitmap(stringToBitmap(""));
        img12.setImageBitmap(stringToBitmap(""));
        img13.setImageBitmap(stringToBitmap(""));
        img14.setImageBitmap(stringToBitmap(""));
        img15.setImageBitmap(stringToBitmap(""));
        img16.setImageBitmap(stringToBitmap(""));
        img17.setImageBitmap(stringToBitmap(""));
        img18.setImageBitmap(stringToBitmap(""));
        img19.setImageBitmap(stringToBitmap(""));




        String userInfo=dbcon.userInfo(user,Articleinfo);
        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
            String[] makArr1 = userInfo.split("]");
            InfoSQL = new article_img[makArr1.length];
            for (int z = 0; z < makArr1.length; z++) {
                imageViews=new ImageView[makArr1.length];
                imageViews=new ImageView[]{
                        img1,img2,img3,img4,img5,img6,img7,img8,img9,img11,img12,img13,img14,img15,img16,img17,img18,img19
                };
                String tmp = makArr1[z];
                String[] infoArr = tmp.split(",");
                if (infoArr[0] != "") {
                    InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));
                    Bitmap btp=scaleMatrix(getBitmapFromURL1(InfoSQL[z].img),450,250);
                    imageViews[z].setImageBitmap(btp);
                }
            }
        }


        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        //int touch=sp.getInt("touchx",0);
        scroll=(LinearLayout)findViewById(R.id.linear_discount);



        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img1.setVisibility(View.GONE);
                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==0){
                                    dbcon.delete(user,InfoSQL[0].tickrtid,String.valueOf(InfoSQL[0].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    formatter.setTimeZone(TimeZone.getTimeZone("GMT 08"));
                                    String str=formatter.format(new Date());
                                    dbcon.insertLog(user,InfoSQL[0].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img2.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==1){
                                    dbcon.delete(user,InfoSQL[1].tickrtid,String.valueOf(InfoSQL[1].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[1].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img3.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==2){
                                    dbcon.delete(user,InfoSQL[2].tickrtid,String.valueOf(InfoSQL[2].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[2].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                        img4.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==3){
                                    dbcon.delete(user,InfoSQL[3].tickrtid,String.valueOf(InfoSQL[3].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[3].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                        img5.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==4){
                                    dbcon.delete(user,InfoSQL[4].tickrtid,String.valueOf(InfoSQL[4].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[4].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                        img6.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==5){
                                    dbcon.delete(user,InfoSQL[5].tickrtid,String.valueOf(InfoSQL[5].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[5].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                        img7.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==6){
                                    dbcon.delete(user,InfoSQL[6].tickrtid,String.valueOf(InfoSQL[6].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[6].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
                        img8.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==7){
                                    dbcon.delete(user,InfoSQL[7].tickrtid,String.valueOf(InfoSQL[7].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[7].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img9.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==8){
                                    dbcon.delete(user,InfoSQL[8].tickrtid,String.valueOf(InfoSQL[8].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[8].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });


        img11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img11.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==9){
                                    dbcon.delete(user,InfoSQL[9].tickrtid,String.valueOf(InfoSQL[9].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[9].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img12.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==10){
                                    dbcon.delete(user,InfoSQL[10].tickrtid,String.valueOf(InfoSQL[10].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[10].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img13.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==11){
                                    dbcon.delete(user,InfoSQL[11].tickrtid,String.valueOf(InfoSQL[11].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[11].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img14.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==12){
                                    dbcon.delete(user,InfoSQL[12].tickrtid,String.valueOf(InfoSQL[12].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[12].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img15.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==13){
                                    dbcon.delete(user,InfoSQL[13].tickrtid,String.valueOf(InfoSQL[13].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[13].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img16.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==14){
                                    dbcon.delete(user,InfoSQL[14].tickrtid,String.valueOf(InfoSQL[14].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[14].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img17.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==15){
                                    dbcon.delete(user,InfoSQL[15].tickrtid,String.valueOf(InfoSQL[15].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[15].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img18.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==16){
                                    dbcon.delete(user,InfoSQL[16].tickrtid,String.valueOf(InfoSQL[16].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[16].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        img19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(articleActivity.this);
                alertDialog.setTitle("折價卷");
                alertDialog.setMessage("確定使用此折價卷嗎?");
                alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getBaseContext(), "已使用此折價卷", Toast.LENGTH_SHORT).show();
                        img19.setVisibility(View.GONE);

                        String userInfo=dbcon.userInfo(user,Articleinfo);
                        if(dbcon.userInfo(user,Articleinfo).length()!=0) {
                            String[] makArr1 = userInfo.split("]");
                            InfoSQL = new article_img[makArr1.length];
                            for (int z = 0; z < makArr1.length; z++) {

                                String tmp = makArr1[z];
                                String[] infoArr = tmp.split(",");
                                InfoSQL[z] = new article_img(infoArr[0],infoArr[1],infoArr[2],Integer.parseInt(infoArr[3]));

                                if(z==17){
                                    dbcon.delete(user,InfoSQL[17].tickrtid,String.valueOf(InfoSQL[17].ticket_num),deleteArticle);
                                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date curDate=new Date(System.currentTimeMillis());
                                    String str=formatter.format(curDate);
                                    dbcon.insertLog(user,InfoSQL[17].tickrtid,"null","tickrt",str,logUrl);
                                }
                            }
                        }

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNeutralButton("取消", null);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

    }



    class article_img {
        public String shopid,tickrtid,img;
        public int ticket_num;
        public article_img(String i, String j,String k,int l) {

            shopid=i;
            tickrtid=j;
            img=k;
            ticket_num=l;


        }

    }

    public static Bitmap scaleMatrix(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float scaleW = width / w;
        float scaleH = height / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleW, scaleH); // 長和寬放大縮小的比例
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    public Bitmap getBitmapFromURL1(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            myBitmap = Bitmap.createScaledBitmap(myBitmap,75,50, true);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    protected Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {
// 圖片源
// Bitmap bm = BitmapFactory.decodeStream(getResources()
// .openRawResource(id));
// 獲得圖片的寬高
        int width = bm.getWidth();
        int height = bm.getHeight();
// 設定想要的大小
        int newWidth1 = newWidth;
        int newHeight1 = newHeight;
// 計算縮放比例
        float scaleWidth = ((float) newWidth1) / width;
        float scaleHeight = ((float) newHeight1) / height;
// 取得想要縮放的matrix引數
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
// 得到新的圖片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
        return newbm;

    }

    public Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void setName(String i){
        user=i;
    }

    public static String getRandomString(int length)
    {
        String sr="abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer st=new StringBuffer();
        for(int i=0;i<length;i++)
        {
            int number=random.nextInt(62);//0~61
            st.append(sr.charAt(number));
        }
        return st.toString();
    }
}
