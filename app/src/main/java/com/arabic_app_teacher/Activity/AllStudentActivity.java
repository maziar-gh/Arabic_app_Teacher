package com.arabic_app_teacher.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arabic_app_teacher.Model.AllStudendt;
import com.arabic_app_teacher.Network.AppController;
import com.arabic_app_teacher.R;
import com.arabic_app_teacher.Adapter.RecyclerAdapter_AllStudent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllStudentActivity extends AppCompatActivity {


    RecyclerView recyclerView_user;
    private RecyclerAdapter_AllStudent adapter_user;
    private List<AllStudendt> itemList_user = new ArrayList<>();
    ProgressDialog dialog;

    ImageView img_refresh;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        img_refresh = (ImageView) findViewById(R.id.img_student_refresh);
        dialog = new ProgressDialog(this);

        recyclerView_user = (RecyclerView) findViewById(R.id.recyclerview_calculator);
        adapter_user = new RecyclerAdapter_AllStudent(this, itemList_user);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView_user.setLayoutManager(mLayoutManager);
        recyclerView_user.setItemAnimator(new DefaultItemAnimator());
        recyclerView_user.setAdapter(adapter_user);

        fill();


        img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemList_user.clear();
                adapter_user.notifyDataSetChanged();
                fill();
            }
        });

    }


    private void fill() {

        dialog.setMessage("Loading...");
        dialog.show();

        JsonArrayRequest req = new JsonArrayRequest(AppController.URL_ALL_USERS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("TAG---------OK", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                AllStudendt item = new AllStudendt();
                                item.setId(object.getString("id"));
                                item.setFullname(object.getString("fullname"));
                                item.setEmail(object.getString("email"));
                                item.setActive(object.getString("active"));


                                itemList_user.add(item);
                                item = null;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter_user.notifyDataSetChanged();

                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG------------Error", "Error: " + error.getMessage());
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req, "ALL_USER");
    }


}

