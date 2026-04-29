package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.Message;
import com.ehxy.tsg.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public Message selectById(Long id) {
        return messageMapper.selectById(id);
    }

    public List<Message> selectByReaderId(Long readerId) {
        return messageMapper.selectByReaderId(readerId);
    }

    public List<Message> selectUnreadByReaderId(Long readerId) {
        return messageMapper.selectUnreadByReaderId(readerId);
    }

    public int insert(Message message) {
        return messageMapper.insert(message);
    }

    public int markAsRead(Long id) {
        return messageMapper.markAsRead(id);
    }

    public int markAllAsRead(Long readerId) {
        return messageMapper.markAllAsRead(readerId);
    }

    public int deleteById(Long id) {
        return messageMapper.deleteById(id);
    }

    public int countUnreadByReaderId(Long readerId) {
        return messageMapper.countUnreadByReaderId(readerId);
    }

    public void sendMessage(Long readerId, String title, String content) {
        Message message = new Message();
        message.setReaderId(readerId);
        message.setTitle(title);
        message.setContent(content);
        message.setIsRead(0);
        messageMapper.insert(message);
    }
}
