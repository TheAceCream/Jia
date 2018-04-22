package com.jia.jia.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.module.Goods;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class ShoppingCarAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    public ShoppingCarAdapter() {
        super(R.layout.item_shoppingcar);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        helper.setText(R.id.title, item.getName());
        helper.setText(R.id.price, String.valueOf(item.getPrice()));
        helper.setText(R.id.type, item.getSortName());
        helper.setText(R.id.count, String.valueOf(item.getCounts()));
        helper.addOnClickListener(R.id.add);
        helper.addOnClickListener(R.id.sub);
        // 加载网络图片

        //LogUtils.e("---lin--->   img  " + item.getImg());


        Glide.with(mContext).load(  item.getImg()).into((ImageView) helper.getView(R.id.img));
    }
}
