package com.jia.jia.adapter;

import android.widget.ImageView;

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

public class GoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    public GoodsAdapter() {
        super(R.layout.item_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.price, String.valueOf(item.getPrice()));
        // 加载网络图片
        Glide.with(mContext).load(MyApplication.baseUrl + "img/" + item.getImg()).into((ImageView) helper.getView(R.id.img));
    }
}
