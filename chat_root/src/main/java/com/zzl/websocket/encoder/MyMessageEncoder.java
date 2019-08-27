package com.zzl.websocket.encoder;/**
 * Created by admin on 2019/8/20.
 */

import com.alibaba.fastjson.JSON;
import com.zzl.websocket.dto.MessageDTO;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author zzl
 * @version 1.0
 * @desception
 * @date 2019/8/20 19:05
 */
public class MyMessageEncoder implements Encoder.Text<MessageDTO> {
    @Override
    public String encode(MessageDTO object) throws EncodeException {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
