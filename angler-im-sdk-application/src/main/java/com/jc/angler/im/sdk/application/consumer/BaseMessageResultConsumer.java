package com.jc.angler.im.sdk.application.consumer;

import com.alibaba.fastjson.JSONObject;
import com.jc.angler.im.common.domain.constants.IMConstants;
import com.jc.angler.im.common.domain.model.IMSendResult;

public class BaseMessageResultConsumer {

    /**
     * 解析数据
     */
    protected IMSendResult<?> getResultMessage(String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String eventStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(eventStr, IMSendResult.class);
    }
}
