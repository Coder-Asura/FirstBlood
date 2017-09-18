package com.liuxd.firstblood.ui.welcome;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.liuxd.firstblood.R;
import com.liuxd.firstblood.ui.base.BaseActivity;
import com.liuxd.firstblood.ui.home.HomeActivity;

import butterknife.BindView;

/**
 * Created by Liuxd on 2017/7/25 14:24.
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;

    @Override
    public int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        Animation animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.alpha_iv_logo);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                requestPermission(new String[]{Manifest.permission.READ_PHONE_STATE});
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mIvLogo.startAnimation(animation);
    }

    @Override
    public void onPermissionResult(boolean isAllow) {
        super.onPermissionResult(isAllow);
        if (isAllow) {
            startActivity(HomeActivity.class);
            finish();
        } else {
            finish();
        }
    }
}
