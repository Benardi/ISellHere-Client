package com.example.grupoes.projetoes.presentation.presenters.impl;

import com.example.grupoes.projetoes.beans.EditPointOfSaleBean;
import com.example.grupoes.projetoes.beans.EditProductBean;
import com.example.grupoes.projetoes.domain.executor.Executor;
import com.example.grupoes.projetoes.domain.executor.MainThread;
import com.example.grupoes.projetoes.domain.interactors.api.AddProductInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.EditPointInteractor;
import com.example.grupoes.projetoes.domain.interactors.api.EditProductInteractor;
import com.example.grupoes.projetoes.domain.interactors.impl.AddProductInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.EditPointInteractorImpl;
import com.example.grupoes.projetoes.domain.interactors.impl.EditProductInteractorImpl;
import com.example.grupoes.projetoes.presentation.presenters.api.EditPointPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.EditProductPresenter;
import com.example.grupoes.projetoes.presentation.presenters.base.AbstractPresenter;
import com.example.grupoes.projetoes.util.InputType;
import com.example.grupoes.projetoes.util.InputValidator;
import com.example.grupoes.projetoes.util.InvalidInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class EditProductPresenterImpl extends AbstractPresenter implements EditProductPresenter, EditProductInteractorImpl.Callback {
    private MainThread mainThread;
    private Executor executor;
    private View view;
    private boolean isRunningEditProduct;

    public EditProductPresenterImpl(Executor executor,
                                    MainThread mainThread,
                                    View view) {
        super(executor, mainThread);
        this.view = view;
        this.executor = executor;
        this.mainThread = mainThread;
        this.isRunningEditProduct = false;
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
        isRunningEditProduct = false;
        view.showError(message);
    }

    @Override
    public void onEditedProduct() {
        isRunningEditProduct = false;
        view.onSuccessfulEdit();
    }

    @Override
    public void onEditFailed(String error) {
        onError(error);
    }

    @Override
    public void requestEditProduct(EditProductBean bean) {
        if (!isRunningEditProduct) {
            List<InvalidInput> invalidInputs = new ArrayList<>();

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
                isRunningEditProduct = true;
                EditProductInteractor interactor = new EditProductInteractorImpl(executor, mainThread, this, bean);
                interactor.execute();
            } else {
                view.onInvalidInput(invalidInputs);
            }
        }
    }
}
