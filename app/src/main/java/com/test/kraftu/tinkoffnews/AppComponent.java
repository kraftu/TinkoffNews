package com.test.kraftu.tinkoffnews;

import com.test.kraftu.tinkoffnews.ui.list.MainActivity;
import com.test.kraftu.tinkoffnews.ui.list.MainPresenter;
import com.test.kraftu.tinkoffnews.ui.item.ShowPostPresenter;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainPresenter mainPresenter);
    void inject(ShowPostPresenter showPostPresenter);
}
