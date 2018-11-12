package com.lichkin.framework.app.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 动态页
 */
public class LKTabFragment extends Fragment {

    /** 页面ID */
    protected String tabId;

    /** 页面名称 */
    protected String tabName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        tabId = bundle.getString("tabId");
        tabName = bundle.getString("tabName");
    }

}
