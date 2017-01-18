package com.haitran.filemanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.haitran.filemanager.R;
import com.haitran.filemanager.adapter.ItemDialogAdapter;
import com.haitran.filemanager.adapter.TitleAdapter;
import com.haitran.filemanager.manager.FileManager;
import com.haitran.filemanager.model.FileItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hai Tran on 1/18/2017.
 */

public class CustomDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener, TitleAdapter.OnClickItemTitle {
    @Bind(R.id.img_add)
    ImageView imgAdd;

    @Bind(R.id.img_home_dialog)
    ImageView imgHome;

    @Bind(R.id.list_view_dialog)
    ListView listViewDialog;

    @Bind(R.id.tv_username)
    TextView txtNameDialog;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.txt_ok)
    TextView txtOK;

    @Bind(R.id.txt_not_file)
    TextView txtNotFile;

    @Bind(R.id.txt_cancel)
    TextView txtCancel;

    private ItemDialogAdapter itemAdapter;
    private FileManager fileManager;
    private ArrayList<FileItem> arrayList;
    private String pathItem;
    private TitleAdapter titleAdapter;
    private ArrayList<String> arrayTitle;

    public CustomDialog(Context context, String nameDialog) {
        super(context);
        setContentView(R.layout.custum_dialog);
        ButterKnife.bind(this);
        txtNameDialog.setText(nameDialog);
        initData();
    }

    private void initData() {
        pathItem = FileManager.PATH_EXTERNAL;
        txtNotFile.setVisibility(View.INVISIBLE);
        arrayList = new ArrayList<>();
        fileManager = new FileManager(getContext());
        itemAdapter = new ItemDialogAdapter(getContext(), arrayList);
        intListView();
        listViewDialog.setAdapter(itemAdapter);
        readFile(FileManager.PATH_EXTERNAL);
        txtOK.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        listViewDialog.setOnItemClickListener(this);
        titleAdapter.setOnClickItemTitle(this);
    }

    public void intListView() {
        arrayTitle = new ArrayList<>();
        arrayTitle.add("sdcard");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        titleAdapter = new TitleAdapter(arrayTitle);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(titleAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private void readFile(String path) {
        arrayList.clear();
        arrayList.addAll(fileManager.readFile(path));
        for (int i = 0; i < arrayList.size(); i++) {
            if (!arrayList.get(i).isDirectory()) {
                arrayList.remove(i);
            }
        }
        if (arrayList.size() == 0) {
            txtNotFile.setVisibility(View.VISIBLE);
        } else {
            txtNotFile.setVisibility(View.INVISIBLE);
        }
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_ok:
                dismiss();
                break;
            case R.id.txt_cancel:
                dismiss();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (arrayList.get(i).isDirectory()) {
            pathItem = arrayList.get(i).getPathFile();
            readFile(pathItem);
            getTitle();
        }
    }

    public void getTitle() {
        String result;
        int a = pathItem.lastIndexOf("/");
        result = pathItem.substring(a + 1);
        arrayTitle.add(result);
        titleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItemTitle(int position) {
        String result = FileManager.PATH_EXTERNAL;
        if (arrayTitle.size() == 1) {
            return;
        }
        if (position == 0) {
            arrayList.clear();
            readFile(result);
            pathItem = result;
        }
        for (int i = 1; i < arrayTitle.size(); i++) {
            if (i <= position) {
                result = result + "/" + arrayTitle.get(i) + "/";
            }
        }

        for (int i = arrayTitle.size() - 1; i > position; i--) {
            arrayTitle.remove(i);
        }
        titleAdapter.notifyDataSetChanged();
        arrayList.clear();
        readFile(result);
        pathItem = result;
    }
}
