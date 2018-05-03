package com.lichkin.framework.defines.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用JavaScript时传入的参数对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class LKCallJsFuncBean {

    /** 方法名 */
    private String method;

    /** 参数 */
    private String datas;

}
