package org.pguide.adapter.wifi.common.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DKwms
 * @Date 2023/12/8 22:22
 * @description
 * spd TCP 连接池
 */

// TODO 定时清理socket
@Slf4j
@Component
public class SpdWifiSocketPools {

    //TODO 配置文件中读取
    @Value("${pguide.spd.rfid.host}")
    public static final String SERVER_ADDRESS = "192.168.3.254";

    @Value("${pguide.spd.rfid.port}")
    public static final Integer SERVER_PORT = 8001;

    @Value("${pguide.spd.rfid.name}")
    public static final String SERVER_NAME = "wifi01";

    public static ConcurrentHashMap<String, Socket> WIFI_SOCKET_POOLS = new ConcurrentHashMap<>();

    // 载入初始wifi连接
    static {
        try {
            Socket socket = new Socket(SERVER_ADDRESS,SERVER_PORT);
            WIFI_SOCKET_POOLS.put(SERVER_NAME,socket);
        } catch (IOException e) {
            log.error("初始设备连接失败！尝试连接设备host:{},尝试连接设备ip:{}.",SERVER_ADDRESS,SERVER_PORT);
            // TODO 初始加载失败重试策略
        }
    }


    /**
     * 基础添加操作
     * @param name
     * @param port
     * @return
     */
    public boolean addSocket(String name,int port) {
        try {
            Socket socket = new Socket(name, port);
            WIFI_SOCKET_POOLS.put(name,socket);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 获取连接池
     * @param name
     * @return 空值抛出异常
     */
    public Socket getSocket(String name){

        Socket socket = WIFI_SOCKET_POOLS.get(name);
        if (socket==null){
            // TODO 没有找到，抛出异常
        }
        return socket;
    }

    /**
     * 获取所有连接池
     * @return 不报错
     * 连接池为空返回null
     */
    public HashMap<String,String> getAllSocket(){
        ConcurrentHashMap<String, Socket> wifiSocketPools = WIFI_SOCKET_POOLS;
        Set<Map.Entry<String, Socket>> entries = wifiSocketPools.entrySet();
        HashMap<String, String> resultMap= new HashMap<>();
        for (Map.Entry<String, Socket> entry : entries) {
            resultMap.put(entry.getKey(),entry.getValue().getLocalAddress().getHostAddress());
        }
        if (!resultMap.isEmpty()){
            return resultMap;
        }

        return null;
    }



    /**
     * 释放连接池
     */
    public boolean releaseSocket(String name){
        Socket socket = WIFI_SOCKET_POOLS.get(name);
        try {
            socket.close();
        } catch (IOException e) {
            log.error("[PGuide-spd-client] -SpdWifiSocketPools- Socket关闭失败！SocketName={}",name);
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 关闭所有连接池
     */
    public void releaseSocketAll(){
        Set<Map.Entry<String, Socket>> entries = WIFI_SOCKET_POOLS.entrySet();
        for (Map.Entry<String, Socket> entry : entries) {
            try {
                entry.getValue().close();
            } catch (IOException e) {
                log.error("[PGuide-spd-client] -SpdWifiSocketPools- Socket:{} 关闭失败！SocketName={}",entry.getValue(),entry.getKey());
            }
        }
    }


}
