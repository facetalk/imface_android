package com.facehu.imface.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hxb on 2014/10/4.
 */
public class User {
    @SerializedName("userName")
    public String userName;
    public boolean online;
}
