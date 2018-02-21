package com.arabic_app_teacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arabic_app_teacher.Adapter.NewClassRecyclerAdapter;
import com.arabic_app_teacher.Adapter.RecyclerAdapter_Student;
import com.arabic_app_teacher.Model.AllStudendt;
import com.arabic_app_teacher.Model.NewClass;
import com.arabic_app_teacher.Network.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Frag_Student_DetailClass extends Fragment {


    View v;
    View row;
    Context context;
    ProgressDialog dialog;
    SavePref save;

    private RecyclerView recyclerView;
    private List<AllStudendt> itemList_student = new ArrayList<>();
    private RecyclerAdapter_Student adapter_student;



    public Frag_Student_DetailClass() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_student, container , false);
        context = v.getContext();

        dialog = new ProgressDialog(context);
        save = new SavePref(context);

        recyclerView = (RecyclerView) v.findViewById(R.id.rv_student);
        adapter_student = new RecyclerAdapter_Student(context, itemList_student);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter_student);


        fill();

        return v;

    }


    private void fill() {

        dialog.setMessage("Loading...");
        dialog.show();

        JsonArrayRequest req = new JsonArrayRequest(AppController.URL_CLASS_DETAIL + save.load(AppController.SAVE_CLASS_ID, "-1"),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //Log.e("TAG---------OK", AppController.URL_CLASS_DETAIL + save.load(AppController.SAVE_CLASS_ID, "-1"));
                        //Log.e("TAG---------OK", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                AllStudendt std = new AllStudendt();
                                std.setId(object.getString("id"));
                                std.setFullname(object.getString("fullname"));
                                std.setEmail(object.getString("email"));
                                std.setActive(object.getString("active"));


                                //Log.e("TAG-----", object.getString("fullname"));

                                itemList_student.add(std);

                                std = null;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter_student.notifyDataSetChanged();

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
        AppController.getInstance().addToRequestQueue(req, "DETAIL_CLASS");

    }



}
