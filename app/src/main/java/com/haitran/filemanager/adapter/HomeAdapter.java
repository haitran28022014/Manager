package com.haitran.filemanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haitran.filemanager.R;
import com.haitran.filemanager.model.Category;
import com.haitran.filemanager.util.HelpUtil;

import java.util.ArrayList;

/**
 * Created by Hai Tran on 1/10/2017.
 */

public class HomeAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Category> arrayList;

    public HomeAdapter(ArrayList<Category> arrayList, Context context) {
        this.arrayList = arrayList;
        inflater = inflater.from(context);
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Category getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_home, viewGroup, false);
            viewHolder.imageIcon = (ImageView) view.findViewById(R.id.img_icon_item);
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_item);
            viewHolder.txtNumberFileItem = (TextView) view.findViewById(R.id.txt_number_file);
            viewHolder.txtNameItem = (TextView) view.findViewById(R.id.txt_name_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(view.getContext()).load(HelpUtil.getImageId(getItem(i).getImageIcon(), view.getContext())).into(viewHolder.imageIcon);
        GradientDrawable bgShape = (GradientDrawable) viewHolder.relativeLayout.getBackground();
        bgShape.setColor(Color.parseColor(getItem(i).getColorBackground()));
        viewHolder.txtNumberFileItem.setText(getItem(i).getNumberFileHome() + "");
        viewHolder.txtNameItem.setText(getItem(i).getNameItem());
        return view;
    }


    private class ViewHolder {
        private ImageView imageIcon;
        private RelativeLayout relativeLayout;
        private TextView txtNumberFileItem;
        private TextView txtNameItem;
    }
}
