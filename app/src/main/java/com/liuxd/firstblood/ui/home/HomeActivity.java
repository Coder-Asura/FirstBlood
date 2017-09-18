package com.liuxd.firstblood.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.MenuItem;

import com.liuxd.firstblood.R;
import com.liuxd.firstblood.constant.Constant;
import com.liuxd.firstblood.ui.base.BaseActivity;
import com.liuxd.firstblood.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Liuxd on 2016/11/21 11:24.
 * 主页面
 */

public class HomeActivity extends BaseActivity {
//    @BindView(R.id.rg_home)
//    RadioGroup mRgHome;

    @BindView(R.id.bottom_nav)
    BottomNavigationView mBottomNav;

    private MenuItem mLastItem;
    private SparseArray<BaseFragment> fragments;
    private FragmentManager manager;
    private int currentIndex = 0;

    @Override
    public int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        initFragments();
        mBottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (mLastItem != item) {
                            mLastItem = item;
                            switch (item.getItemId()) {
                                case R.id.item_news:
                                    showFragment(0);
                                    break;
                                case R.id.item_joke:
                                    showFragment(1);
                                    break;
                                case R.id.item_cardNo:
                                    showFragment(2);
                                    break;
                                case R.id.item_robot:
                                    showFragment(3);
                                    break;
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(Constant.BundleName.CURRENT_INDEX_HOME, 0);
        }
        showFragment(currentIndex);
//        mRgHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_news:
//                        showFragment(0);
//                        break;
//                    case R.id.rb_joke:
//                        showFragment(1);
//                        break;
//                    case R.id.rb_cardNo:
//                        showFragment(2);
//                        break;
//                    case R.id.rb_robot:
//                        showFragment(3);
//                        break;
//                }
//            }
//        });
    }

    private void initFragments() {
        fragments = new SparseArray<>();
        fragments.put(0, new NewsFragment());
        fragments.put(1, new JokeListFragment());
        fragments.put(2, new IdCardFragment());
        fragments.put(3, new RobotFragment());
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            transaction.add(R.id.frame_content, fragments.get(i));
        }
        transaction.commit();
    }

    private void showFragment(int index) {
        currentIndex = index;
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == index) {
                transaction.show(fragments.get(index));
            } else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.BundleName.CURRENT_INDEX_HOME, currentIndex);
    }

}
