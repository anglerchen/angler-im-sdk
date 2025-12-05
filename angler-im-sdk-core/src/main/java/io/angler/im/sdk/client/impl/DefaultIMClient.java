package io.angler.im.sdk.client.impl;

import io.angler.im.common.domain.enums.IMTerminalType;
import io.angler.im.common.domain.model.IMGroupMessage;
import io.angler.im.common.domain.model.IMPrivateMessage;
import io.angler.im.sdk.client.IMClient;
import io.angler.im.sdk.interfaces.sender.IMSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DefaultIMClient implements IMClient {
    @Autowired
    private IMSender imSender;

    @Override
    public <T> void sendPrivateMessage(IMPrivateMessage<T> message) {
        imSender.sendPrivateMessage(message);
    }

    @Override
    public <T> void sendGroupMessage(IMGroupMessage<T> message) {
        imSender.sendGroupMessage(message);
    }

    @Override
    public Boolean isOnline(Long userId) {
        return imSender.isOnline(userId);
    }

    @Override
    public List<Long> getOnlineUserList(List<Long> userIds) {
        return imSender.getOnlineUser(userIds);
    }

    @Override
    public Map<Long, List<IMTerminalType>> getOnlineTerminal(List<Long> userIds) {
        return imSender.getOnlineTerminal(userIds);
    }
}
