package com.example.grupoes.projetoes.presentation.presenters.api;


import com.example.grupoes.projetoes.beans.LoginBean;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.presentation.presenters.base.BasePresenter;
import com.example.grupoes.projetoes.presentation.ui.api.BaseView;

import java.util.List;

public interface ViewMapFragmentPresenter extends BasePresenter {

    interface View extends BaseView {
        void onSuccessfulFetch(List<PointOfSale> pointsOfSale);
        void setPresenter(ViewMapFragmentPresenter presenter);
    }

    void requestFetch();
}
