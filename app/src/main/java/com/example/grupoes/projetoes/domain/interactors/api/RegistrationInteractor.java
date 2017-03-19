package com.example.grupoes.projetoes.domain.interactors.api;


import com.example.grupoes.projetoes.domain.interactors.base.Interactor;

/**
 * Created by Wesley on 15/03/2017.
 */

public interface RegistrationInteractor extends Interactor {
    interface Callback {
        void onRegistrationComplete();
        void onRegistrationFailed(String error);
;   }
}
