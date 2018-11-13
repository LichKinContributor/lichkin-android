package com.lichkin.framework.defines.beans;

import com.lichkin.framework.app.android.utils.LKPropertiesLoader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
class Datas {

    /** 客户端唯一标识 */
    private final String appKey;

    /** 客户端类型 */
    private final String clientType = "ANDROID";

    /** 客户端版本号（大版本号） */
    private final Byte versionX;

    /** 客户端版本号（中版本号） */
    private final Byte versionY;

    /** 客户端版本号（小版本号） */
    private final Short versionZ;

    /** 登录后获取得 */
    private final String token;

    /** 公司令牌 */
    private final String compToken = LKPropertiesLoader.appToken;

}
