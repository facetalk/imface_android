package com.facehu.imface.net;

import java.net.CookieStore;

/**
 * Created by hxb on 2014/10/4.
 */
public interface CookieHandler {

    void receiveCookie(CookieStore cs);

    void sendCookie(CookieStore cs);
}
