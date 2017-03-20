package com.example.grupoes.projetoes.presentation.presenters.impl;

import android.util.Log;

import com.example.grupoes.projetoes.beans.AddPointOfSaleBean;
import com.example.grupoes.projetoes.beans.AddProductBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.AddPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.AddProductInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.AddPointInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.AddProductInteractorImpl;
import com.example.grupoes.projetoes.domain.repository.ProductsRepository;
import com.example.grupoes.projetoes.models.Product;
import com.example.grupoes.projetoes.presentation.presenters.api.AddPointFragmentPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.AddProductPresenter;
import com.example.grupoes.projetoes.presentation.presenters.base.AbstractPresenter;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InputValidator;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class AddProductPresenterImpl extends AbstractPresenter implements AddProductPresenter, AddProductInteractorImpl.Callback {
    private MainThread mainThread;
    private Executor executor;
    private View view;
    private boolean isRunningAddProduct;

    public AddProductPresenterImpl(Executor executor,
                                   MainThread mainThread,
                                   View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;
        this.mainThread = mainThread;
        this.isRunningAddProduct = false;

        view.setPresenter(this);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }

    @Override
    public void onAddedProduct() {
        isRunningAddProduct = false;
        view.onSuccessfulAdd();
    }

    @Override
    public void onAddFailed(String error) {
        isRunningAddProduct = false;
        onError(error);
    }


    @Override
    public void requestAddProduct(String creatorName, String pointOfSale, String nameProduct, String descriptionProduct, String productPrice, String imageProduct) {
        if (!isRunningAddProduct) {
            List<InvalidInput> invalidInputs = new ArrayList<>();

            AddProductBean bean = new AddProductBean(creatorName, pointOfSale, nameProduct, descriptionProduct, productPrice, imageProduct);

            if (InputValidator.isNameInvalid(bean.getProductName())) {
                invalidInputs.add(new InvalidInput(InputType.ADDPRODUCT_NAME, "The name must have 1 to 20 characters and no special symbols!"));
            }

            if (InputValidator.isDescriptionInvalid(bean.getProductComment())) {
                invalidInputs.add(new InvalidInput(InputType.ADDPRODUCT_DESCRIPTION, "The description must have 0 to 255 characters and no special symbols!"));
            }

            if (InputValidator.isPriceInvalid(bean.getProductPrice())) {
                invalidInputs.add(new InvalidInput(InputType.ADDPRODUCT_PRICE, "Invalid price. Example of valid price: 100.00"));
            }

            if (invalidInputs.size() == 0) {
                isRunningAddProduct = true;
                AddProductInteractor interactor = new AddProductInteractorImpl(executor, mainThread, this, bean);
                interactor.execute();
                ProductsRepository.getInstance().getProducts().get(pointOfSale).add(new Product(bean.getCreator(), pointOfSale, bean.getProductName(), bean.getProductComment(), Double.parseDouble(bean.getProductPrice()), bean.getProductImage()));
            } else {
                view.onInvalidInput(invalidInputs);
            }
        }
    }
}
