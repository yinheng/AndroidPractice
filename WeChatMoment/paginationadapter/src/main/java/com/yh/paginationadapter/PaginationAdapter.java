package com.yh.paginationadapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class PaginationAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final List<T> list;
    private final int FOOTER_TYPE = 1;
    private final int ITEM_TYPE = 2;
    private final int LOADING = 0;
    private final int LOAD_COMPLETE = 1;
    private final int LOAD_END = 2;
    private int currentState = LOAD_COMPLETE;
    protected int totalSize;


    public PaginationAdapter(List<T> list, int totalSize) {
        this.list = list;
        this.totalSize = totalSize;
    }

    private void setCurrentState(int state) {
        this.currentState = state;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE) {
            return onCreateItemViewHolder(parent, viewType);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof FooterViewHolder)) {
            onBindItemViewHolder(holder, position);
            return;
        }
        Log.e("Repositories", "position = " + position);

        FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        checkState();
        switch (currentState) {
            case LOADING:
                footerViewHolder.progressBar.setVisibility(View.VISIBLE);
                footerViewHolder.loadMore.setVisibility(View.VISIBLE);
                footerViewHolder.loadEnd.setVisibility(View.GONE);
                break;
            case LOAD_END:
                footerViewHolder.progressBar.setVisibility(View.GONE);
                footerViewHolder.loadMore.setVisibility(View.GONE);
                footerViewHolder.loadEnd.setVisibility(View.VISIBLE);
                break;
        }

        if (currentState == LOADING) {
            startLoading(footerViewHolder.itemView.getContext());
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOTER_TYPE;
        }
        return ITEM_TYPE;
    }

    public abstract RecyclerView.ViewHolder onCreateItemViewHolder(@NonNull ViewGroup parent, int viewType);

    public abstract void onBindItemViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        private final TextView loadMore;
        private final ProgressBar progressBar;
        private final TextView loadEnd;
        private final View itemView;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            loadMore = itemView.findViewById(R.id.loadMore);
            loadEnd = itemView.findViewById(R.id.loadEnd);
            progressBar = itemView.findViewById(R.id.progressBar);
            this.itemView = itemView;
        }
    }

    public abstract List<T> loadNextPage(Context context, int position);

    private void checkState() {
        int currentItemCount = getItemCount() - 1;
        if (currentItemCount < totalSize) {
            setCurrentState(LOADING);
        } else {
            setCurrentState(LOAD_END);
        }
    }

    private void startLoading(Context context) {
        int currentItemCount = getItemCount() - 1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<T> nextItems = loadNextPage(context, currentItemCount);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        list.addAll(nextItems);
                        notifyItemRangeChanged(currentItemCount, totalSize);
                    }
                });
            }
        }).start();
    }

    private void changeLoadState(Context context, int position) {
        Log.e("Repositories", "position = " + position + " getItemCount = " + getItemCount() + " totalSize = " + totalSize);
        int currentSize = getItemCount() - 1;
        if (currentSize < totalSize) {
            currentState = LOADING;
            notifyItemChanged(currentSize);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<T> tList = loadNextPage(context, currentSize);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            list.addAll(tList);
                            currentState = LOAD_COMPLETE;
                            Log.e("Repositories", "position = " + position + " getItemCount = " + getItemCount());
                            notifyItemRangeChanged(currentSize, totalSize);
                        }
                    });
                }
            }).start();


        } else {
            currentState = LOAD_COMPLETE;
            notifyItemRangeChanged(currentSize, totalSize);
        }
    }
}
