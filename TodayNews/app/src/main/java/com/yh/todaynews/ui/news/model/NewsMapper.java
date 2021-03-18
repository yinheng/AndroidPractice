package com.yh.todaynews.ui.news.model;

import com.yh.todaynews.data.model.NewsData;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NewsMapper {
    public News toNews(NewsData data) {
        return new News(data.getTitle(),
                data.getDate(),
                data.getAuthor(),
                data.getUrl(),
                data.getThumbnail1(),
                data.getThumbnail2(),
                data.getThumbnail3());
    }

    public List<News> toNewsList(List<NewsData> data) {
        return data.stream().map(new Function<NewsData, News>() {
            @Override
            public News apply(NewsData data1) {
                return NewsMapper.this.toNews(data1);
            }
        }).collect(Collectors.toList());
    }
}
