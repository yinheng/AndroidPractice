package com.yh.todaynews.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yh.todaynews.R;

public class Tab2Fragment extends Fragment {

    public static Tab2Fragment getInstance(){
        Tab2Fragment tab2Fragment = new Tab2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", "hello world");
        tab2Fragment.setArguments(bundle);
        return tab2Fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_layout, container, false);
        TextView textView = view.findViewById(R.id.content);
        TextView tv = view.findViewById(R.id.tv);
        assert getArguments() != null;
        textView.setText(getArguments().getString("content"));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MenuActivity.class));
            }
        });
        return view;
    }
}
