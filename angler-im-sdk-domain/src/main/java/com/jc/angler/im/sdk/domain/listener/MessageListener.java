package com.jc.angler.im.sdk.domain.listener;


import com.jc.angler.im.common.domain.model.IMSendResult;

public interface MessageListener<T> {

    /**
     * 处理发送的结果
     */
    void doProcess(IMSendResult<T> result);
}
