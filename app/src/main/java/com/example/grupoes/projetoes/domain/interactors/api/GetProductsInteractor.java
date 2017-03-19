package com.example.grupoes.projetoes.domain.interactors.api;


import com.example.grupoes.projetoes.domain.interactors.base.Interactor;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;

import java.util.List;

/**
 * Created by Wesley on 15/03/2017.
 */

public interface GetProductsInteractor extends Interactor {
    interface Callback {
        void onProductsRetrieved(List<Product> pointsOfSale);
        void onProductsRetrievalFailed(String error);
;   }
}
