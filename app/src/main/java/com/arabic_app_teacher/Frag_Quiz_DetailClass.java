package com.arabic_app_teacher;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Frag_Quiz_DetailClass extends Fragment {




    View v;
    Context context;

    public Frag_Quiz_DetailClass() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_quiz, container , false);
        context = v.getContext();












        return v;

    }
}
