package com.jia.jia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.jia.jia.R;
import com.jia.jia.adapter.CriticAdapter;
import com.jia.jia.module.Critics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AceCream on 2018/5/7.
 */

public class CriticActivity extends AppCompatActivity {

    private List<Critics> criticsList = new ArrayList<>();
    private TextView cri_tv;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cri);
        cri_tv = findViewById(R.id.cri_tv);
        recyclerView = findViewById(R.id.cri_rec);
        Intent intent = getIntent();
        //数据
        criticsList = (List<Critics>) intent.getSerializableExtra("cri_content");
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        CriticAdapter criticAdapter = new CriticAdapter(criticsList);
        recyclerView.setAdapter(criticAdapter);
//        cri_tv.setText(criticsList.toString());
    }


}
