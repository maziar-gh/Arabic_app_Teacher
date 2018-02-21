package com.arabic_app_teacher.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabic_app_teacher.Activity.DetailClassActivity;
import com.arabic_app_teacher.Model.NewClass;
import com.arabic_app_teacher.Network.AppController;
import com.arabic_app_teacher.R;
import com.arabic_app_teacher.SavePref;

import java.util.List;

/**
 * Created by Maziar on 11/25/2017.
 */

public class NewClassRecyclerAdapter extends RecyclerView.Adapter<NewClassRecyclerAdapter.MyViewHolder> {

    private int lastPosition = -1;
    private boolean iscolor = true;
    private List<NewClass> itemList;
    private Context mContext;
    private Typeface font;
    private SavePref save;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name, tv_subject , tv_ostad;
        public Button btn_class;
        public ImageView img_history;
        public CardView click_linear;

        public MyViewHolder(View view) {
            super(view);


            this.tv_name = (TextView) itemView.findViewById(R.id.tv_class_name);
            this.tv_subject = (TextView) itemView.findViewById(R.id.tv_class_subject);
            this.tv_ostad = (TextView) itemView.findViewById(R.id.tv_class_ostad);
            this.btn_class = (Button) itemView.findViewById(R.id.btn_enter_class);


        }
    }


    public NewClassRecyclerAdapter(Context context, List<NewClass> itemList) {
        this.itemList = itemList;
        this.mContext = context;
        save = new SavePref(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_class_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NewClass Item = itemList.get(position);

        holder.tv_name.setText(Item.getClassName());
        holder.tv_subject.setText(Item.getDesc());
        holder.tv_ostad.setText(Item.getOstadName());
        holder.btn_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.e("TAG---------id cl----", Item.getId());
                save.save(AppController.SAVE_CLASS_ID, Item.getId());
                Intent intent = new Intent(mContext, DetailClassActivity.class);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /*private void setAnimation(View viewToAnimate) {

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.up_from_bottom);
        animation.setDuration(1000);
        viewToAnimate.startAnimation(animation);


    }*/


    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        //holder.card_item.clearAnimation();
    }

}
