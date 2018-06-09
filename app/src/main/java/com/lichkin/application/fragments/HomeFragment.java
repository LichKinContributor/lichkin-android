package com.lichkin.application.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.impl.in.GetBannerListIn;
import com.lichkin.application.beans.impl.out.GetBannerListOut;
import com.lichkin.application.invokers.impl.GetBannerListInvoker;
import com.lichkin.application.testers.GetBannerListTester;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class HomeFragment extends Fragment {

    /** 配置键：Banner图片高宽比例 */
    private static final String CONFIG_KEY_BANNER_RATIO = "lichkin.framework.api.banner.ratio";

    /** 配置值：Banner图片高宽比例 */
    private static final double CONFIG_VALUE_BANNER_RATIO = LKPropertiesLoader.getDivision(CONFIG_KEY_BANNER_RATIO);

    /** Banner控件 */
    private Banner banner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //填充页面
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        //获取Banner控件
        banner = view.findViewById(R.id.banner);

        //设置样式-数字指示器和标题
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {

            @Override
            public void displayImage(Context context, Object obj, ImageView imageView) {
                Picasso.get().load(((GetBannerListOut) obj).getImageUrl()).into(imageView);
            }

        });

        //图片按比例设置值
        ViewGroup.LayoutParams params = banner.getLayoutParams();
        params.height = (int) (getResources().getDisplayMetrics().widthPixels * CONFIG_VALUE_BANNER_RATIO);
        banner.setLayoutParams(params);

        //设置图片
        banner.setImages(new ArrayList<>());

        //开始轮播-如果请求没有结果时会显示无图片
        banner.start();

        //获取Banner列表
        getBannerList();

        //返回视图
        return view;
    }

    /**
     * 获取Banner列表
     */
    private void getBannerList() {
        //请求参数
        GetBannerListIn in = new GetBannerListIn();

        //创建请求对象
        final LKRetrofit<GetBannerListIn, List<GetBannerListOut>> retrofit = new LKRetrofit<>(this.getActivity(), GetBannerListInvoker.class);

        //测试代码
        GetBannerListTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetBannerListIn, List<GetBannerListOut>>() {

            @Override
            protected void success(Context context, GetBannerListIn getLastAppVersionIn, final List<GetBannerListOut> responseDatas) {
                if (responseDatas == null) {
                    return;
                }

                //设置图片
                banner.setImages(responseDatas);
                //设置标题
                banner.setBannerTitles(getBannerTitleList(responseDatas));
                //设置点击事件
                banner.setOnBannerListener(new OnBannerListener() {

                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(getActivity(), LKWebViewActivity.class);
                        intent.putExtra(LKWebViewActivity.KEY_URL, responseDatas.get(position).getPageUrl());
                        startActivity(intent);
                    }

                });
                //开始轮播
                banner.start();
            }

        });
    }

    /**
     * 提取Banner标题列表
     * @param bannerList Banner列表
     * @return Banner标题列表
     */
    private List<String> getBannerTitleList(List<GetBannerListOut> bannerList) {
        List<String> titleList = new ArrayList<>(bannerList.size());
        for (int i = 0; i < bannerList.size(); i++) {
            titleList.add(bannerList.get(i).getTitle());
        }
        return titleList;
    }

}