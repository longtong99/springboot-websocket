# springboot-websocket
springboot整合websocket，实现点对点聊天、群聊、统计在线人数

springboot整合websocket非常简单，就是加入一个依赖，然后在启动类上加个@EnableWebSocket注解
主要的逻辑都是在加了@ServerEndpoint这个注解的类下面写，另外需要把这个类注册到IOC中
@Component
@ServerEndpoint(value = "/chat-room/{username}", encoders = { MyMessageEncoder.class })
@AutoConfigureAfter(LocalCache.class)
public class ChatRootServerEndpoint {
    
}

value就是这个websocket服务端端点的URL，后面的encoders是针对那些前台穿json数据，后台字符串接收加的处理


这个类下面一般会写4个方法，分别加上
@OnOpen:创建连接的时候调用
对应前台的ws = new WebSocket(url);
@OnMessage:发送消息的时候调用
对应前台的ws.send(msg);
@OnClose:关闭的时候调用
对应前台的ws.close();
@OnError:发生错误的时候调用

说一下逻辑：每个连接都有一个自己的session，这个session就是用来给自己发送消息的，所以我们需要在后台记录所有登陆的session，这样就能够灵活的发送消息给指定的人（例如一个user一个session，给谁发就用谁的session）。
