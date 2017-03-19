package com.example.grupoes.projetoes.domain.executor.impl;

import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor implements Executor {
    private static volatile ThreadExecutor instance;

    private static final int CORE_POOL_SIZE  = 3;
    private static final int MAX_POOL_SIZE   = 5;
    private static final long KEEP_ALIVE_TIME = 120L;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;

    public static Executor getInstance() {
        if (instance == null) {
            instance = new ThreadExecutor();
        }

        return instance;
    }

    private ThreadExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE);
    }

    @Override
    public void execute(final AbstractInteractor interactor) {
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                interactor.run();
                interactor.onFinished();
            }
        });
    }
}
