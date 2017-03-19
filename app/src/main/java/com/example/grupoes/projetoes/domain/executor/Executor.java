package com.example.grupoes.projetoes.domain.executor;


import com.example.grupoes.projetoes.domain.interactors.base.AbstractInteractor;

public interface Executor {
    void execute(final AbstractInteractor interactor);
}
