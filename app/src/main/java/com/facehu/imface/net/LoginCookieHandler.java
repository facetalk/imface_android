package com.facehu.imface.net;

import com.facehu.imface.global.Login;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

/**
 * Created by hxb on 2014/10/4.
 */
public class LoginCookieHandler implements CookieHandler {
    public static final String COOKIE_FH_USERNAME = "_fh_username";
    public static final String COOKIE_FH_AUTHO = "_fh_autho";
    public static final String COOKIE_FH_AUTHSESSION = "_fh_authsession";
    private URI uri;

    public LoginCookieHandler(String uri) {
        this.uri = URI.create(uri);
    }

    @Override
    public void receiveCookie(CookieStore cs) {
        List<HttpCookie> httpCookies = cs.get(uri);
        String uid = null, authO = null, authSession = null;
        if (httpCookies != null) {
            for (HttpCookie cookie : httpCookies) {
                if (COOKIE_FH_USERNAME.equals(cookie.getName())) {
                    uid = cookie.getValue();
                } else if (COOKIE_FH_AUTHO.equals(cookie.getName())) {
                    authO = cookie.getValue();
                } else if (COOKIE_FH_AUTHSESSION.equals(cookie.getName())) {
                    authSession = cookie.getValue();
                }
            }
        }
        updateLogin(uid, authO, authSession);
    }

    public void updateLogin(String uid, String authO, String authSession) {
        Login.updateLogin(null, uid, authO, authSession);
    }

    @Override
    public void sendCookie(CookieStore cs) {
        Login login = Login.getInstance();
        if (cs != null && login != null) {
            cs.add(uri, new HttpCookie(COOKIE_FH_USERNAME, login.uid));
            cs.add(uri, new HttpCookie(COOKIE_FH_AUTHO, login.authO));
            cs.add(uri, new HttpCookie(COOKIE_FH_AUTHSESSION, login.authSession));
        }
    }
}
