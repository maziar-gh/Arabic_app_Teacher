package com.arabic_app_teacher.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.arabic_app_teacher.Activity.MainActivity;
import com.arabic_app_teacher.Model.AllStudendt;
import com.arabic_app_teacher.Network.AppController;
import com.arabic_app_teacher.R;
import com.arabic_app_teacher.SavePref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class RecyclerAdapter_AllStudent extends RecyclerView.Adapter<RecyclerAdapter_AllStudent.MyViewHolder> {

    private boolean iscolor = true;
    private List<AllStudendt> itemList;
    private Context mContext;
    private Typeface font;
    private SavePref save;
    MaterialDialog student;

    ProgressDialog dialog;

    class MyViewHolder extends RecyclerView.ViewHolder {

        public int currentItem;
        public TextView tv_name, tv_company, tv_row, tv_active;
        public LinearLayout ll_bg;

        public MyViewHolder(View view) {
            super(view);

            this.tv_row = (TextView) itemView.findViewById(R.id.tv_row_allSt);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name_allSt);
            this.tv_company = (TextView) itemView.findViewById(R.id.tv_username_allSt);
            this.tv_active = (TextView) itemView.findViewById(R.id.tv_active_allSt);
            this.ll_bg = (LinearLayout) view.findViewById(R.id.ll_bg_row_cal);


        }
    }


    public RecyclerAdapter_AllStudent(Context context, List<AllStudendt> itemList) {
        this.itemList = itemList;
        this.mContext = context;
        save = new SavePref(context);
        dialog = new ProgressDialog(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_all_student_row, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AllStudendt Item = itemList.get(position);

        holder.tv_row.setText(String.valueOf(position + 1));
        holder.tv_name.setText(Item.getFullname());
        holder.tv_company.setText(Item.getEmail());

        if (Item.getActive().equals("1")) {
            holder.tv_active.setText("فعال");
        } else {
            holder.tv_active.setText("غیر فعال");
        }


        if (iscolor) {
            holder.ll_bg.setBackgroundColor(Color.GRAY);
            iscolor = false;
        } else {
            holder.ll_bg.setBackgroundColor(Color.LTGRAY);
            iscolor = true;
        }

        holder.ll_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(mContext, Item);


            }
        });

        //setAnimation(holder.card_item);
        //setScaleAnimation(holder.click_discount_cardview);
        //setFadeAnimation(holder.card_item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    private void showDialog(Context context, final AllStudendt item) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        Button del = (Button) dialog.findViewById(R.id.btn_dialog_del);
        Button detail = (Button) dialog.findViewById(R.id.btn_dialog_detail);
        TextView tv_namayandeh = (TextView) dialog.findViewById(R.id.tv_dialog_namayandeh);
        tv_namayandeh.setText(item.getFullname());

        save.save(AppController.SAVE_USER_ID_FOR_SENDFILE, item.getId());

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("آیا این کاربر را حذف می کنید؟");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "بله",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                delete(item.getId());
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "خیر",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();



            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                detail(item);


            }
        });


        dialog.show();

    }

    @SuppressLint("SetTextI18n")
    private void detail(AllStudendt item) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_detail);

        String active = "";

        if (item.getActive().equals("1")) {
            active = "فعال";
        } else {
            active = "غیر فعال";
        }

        TextView tv_detail = (TextView) dialog.findViewById(R.id.tv_detail_all);
        tv_detail.setText(
                "نام: " + item.getFullname() + "\n" +
                        "ایمیل: " + item.getEmail() + "\n" +
                        "وضعیت: " + active

        );

        dialog.show();
    }

    private void delete(String id) {
        dialog.setMessage("Loading...");
        dialog.show();


        StringRequest req = new StringRequest(Request.Method.GET,
                AppController.URL_DELETE_USERS + id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    if (jsonObject.getString("status").equals("200")) {
                        Toast.makeText(mContext, "کاربر حذف شد", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "انجام نشد!", Toast.LENGTH_SHORT).show();
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
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(mContext, "در زمان دیگری امتحان کنید", Toast.LENGTH_SHORT).show();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }


            }
        });

        req.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(req, "DELETE_USER");
    }


}