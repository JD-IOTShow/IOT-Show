package com.jdnw.iotshow.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.jdnw.iotshow.vo.MessageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


/**
 * AUTHOR: zxy
 * com.jdnw.iotshow.handler
 * DATE: 2017/10/24
 * TIME: 14:55
 **/
@Component
@Controller
public class MessageEventHandler {
    private final SocketIOServer server;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    public MessageEventHandler(SocketIOServer server)
    {
        this.server = server;
    }
    //添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
    //方便后面发送消息时查找到对应的目标client,
    @OnConnect
    public void onConnect(SocketIOClient client)
    {
        System.out.println("新链接"+client);
        //client.sendEvent("messageevent",redisUtils.get(client.getSessionId().toString()));

        stringRedisTemplate.opsForValue().set("test", "100",60*10, TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间
        System.out.println("aaaaaaaaaaaa"+stringRedisTemplate.opsForValue().get("test"));//根据key获取缓存中的val
    }

    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    public void onDisconnect(SocketIOClient client)
    {
        System.out.println("连接断开"+client);
        client.getSessionId();
        client.sendEvent("sss","sss");
    }

    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data)
    {

        /*String targetClientId = data.getTargetClientId();

        UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
        System.out.println(uuid.toString());
        MessageInfo sendData = new MessageInfo();
        sendData.setSourceClientId(data.getSourceClientId());
        sendData.setTargetClientId(data.getTargetClientId());
        sendData.setMsgType("chat");
        sendData.setMsgContent(data.getMsgContent());
        client.sendEvent("messageevent", sendData);
        server.getClient(uuid).sendEvent("messageevent", sendData);*/

    }
    @RequestMapping("/show")
    public void sendMessage2All(){
        Collection<SocketIOClient> clients = server.getAllClients();
        Iterator<SocketIOClient> it = clients.iterator();
        while (it.hasNext()) {
            SocketIOClient i = (SocketIOClient) it.next();
            i.sendEvent("messageevent",i.getSessionId().getLeastSignificantBits());
        }
    }
}
