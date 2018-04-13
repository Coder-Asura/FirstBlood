package com.liuxd.firstblood.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity管理类,可以实现快速关闭到指定activity
 * Created by Liuxd on 2016/7/4 9:11.
 */
public class AppManager {
    private static List<Activity> activityList;

    private AppManager() {
        activityList = new ArrayList<Activity>();
    }

    private static AppManager instance;

    public static AppManager getInstance() {
        if (instance == null)
            synchronized (AppManager.class) {
                if (instance == null)
                    instance = new AppManager();
            }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
        for (int i = 0; i < activityList.size(); i++) {
            LogUtil.d("activityListAdd", activityList.get(i).getClass().getSimpleName());
        }
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
        for (int i = 0; i < activityList.size(); i++) {
            LogUtil.d("activityListRemove", activityList.get(i).getClass().getSimpleName());
        }
    }

    public Activity getLastActivity() {
        if (activityList.size() >= 1) {
            return activityList.get(activityList.size() - 1);
        }
        return null;
    }

    public void exitApp() {
        finishAllActivities();
        try {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e, "退出程序异常！");
        }
    }

    private void finishAllActivities() {
        for (Activity activity : activityList) {
            try {
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        activityList.clear();
    }

    public void finishToActivity(Class<Activity> activity) {
        if (activityList == null || activityList.size() == 0)
            return;
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity item = activityList.get(i);
            LogUtil.d("activityList", item.getClass() + "=====" + activity);
            if (item.getClass().equals(activity)) {
                return;
            } else {
                try {
                    item.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void finishToActivity(Activity activity) {
        if (activityList == null || activityList.size() == 0)
            return;
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity item = activityList.get(i);
            if (item != activity) {
                try {
                    item.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
        }
    }

    public void finishToActivityBefore(Class activity) {
        if (activityList == null || activityList.size() == 0)
            return;
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity item = activityList.get(i);
            try {
                if (item.getClass().equals(activity)) {
                    item.finish();
                    return;
                } else {
                    item.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void finishToActivityBefore(Activity activity) {
        if (activityList == null || activityList.size() == 0)
            return;
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity item = activityList.get(i);
            try {
                if (item == activity) {
                    item.finish();
                    return;
                } else {
                    item.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void finishActivity(Class activity) {
        for (Activity a : activityList) {
            if (a.getClass().equals(activity)) {
                a.finish();
            }
        }
    }
}
