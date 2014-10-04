package com.facehu.imface.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hxb on 2014/10/4.
 */
public class LoginUser {
    public String email;
    @SerializedName("username")
    public String uid;
    public String name;
    public String introduction;
    public String price;
    public int status;
    public int gender;
    public int sexualOrientation;
    public int infoCompleteness;
    public int creationTime;
    public int modificationTime;

}
