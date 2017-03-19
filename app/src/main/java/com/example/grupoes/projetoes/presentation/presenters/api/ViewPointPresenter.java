package com.example.grupoes.projetoes.presentation.presenters.api;


import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.DeletePointOfSaleBean;
import com.example.grupoes.projetoes.models.PointOfSale;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.presentation.presenters.base.BasePresenter;
import com.example.grupoes.projetoes.presentation.ui.api.BaseView;

import java.util.List;

public interface ViewPointPresenter extends BasePresenter {

    interface View extends BaseView {
        String getPointName();
        void onSuccessfulDeletion();
        void onSucessfulProductsFetch();
        void setPresenter(ViewPointPresenter presenter);
    }

    PointOfSale getPointOfSale();
    List<Product> getProducts();
    void requestDelete(String pointName);
    void requestFetchProducts();
}
