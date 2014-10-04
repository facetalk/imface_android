package com.facehu.imface.global;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by hxb on 2014/10/4.
 */
public class CommonImageCache extends DiskBasedCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> mCache;
    private int diskExpireIn = Integer.MAX_VALUE;

    public CommonImageCache(Context context, int maxDiskSize, int maxMemSize, int diskExpireIn) {
        super(context.getExternalCacheDir(), maxDiskSize);
        mCache = new LruCache<String, Bitmap>(maxMemSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = mCache.get(url);
        if (bitmap == null) {
            Entry entry = get(url);
            if (entry != null && entry.data != null && entry.data.length > 0) {
                bitmap = BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length);
                mCache.put(url, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
        byte[] bytes = Utils.bitmapToBytes(bitmap);
        if (bytes != null) {
            Entry entry = new Entry();
            entry.data = bytes;
            entry.ttl = diskExpireIn + System.currentTimeMillis();
            put(url, entry);
        }
    }

}
