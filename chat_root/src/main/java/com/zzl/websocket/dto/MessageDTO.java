package com.zzl.websocket.dto;/**
 * Created by admin on 2019/8/20.
 */

import lombok.Data;

/**
 * @author zzl
 * @version 1.0
 * @desception 消息实体类
 * @date 2019/8/20 18:42
 */
@Data
public class MessageDTO {
    /**
     * 发送人
     */
    private String fromUser;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 接收人
     */
    private String toUser;

}
