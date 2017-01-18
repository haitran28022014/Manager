package com.haitran.filemanager.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.haitran.filemanager.R;
import com.haitran.filemanager.fragment.HomeFragment;
import com.haitran.filemanager.fragment.MainFragment;
import com.haitran.filemanager.util.HelpUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.haitran.filemanager.fragment.HomeFragment.KEY_1;
import static com.haitran.filemanager.fragment.HomeFragment.MICRO_SD;
import static com.haitran.filemanager.fragment.HomeFragment.STORAGE_INTERNAL;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private OnClickBackPressed onClickBackPressed;
    public static final String TAG_FRAGMENT_HOME = "TAG_FRAGMENT_HOME";
    public static final String TAG_FRAGMENT_MAIN = "TAG_FRAGMENT_MAIN";
    @Bind(R.id.tool_bar)
    Toolbar toolbar;

    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;

    @Bind(R.id.content)
    LinearLayout content;

    @Bind(R.id.linear_internal)
    LinearLayout linearInternal;

    @Bind(R.id.linear_sd)
    LinearLayout linearSd;

    @Bind(R.id.linear_home)
    LinearLayout linearHome;


    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initViews();
        initData();
        initListener();
    }

    private void initListener() {
        linearInternal.setOnClickListener(this);
        linearSd.setOnClickListener(this);
        linearHome.setOnClickListener(this);
    }

    private void initViews() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        replaceFragment(homeFragment, TAG_FRAGMENT_HOME);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initData() {
        int actionBarSize = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarSize = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        actionBarSize = actionBarSize / 2;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, HelpUtil.pxFromDp(this, actionBarSize), 0, 0);
        content.setLayoutParams(lp);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miSearch:

                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }


    public void replaceFragment(Fragment fragment, String tagFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        transaction.add(R.id.content, fragment, tagFragment);
        transaction.show(fragment);
        transaction.commit();
    }

    public void backToMainFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragment instanceof MainFragment || fragment instanceof MainFragment) {
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        }
        if (fragment instanceof HomeFragment || fragment instanceof HomeFragment) {
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        }
        transaction.remove(fragment);
        fragment.onDestroyView();
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(TAG_FRAGMENT_MAIN) != null) {
            onClickBackPressed.onClickPressed();
        }
        if (fragmentManager.findFragmentByTag(TAG_FRAGMENT_HOME) != null) {
            super.onBackPressed();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_internal:
                Bundle bundle = new Bundle();
                bundle.putInt(KEY_1, STORAGE_INTERNAL);

                if (homeFragment.getMainFragment() == null) {
                    homeFragment.setMainFragment(MainFragment.newInstance(bundle));
                } else {
                    backToMainFragment(homeFragment.getMainFragment());
                    homeFragment.setMainFragment(MainFragment.newInstance(bundle));
                }
                if (homeFragment != null) {
                    backToMainFragment(homeFragment);
                }
                replaceFragment(homeFragment.getMainFragment(), HomeActivity.TAG_FRAGMENT_MAIN);
                drawerLayout.closeDrawers();
                break;
            case R.id.linear_sd:
                bundle = new Bundle();
                bundle.putInt(KEY_1, MICRO_SD);

                if (homeFragment.getMainFragment() == null) {
                    homeFragment.setMainFragment(MainFragment.newInstance(bundle));
                } else {
                    backToMainFragment(homeFragment.getMainFragment());
                    homeFragment.setMainFragment(MainFragment.newInstance(bundle));

                }
                if (homeFragment != null) {
                    backToMainFragment(homeFragment);
                }
                replaceFragment(homeFragment.getMainFragment(), HomeActivity.TAG_FRAGMENT_MAIN);
                drawerLayout.closeDrawers();
                break;
            case R.id.linear_home:
                drawerLayout.closeDrawers();
                if (homeFragment.getMainFragment() != null) {
                    backToMainFragment(homeFragment.getMainFragment());
                }
                backToMainFragment(homeFragment);
                replaceFragment(homeFragment, HomeActivity.TAG_FRAGMENT_HOME);

                break;
        }
    }

//    public void showToolBar(int why) {
//        if (why == SHOW_TOOL) {
//            relativeToolBar.setVisibility(View.INVISIBLE);
//            toolbar.setVisibility(View.VISIBLE);
//        } else {
//            relativeToolBar.setVisibility(View.VISIBLE);
//            toolbar.setVisibility(View.INVISIBLE);
//        }
//    }

    public interface OnClickBackPressed {
        void onClickPressed();
    }


    public void setOnClickBackPressed(OnClickBackPressed onClickBackPressed) {
        this.onClickBackPressed = onClickBackPressed;
    }

    public LinearLayout getContent() {
        return content;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }
}
