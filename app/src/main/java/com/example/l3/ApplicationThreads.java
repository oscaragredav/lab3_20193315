package com.example.l3;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationThreads extends Application {

    public ExecutorService executorService = Executors.newFixedThreadPool(4);
}