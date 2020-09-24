package com.milk.redisBucket.event;

import lombok.Data;

/**
 * @Description: 令牌桶参数
 * @author wanghong
 * @date 2020/7/7 - 13:51
*/
@Data
public class DingDingLimitEvent {

    private String id;
    private String value;
}
