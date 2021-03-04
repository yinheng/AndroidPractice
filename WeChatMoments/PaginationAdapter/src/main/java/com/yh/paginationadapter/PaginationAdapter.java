package com.yh.paginationadapter;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class PaginationAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter {

    private static final int FOOTER_TYPE = 1;
    private static final int ITEM_TYPE = 2;
    private static final int LOADING = 1;
    private static final int LOAD_COMPLETE = 2;
    private static final int LOAD_END = 3;
    protected static final int PAGE_SIZE = 5;
    protected final List<T> list;
    private int currentState = LOAD_COMPLETE;
    private final int totalCount;

    public PaginationAdapter(List<T> list, int totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            //onLoadStateChange(position);
            return FOOTER_TYPE;
        }
        return ITEM_TYPE;
    }

    private void setLoadState(int state) {
        this.currentState = state;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FOOTER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
            return new FooterViewHolder(view);
        }

        return createItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof FooterViewHolder)) {
            bindItemViewHolder(holder, position);
            return;
        }

        FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        switch (currentState) {
            case LOADING:
                footerViewHolder.progressBar.setVisibility(View.VISIBLE);
                footerViewHolder.tv.setVisibility(View.VISIBLE);
                footerViewHolder.loadEnd.setVisibility(View.GONE);
                break;
            case LOAD_COMPLETE:
                footerViewHolder.progressBar.setVisibility(View.GONE);
                footerViewHolder.tv.setVisibility(View.GONE);
                footerViewHolder.loadEnd.setVisibility(View.GONE);
                break;
            case LOAD_END:
                footerViewHolder.progressBar.setVisibility(View.GONE);
                footerViewHolder.tv.setVisibility(View.GONE);
                footerViewHolder.loadEnd.setVisibility(View.VISIBLE);
                break;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                onLoadStateChange();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public abstract VH createItemViewHolder(@NonNull ViewGroup parent, int viewType);

    public abstract void bindItemViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private TextView tv;
        private LinearLayout loadEnd;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.process);
            tv = itemView.findViewById(R.id.loadMoreText);
            loadEnd = itemView.findViewById(R.id.loadEnd);
        }
    }

    private void onLoadStateChange() {
        int currentItemCount = getItemCount() - 1;
        if (currentItemCount < totalCount) {
            setLoadState(LOADING);
            notifyItemChanged(currentItemCount);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<T> nextItems = findNextPage(currentItemCount);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            list.addAll(nextItems);
                            setLoadState(LOAD_COMPLETE);
                            notifyItemRangeChanged(currentItemCount, totalCount);
                        }
                    });
                }
            }).start();


        } else {
            setLoadState(LOAD_END);
            notifyItemRangeChanged(currentItemCount, totalCount);
        }
    }

    public abstract List<T> findNextPage(int currentItemCount);
}
