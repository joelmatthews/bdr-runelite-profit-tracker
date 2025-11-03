package com.profittracker.support.di;

import com.google.inject.AbstractModule;
import com.profittracker.support.Config;

public class HttpAdapterModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Config.class).to(Config.class);
    }
}
