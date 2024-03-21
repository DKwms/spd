package org.pguide.adapter.wifi.common.thread;

import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author DKwms
 * @Date 2023/12/8 20:31
 * @description
 */

@Data
public class SpdTheadPools {

    public static int nowCore = Runtime.getRuntime().availableProcessors();


    ThreadPoolExecutor socketPools = new ThreadPoolExecutor(
            nowCore/2,nowCore*2,
            1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(nowCore*10),
            Executors.defaultThreadFactory());






}
