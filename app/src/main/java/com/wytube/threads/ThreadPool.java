package com.wytube.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建时间: 2017/2/21.
 * 类 描 述: 线程池
 */

public class ThreadPool {

    private static ExecutorService cachedThreadPool;

    static {
        cachedThreadPool = Executors.newCachedThreadPool();
    }

    /**
     * 添加线程到线程池中
     *
     * @param thread 实现Runnable接口的类
     */
    public static void addThread(Thread thread) {
        cachedThreadPool.execute(thread);
    }

    public static ExecutorService getCachedThreadPool() {
        return cachedThreadPool;
    }
}
