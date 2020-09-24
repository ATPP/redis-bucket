package com.milk.redisBucket.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author lihaijian
 * @date 2019/09/24
 */
public class ThreadPoolUtil {

    public static ThreadPoolExecutor threadPool;

    public static void execute(Runnable runnable){
        getThreadPool().execute(runnable);
    }

    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(5, 20, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
                }
                return threadPool;
            }
        }
    }

}
