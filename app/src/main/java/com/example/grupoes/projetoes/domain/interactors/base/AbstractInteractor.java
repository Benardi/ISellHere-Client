package com.example.grupoes.projetoes.domain.interactors.base;

import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;

public abstract class AbstractInteractor implements Interactor {
    private Executor threadExecutor;
    private MainThread mainThread;

    private volatile boolean isCanceled;
    private volatile boolean isRunning;

    public AbstractInteractor(Executor threadExecutor, MainThread mainThread) {
        this.threadExecutor = threadExecutor;
        this.mainThread = mainThread;

        this.isCanceled = false;
        this.isRunning = false;
    }

    public abstract void run();

    public void cancel() {
        isCanceled = true;
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void onFinished() {
        isRunning = false;
        isCanceled = false;
    }

    public void execute() {
        this.isRunning = true;
        threadExecutor.execute(this);
    }

    protected Executor getThreadExecutor() {
        return threadExecutor;
    }

    protected MainThread getMainThread() {
        return mainThread;
    }
}
