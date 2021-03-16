package com.yh.todaynews.view;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yh.imageloader.ImageLoader;
import com.yh.paginationadapter.PaginationAdapter;
import com.yh.todaynews.R;
import com.yh.todaynews.utils.TimeUtils;
import com.yh.todaynews.view.ImagesAdapter;
import com.yh.todaynews.view.WebViewActivity;
import com.yh.todaynews.model.News;
import com.yh.todaynews.viewmodle.PageViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends PaginationAdapter<News> {
    private final ImageLoader imageLoader;
    private final FragmentActivity activity;
    public final static int PAGE_SIZE = 5;

    public NewsAdapter(List<News> newsList, ImageLoader imageLoader, FragmentActivity activity) {
        super(newsList);
        this.imageLoader = imageLoader;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mews_item_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onItemBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsViewHolder newsViewHolder = (NewsViewHolder)holder;
        News news = list.get(position);
        newsViewHolder.title.setText(news.getTitle());
        newsViewHolder.author.setText(news.getAuthor());
        newsViewHolder.date.setText(TimeUtils.timeFormat(news.getDate()));
        List<String> imgs = new ArrayList<>();
        if (news.getThumbnail1() != null && !"".equals(news.getThumbnail1())) {
            imgs.add(news.getThumbnail1());
        }
        if (news.getThumbnail2() != null && !"".equals(news.getThumbnail2())) {
            imgs.add(news.getThumbnail2());
        }
        if (news.getThumbnail3() != null && !"".equals(news.getThumbnail3())) {
            imgs.add(news.getThumbnail3());
        }
        ImagesAdapter imagesAdapter = new ImagesAdapter(imgs, imageLoader);
        newsViewHolder.imgs.setAdapter(imagesAdapter);
        newsViewHolder.imgs.setLayoutManager(new GridLayoutManager(newsViewHolder.imgs.getContext(), 3));

        String url = news.getUrl();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebViewActivity.class);
                intent.putExtra("url", url);
                activity.startActivity(intent);
            }
        });
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final RecyclerView imgs;
        private final TextView author;
        private final TextView date;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imgs = itemView.findViewById(R.id.imgs);
            author = itemView.findViewById(R.id.author);
            date = itemView.findViewById(R.id.date);
        }
    }
    public void updateData(List<News> news){
        list.clear();
        list.addAll(news);
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<News> getNextPageNews(int position, int pageSize) {
        PageViewModel pageViewModel = new PageViewModel(activity);
        return pageViewModel.getNextPage(position, pageSize);
    }
}
