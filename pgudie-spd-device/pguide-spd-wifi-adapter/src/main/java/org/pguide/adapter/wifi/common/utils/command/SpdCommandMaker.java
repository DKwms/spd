package org.pguide.adapter.wifi.common.utils.command;


import lombok.extern.slf4j.Slf4j;
import org.pguide.adapter.wifi.common.utils.Crc16;
import org.pguide.adapter.wifi.common.utils.Tools;

/**
 * 命令生成工具
 */

@Slf4j
public class SpdCommandMaker {

    public static final String LOG_Header = "[PGuide-SPD-INFO]:";

    /**
     *  int[] arr
     */

    /**
     * 读卡命令
     * @return 命令
     */
    public static String findCards_01(){

        int start=0x3a; //起始符
        int number=0x01; //帧编号
        int code=0x01;//功能码
        int len_low=0x02;//数据长度低位
        int len_high=0x00;// 数据长度高位
        int cp=0x07;//命令标识

        int[] arr=new int[]{number,code,len_low,len_high,cp};
        Crc16 crc16 = new Crc16();

        String crc = (Integer.toString(crc16.ComputeChecksum(arr), 16).substring(2, 4)
                + Integer.toString(crc16.ComputeChecksum(arr), 16).substring(0, 2));

        String commands="";
        for (int i=0;i<arr.length;i++){
            commands+=String.format("%02X",arr[i]);
        }
        String command = ":" +commands + crc;
        log.info("{}向串口发送数据：{}",LOG_Header,command);
        return command+ "\r\n";

    }

    /**
     * 模块数据写入命令
     * @param card
     * @param start_from
     * @param data
     * @return 命令
     */
    public static String WriteSingleBilock_08(String card,int start_from,String data){
        int start=0x3a; //起始符
        int number=0x35; //帧编号
        int code=0x08;//功能码
        int len_low=6+card.length()+data.length();//数据长度低位
        int len_high=0x00;// 数据长度高位
        int Block_Length=0x04;
        int Command_Flag=0x23;
        int[] commandhead = {number, code, len_low, len_high, Block_Length, Command_Flag,start_from};
        int[] dataInt= Tools.changeToHex(data);
        int[] UID=Tools.changeToHex(card);
        int totalLength = commandhead.length + dataInt.length + UID.length;

        // 创建新数组
        int[] combinedArray = new int[totalLength];

        // 使用 System.arraycopy 依次复制三个数组到新数组中
        System.arraycopy(commandhead, 0, combinedArray, 0, commandhead.length);
        System.arraycopy(dataInt, 0, combinedArray, commandhead.length, dataInt.length);
        System.arraycopy(UID, 0, combinedArray, commandhead.length + dataInt.length, UID.length);
        Crc16 crc16=new Crc16();
        String crc = (Integer.toString(crc16.ComputeChecksum(combinedArray), 16).substring(2, 4)
                + Integer.toString(crc16.ComputeChecksum(combinedArray), 16).substring(0, 2));
        String commands="";
        for (int i=0;i<combinedArray.length;i++){
            commands+=String.format("%02X",combinedArray[i]);
        }
        String command = ":" +commands + crc + "\r\n";
        log.info("{}向串口发送数据：{}",LOG_Header,command);
        return command;
    }

    /**
     * 切换天线<br/>
     * 单层
     * @param num 切换天线数字
     * @return 命令
     */
    public static String checkRoad(int num){
        int[] Arr =  {0xB3,0xF4,0x02,0x00, 0x00+num};

        Crc16 crc16 = new Crc16();
        String crc = (Integer.toString(crc16.ComputeChecksum(Arr), 16).substring(2, 4)
                + Integer.toString(crc16.ComputeChecksum(Arr), 16).substring(0, 2));

        String commands="";
        for (int i=0;i<Arr.length;i++){
            commands+=String.format("%02X",Arr[i]);
        }
        String command = ":" +commands + crc + "\r\n";
        log.info("{}向串口发送数据：{}",LOG_Header,command);
        return command;
    }

}
