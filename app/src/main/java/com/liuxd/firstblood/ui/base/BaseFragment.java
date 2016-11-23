package com.liuxd.firstblood.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuxd.firstblood.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Liuxd on 2016/11/14 18:38.
 * <p>
 * 通用的Fragment
 * </p>
 * 集合了ButterKnife、沉浸式状态栏、侧滑返回、activity栈管理以及一些常用的方法
 */

public abstract class BaseFragment extends Fragment implements IBase, BaseView {
    private View mRootView;
    private List<View> mViews;
    private boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;
    private Unbinder mUnbinder;
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (context instanceof Activity) {
                onAttachToContext(context);
            }
        }
        onAttachToContext(context);
    }

    /**
     * 因为Api23之后的方法不一样，所以都调用这个方法
     *
     * @param context 上下文（一般都是数据交互的接口）
     */
    public void onAttachToContext(Context context) {
        LogUtil.d(TAG, "onAttachToContext");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreateView");
        if (null != mRootView) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        }
        mRootView = inflater.inflate(setLayoutId(), null);
        mViews=new ArrayList<>();
        mViews.add(mRootView);
        mUnbinder = ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.d(TAG, "onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(TAG, "onActivityCreated");
        isPrepared = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d(TAG, "setUserVisibleHint");
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
//            onInvisible();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume");
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        lazyRequest();
        isFirst = false;
    }

    /**
     * fragment被设置为不可见时调用
     */
//    protected abstract void onInvisible();

    /**
     * 这里获取数据，刷新界面
     */
    public  void lazyRequest(){};
    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(TAG, "onDestroyView");
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d(TAG, "onDetach");
    }

    @Override
    public void showLoading() {
//View view=LayoutInflater.from()
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }
}
