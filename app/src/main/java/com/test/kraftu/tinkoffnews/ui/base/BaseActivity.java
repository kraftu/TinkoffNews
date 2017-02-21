package com.test.kraftu.tinkoffnews.ui.base;

import android.os.Bundle;

import com.test.kraftu.tinkoffnews.AppComponent;
import com.test.kraftu.tinkoffnews.TinkoffApp;
import com.test.kraftu.tinkoffnews.tools.Injector;
import com.test.kraftu.tinkoffnews.ui.list.MainPresenter;

import nucleus.factory.PresenterFactory;
import nucleus.presenter.Presenter;
import nucleus.presenter.RxPresenter;
import nucleus.view.NucleusAppCompatActivity;


public class BaseActivity<T extends RxPresenter> extends NucleusAppCompatActivity<T> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final PresenterFactory<T> superFactory = super.getPresenterFactory();
        setPresenterFactory(superFactory == null ? null : (PresenterFactory<T>) () -> {
            T presenter = superFactory.createPresenter();
            ((Injector)getApplication()).inject(presenter);
            return presenter;
        });

        super.onCreate(savedInstanceState);

    }

}
