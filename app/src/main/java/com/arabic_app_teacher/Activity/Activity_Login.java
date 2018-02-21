package com.arabic_app_teacher.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.arabic_app_teacher.Network.AppController;
import com.arabic_app_teacher.Network.CustomRequest;
import com.arabic_app_teacher.R;
import com.arabic_app_teacher.SavePref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Login extends AppCompatActivity {

    Button btn_login_main;
    SavePref save;
    EditText edt_email, edt_pass;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        save = new SavePref(this);
        dialog = new ProgressDialog(this);

        if(save.load(AppController.SAVE_LOGIN, "0").equals("1")){
            startActivity(new Intent(Activity_Login.this, MainActivity.class));
        }


        btn_login_main = (Button)findViewById(R.id.btn_login_main);
        edt_email = (EditText)findViewById(R.id.edt_login_mail);
        edt_pass = (EditText)findViewById(R.id.edt_login_pass);

        btn_login_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(edt_email.getText().toString(), edt_pass.getText().toString());
            }
        });
    }

    private void Login(String email, String pass) {

        dialog.setMessage("Loading...");
        dialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("pass", pass);

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, AppController.URL_LOGIN, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject resp = response;
                //Log.e("TAG--------OK", resp.toString());

                String all = "";
                try {
                    all = resp.getString("status");
                    save.save(AppController.SAVE_USER_ID, resp.getString("id"));
                    //Log.e("TAG-----id", resp.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (Integer.parseInt(all) == 210) {

                    save.save(AppController.SAVE_LOGIN, "1");

                    startActivity(new Intent(Activity_Login.this, MainActivity.class));
                    finish();

                } else if (all.equals("204")) {
                    Toast.makeText(Activity_Login.this, "کاربری با این ایمیل یافت نشد", Toast.LENGTH_SHORT).show();
                } else if (all.equals("205")) {
                    Toast.makeText(Activity_Login.this, "کلمه عبور اشتباه است", Toast.LENGTH_SHORT).show();
                }


                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("TAG--------Error", "Error: " + error.getMessage());

                Toast.makeText(Activity_Login.this, "لطفا در زمان دیگری اقدام کنید", Toast.LENGTH_SHORT).show();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        jsonObjReq.setShouldCache(false);
        //myRequestQueue.getCache().clear();
        AppController.getInstance().addToRequestQueue(jsonObjReq, "LOGIN");

    }


    public void loginOnClicks (View v)
    {
        switch (v.getId())
        {
            case R.id.tv_login_forget:

                Toast.makeText(Activity_Login.this,"soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_login_register:

                startActivity(new Intent(Activity_Login.this, Activity_Register.class));

                break;


            default:
                break;
        }
    }
}
