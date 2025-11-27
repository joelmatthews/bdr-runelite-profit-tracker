package com.profittracker.support.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.profittracker.adapters.filesystem.FileSystemAdapter;
import com.profittracker.adapters.filesystem.FileSystemAdapterImpl;
import com.profittracker.adapters.http.HttpAdapter;
import com.profittracker.adapters.http.HttpAdapterImpl;
import com.profittracker.services.leagueservice.LeagueService;
import com.profittracker.services.leagueservice.LeagueServiceImpl;
import com.profittracker.services.screenshotservice.ScreenshotService;
import com.profittracker.services.screenshotservice.ScreenshotServiceImpl;

public class ScreenshotModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ScreenshotService.class).to(ScreenshotServiceImpl.class);
        bind(HttpAdapter.class).to(HttpAdapterImpl.class);
        bind(FileSystemAdapter.class).to(FileSystemAdapterImpl.class);
        bind(LeagueService.class).to(LeagueServiceImpl.class);
    }
}
