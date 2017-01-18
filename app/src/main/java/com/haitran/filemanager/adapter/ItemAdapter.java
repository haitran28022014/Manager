package com.haitran.filemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haitran.filemanager.R;
import com.haitran.filemanager.model.FileItem;

import java.util.ArrayList;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private OnClickItemCheckBox onClickItemCheckBox;
    private OnClickImage onClickImage;
    public int ofWho;
    public static int OF_CHECK = 1;
    public static int OF_VIEW = 2;
    private LayoutInflater inflater;
    private ArrayList<FileItem> arrFileItem;
    public static boolean NOT_SELECTED = false;
    public static boolean SELECTED = true;
    public boolean checkClickBox;

    public ItemAdapter(Context context, ArrayList<FileItem> arrFileItem) {
        this.context = context;
        this.arrFileItem = arrFileItem;
        inflater = LayoutInflater.from(context);
        checkClickBox = NOT_SELECTED;
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
        final ViewHolder viewHolder;
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
        if (fileItem.isDirectory()) {
            viewHolder.imgFolder.setImageResource(R.drawable.bg_folder);
            viewHolder.txtNumberItem.setText(fileItem.getNumberItem() + " item");
        } else if (fileItem.getPathFile().toLowerCase().endsWith(".jpg")
                || fileItem.getPathFile().toLowerCase().endsWith(".png")
                || fileItem.getPathFile().toLowerCase().endsWith(".jpeg")) {
            Glide.with(context).load(fileItem.getPathFile()).override(64, 64).into(viewHolder.imgFolder);
            viewHolder.txtNumberItem.setText("");
        } else if (fileItem.getPathFile().toLowerCase().endsWith(".mp3")) {
            viewHolder.imgFolder.setImageResource(R.drawable.bg_music);
            viewHolder.txtNumberItem.setText("");
        } else {
            viewHolder.imgFolder.setImageResource(R.drawable.bg_file);
            viewHolder.txtNumberItem.setText("");
        }
        if (checkClickBox == NOT_SELECTED) {
            viewHolder.checkBox.setVisibility(View.GONE);
        } else if (checkClickBox == SELECTED) {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ofWho = OF_CHECK;
                    onClickItemCheckBox.onClickCheckBox(viewHolder.checkBox, i);
                }
            });
            if (!fileItem.isSelect()) {
                viewHolder.checkBox.setChecked(false);
            } else {
                viewHolder.checkBox.setChecked(true);
            }
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ofWho = OF_VIEW;
                onClickItemCheckBox.onClickCheckBox(viewHolder.checkBox, i);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClickItemCheckBox.onLongClick(viewHolder.checkBox, i);
                return false;
            }
        });

        viewHolder.txtNameFile.setText(fileItem.getName());
        viewHolder.txtDate.setText(fileItem.getDate());

        return view;
    }

    public interface OnClickItemCheckBox {
        void onClickCheckBox(CheckBox checkBox, int position);

        void onLongClick(CheckBox checkBox, int position);
    }


    public void setOnClickItemCheckBox(OnClickItemCheckBox onClickItemCheckBox) {
        this.onClickItemCheckBox = onClickItemCheckBox;
    }

    public interface OnClickImage {
        void onClickImage(String pathFile);
    }

    public void setOnClickImage(OnClickImage onClickImage) {
        this.onClickImage = onClickImage;
    }


    private class ViewHolder {
        ImageView imgFolder;
        TextView txtNameFile;
        TextView txtDate;
        TextView txtNumberItem;
        CheckBox checkBox;
    }
}
