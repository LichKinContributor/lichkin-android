package com.lichkin.framework.app.android.beans;

import com.lichkin.framework.defines.LKFrameworkStatics;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 动态TAB页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LKDynamicTab {

    /** TABID */
    private String tabId;

    /** TAB名称 */
    private String tabName;

    /** TAB图片(Base64) */
    private String tabIcon;

    /**
     * 构造方法
     * @param tabId TABID
     * @param tabName TAB名称
     * @param tabIcon TAB图片(Base64)
     */
    public LKDynamicTab(String tabId, String tabName, String tabIcon) {
        this.tabId = tabId;
        this.tabName = tabName;
        this.tabIcon = tabIcon;
    }

    /**
     * 转换为字符串
     * @return 字符串
     */
    public String convertString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tabId).append(LKFrameworkStatics.SPLITOR_FIELDS).append(tabName).append(LKFrameworkStatics.SPLITOR_FIELDS).append(tabIcon).append(LKFrameworkStatics.SPLITOR);
        return sb.toString();
    }

    /**
     * 转换为对象
     * @param compStr 字符串
     * @return 对象
     */
    public static LKDynamicTab convertObject(String compStr) {
        String[] compStrArr = compStr.split(LKFrameworkStatics.SPLITOR_FIELDS);
        LKDynamicTab info = new LKDynamicTab();
        info.tabId = compStrArr[0];
        info.tabName = compStrArr[1];
        info.tabIcon = compStrArr[2];
        return info;
    }

    /**
     * 转换为列表对象
     * @param listCompStr 字符串
     * @return 列表对象
     */
    public static List<LKDynamicTab> convertListObject(String listCompStr) {
        List<LKDynamicTab> listComp = new ArrayList<>();
        String[] listCompStrArr = listCompStr.split(LKFrameworkStatics.SPLITOR);
        for (String compStr : listCompStrArr) {
            listComp.add(convertObject(compStr));
        }
        return listComp;
    }

}
