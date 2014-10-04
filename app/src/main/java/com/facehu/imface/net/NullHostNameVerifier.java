package com.facehu.imface.net;

import android.util.Log;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
* Created by hxb on 2014/10/4.
*/
public class NullHostNameVerifier implements HostnameVerifier {

    public boolean verify(String hostname, SSLSession session) {
        Log.i("RestUtilImpl", "Approving certificate for " + hostname);
        return true;
    }
}
