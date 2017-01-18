package com.haitran.filemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.haitran.filemanager.R;
import com.haitran.filemanager.model.FileItem;

import java.util.ArrayList;

/**
 * Created by Hai Tran on 1/18/2017.
 */

public class ItemDialogAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<FileItem> arrFileItem;

    public ItemDialogAdapter(Context context, ArrayList<FileItem> arrFileItem) {
        this.arrFileItem = arrFileItem;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrFileItem.size();
    }

    @Override
    public FileItem getItem(int i) {
        return arrFileItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_file, viewGroup, false);
            viewHolder.imgFolder = (ImageView) view.findViewById(R.id.img_folder);
            viewHolder.txtNameFile = (TextView) view.findViewById(R.id.txt_name_folder);
            viewHolder.txtDate = (TextView) view.findViewById(R.id.txt_date);
            viewHolder.txtNumberItem = (TextView) view.findViewById(R.id.txt_number_item);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.check_box);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final FileItem fileItem = getItem(i);
        viewHolder.checkBox.setVisibility(View.GONE);
        viewHolder.txtNumberItem.setVisibility(View.INVISIBLE);
        if (fileItem.isDirectory()) {
            viewHolder.txtNameFile.setText(fileItem.getName());
            viewHolder.txtDate.setText(fileItem.getDate());
        } else {
            return null;
        }
        return view;
    }

    private class ViewHolder {
        ImageView imgFolder;
        TextView txtNameFile;
        TextView txtDate;
        TextView txtNumberItem;
        CheckBox checkBox;
    }
}
