package com.zzl.websocket.cache;/**
 * Created by admin on 2019/8/20.
 */

import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zzl
 * @version 1.0
 * @desception
 * @date 2019/8/20 11:44
 */
@Component
public class LocalCache {

    //当前在线用户
    private static Map<String,Session> livingSessions = new ConcurrentHashMap<>();

    public static void addCache(String userName,Session session){
        livingSessions.put(userName,session);
    }

    public static Map<String,Session> getLivingSessions(){
        return livingSessions;
    }

    public static void removeCache(String userName){
        livingSessions.remove(userName);
    }

}
