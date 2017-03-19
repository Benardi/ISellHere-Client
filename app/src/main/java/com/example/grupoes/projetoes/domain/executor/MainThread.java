package com.example.grupoes.projetoes.domain.executor;

public interface MainThread {
    void post(final Runnable runnable);
}
