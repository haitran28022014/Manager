package com.haitran.filemanager.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haitran.filemanager.R;
import com.haitran.filemanager.viewholder.TitleViewHolder;

import java.util.ArrayList;

/**
 * Created by Hai Tran on 1/17/2017.
 */

public class TitleAdapter extends RecyclerView.Adapter<TitleViewHolder> {
    private ArrayList<String> arrayList;
    private OnClickItemTitle onClickItem;

    public TitleAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;

    }

    @Override
    public TitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_title, parent, false);
        TitleViewHolder titleViewHolder = new TitleViewHolder(view);
        return titleViewHolder;
    }

    @Override
    public void onBindViewHolder(TitleViewHolder holder, final int position) {
        if (position == arrayList.size() - 1) {
            holder.getTxtTitle().setTextColor(Color.parseColor("#388E3C"));
        } else {
            holder.getTxtTitle().setTextColor(Color.BLACK);
        }
        holder.getTxtTitle().setText(arrayList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem.onClickItemTitle(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public interface OnClickItemTitle {
        void onClickItemTitle(int position);
    }

    public void setOnClickItemTitle(OnClickItemTitle onClickItemTitle) {
        this.onClickItem = onClickItemTitle;
    }
}
