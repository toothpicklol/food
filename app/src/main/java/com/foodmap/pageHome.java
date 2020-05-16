package com.foodmap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.foodmap.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

import java.io.InputStream;
import java.net.URL;

public class pageHome extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    int check=0;
    TextView name,acc;
    LinearLayout ll;
    ImageView head;
    View view;
    String info="http://114.32.152.202/foodphp/userinfo.php";
    private static String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_home);
        HomeFragment.setName(user);






        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        view =navigationView.getHeaderView(0);
        name=findViewById(R.id.txNavName);
        acc=findViewById(R.id.txNavAcc);
        ll=findViewById(R.id.ll);
        head=findViewById(R.id.imgNavHead);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "創建商店(在按一次退出模式)", Snackbar.LENGTH_LONG)
                        .setAction("", null).show();

                if(check==0){
                HomeFragment.selectShop();
                check++;
                }
                else
                {
                    Intent intent = new Intent();
                    intent.setClass(pageHome.this, pageCreateShop.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
        makeInfo[] InfoSQL = new makeInfo[1];
        String userInfo=dbcon.userInfo(user,info);
        String[] infoArr=userInfo.split(",");
        InfoSQL[0] = new makeInfo(infoArr[0], infoArr[1], infoArr[2],infoArr[3]);
        for (makeInfo point : InfoSQL) {
            view =navigationView.getHeaderView(0);
            name=view.findViewById(R.id.txNavName);
            acc=view.findViewById(R.id.txNavAcc);
            ll=view.findViewById(R.id.ll);
            head=view.findViewById(R.id.imgNavHead);

            name.setText(point.username);
            acc.setText(user);
            head.setImageDrawable(loadImageFromURL(point.bigHead));
            ll.setBackground(loadImageFromURL(point.bg));




        }



        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
    public static void setName(String i){
        user=i;
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



}
