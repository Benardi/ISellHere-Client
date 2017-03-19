package com.example.grupoes.projetoes.presentation.presenters.api;


import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.presentation.presenters.base.BasePresenter;
import com.example.grupoes.projetoes.presentation.ui.api.BaseView;

import java.util.List;

public interface ViewProductPresenter extends BasePresenter {

    interface View extends BaseView {
        String getPointName();
        void onSuccessfulDeletion();
        void setPresenter(ViewProductPresenter presenter);
    }

    void requestDelete(String pointName);
}
