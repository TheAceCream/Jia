package com.jia.jia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jia.jia.R;
import com.jia.jia.module.Critics;

import java.util.List;

/**
 * Created by AceCream on 2018/5/7.
 */

public class CriticAdapter extends RecyclerView.Adapter<CriticAdapter.ViewHolder>{

    private List<Critics> mCriticsList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView user_tv,star_tv,note_tv;

        public ViewHolder(View view) {
            super(view);
             user_tv = view.findViewById(R.id.critic_user);
             star_tv = view.findViewById(R.id.critic_star);
             note_tv = view.findViewById(R.id.critic_note);
        }
    }

    public CriticAdapter(List<Critics> criticsList) {
       mCriticsList = criticsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ctitic_tiem,
                parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Critics critics = mCriticsList.get(position);
        holder.user_tv.setText("用户名："+critics.getUsername());
        holder.star_tv.setText("评价："+critics.getStar()+"");
        holder.note_tv.setText("内容："+critics.getNote());
    }

    @Override
    public int getItemCount() {
        return mCriticsList.size();
    }
}
