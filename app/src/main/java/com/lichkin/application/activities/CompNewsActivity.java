package com.lichkin.application.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.in.impl.GetCompNewsPageIn;
import com.lichkin.application.beans.out.impl.GetCompNewsPageOut;
import com.lichkin.application.invokers.impl.GetCompNewsPageInvoker;
import com.lichkin.application.testers.GetCompNewsPageTester;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKImageLoader;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.lichkin.framework.defines.beans.LKPageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司新闻页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class CompNewsActivity extends AppCompatActivity {

    /** 公司ID */
    private String compId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_comp_news);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //绑定页面
        ButterKnife.bind(this);

        compId = getIntent().getStringExtra("tabId");

        //初始化新闻栏
        initNewsSection();
    }

    /** 新闻栏控件 */
    @BindView(R.id.news)
    PullToRefreshListView newsSection;
    /** 数据列表 */
    private List<GetCompNewsPageOut> newsList = new ArrayList<>();
    /** 适配器 */
    private BaseAdapter adapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public GetCompNewsPageOut getItem(int position) {
            return newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //取对应的新闻对象
            final GetCompNewsPageOut news = getItem(position);

            //根据图片数量确定布局
            List<String> imageUrls = news.getImageUrls();
            int imageSize = imageUrls == null ? 0 : imageUrls.size();
            int layoutResId = 0;
            switch (imageSize) {
                case 9:
                    if (LKPropertiesLoader.newsPositionTop) {
                        layoutResId = R.layout.news_item_9_top;
                    } else {
                        layoutResId = R.layout.news_item_9_bottom;
                    }
                    break;
                case 8:
                    if (LKPropertiesLoader.newsPositionTop) {
                        layoutResId = R.layout.news_item_8_top;
                    } else {
                        layoutResId = R.layout.news_item_8_bottom;
                    }
                    break;
                case 7:
                    if (LKPropertiesLoader.newsPositionTop) {
                        layoutResId = R.layout.news_item_7_top;
                    } else {
                        layoutResId = R.layout.news_item_7_bottom;
                    }
                    break;
                case 6:
                    if (LKPropertiesLoader.newsPositionTop) {
                        layoutResId = R.layout.news_item_6_top;
                    } else {
                        layoutResId = R.layout.news_item_6_bottom;
                    }
                    break;
                case 5:
                    if (LKPropertiesLoader.newsPositionTop) {
                        layoutResId = R.layout.news_item_5_top;
                    } else {
                        layoutResId = R.layout.news_item_5_bottom;
                    }
                    break;
                case 4:
                    if (LKPropertiesLoader.newsPositionTop) {
                        layoutResId = R.layout.news_item_4_top;
                    } else {
                        layoutResId = R.layout.news_item_4_bottom;
                    }
                    break;
                case 3:
                    if (LKPropertiesLoader.newsPositionTop) {
                        layoutResId = R.layout.news_item_3_top;
                    } else {
                        layoutResId = R.layout.news_item_3_bottom;
                    }
                    break;
                case 2:
                    if (LKPropertiesLoader.newsPositionTop) {
                        layoutResId = R.layout.news_item_2_top;
                    } else {
                        layoutResId = R.layout.news_item_2_bottom;
                    }
                    break;
                case 1:
                    if (LKPropertiesLoader.newsPositionTop) {
                        layoutResId = R.layout.news_item_1_top;
                    } else {
                        layoutResId = R.layout.news_item_1_bottom;
                    }
                    break;
                case 0:
                    //无图样式
                    layoutResId = R.layout.news_item_0;
                    break;
            }

            //添加布局
            convertView = View.inflate(newsSection.getContext(), layoutResId, null);

            //点击事件监听
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LKWebViewActivity.open(newsSection.getContext(), news.getUrl());
                }
            });

            //设置标题
            ((TextView) convertView.findViewById(R.id.news_title)).setText(news.getTitle());

            //设置简介
            ((TextView) convertView.findViewById(R.id.news_brief)).setText(news.getBrief());

            //设置图片
            switch (imageSize) {
                case 9:
                    LKImageLoader.load(imageUrls.get(8), (ImageView) convertView.findViewById(R.id.news_image_9));
                case 8:
                    LKImageLoader.load(imageUrls.get(7), (ImageView) convertView.findViewById(R.id.news_image_8));
                case 7:
                    LKImageLoader.load(imageUrls.get(6), (ImageView) convertView.findViewById(R.id.news_image_7));
                case 6:
                    LKImageLoader.load(imageUrls.get(5), (ImageView) convertView.findViewById(R.id.news_image_6));
                case 5:
                    LKImageLoader.load(imageUrls.get(4), (ImageView) convertView.findViewById(R.id.news_image_5));
                case 4:
                    LKImageLoader.load(imageUrls.get(3), (ImageView) convertView.findViewById(R.id.news_image_4));
                case 3:
                    LKImageLoader.load(imageUrls.get(2), (ImageView) convertView.findViewById(R.id.news_image_3));
                case 2:
                    LKImageLoader.load(imageUrls.get(1), (ImageView) convertView.findViewById(R.id.news_image_2));
                case 1:
                    LKImageLoader.load(imageUrls.get(0), (ImageView) convertView.findViewById(R.id.news_image_1));
            }

            //返回视图
            return convertView;
        }

    };

    /** 页码 */
    private int pageNumber = 0;
    /** 已经最后一页 */
    private boolean last;

    /**
     * 初始化新闻栏
     */
    private void initNewsSection() {
        //新闻栏高度
        int newsSectionHeight = getResources().getDisplayMetrics().heightPixels//总高度
                - (int) LKAndroidUtils.getStatusBarHeight()//状态栏高度
                ;
        ViewGroup.LayoutParams newsParams = newsSection.getLayoutParams();
        newsParams.height = newsSectionHeight;
        newsSection.setLayoutParams(newsParams);

        //设置适配器
        newsSection.getRefreshableView().setAdapter(adapter);

        //设置刷新模式
        newsSection.setMode(PullToRefreshBase.Mode.DISABLED);

        //设置刷新事件监听
        newsSection.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //清空列表
                newsList.clear();
                //更新列表状态
                adapter.notifyDataSetChanged();
                //请求新闻数据
                invokeGetCompNewsPage();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (last) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LKToast.showTip(R.string.invoke_no_more_data);
                            //更新列表状态
                            newsSection.onRefreshComplete();
                        }
                    }, 500);
                    return;
                }
                //请求新闻数据
                invokeGetCompNewsPage();
            }

        });

        //有新闻背景
        newsSection.setBackground(LKAndroidUtils.getDrawable(R.drawable.no_news));

        //请求新闻数据
        invokeGetCompNewsPage();
    }

    /**
     * 请求获取新闻分页
     */
    private void invokeGetCompNewsPage() {
        //请求参数
        GetCompNewsPageIn in = new GetCompNewsPageIn(pageNumber++, compId);

        //创建请求对象
        final LKRetrofit<GetCompNewsPageIn, LKPageBean<GetCompNewsPageOut>> retrofit = new LKRetrofit<>(CompNewsActivity.this, GetCompNewsPageInvoker.class);

        //测试代码
        GetCompNewsPageTester.test(retrofit, in);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetCompNewsPageIn, LKPageBean<GetCompNewsPageOut>>() {

            @Override
            protected void success(Context context, GetCompNewsPageIn getLastAppVersionIn, final LKPageBean<GetCompNewsPageOut> responseDatas) {
                if (responseDatas == null) {
                    //更新列表状态
                    newsSection.onRefreshComplete();

                    //无数据禁用上拉刷新
                    if (newsSection.getMode().equals(PullToRefreshBase.Mode.PULL_FROM_END)) {
                        newsSection.setMode(PullToRefreshBase.Mode.DISABLED);
                    }
                    return;
                }

                //有数据开启上拉刷新
                if (newsSection.getMode().equals(PullToRefreshBase.Mode.DISABLED)) {
                    newsSection.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                    //有新闻背景
                    newsSection.setBackground(LKAndroidUtils.getDrawable(R.drawable.has_news));
                }

                //将结果数据加入到列表中
                newsList.addAll(responseDatas.getContent());

                //更新列表状态
                newsSection.onRefreshComplete();
                adapter.notifyDataSetChanged();

                //最后一页禁用刷新
                if (responseDatas.isLast()) {
                    last = true;
                }
            }

            @Override
            protected void busError(Context context, GetCompNewsPageIn GetCompNewsPageIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, GetCompNewsPageIn, errorCode, errorType, errorBean);
                newsSection.onRefreshComplete();
            }

            @Override
            public void connectError(Context context, String requestId, GetCompNewsPageIn GetCompNewsPageIn, DialogInterface dialog) {
                super.connectError(context, requestId, GetCompNewsPageIn, dialog);
                newsSection.onRefreshComplete();
            }

            @Override
            public void timeoutError(Context context, String requestId, GetCompNewsPageIn GetCompNewsPageIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, GetCompNewsPageIn, dialog);
                newsSection.onRefreshComplete();
            }

        });
    }

}
