package com.arabic_app_teacher.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arabic_app_teacher.Network.AppController;
import com.arabic_app_teacher.Network.CustomRequest;
import com.arabic_app_teacher.R;
import com.arabic_app_teacher.SavePref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Register extends AppCompatActivity {

    ProgressDialog dialog;
    EditText edt_name, edt_famil, edt_email, edt_pass;
    SavePref save;

    Spinner spinner;
    ArrayAdapter adapter;
    ArrayList<String> courceName, idCource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        save = new SavePref(this);

        dialog = new ProgressDialog(this);

        edt_name = (EditText) findViewById(R.id.txt_name);
        edt_famil = (EditText) findViewById(R.id.txt_family);
        edt_email = (EditText) findViewById(R.id.txt_email);
        edt_pass = (EditText) findViewById(R.id.txt_password);


        spin();

    }

    private void spin() {
        courceName = new ArrayList<>();
        idCource = new ArrayList<>();


        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courceName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        dialog.setMessage("Loading...");
        dialog.show();

        JsonArrayRequest req = new JsonArrayRequest(AppController.URL_COURCE,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("TAG---------OK", response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                idCource.add(object.getString("id"));
                                courceName.add(object.getString("fullname"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();

                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG------------Error", "Error: " + error.getMessage());
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req, "COURCE_NAME");

    }


    public void registerOnClicks(View v) {
        switch (v.getId()) {
            case R.id.tv_register_login: {
                startActivity(new Intent(Activity_Register.this, Activity_Login.class));
                break;
            }
            case R.id.btn_register_main: {

                register(
                        edt_name.getText().toString(),
                        edt_famil.getText().toString(),
                        edt_email.getText().toString(),
                        edt_pass.getText().toString(),
                        idCource.get(spinner.getSelectedItemPosition())
                );

                break;
            }
            default:
                break;
        }
    }


    private void register(String fname, String lname, String email, String pass, String id_course) {

        dialog.setMessage("Registering...");
        dialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("fname", fname);
        params.put("lname", lname);
        params.put("email", email);
        params.put("password", pass);
        params.put("id_course", id_course);

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, AppController.URL_SIGNUP_OSTAD, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject resp = response;
                //Log.d("TAG--------OK", resp.toString());

                try {
                    if(resp.getString("status").equals("200")){

                        Toast.makeText(Activity_Register.this, "ثبت نام انجام شد", Toast.LENGTH_SHORT).show();

                        save.save(AppController.SAVE_LOGIN, "1");
                        save.save(AppController.SAVE_USER_ID, resp.getString("id"));

                        startActivity(new Intent(Activity_Register.this, MainActivity.class));
                        finish();

                    }else{
                        Toast.makeText(Activity_Register.this, "خطا در سرور لطفا بعدا سعی کنید", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("TAG--------Error", "Error: " + error.getMessage());

                Toast.makeText(Activity_Register.this, "لطفا در زمان دیگری اقدام کنید", Toast.LENGTH_SHORT).show();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        jsonObjReq.setShouldCache(false);
        //myRequestQueue.getCache().clear();
        AppController.getInstance().addToRequestQueue(jsonObjReq,"SIGNUP_OSTAD");

    }

}
