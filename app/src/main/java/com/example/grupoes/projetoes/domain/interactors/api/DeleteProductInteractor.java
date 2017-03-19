package com.example.grupoes.projetoes.domain.interactors.api;


import com.example.grupoes.projetoes.domain.interactors.base.Interactor;

/**
 * Created by Wesley on 15/03/2017.
 */

public interface DeleteProductInteractor extends Interactor {
    interface Callback {
        void onDeleteComplete();
        void onDeleteFailed(String error);
;   }
}
