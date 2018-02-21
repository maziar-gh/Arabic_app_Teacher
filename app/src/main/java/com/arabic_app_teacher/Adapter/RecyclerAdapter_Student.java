package com.arabic_app_teacher.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabic_app_teacher.Activity.MainActivity;
import com.arabic_app_teacher.Model.AllStudendt;
import com.arabic_app_teacher.Network.AppController;
import com.arabic_app_teacher.R;
import com.arabic_app_teacher.SavePref;

import java.util.List;

/**
 * Created by Maziar on 2/15/2018.
 */

public class RecyclerAdapter_Student  extends RecyclerView.Adapter<RecyclerAdapter_Student.MyViewHolder> {

    private boolean iscolor = true;
    private List<AllStudendt> itemList;
    private Context mContext;
    private Typeface font;
    private SavePref save;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name, tv_email, tv_active;
        public LinearLayout ll_bg, ll_allStudent;

        public MyViewHolder(View view) {
            super(view);

            this.tv_active = (TextView) itemView.findViewById(R.id.tv_stu_active);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_stu_name);
            this.tv_email = (TextView) itemView.findViewById(R.id.tv_stu_email);
            this.ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg_row_cal);
            this.ll_allStudent = (LinearLayout) itemView.findViewById(R.id.ll_allStudentss);

        }
    }


    public RecyclerAdapter_Student(Context context, List<AllStudendt> itemList) {
        this.itemList = itemList;
        this.mContext = context;
        save = new SavePref(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_all_user_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AllStudendt Item = itemList.get(position);

        String active = "";
        if(Item.getActive().equals("1"))
            active ="فعال";
        else
            active = "غیر فعال";

        holder.tv_active.setText(active);
        holder.tv_name.setText(Item.getFullname());
        holder.tv_email.setText(Item.getEmail());

        if (iscolor) {
            holder.ll_bg.setBackgroundColor(Color.GRAY);
            iscolor = false;
        } else {
            holder.ll_bg.setBackgroundColor(Color.LTGRAY);
            iscolor = true;
        }



        //setAnimation(holder.card_item);
        //setScaleAnimation(holder.click_discount_cardview);
        //setFadeAnimation(holder.card_item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }




    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        //holder.card_item.clearAnimation();
    }



}
