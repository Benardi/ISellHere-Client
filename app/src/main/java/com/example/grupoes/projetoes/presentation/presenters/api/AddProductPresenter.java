package com.example.grupoes.projetoes.presentation.presenters.api;


import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.AddProductBean;
import com.example.grupoes.projetoes.presentation.presenters.base.BasePresenter;
import com.example.grupoes.projetoes.presentation.ui.api.BaseView;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.List;

public interface AddProductPresenter extends BasePresenter {

    interface View extends BaseView {
        void onSuccessfulAdd();
        void onInvalidInput(List<InvalidInput> invalidInputs);
        void setPresenter(AddProductPresenter presenter);
    }

    void requestAddProduct(String creatorName, String pointOfSale, String nameProduct, String descriptionProduct, String productPrice, String imageProduct);
}
