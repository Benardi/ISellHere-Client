package com.example.grupoes.projetoes.domain.interactors.api;


import com.example.grupoes.projetoes.domain.interactors.base.Interactor;

/**
 * Created by Wesley on 15/03/2017.
 */

public interface AddProductInteractor extends Interactor {
    interface Callback {
        void onAddedProduct();
        void onAddFailed(String error);
;   }
}
