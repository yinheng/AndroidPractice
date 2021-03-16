package com.yh.todaynews.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yh.imageloader.ImageLoader;
import com.yh.todaynews.R;
import com.yh.todaynews.model.News;
import com.yh.todaynews.viewmodle.PageViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private ImageLoader imageLoader;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new PageViewModel(getActivity());
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Log.e("PlaceholderFragment", "index = " + index);
        imageLoader = ((MainActivity) Objects.requireNonNull(getActivity())).getImageLoader();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.e("PlaceholderFragment", "onCreateView");
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        swipeRefreshLayout.setDistanceToTriggerSync(300);

        final RecyclerView recyclerView = root.findViewById(R.id.news);
        final NewsAdapter[] newsAdapter = {null};
        final Integer[] totalSize = {0};

        initData(recyclerView, newsAdapter, totalSize, swipeRefreshLayout);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager linearLayoutManager;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                    int totalCount = linearLayoutManager.getItemCount();
                    if (lastPosition == (totalCount - 1)) {

                        // 加载更多
                        newsAdapter[0].findNextPage(totalSize[0], lastPosition, NewsAdapter.PAGE_SIZE);
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(recyclerView, newsAdapter, totalSize, swipeRefreshLayout);
            }
        });

        int index = getArguments().getInt(ARG_SECTION_NUMBER);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData(RecyclerView recyclerView, NewsAdapter[] newsAdapter, Integer[] totalSize, SwipeRefreshLayout swipeRefreshLayout) {
        pageViewModel.getLocalNewsByPage(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> map) {

                totalSize[0] = (Integer) map.get("total");
                List<News> newsList = new ArrayList<>();
                if (map.get("newsList") instanceof ArrayList<?>) {
                    for (Object o : (ArrayList<?>) Objects.requireNonNull(map.get("newsList"))) {
                        newsList.add((News) o);
                    }
                }

                newsAdapter[0] = new NewsAdapter(newsList, imageLoader, getActivity());
                recyclerView.setAdapter(newsAdapter[0]);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 0, NewsAdapter.PAGE_SIZE);

        pageViewModel.getRemoteNews(new Consumer<List<News>>() {
            @Override
            public void accept(List<News> news) {
                if (news != null) {
                    totalSize[0] = news.size();
                    List<News> newsList  = news.size() > NewsAdapter.PAGE_SIZE ? news.subList(0, NewsAdapter.PAGE_SIZE) : news;
                    if (newsAdapter[0] == null) {
                        newsAdapter[0] = new NewsAdapter(newsList, imageLoader, getActivity());
                        recyclerView.setAdapter(newsAdapter[0]);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    } else {
                        newsAdapter[0].updateData(newsList);
                    }
                }

            }
        });
    }

}