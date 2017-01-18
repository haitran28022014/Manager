package com.haitran.filemanager.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.haitran.filemanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hai Tran on 1/17/2017.
 */

public class TitleViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.txt_title)
    TextView txtTitle;

    public TitleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }
}
