package com.haitran.filemanager.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.haitran.filemanager.R;
import com.haitran.filemanager.activity.HomeActivity;
import com.haitran.filemanager.adapter.ItemAdapter;
import com.haitran.filemanager.adapter.TitleAdapter;
import com.haitran.filemanager.dialog.CustomDialog;
import com.haitran.filemanager.manager.FileManager;
import com.haitran.filemanager.model.FileItem;
import com.haitran.filemanager.util.HelpUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hai Tran on 1/17/2017.
 */

public class MainFragment extends Fragment implements View.OnClickListener
        , HomeActivity.OnClickBackPressed
        , TitleAdapter.OnClickItemTitle
        , View.OnTouchListener
        , ItemAdapter.OnClickItemCheckBox {
    @Bind(R.id.list_view_item)
    ListView listView;

    @Bind(R.id.img_home)
    ImageView imgHome;

    @Bind(R.id.txt_not_file)
    TextView txtNotFile;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.img_list)
    ImageView imgList;

    @Bind(R.id.floating_action_menu)
    FloatingActionMenu floatingActionMenu;

    @Bind(R.id.floating_folder)
    FloatingActionButton floatingFolder;

    @Bind(R.id.floating_file)
    FloatingActionButton floatingFile;

    @Bind(R.id.img_back)
    ImageView imgBack;

    @Bind(R.id.txt_selected)
    TextView txtSelected;

    @Bind(R.id.img_select_all)
    ImageView imgSelectAll;

    @Bind(R.id.img_delete)
    ImageView imgDelete;

    @Bind(R.id.img_more)
    ImageView imgMore;

    @Bind(R.id.relative_tool_bar)
    RelativeLayout relativeToolBar;


    private FileManager fileManager;
    private ArrayList<FileItem> arrayList;
    private ArrayList<String> arrayTitle;
    private ItemAdapter itemAdapter;
    public String pathItem;
    private TitleAdapter titleAdapter;
    private String pathOfWho;
    private int checkSelectAll;

    public static MainFragment newInstance(Bundle bundle) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getData();
        initViews();
        initData();
        initListener();
    }

    private void getData() {
        Bundle bundle = getArguments();
        int position = bundle.getInt(HomeFragment.KEY_1);
        arrayTitle = new ArrayList<>();
        arrayList = new ArrayList<>();
        fileManager = new FileManager(getContext());
        if (position == HomeFragment.STORAGE_INTERNAL) {
            arrayTitle.add("sdcard");
            pathItem = FileManager.PATH_EXTERNAL;
            pathOfWho = FileManager.PATH_EXTERNAL;
        } else if (position == HomeFragment.MICRO_SD) {
            String nameTitle = FileManager.name;
            nameTitle = nameTitle.substring(nameTitle.lastIndexOf("/") + 1);
            arrayTitle.add(nameTitle);
            pathItem = FileManager.name;
            pathOfWho = FileManager.name;
        }
        itemAdapter = new ItemAdapter(getContext(), arrayList);
        listView.setAdapter(itemAdapter);
        readFile(pathItem);
    }

    private void initData() {
        relativeToolBar.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        titleAdapter = new TitleAdapter(arrayTitle);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(titleAdapter);
        int actionBarSize = 0;
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarSize = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        actionBarSize = actionBarSize / 2;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, HelpUtil.pxFromDp(getContext(), actionBarSize), 0, 0);
        ((HomeActivity) getActivity()).getContent().setLayoutParams(lp);
    }

    private void initViews() {
        txtNotFile.setVisibility(View.INVISIBLE);
        ((HomeActivity) getActivity()).setOnClickBackPressed(this);
    }

    private void readFile(String path) {
        arrayList.clear();
        arrayList.addAll(fileManager.readFile(path));
        if (arrayList.size() == 0) {
            txtNotFile.setVisibility(View.VISIBLE);
        } else {
            txtNotFile.setVisibility(View.INVISIBLE);
        }
        itemAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        listView.setOnTouchListener(this);
        imgHome.setOnClickListener(this);
        titleAdapter.setOnClickItemTitle(this);
        itemAdapter.setOnClickItemCheckBox(this);
        imgMore.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgSelectAll.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        floatingFolder.setOnClickListener(this);
        floatingFile.setOnClickListener(this);
        //itemAdapter.setOnClickImage(this);
    }

    public void getTitle() {
        String result;
        int a = pathItem.lastIndexOf("/");
        result = pathItem.substring(a + 1);
        arrayTitle.add(result);
        titleAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_home:
                ((HomeActivity) getActivity()).backToMainFragment(MainFragment.this);
                ((HomeActivity) getActivity()).replaceFragment(new HomeFragment(), HomeActivity.TAG_FRAGMENT_HOME);
                break;
            case R.id.img_more:
                handlingMore();
                break;
            case R.id.floating_folder:
                handlingCreateFolder();
                break;
            case R.id.floating_file:
                handlingCreateFile();
                break;
            case R.id.img_back:
                handlingToolbar();
                for (int i = 0; i < arrayList.size(); i++) {
                    arrayList.get(i).setSelect(false);
                }
                itemAdapter.notifyDataSetChanged();
                break;
            case R.id.img_select_all:
                if (checkSelectAll == 1) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).setSelect(true);
                    }
                    checkSelectAll = 2;
                } else {
                    handlingToolbar();
                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).setSelect(false);
                    }
                }
                itemAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void handlingCreateFile() {
        customAlertDialog("Create new file");
    }

    private void handlingCreateFolder() {
        customAlertDialog("Create new folder");
    }

    public void customAlertDialog(String nameDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(nameDialog);
        builder.setMessage("Input name");
        final EditText inputName = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inputName.setLayoutParams(lp);
        builder.setView(inputName);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void handlingMore() {
        PopupMenu popupMenu = new PopupMenu(getContext(), imgMore);
        popupMenu.getMenuInflater().inflate(R.menu.menu_toolbar_relative, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.miCopy:
                        handlingToolbar();
                        for (int i = 0; i < arrayList.size(); i++) {
                            arrayList.get(i).setSelect(false);
                        }
                        itemAdapter.notifyDataSetChanged();
                        CustomDialog customDialog1 = new CustomDialog(getContext(), "Copy to");
                        customDialog1.show();
                        break;
                    case R.id.miMove:
                        handlingToolbar();
                        for (int i = 0; i < arrayList.size(); i++) {
                            arrayList.get(i).setSelect(false);
                        }
                        itemAdapter.notifyDataSetChanged();
                        CustomDialog customDialog = new CustomDialog(getContext(), "Move to");
                        customDialog.show();
                        break;
                    case R.id.miRename:
                        break;
                    case R.id.miInfo:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////        if (floatingActionMenu.isOpened()) {
////            floatingActionMenu.close(true);
////        }
////        if (!arrayList.get(i).isFile()) {
////            pathItem = arrayList.get(i).getPathFile();
////            readFile(pathItem);
////            getTitle();
////        }
//    }


    @Override
    public void onClickPressed() {
        if (itemAdapter.checkClickBox == ItemAdapter.SELECTED) {
            handlingToolbar();
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.get(i).setSelect(false);
            }
            itemAdapter.notifyDataSetChanged();
            return;
        }
        if (pathItem == null) {
            pathItem = FileManager.PATH_EXTERNAL;
        }
        int beforePath = pathItem.lastIndexOf("/");
        String url = pathItem.substring(0, beforePath);
        String name = url.substring(url.lastIndexOf("/") + 1);
        arrayTitle.remove(arrayTitle.size() - 1);
        titleAdapter.notifyDataSetChanged();
        if (name.equals("0")) {

        }
        if (pathItem.equals(FileManager.PATH_EXTERNAL) || pathItem.equals(FileManager.name)) {
            ((HomeActivity) getActivity()).backToMainFragment(MainFragment.this);
            ((HomeActivity) getActivity()).replaceFragment(((HomeActivity) getActivity()).getHomeFragment(), HomeActivity.TAG_FRAGMENT_HOME);
        } else {
            arrayList.clear();
            readFile(url);
            pathItem = url;
            return;
        }
    }

    @Override
    public void onClickItemTitle(int position) {
        String result = pathOfWho;
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
//
//    @Override
//    public void onClickImage(String pathFile) {
//        Toast.makeText(getContext(), "click image", Toast.LENGTH_SHORT).show();
////        Intent intent = new Intent();
////        intent.setAction(android.content.Intent.ACTION_VIEW);
////        Uri uri = Uri.parse("file://" + pathFile);
////        intent.setDataAndType(uri, "image/*");
////        startActivity(intent);
//    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if (floatingActionMenu.isOpened()) {
                floatingActionMenu.close(true);
            }
        }
        return false;
    }


    @Override
    public void onClickCheckBox(CheckBox checkBox, int position) {
        if (itemAdapter.checkClickBox == ItemAdapter.SELECTED) {
            if (itemAdapter.ofWho == ItemAdapter.OF_VIEW) {
                if (!checkBox.isChecked()) {
                    arrayList.get(position).setSelect(true);
                } else {
                    arrayList.get(position).setSelect(false);
                }
            } else {
                if (checkBox.isChecked()) {
                    arrayList.get(position).setSelect(true);
                } else {
                    arrayList.get(position).setSelect(false);
                }
            }

            int s = 0;
            for (int i = 0; i < arrayList.size(); i++) {
                if (!arrayList.get(i).isSelect()) {
                    s = s + 1;
                }
            }
            txtSelected.setText((arrayList.size() - s) + " selected");
            if (s == arrayList.size()) {
                handlingToolbar();
            }
            itemAdapter.notifyDataSetChanged();
        } else {
            if (floatingActionMenu.isOpened()) {
                floatingActionMenu.close(true);
            }
            if (!arrayList.get(position).isFile()) {
                pathItem = arrayList.get(position).getPathFile();
                readFile(pathItem);
                getTitle();
            }
        }
    }

    public void handlingToolbar() {
        itemAdapter.checkClickBox = ItemAdapter.NOT_SELECTED;
        int actionBarSize = 0;
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarSize = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        actionBarSize = actionBarSize / 2;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, HelpUtil.pxFromDp(getContext(), actionBarSize), 0, 0);
        ((HomeActivity) getActivity()).getContent().setLayoutParams(lp);
        relativeToolBar.setVisibility(View.GONE);
    }

    @Override
    public void onLongClick(CheckBox checkBox, int position) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 0, 0);
        ((HomeActivity) getActivity()).getContent().setLayoutParams(lp);
        relativeToolBar.setVisibility(View.VISIBLE);
        itemAdapter.checkClickBox = ItemAdapter.SELECTED;
        txtSelected.setText("1 selected");
        checkSelectAll = 1;
        checkBox.setVisibility(View.VISIBLE);
        checkBox.setChecked(true);
        arrayList.get(position).setSelect(true);
        itemAdapter.notifyDataSetChanged();
    }
}
