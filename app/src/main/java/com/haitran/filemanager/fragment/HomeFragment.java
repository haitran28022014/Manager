package com.haitran.filemanager.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.gelitenight.waveview.library.WaveView;
import com.haitran.filemanager.R;
import com.haitran.filemanager.activity.HomeActivity;
import com.haitran.filemanager.adapter.HomeAdapter;
import com.haitran.filemanager.manager.FileManager;
import com.haitran.filemanager.manager.HomeManager;
import com.haitran.filemanager.model.Category;
import com.haitran.filemanager.view.WaveHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hai Tran on 1/17/2017.
 */

public class HomeFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {
    public static final int STORAGE_INTERNAL = 1;
    public static final int MICRO_SD = 2;
    public static final String KEY_1 = "1";
    @Bind(R.id.wave_storage)
    WaveView waveStorage;

    @Bind(R.id.wave_sd)
    WaveView waveSd;

    @Bind(R.id.txt_used)
    TextView txtUsed;

    @Bind(R.id.txt_total)
    TextView txtTotal;

    @Bind(R.id.txt_used_sd)
    TextView txtUsedSd;

    @Bind(R.id.txt_total_sd)
    TextView txtTotalSd;

    @Bind(R.id.grid_view)
    GridView gridView;

    private WaveHelper mWaveHelperStorage;
    private WaveHelper mWaveHelperSd;
    private int mBorderWidth = 3;
    private FileManager fileManager;
    private HomeAdapter homeAdapter;
    private HomeManager homeManger;
    private MainFragment mainFragment;
    private ArrayList<Category> arrCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
        initData();
    }

    private void initViews() {
        mWaveHelperStorage = new WaveHelper(waveStorage);
        mWaveHelperSd = new WaveHelper(waveSd);
        homeManger = new HomeManager();
        arrCategory = homeManger.getCategory();
        homeAdapter = new HomeAdapter(arrCategory, getContext());
        gridView.setAdapter(homeAdapter);
        gridView.setOnTouchListener(this);
        waveStorage.setShapeType(WaveView.ShapeType.CIRCLE);
        waveStorage.setWaterLevelRatio(1);
        waveStorage.setBorder(mBorderWidth, Color.BLACK);


        waveSd.setShapeType(WaveView.ShapeType.CIRCLE);
        waveSd.setBorder(mBorderWidth, Color.BLACK);
    }

    public void initData() {
        fileManager = new FileManager(getContext());
        String resultInternal = fileManager.getSizeInternal();
        String[] split = resultInternal.split("_");
        float values = Float.parseFloat(split[1].replace(",", ".")) / Float.parseFloat(split[0].replace(",", "."));
        if (values > 0.8) {
            waveStorage.setWaveColor(Color.parseColor("#EF5350"), Color.parseColor("#E53935"));
        } else {
            waveStorage.setWaveColor(Color.parseColor("#A5D6A7"), Color.parseColor("#4CAF50"));
        }
        txtTotal.setText(split[0] + " GB");
        txtUsed.setText(split[1] + " GB");
        mWaveHelperStorage.getWaterLevelAnim().setFloatValues(0f, values);

        String resultSd = fileManager.getSdCard();
        String[] split1 = resultSd.split("_");
        float values1 = Float.parseFloat(split1[1].replace(",", ".")) / Float.parseFloat(split1[0].replace(",", "."));
        if (values1 > 0.8) {
            waveSd.setWaveColor(Color.parseColor("#EF5350"), Color.parseColor("#E53935"));
        } else {
            waveSd.setWaveColor(Color.parseColor("#A5D6A7"), Color.parseColor("#4CAF50"));
        }
        txtTotalSd.setText(split1[0] + " GB");
        txtUsedSd.setText(split1[1] + " GB");
        mWaveHelperSd.getWaterLevelAnim().setFloatValues(0f, values1);
    }

    @Override
    public void onPause() {
        super.onPause();
        mWaveHelperStorage.cancel();
        mWaveHelperSd.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWaveHelperStorage.start();
        mWaveHelperSd.start();
        waveStorage.setOnClickListener(this);
        waveSd.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wave_storage:
                Bundle bundle = new Bundle();
                bundle.putInt(KEY_1, STORAGE_INTERNAL);
                mainFragment = MainFragment.newInstance(bundle);
                ((HomeActivity) getActivity()).backToMainFragment(HomeFragment.this);
                ((HomeActivity) getActivity()).replaceFragment(mainFragment, HomeActivity.TAG_FRAGMENT_MAIN);
                break;
            case R.id.wave_sd:
                bundle = new Bundle();
                bundle.putInt(KEY_1, MICRO_SD);
                mainFragment = MainFragment.newInstance(bundle);
                ((HomeActivity) getActivity()).backToMainFragment(HomeFragment.this);
                ((HomeActivity) getActivity()).replaceFragment(mainFragment, HomeActivity.TAG_FRAGMENT_MAIN);
                break;
        }
    }


    public MainFragment getMainFragment() {
        return mainFragment;
    }

    public void setMainFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }
}
