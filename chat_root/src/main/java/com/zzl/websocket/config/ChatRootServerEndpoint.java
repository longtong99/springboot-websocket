package com.zzl.websocket.config;/**
 * Created by admin on 2019/8/16.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzl.websocket.ChatRootApplication;
import com.zzl.websocket.cache.LocalCache;
import com.zzl.websocket.dto.MessageDTO;
import com.zzl.websocket.encoder.MyMessageEncoder;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zzl
 * @version 1.0
 * @desception
 * @date 2019/8/16 18:47
 */
@Component
@ServerEndpoint(value = "/chat-room/{username}", encoders = { MyMessageEncoder.class })
@AutoConfigureAfter(LocalCache.class)
public class ChatRootServerEndpoint {


    /**
     * 创建连接调用的方法
     * @param username
     * @param session
     */
    @OnOpen
    public void onOpenSession(@PathParam("username") String username, Session session){
        String sessionId = session.getId();
        LocalCache.addCache(username,session);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFromUser(username);
        messageDTO.setMessage("欢迎-->"+username+" 来到聊天室...");
        sendObjectAll(messageDTO);
    }

    /**
     * 发送消息调用的方法
     * @param username
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(@PathParam("username") String username, Session session, String message){
        JSONObject jsonObject = JSON.parseObject(message);
        String toUser = jsonObject.get("toUser").toString();
        String msg = jsonObject.get("message").toString();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFromUser(username);
        messageDTO.setMessage("用户-->"+username+" 说:"+msg);
        //发送给所有用户
        if("all".equals(toUser)){
            sendObjectAll(messageDTO);
        }else {
            //发sing给指定用户
            Session session1 = LocalCache.getLivingSessions().get(toUser);
            sendObject(session1,messageDTO);
            //并且发送给自己
            sendObject(session,messageDTO);
        }

    }

    @OnClose
    public void  onClose(@PathParam("username") String username, Session session){
        LocalCache.removeCache(username);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFromUser(username);
        messageDTO.setMessage("用户-->"+username+" 离开了聊天室...");
        sendObjectAll(messageDTO);
    }

    @OnError
    public void onError(@PathParam("username") String username,Throwable throwable, Session session){

    }

    /**
     * 单条发送消息实体
     * @param session
     * @param messageDTO
     */
    private void sendObject(Session session,MessageDTO messageDTO){
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        try {
            basic.sendObject(messageDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量发送消息实体
     * @param message
     */
    private void sendObjectAll(MessageDTO message){
        LocalCache.getLivingSessions().forEach((s, session) -> {
            sendObject(session,message);
        });
    }

    /**
     * 批量发送消息文本
     * @param message
     */
    private void sendObjectAll(String message){
        LocalCache.getLivingSessions().forEach((s, session) -> {
            sendText(session,message);
        });
    }

    /**
     * 单条发送消息文本
     * @param session
     * @param message
     */
    private void sendText(Session session,String message){
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        try {
            basic.sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
