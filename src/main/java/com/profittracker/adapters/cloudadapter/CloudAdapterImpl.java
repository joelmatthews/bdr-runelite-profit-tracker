package com.profittracker.adapters.cloudadapter;

import com.profittracker.services.screenshotservice.ScreenshotService;
import org.apache.http.HttpException;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.List;

public class CloudAdapterImpl implements CloudAdapter {

    private final SomeCloudProvider _someCloudProvider; // probably use cloudinary at first for simplicity and free tier

    public CloudAdapterImpl(SomeCloudProvider someCloudProvider) {
        _someCloudProvider = someCloudProvider;
    }

    public String upload(Path path) throws HttpException {
        try {
            String url = _someCloudProvider.magicallySendToCloud(path);
            return url;
        } catch(Exception e) {
            e.printStackTrace();
            throw new HttpException(e.getMessage());
        }
    }
}
