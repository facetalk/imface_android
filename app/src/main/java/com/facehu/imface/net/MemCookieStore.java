package com.facehu.imface.net;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
* Created by hxb on 2014/10/4.
*/
class MemCookieStore implements CookieStore {
    HashMap<URI, LinkedList<HttpCookie>> cookieHashMap = new HashMap<URI, LinkedList<HttpCookie>>();

    @Override
    public void add(URI uri, HttpCookie httpCookie) {
        LinkedList<HttpCookie> httpCookies = cookieHashMap.get(uri);
        if (httpCookies == null) {
            httpCookies = new LinkedList<HttpCookie>();
        }
        if (!httpCookies.contains(httpCookie)) {
            httpCookies.add(httpCookie);
        }
        cookieHashMap.put(uri, httpCookies);
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return cookieHashMap.get(uri);
    }

    @Override
    public List<HttpCookie> getCookies() {
        LinkedList<HttpCookie> httpCookies = new LinkedList<HttpCookie>();
        for (LinkedList<HttpCookie> cookies : cookieHashMap.values()) {
            httpCookies.addAll(cookies);
        }
        return httpCookies;
    }

    @Override
    public List<URI> getURIs() {
        return new LinkedList<URI>(cookieHashMap.keySet());
    }

    @Override
    public boolean remove(URI uri, HttpCookie httpCookie) {
        LinkedList<HttpCookie> httpCookies = cookieHashMap.get(uri);
        if (httpCookies != null && httpCookies.contains(httpCookie)) {
            httpCookies.remove(httpCookie);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        if (cookieHashMap.keySet().size() > 0) {
            cookieHashMap.clear();
            return true;
        }
        return false;
    }
}
