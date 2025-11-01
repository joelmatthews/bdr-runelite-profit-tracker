package com.profittracker.adapters.cloudadapter;

import org.apache.http.HttpException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface CloudAdapter {
    public String upload(String pathToScreenshot) throws HttpException;
}
