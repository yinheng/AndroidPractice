package com.yh.todaynews.ui.news.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class News {
    private final String title;
    private final String date;

    private final String author;
    private final String url;
    private final String thumbnail1;
    private final String thumbnail2;
    private final String thumbnail3;
}
