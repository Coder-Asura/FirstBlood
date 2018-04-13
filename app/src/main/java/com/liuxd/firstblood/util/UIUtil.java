package com.liuxd.firstblood.util;

import com.liuxd.firstblood.MyApp;

/**
 * Created by Liuxd on 2017/9/18 11:07.
 */
public class UIUtil {
    public static String getString(int resId) {
        return MyApp.getInstance().getResources().getString(resId);
    }
}
