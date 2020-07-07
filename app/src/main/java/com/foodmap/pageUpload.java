package com.foodmap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class pageUpload extends AppCompatActivity implements View.OnClickListener{

    private static String user;
    private static String postTitle;
    private static String postText;
    private final String PERMISSION_WRITE_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    private TextView messageText;
    private Button btnHead,btnBg;
    private ImageView imageview;
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;
    private String upLoadServerUri = null;
    private String imagepath = null;
    int checkImage=0;//檢查上傳模式
    public static int check;
    String update="http://114.32.152.202/foodphp/updateimage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_upload);

        //宣告的物件，跟View上的元件來連結。


        messageText  = (TextView)findViewById(R.id.txImgUpload);
        btnHead = (Button) findViewById(R.id.btnChose);
        btnBg =findViewById(R.id.btnChoseBg);
        imageview = (ImageView) findViewById(R.id.imageUpload);

        //設定Button的監聽事件。
        btnHead.setOnClickListener(this);
        btnBg.setOnClickListener(this);
        System.out.println(check);
        if(check==3){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //回傳時，要如何處理。請重新Override onActivityResult函式。
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
        }


        //設定連結到PHP的網址。(建議用手機來測試，再連到固定IP的網址。)
        upLoadServerUri = "http://114.32.152.202/foodphp/upload.php";
        if (!hasPermission()) {
            if (needCheckPermission()) {
                //如果須要檢查權限，由於這個步驟要等待使用者確認，
                //所以不能立即執行儲存的動作，
                //必須在 onRequestPermissionsResult 回應中才執行
                return;
            }
        }
    }
    private boolean needCheckPermission() {
        //MarshMallow(API-23)之後要在 Runtime 詢問權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {PERMISSION_WRITE_STORAGE};
            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);
            return true;
        }

        return false;
    }
    private boolean hasPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return(ActivityCompat.checkSelfPermission(this, PERMISSION_WRITE_STORAGE) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200){
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        //Button的監聽事件要做什麼事。
        if (v == btnHead) {
            //觸發開啟手機上的相片(類似想檔案總管)，來選要上傳的照片。
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //回傳時，要如何處理。請重新Override onActivityResult函式。
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);

        }
        else if(v==btnBg){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //回傳時，要如何處理。請重新Override onActivityResult函式。
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
            checkImage=1;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath();


            Uri selectedImageUri = data.getData();

            try { Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));


                imageview.setImageBitmap(bitmap);
                messageText.setText("Uploading file path:" +imagepath);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                //File finalFile = new File(getRealPathFromURI(tempUri));
                imagepath =getRealPathFromURI(tempUri);
                messageText.setText("uploading started.....");
                new Thread(new Runnable() {
                    public void run() {
                        uploadFile(imagepath);
                    }
                }).start();


            } catch(IOException ie) {

                messageText.setText("Error");
            }


        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    //取得選取相片的檔案路徑。
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //進行檔案上傳的動作。
    public int uploadFile(String sourceFileUri) {

        final String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        final File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
//            dialog.dismiss();
            Log.e("uploadFile", "Source File not exist :" + imagepath);

            runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("Source File not exist :" + imagepath);
                }
            });

            return 0;
        } else {
            try {

                //使用HttpURLConnection，連到Server瑞的網頁
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                //打開 HTTP 連到 URL物件上的網頁，再設定要以多媒體的方式，POST資料到Server端。
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                //上傳檔案，不是一次就可以傳送上去。要一部份一部份的上傳。
                //所以，要先設定一個buffer，將檔案的內容分次上傳。
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                //傳送多媒體的form資料。
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                //接收Server端的回傳訊息及代碼
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {
                            String msg = "File Upload Completed.\n\n See uploaded file your server. \n\n";
                            messageText.setText(msg);
                            System.out.println(sourceFile.getName());
                            Toast.makeText(pageUpload.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (check == 0) {
                        if (checkImage == 1) {
                            String r = "http://114.32.152.202/foodphp/upload/" + sourceFile.getName();
                            dbcon.updateImg(r, user, "bg", update);
                            //update 背景網址 http;//114.32.152.202/foodphp/upload/檔名
                        } else {
                            String r = "http://114.32.152.202/foodphp/upload/" + sourceFile.getName();
                            dbcon.updateImg(r, user, "head", update);
                            //update 頭像網址 http;//114.32.152.202/foodphp/upload/檔名

                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                String msg = "File Upload Completed.\n\n See uploaded file your server. \n\n";
                                messageText.setText(msg);
                                System.out.println(sourceFile.getName());
                                Toast.makeText(pageUpload.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    if (check == 1) {
                        if (checkImage == 1) {
                                String r = "http://114.32.152.202/foodphp/upload/" + sourceFile.getName();
                                pageCreateShop.returnImg(null, r);
                                Intent intent = new Intent();
                                intent.setClass(pageUpload.this, pageCreateShop.class);
                                startActivity(intent);
                                finish();
                                //update 背景網址 http;//114.32.152.202/foodphp/upload/檔名
                        }
                        else {
                                String r = "http://114.32.152.202/foodphp/upload/" + sourceFile.getName();
                                pageCreateShop.returnImg(r, null);
                                Intent intent = new Intent();
                                intent.setClass(pageUpload.this, pageCreateShop.class);
                                startActivity(intent);
                                finish();
                                //update 頭像網址 http;//114.32.152.202/foodphp/upload/檔名

                            }

                        }
                    if(check==3){

                        String r = "http://114.32.152.202/foodphp/upload/" + sourceFile.getName();

                        pagePost.postTmp(postTitle,postText+"\n"+"[img="+r+"]");
                        Intent intent = new Intent();
                        intent.setClass(pageUpload.this, pagePost.class);
                        startActivity(intent);
                        finish();

                    }
                    }
                }

                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(pageUpload.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(pageUpload.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            //dialog.dismiss();
            return serverResponseCode;
        }
    }
    public static void setName(String i){
        user=i;
    }

    public static void setMode(int x){
        check=x;

    }
    public static void tmpPost(String title,String text){
        postTitle=title;
        postText=text;

    }

}


