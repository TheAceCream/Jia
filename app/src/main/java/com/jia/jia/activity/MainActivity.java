package com.jia.jia.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jia.jia.adapter.MainActivityAdapter;
import com.jia.jia.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tablayout;
    private MainActivityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tab_layout);
        initviews();


    }

    private void initviews() {
        /**
         * HomeActivity里面的元素,绑定id
         */
        //draw_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mAdapter = new MainActivityAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(4);
        tablayout.setupWithViewPager(viewPager);
        tablayout.setSelectedTabIndicatorColor(0xffffc107);
        mAdapter.setupTabLayout(tablayout, viewPager);


        for (int i = 0; i < tablayout.getTabCount(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);

            if (tab != null) {
                if (i == 0) {
                    tab.setCustomView(mAdapter.getTabView(i, this, true));
                } else {
                    tab.setCustomView(mAdapter.getTabView(i, this, false));
                }
            }
        }

    }

}
