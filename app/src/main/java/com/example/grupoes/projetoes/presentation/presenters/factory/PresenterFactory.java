package com.example.grupoes.projetoes.presentation.presenters.factory;

import com.example.grupoes.projetoes.domain.executor.impl.ThreadExecutor;
import com.example.grupoes.projetoes.presentation.presenters.api.AddPointFragmentPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.AddProductPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.EditPointPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.EditProductPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.LoginPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.RegistrationPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.SettingsPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewMapFragmentPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewPointPresenter;
import com.example.grupoes.projetoes.presentation.presenters.api.ViewProductPresenter;
import com.example.grupoes.projetoes.presentation.presenters.impl.AddPointFragmentPresenterImpl;
import com.example.grupoes.projetoes.presentation.presenters.impl.AddProductPresenterImpl;
import com.example.grupoes.projetoes.presentation.presenters.impl.EditPointPresenterImpl;
import com.example.grupoes.projetoes.presentation.presenters.impl.EditProductPresenterImpl;
import com.example.grupoes.projetoes.presentation.presenters.impl.LoginPresenterImpl;
import com.example.grupoes.projetoes.presentation.presenters.impl.RegistrationPresenterImpl;
import com.example.grupoes.projetoes.presentation.presenters.impl.SettingsPresenterImpl;
import com.example.grupoes.projetoes.presentation.presenters.impl.ViewMapFragmentPresenterImpl;
import com.example.grupoes.projetoes.presentation.presenters.impl.ViewPointPresenterImpl;
import com.example.grupoes.projetoes.presentation.presenters.impl.ViewProductPresenterImpl;
import com.example.grupoes.projetoes.threading.MainThreadImpl;

/**
 * Created by Wesley on 17/03/2017.
 */

public class PresenterFactory {
    private static PresenterFactory instance;

    public static PresenterFactory getInstance() {
        if (instance == null) {
            instance = new PresenterFactory();
        }

        return instance;
    }

    private PresenterFactory() {

    }

    public RegistrationPresenter createRegistrationPresenter(RegistrationPresenter.View view) {
        return new RegistrationPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }

    public LoginPresenter createLoginPresenter(LoginPresenter.View view) {
        return new LoginPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }

    public ViewMapFragmentPresenter createViewMapFragmentPresenter(ViewMapFragmentPresenter.View view) {
        return new ViewMapFragmentPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }

    public AddPointFragmentPresenter createAddPointFragmentPresenter(AddPointFragmentPresenter.View view) {
        return new AddPointFragmentPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }

    public SettingsPresenter createSettingsPresenter(SettingsPresenter.View view) {
        return new SettingsPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }

    public EditPointPresenter createEditPointPresenter(EditPointPresenter.View view) {
        return new EditPointPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }

    public EditProductPresenter createEditProductPresenter(EditProductPresenter.View view) {
        return new EditProductPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }

    public ViewPointPresenter createViewPointPresenter(ViewPointPresenter.View view) {
        return new ViewPointPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }

    public ViewProductPresenter createViewProductPresenter(ViewProductPresenter.View view) {
        return new ViewProductPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }

    public AddProductPresenter createAddProductPresenter(AddProductPresenter.View view) {
        return new AddProductPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), view);
    }
}
