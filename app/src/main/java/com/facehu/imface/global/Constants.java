package com.facehu.imface.global;

import com.facehu.imface.BuildConfig;

/**
 * Created by hxb on 2014/10/4.
 */
public class Constants {
    public static final String BASE_URI = BuildConfig.BASE_URI;
    public static final int PAGE_COUNT = 20;
    public static final int IMAGE_CACHE_MAX_DISK_SIZE = 100 * 1024 * 1024;
    public static final int IMAGE_CACHE_MAX_MEM_SIZE = 10 * 1024 * 1024;
    public static final int IMAGE_CACHE_DISK_EXPIRE_IN = 30 * 24 * 60 * 60 * 1000 * 1000;
    static final String PREF_NAME = BuildConfig.APPLICATION_ID + ".prefs";
}
