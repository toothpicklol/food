package com.foodmap.ui.gallery;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.foodmap.R;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class GalleryFragment extends Fragment {

    Button  newList;
    LinearLayout ll;
    ArrayList<HashMap> objectList;
    View  buttonView,vi;
    int post =1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        buttonView = inflater.inflate(R.layout.personal_object_button, null);
        ll = (LinearLayout)root.findViewById(R.id.ll_in_sv);
        newList = (Button)buttonView.findViewById(R.id.info_dialog_new);
         vi = inflater.inflate(R.layout.personal_object, null);

        addListView();
        setActions();
        return root;
    }

    public void addListView(){

        ll.removeAllViews();

        for (int i = 0; i <post; i++) {
            View x =vi;
            ll.addView(vi);
        }
        ll.addView(buttonView);
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
//    Button  newList;
//    LinearLayout   ll;
//    ArrayList<HashMap> objectList;
//    View buttonView,view;
//    int post =1;//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        buttonView = LayoutInflater.from(MainActivity.this).inflate(R.layout.personal_object_button, null);
//        ll = (LinearLayout)findViewById(R.id.ll_in_sv);
//        newList = (Button)buttonView.findViewById(R.id.info_dialog_new);
//        addListView();
//        setActions();
//    }
//
//    public void addListView(){
//        objectList = new ArrayList<HashMap>();
//
//        ll.removeAllViews();//
//        for (int i = 0; i < post; i++) {
//            view = LayoutInflater.from(MainActivity.this).inflate(R.layout.personal_object, null);//
//            ll.addView(view);
//        }
//
//        ll.addView(buttonView);
//
//    }
//    private void setActions(){
//        newList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                post++;
//                addListView();
//            }
//        });
//    }




}