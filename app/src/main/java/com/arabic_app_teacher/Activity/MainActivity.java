package com.arabic_app_teacher.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arabic_app_teacher.Adapter.NewClassRecyclerAdapter;
import com.arabic_app_teacher.Model.AllStudendt;
import com.arabic_app_teacher.Model.NewClass;
import com.arabic_app_teacher.Network.AppController;
import com.arabic_app_teacher.Network.CustomRequest;
import com.arabic_app_teacher.R;
import com.arabic_app_teacher.SavePref;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;
    MaterialDialog profile;
    private EditText et_classSubject, et_className;
    private Button btn_createClass , btn_seeAllStudent_main , btn_searchClass_main , btn_attachFile_main;

    private RecyclerView recyclerView;
    private List<NewClass> itemList_class = new ArrayList<>();
    private NewClassRecyclerAdapter adapter_class;

    private FilePickerDialog dialog;

    ProgressDialog dialog_class;


    private SavePref save;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();

        save = new SavePref(this);

        dialog_class = new ProgressDialog(this);

        recyclerView = (RecyclerView)findViewById(R.id.rv_newClass);
        adapter_class = new NewClassRecyclerAdapter(this, itemList_class);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter_class);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newClass();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });

        fill();
        //SendFile();

    }

    private void fill() {


        dialog_class.setMessage("Loading...");
        dialog_class.show();

        JsonArrayRequest req = new JsonArrayRequest(AppController.URL_CLASS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("TAG---------OK", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                NewClass newClass =new NewClass();
                                newClass.setId(object.getString("id"));
                                newClass.setClassName(object.getString("fullname"));
                                newClass.setDesc(object.getString("summery"));
                                newClass.setOstadName(object.getString("ostadfname")+" "+object.getString("ostadlname"));


                                Log.e("TAG-----id class", object.getString("id"));

                                if (object.getString("id_ostad").equals(save.load(AppController.SAVE_USER_ID, "-1"))) itemList_class.add(newClass);

                                newClass = null;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter_class.notifyDataSetChanged();

                        if (dialog_class.isShowing()) {
                            dialog_class.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG------------Error", "Error: " + error.getMessage());
                if (dialog_class.isShowing()) {
                    dialog_class.dismiss();
                }
            }
        });
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req, "ALL_CLASS");


    }

    private void SendFile()
    {

        //File Picker
        //Create a DialogProperties object.
        final DialogProperties properties=new DialogProperties();
        dialog=new FilePickerDialog(MainActivity.this,properties);
        dialog.setTitle("انتخاب جزوه");
        dialog.setPositiveBtnName("انتخاب");
        dialog.setNegativeBtnName("لغو");
        //Setting selection mode to multiple selection.
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        //properties.selection_mode = DialogConfigs.SINGLE_MODE;
        //Setting selection type to files and directories.
        properties.selection_type=DialogConfigs.FILE_AND_DIR_SELECT;
        properties.error_dir=new File("/mnt");
        //Set new properties of dialog.
        dialog.setProperties(properties);


    }

    //Add this method to show Dialog when the required permission has been granted to the app.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(dialog!=null) {
                        //Show dialog if the read permission has been granted.
                        dialog.show();
                    }
                }
                else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(MainActivity.this,"Permission is Required for getting list of files",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void newClass() {
        profile = new MaterialDialog.Builder(MainActivity.this)
                .customView(R.layout.alert_new_class, false)
                .show();

        btn_createClass = (Button) profile.findViewById(R.id.btn_createClass);
        et_className = (EditText) profile.findViewById(R.id.et_className);
        et_classSubject = (EditText) profile.findViewById(R.id.et_classSubject);


        btn_createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addClass(
                        save.load(AppController.SAVE_USER_ID, "-1"),
                        et_className.getText().toString(),
                        et_classSubject.getText().toString()
                        );

                profile.dismiss();
            }
        });
    }

    private void addClass(String id, String classname, String classsummery) {

        dialog_class.setMessage("Loading...");
        dialog_class.show();

        Map<String, String> params = new HashMap<>();
        params.put("id_ostad", id);
        params.put("fullname", classname);
        params.put("summery", classsummery);

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, AppController.URL_CLASS_ADD, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject resp = response;
                //Log.d("TAG--------OK", resp.toString());

                try {
                    if(resp.getString("status").equals("200")){

                        Toast.makeText(MainActivity.this, "کلاس اضافه شد", Toast.LENGTH_SHORT).show();

                        itemList_class.clear();
                        adapter_class.notifyDataSetChanged();
                        fill();

                    }else{
                        Toast.makeText(MainActivity.this, "خطا در سرور لطفا بعدا سعی کنید", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (dialog_class.isShowing()) {
                    dialog_class.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("TAG--------Error", "Error: " + error.getMessage());

                Toast.makeText(MainActivity.this, "لطفا در زمان دیگری اقدام کنید", Toast.LENGTH_SHORT).show();

                if (dialog_class.isShowing()) {
                    dialog_class.dismiss();
                }
            }
        });
        jsonObjReq.setShouldCache(false);
        //myRequestQueue.getCache().clear();
        AppController.getInstance().addToRequestQueue(jsonObjReq,"ADD_CLASS");

    }


    private void findview() {

        recyclerView =(RecyclerView) findViewById(R.id.recyclerview_onlineClass);
        btn_seeAllStudent_main =(Button) findViewById(R.id.btn_seeAllStudent_main);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);


    }

    public void TopButtonOnclick(View v)
    {

        switch (v.getId())
        {
            case R.id.btn_seeAllStudent_main:
            {
                Intent i = new Intent(MainActivity.this , AllStudentActivity.class);
                startActivity(i);
                break;
            }

            case R.id.btn_attachFile_main:
            {
                Intent i = new Intent(MainActivity.this , Show_AttachFile.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext() , "فایلی فرستاده نشد" , Toast.LENGTH_LONG).show();
                break;
            }

            default:
                break;
        }


    }
}
