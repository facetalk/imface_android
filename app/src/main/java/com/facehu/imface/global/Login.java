package com.facehu.imface.global;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;

/**
 * Created by hxb on 2014/10/4.
 */
public final class Login {
    private static final String PREF_LOGIN = "login";
    public String uid;
    public String authO;
    public String authSession;
    private static Login instance;

    private Login(String uid, String authO, String authSession) {
        this.uid = uid;
        this.authO = authO;
        this.authSession = authSession;
    }

    public static void updateLogin(Context context, String uid, String authO, String authSession) {
        if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(authO) && !TextUtils.isEmpty(authSession)) {
            instance = new Login(uid, authO, authSession);
            if (context != null) {
                Utils.writePreference(context, "login", GsonHelper.getGson().toJson(instance));
            }
        }
    }

    public static boolean isLogin(Context context) {
        if (instance == null && context != null) {
            try {
                instance = GsonHelper.getGson().fromJson(Utils.readPreference(context, PREF_LOGIN, ""), Login.class);
            } catch (JsonSyntaxException e) {
            }
        }
        return instance != null && !TextUtils.isEmpty(instance.uid) && !TextUtils.isEmpty(instance.authO) && !TextUtils.isEmpty(instance.authSession);
    }

    public static Login getInstance() {
        return instance;
    }
}
