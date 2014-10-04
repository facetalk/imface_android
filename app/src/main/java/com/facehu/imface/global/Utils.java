package com.facehu.imface.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.facehu.imface.R;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hxb on 2014/10/4.
 */
public class Utils {
    private static final String LOG_TAG = Utils.class.getName();
    private static ReentrantLock requestQueueLock = new ReentrantLock(true);
    private static ReentrantLock imageLoaderLock = new ReentrantLock(true);
    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    public static RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueueLock.lock();
            try {
                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(context);
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, String.valueOf(e));
            } finally {
                requestQueueLock.unlock();
            }
        }
        return requestQueue;
    }

    public static ImageLoader getImageLoader(Context context) {
        if (imageLoader == null) {
            imageLoaderLock.lock();
            try {
                if (imageLoader == null) {
                    imageLoader = new ImageLoader(Volley.newRequestQueue(context), new CommonImageCache(context, Constants.IMAGE_CACHE_MAX_DISK_SIZE, Constants.IMAGE_CACHE_MAX_MEM_SIZE, Constants.IMAGE_CACHE_DISK_EXPIRE_IN));
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, String.valueOf(e));
            } finally {
                imageLoaderLock.unlock();
            }
        }
        return imageLoader;
    }

    public static void removePreference(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(name);
        edit.commit();
    }

    public static void writePreference(Context context, String name, long value) {
        SharedPreferences prefs = context == null ? null : context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        if (prefs != null) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putLong(name, value);
            edit.commit();
        }
    }

    public static long readPreference(Context context, String name, long defaultValue) {
        SharedPreferences prefs = context == null ? null : context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return prefs == null ? defaultValue : prefs.getLong(name, defaultValue);
    }

    public static void writePreference(Context context, String name, int value) {
        SharedPreferences prefs = context == null ? null : context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        if (prefs != null) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putInt(name, value);
            edit.commit();
        }
    }

    public static int readPreference(Context context, String name, int defaultValue) {
        SharedPreferences prefs = context == null ? null : context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return prefs == null ? defaultValue : prefs.getInt(name, defaultValue);
    }

    public static void writePreference(Context context, String name, String value) {
        SharedPreferences prefs = context == null ? null : context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        if (prefs != null && !TextUtils.isEmpty(name)) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(name, value);
            edit.commit();
            Log.i(LOG_TAG, "writePreference key=".concat(name).concat(" \nvalue=").concat(String.valueOf(value)));
        }
    }

    public static String readPreference(Context context, String name, String defaultValue) {
        SharedPreferences prefs = context == null ? null : context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        return prefs == null ? defaultValue : prefs.getString(name, defaultValue);
    }

    public static byte[] bitmapToBytes(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        } catch (Exception e) {
            Log.i(LOG_TAG, String.valueOf(e));
            return null;
        }
    }

    public static void displayNetImage(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url) && imageView != null) {
            getImageLoader(context).get(url, ImageLoader.getImageListener(imageView, R.drawable.avatar_loading, R.drawable.avatar_loading), imageView.getWidth(), imageView.getHeight());
        }
    }
}
