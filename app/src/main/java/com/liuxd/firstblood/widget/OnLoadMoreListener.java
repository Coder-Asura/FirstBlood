package com.liuxd.firstblood.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Liuxd on 2016/11/26 15:50.
 */

public class OnLoadMoreListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    int lastVisibleItemPos = mLayoutManager.findLastVisibleItemPosition();

}
