package com.yh.wechatmoments;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            assert manager != null;
            int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            int totalItems = manager.getItemCount();
            Log.e("onScrollStateChanged", "lastVisibleItemPosition : " + lastVisibleItemPosition);
            Log.e("onScrollStateChanged", "totalItems : " + totalItems);
            if (lastVisibleItemPosition == (totalItems - 1) && !recyclerView.canScrollVertically(1)) {
                // 加载更多items
                loadMoreItems();
            }
        }


    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.e("onScrolled", "dy : " + dy);
        isSlidingUpward = dy > 0;
    }



    public abstract void loadMoreItems();
}
