package org.pguide.adapter.wifi.service;

import lombok.extern.slf4j.Slf4j;
import org.pguide.adapter.wifi.common.core.result.JsonResult;
import org.pguide.adapter.wifi.common.thread.SpdTheadPools;
import org.pguide.adapter.wifi.common.thread.SpdWifiSocketPools;
import org.pguide.adapter.wifi.common.utils.command.SpdCommandMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author DKwms
 * @Date 2023/12/17 17:43
 * @description
 */
@Service
@Slf4j
public class WIFISenderService {

    public static final String LOG_HEADER = "[PGuide-SPD-WIFI-Adapter-INFO]:";

    @Autowired
    SpdTheadPools spdTheadPools;

    @Autowired
    SpdWifiSocketPools spdWifiSocketPools;

    /**
     * 初始设别名
     * @param path
     * @return
     */
    @Value("${pguide.spd.rfid.name}")
    String BASE_WIFI_NAME;

    public boolean checkPath(int path){
        // TODO 整理service

        String cmd = SpdCommandMaker.checkRoad(path);
        //TODO 动态读取socket
        Socket wifi01 = spdWifiSocketPools.getSocket(BASE_WIFI_NAME);
        byte[] data = new byte[1048];
        String result = null;
        try {
            wifi01.getOutputStream().write(cmd.getBytes());
            wifi01.getInputStream().read(data);
            result = data.toString();
        } catch (IOException e) {
            return false;
            //throw new RuntimeException(e);
        }

        return true;
    }

    public ArrayList<String> findCard(){
        //TODO 整理service
        //TODO 动态读取socket
        Socket socket = spdWifiSocketPools.getSocket("wifi01");

        ArrayList<String> result = null;
        byte[] data = null;

        try {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            String cards01 = SpdCommandMaker.findCards_01();
            Thread.sleep(1500);

            int buff = inputStream.available();

            // 写入指令
            outputStream.write(cards01.getBytes());

            while (buff != 0) {
                data = new byte[buff];
                inputStream.read(data);
                buff = inputStream.available();
            }

            // 读取返回值

            result = findCardResultParse(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static ArrayList<String> findCardResultParse(byte[] data){
        if (data==null){
            log.error("[wifi-adapter-fincCardResultParse]解析数据失败，响应值为空");
            return null;
        }
        String dataOriginal = new String(data);


        String[] split = dataOriginal.split("");
        ArrayList<String> list2 = new ArrayList<>();
        for (int i = 2; i < split.length-1; i+=2) {
            list2.add(split[i] + split[i+1]);
        }
        String substring = dataOriginal.substring(9);
        String substring1 = substring.substring(0, substring.length() - 6);
        log.debug("{}截取后的数据：{}",LOG_HEADER,substring1);
        ArrayList<String> list = new ArrayList<>();
        while(substring1.length()>1){
            String substring2 = substring1.substring(0, 20);
            char[] charArray = substring2.toCharArray();
            int left = 0;
            int right = charArray.length-1;
            while(left<right){
                char temp = charArray[left];
                char temp2 = charArray[left+1];
                charArray[left] = charArray[right-1];
                charArray[left+1] = charArray[right];
                charArray[right] = temp2;
                charArray[right-1] = temp;
                left += 2;
                right -=2;
            }
            list.add(new String(charArray));
            substring1 = substring1.substring(20);
        }
        return list;
    }


}
