package com.jia.jia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.jia.jia.GlideImageLoader;
import com.jia.jia.R;
import com.jia.jia.activity.ResultActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class ShoppingFragment extends Fragment {


    private Banner banner;
    private ImageView search;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        editText = view.findViewById(R.id.edit_text);
        search = view.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("key", editText.getText().toString());
                startActivity(intent);
            }
        });

        Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        //Integer[] images = {R.mipmap.img1, R.mipmap.img2, R.mipmap.img3};
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.img1);
        images.add(R.mipmap.img2);
        images.add(R.mipmap.img3);
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用

        banner.start();


        return view;

    }
}
