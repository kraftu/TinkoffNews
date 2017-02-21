package com.test.kraftu.tinkoffnews;

import android.app.Application;

import com.test.kraftu.tinkoffnews.tools.ComponentReflectionInjector;
import com.test.kraftu.tinkoffnews.tools.Injector;

public class TinkoffApp extends Application implements Injector {

    private ComponentReflectionInjector<AppComponent> injector;

    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        injector = new ComponentReflectionInjector<>(AppComponent.class, appComponent);
    }

    @Override
    public void inject(Object target) {
        injector.inject(target);
    }
}
