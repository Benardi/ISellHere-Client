package com.example.grupoes.projetoes.domain.interactors.api;


import com.example.grupoes.projetoes.domain.interactors.base.Interactor;
import com.example.grupoes.projetoes.models.PointOfSale;

import java.util.List;

/**
 * Created by Wesley on 15/03/2017.
 */

public interface GetPointsInteractor extends Interactor {
    interface Callback {
        void onPointsRetrieved(List<PointOfSale> pointsOfSale);
        void onRetrievalFailed(String error);
;   }
}
