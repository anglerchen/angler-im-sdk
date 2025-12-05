package io.angler.im.sdk.infrastructure.multicaster.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import io.angler.im.common.domain.enums.IMListenerType;
import io.angler.im.common.domain.model.IMSendResult;
import io.angler.im.sdk.domain.annotation.IMListener;
import io.angler.im.sdk.domain.listener.MessageListener;
import io.angler.im.sdk.infrastructure.multicaster.MessageListenerMulticaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Component
public class DefaultMessageListenerMulticaster implements MessageListenerMulticaster {

    /**
     * 这里的注入其实是注入大后端平台或其他依赖SDK系统 并实现MessageListener接口的实现类
     */
    @Autowired(required = false)
    private List<MessageListener> messageListenerList = Collections.emptyList();

    @Override
    public <T> void multicast(IMListenerType listenerType, IMSendResult<T> result) {
        //为空，直接返回
        if (CollectionUtil.isEmpty(messageListenerList)){
            return;
        }
        messageListenerList.forEach((messageListener) -> {
            // 只有监听器实现类标注了 @IMListener 注解，才会进行回调
            IMListener imListener = messageListener.getClass().getAnnotation(IMListener.class);
            if (imListener != null && (IMListenerType.ALL.equals(imListener.listenerType()) || imListener.listenerType().equals(listenerType))){
                if (result.getData() instanceof JSONObject){
                    Type superInterface = messageListener.getClass().getGenericInterfaces()[0];
                    Type type = ((ParameterizedType)superInterface).getActualTypeArguments()[0];
                    JSONObject data = (JSONObject) result.getData();
                    result.setData(data.toJavaObject(type));
                }
                messageListener.doProcess(result);
            }
        });
    }
}
