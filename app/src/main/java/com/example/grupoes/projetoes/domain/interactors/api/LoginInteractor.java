package com.example.grupoes.projetoes.domain.interactors.api;


import com.example.grupoes.projetoes.domain.interactors.base.Interactor;

/**
 * Created by Wesley on 15/03/2017.
 */

public interface LoginInteractor extends Interactor {
    interface Callback {
        void onLoginComplete();
        void onLoginFailed(String error);
;   }
}
