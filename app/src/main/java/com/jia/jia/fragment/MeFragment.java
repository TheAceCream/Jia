package com.jia.jia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.jia.jia.R;
import com.jia.jia.activity.MeActivity;
import com.jia.jia.activity.OrderActivity;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */
public class MeFragment extends Fragment {

    private TextView order, info, quit, username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        order = view.findViewById(R.id.order);
        info = view.findViewById(R.id.info);
        username = view.findViewById(R.id.username);

        quit = view.findViewById(R.id.quit);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeActivity.class);
                startActivity(intent);
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheUtils.getInstance().clear();
                getActivity().finish();

            }
        });


        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

        String _username = CacheUtils.getInstance().getString("username");
        username.setText(_username);
    }
}

