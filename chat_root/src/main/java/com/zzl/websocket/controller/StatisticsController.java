package com.zzl.websocket.controller;/**
 * Created by admin on 2019/8/16.
 */

import com.zzl.websocket.cache.LocalCache;
import com.zzl.websocket.config.ChatRootServerEndpoint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author zzl
 * @version 1.0
 * @desception
 * @date 2019/8/16 19:11
 */
@RestController("")
@RequestMapping("/statistics")
public class StatisticsController{

    /**
     * 获取在线人数
     * @return
     */
    @GetMapping("/getOnlineCount")
    public Integer getOnlineCount(){
        Integer onLineCount = LocalCache.getLivingSessions().size();
        return onLineCount;
    }

    /**
     * 获取在线人员名单
     * @return
     */
    @GetMapping("/getAllOnLineUser")
    public Set<String> getAllOnLineUser(){
        return LocalCache.getLivingSessions().keySet();
    }

}
