package com.arabic_app_teacher.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.arabic_app_teacher.R;

public class Show_AttachFile extends AppCompatActivity {



    public static ListView lv_showattach_attachfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attach_file);


        lv_showattach_attachfile = (ListView) findViewById(R.id.lv_showattach_attachfile);

    }


}

