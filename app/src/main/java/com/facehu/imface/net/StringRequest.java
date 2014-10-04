package com.facehu.imface.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

/**
 * Created by hxb on 2014/10/4.
 */
public class StringRequest extends com.android.volley.toolbox.StringRequest {

    public static final String HEADER_SET_COOKIE = "Set-Cookie";
    private static final String HEADER_COOKIE_KEY = "Cookie";
    public static final String DATE_FORMAT_COOKIE = "EEE, dd-MMM-yyyy HH:mm:ss zzz";
    public String LOG_TAG = ((Object) this).getClass().getName();
    private CookieHandler cookieHandler;

    public StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(method, url, listener, errorListener, new LoginCookieHandler(url));
    }

    public StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, CookieHandler cookieHandler) {
        super(method, url, listener, errorListener);
        this.cookieHandler = cookieHandler;
    }

    public StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(url, listener, errorListener, new LoginCookieHandler(url));
    }

    public StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, CookieHandler cookieHandler) {
        super(url, listener, errorListener);
        this.cookieHandler = cookieHandler;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        addCookies(headers);
        return headers;
    }

    private void addCookies(Map<String, String> headers) {
        MemCookieStore cookieStore = new MemCookieStore();
        if (cookieHandler != null) {
            cookieHandler.sendCookie(cookieStore);
        }
        List<HttpCookie> httpCookies = cookieStore.get(URI.create(getUrl()));
        if (httpCookies != null) {
            StringBuilder builder = new StringBuilder();
            for (HttpCookie httpCookie : httpCookies) {
                if (httpCookie != null && !httpCookie.hasExpired()) {
                    builder.append(httpCookie.getName())
                            .append("=")
                            .append(httpCookie.getValue());
                }
            }
            if (headers.containsKey(HEADER_COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(HEADER_COOKIE_KEY));
            }
            headers.put(HEADER_COOKIE_KEY, builder.toString());
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Response<String> stringResponse = super.parseNetworkResponse(response);
        if (cookieHandler != null) {
            cookieHandler.receiveCookie(parseCookies(response));
        }
        return stringResponse;
    }

    private CookieStore parseCookies(NetworkResponse response) {
        MemCookieStore memCookieStore = new MemCookieStore();
        Map<String, String> headers = response.headers;
        if (headers != null && headers.containsKey(HEADER_SET_COOKIE)) {
            String cookies = headers.get(HEADER_SET_COOKIE);
            if (cookies != null) {
                URI uri = URI.create(getUrl());
                String[] cookieArray = cookies.split("\n");
                for (String cookie : cookieArray) {
                    String[] valueArray = cookie.split(";");
                    if (valueArray.length > 0) {
                        String[] keyValuePair = valueArray[0].split("=");
                        if (keyValuePair.length > 1) {
                            HttpCookie httpCookie = new HttpCookie(keyValuePair[0], keyValuePair[1]);
                            for (int i = 1; i < valueArray.length; i++) {
                                String[] extKeyValuePair = valueArray[i].split("=");
                                if (extKeyValuePair.length > 1) {
                                    if ("path".equals(extKeyValuePair[0])) {
                                        httpCookie.setPath(extKeyValuePair[1]);
                                    } else if ("Expires".equals(extKeyValuePair[0])) {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_COOKIE);
                                        dateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
                                        try {
                                            Date date = dateFormat.parse(extKeyValuePair[1]);
                                            httpCookie.setMaxAge(date.getSeconds() - new Date().getSeconds());
                                        } catch (ParseException e) {
                                        }
                                    }
                                }
                            }
                            memCookieStore.add(uri, httpCookie);
                        }
                    }
                }
            }
            Log.i(LOG_TAG, "cookie:\n" + cookies);
        }
        return memCookieStore;
    }

}
