package com.yh.paginationadapter;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class PaginationAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int FOOTER = 1;
    private final int ITEM = 2;
    private final int LOADING = 1;
    private final int LOAD_END = 2;
    private final int LOAD_COMPLETE = 3;
    protected final List<T> list;
    private int currentState = LOAD_COMPLETE;

    public PaginationAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return FOOTER;
        }
        return ITEM;
    }

    private void setState(int state) {
        currentState = state;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return onCreateItemViewHolder(parent, viewType);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof FooterViewHolder)) {
            onItemBindViewHolder(holder, position);
            return;
        }

        FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
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

            case LOAD_COMPLETE:
                footerViewHolder.progressBar.setVisibility(View.INVISIBLE);
                footerViewHolder.loadMore.setVisibility(View.INVISIBLE);
                footerViewHolder.loadEnd.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public abstract RecyclerView.ViewHolder onCreateItemViewHolder(@NonNull ViewGroup parent, int viewType);

    public abstract void onItemBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar progressBar;
        private final TextView loadMore, loadEnd;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            loadMore = itemView.findViewById(R.id.loadMore);
            loadEnd = itemView.findViewById(R.id.loadEnd);
        }
    }

    public abstract List<T> getNextPageNews(int position, int pageSize);

    public void findNextPage(int total, int position, int pageSize) {
        if (position < total) {
            setState(LOADING);
            notifyItemChanged(position);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<T> nextPage = getNextPageNews(position, pageSize);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            list.addAll(nextPage);
                            notifyItemRangeChanged(position, total);
                        }
                    });
                }
            }).start();

        } else {
            setState(LOAD_END);
            notifyItemChanged(position);
        }
    }
}
