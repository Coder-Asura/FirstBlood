package com.liuxd.firstblood.ui.base;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jaeger.library.StatusBarUtil;
import com.liuxd.firstblood.R;
import com.liuxd.firstblood.util.AppManager;
import com.liuxd.firstblood.util.LogUtil;
import com.liuxd.firstblood.util.ToastUtil;
import com.liuxd.firstblood.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Liuxd on 2016/11/21 10:41.
 * <p>
 * 通用的Activity
 * </p>
 * 集合了ButterKnife、沉浸式状态栏、侧滑返回、activity栈管理以及一些常用的方法
 */

public abstract class BaseActivity extends AppCompatActivity implements IBase {
    @BindColor(R.color.colorStatusBar)
    int statusBarColor;
    Unbinder mUnbinder;
    public final String TAG = this.getClass().getSimpleName();
    private CompositeSubscription mCompositeSubscription;

    private void addCompositeSubscription(Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscriber);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate");
        setContentView(setLayoutId());//设置布局文件
        mUnbinder = ButterKnife.bind(this);//奶油刀绑定布局
        setStatusBarColor(statusBarColor, 0);//设置状态栏颜色
//        initSlidrConfig();//初始化滑动返回
        AppManager.getInstance().addActivity(this);
        initToolBar();
        init(savedInstanceState);//一些初始化操作
    }

    private void initToolBar() {
    }

    /*private void initSlidrConfig() {
        SlidrConfig config = new SlidrConfig.Builder()
//                .primaryColor(getResources().getColor(R.color.colorPrimary))//滑动时状态栏的渐变结束的颜色
                .position(SlidrPosition.LEFT)//从左边滑动
//                .sensitivity(1f)
//                .scrimColor(Color.BLACK)
//                .scrimStartAlpha(0.8f)//滑动开始时两个Activity之间的透明度
//                .scrimEndAlpha(0f)//滑动结束时两个Activity之间的透明度
                .velocityThreshold(4800)//超过这个滑动速度，忽略位移限定值就切换Activity
                .distanceThreshold(0.5f)//滑动位移占屏幕的百分比，超过这个间距就切换Activity
                .edge(true)//只能从边上开始滑动
//                .edgeSize(0.3f) // The % of the screen that counts as the edge, default 18%
//              .listener(new SlidrListener(){...})
                .build();
        Slidr.attach(this, config);
    }*/

    public void setStatusBarColor(int color, int alpha) {
        StatusBarUtil.setColor(this, color, alpha);
    }

//    public void setSwipeBackSupport(boolean support) {
//        if (support) {
//            Slidr.attach(this).unlock();
//        } else {
//            Slidr.attach(this).lock();
//        }
//    }

    public void showToast(int msgId) {
        ToastUtil.getInstance().Short(getString(msgId)).show();
    }

    public void showToast(String msg) {
        ToastUtil.getInstance().Short(msg).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 跳转时隐藏键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (imm != null) {
            if (view != null && view instanceof EditText) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
        LogUtil.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if (mCompositeSubscription != null)
            mCompositeSubscription.unsubscribe();
        LogUtil.d(TAG, "onDestroy");
    }

    /**
     * 通过Class跳转界面
     *
     * @param cls 跳转的activity类名
     */
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     *
     * @param cls    跳转的activity类名
     * @param bundle bundle对象
     */
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 权限申请
     */
    private static final int PERMISSION_REQUEST_CODE = 0;

    protected void requestPermission(String[] needPermissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            for (String needPermission : needPermissions) {
                if (ContextCompat.checkSelfPermission(this, needPermission) != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(needPermission);
                }
            }
            if (permissions.size() > 0) {
                String permissionName = UIUtil.getString(R.string.need_permission_tip);
                boolean isShouldShowTips = false;
                for (int i = 0; i < permissions.size(); i++) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions.get(i))) {
                        if (i != 0)
                            permissionName += "、";
                        isShouldShowTips = true;
                        switch (permissions.get(i)) {
                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                                permissionName += UIUtil.getString(R.string.write_permission);
                                break;
                            case Manifest.permission.ACCESS_COARSE_LOCATION:
                                permissionName += UIUtil.getString(R.string.location_permission);
                                break;
                            case Manifest.permission.READ_PHONE_STATE:
                                permissionName += UIUtil.getString(R.string.phone_state_permission);
                                break;
                            case Manifest.permission.CAMERA:
                                permissionName += UIUtil.getString(R.string.camera_permission);
                                break;
                        }
                    }
                }
                final String[] permissionArry = permissions.toArray(new String[permissions.size()]);
                if (isShouldShowTips) {
                    createDialog(permissionName, UIUtil.getString(R.string.apply_perimission)
                            , (dialog, which) ->
                                    ActivityCompat.requestPermissions(BaseActivity.this, permissionArry, PERMISSION_REQUEST_CODE));
                } else {
                    ActivityCompat.requestPermissions(this, permissionArry, PERMISSION_REQUEST_CODE);
                }
            } else {
                onPermissionResult(true);
            }
        } else {
            onPermissionResult(true);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            String permissionName = UIUtil.getString(R.string.set_permission_tip);
            boolean isPermissionAllow = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (!isPermissionAllow)
                        permissionName += "、";

                    switch (permissions[i]) {
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                            permissionName += UIUtil.getString(R.string.write_permission);
                            break;
                        case Manifest.permission.ACCESS_COARSE_LOCATION:
                            permissionName += UIUtil.getString(R.string.location_permission);
                            break;
                        case Manifest.permission.READ_PHONE_STATE:
                            permissionName += UIUtil.getString(R.string.phone_state_permission);
                            break;
                        case Manifest.permission.CAMERA:
                            permissionName += UIUtil.getString(R.string.camera_permission);
                            break;
                    }
                    isPermissionAllow = false;
                }
            }
            if (isPermissionAllow) {
                onPermissionResult(true);
            } else {
                createDialog(permissionName, UIUtil.getString(R.string.I_know)
                        , (dialog, which) ->
                                onPermissionResult(false));
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void createDialog(String message, String posButtonMsg, DialogInterface.OnClickListener listener) {
        Dialog dialog = new AlertDialog.Builder(BaseActivity.this)
                .setTitle(UIUtil.getString(R.string.prompt))
                .setMessage(message)
                .setPositiveButton(posButtonMsg, listener)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void onPermissionResult(boolean isAllow) {

    }
}
