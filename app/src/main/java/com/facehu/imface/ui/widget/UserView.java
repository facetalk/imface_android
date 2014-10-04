package com.facehu.imface.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facehu.imface.R;
import com.facehu.imface.global.Constants;
import com.facehu.imface.global.Utils;
import com.facehu.imface.pojo.User;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by hxb on 2014/10/4.
 */
@EViewGroup(R.layout.merge_user_item)
public class UserView extends RelativeLayout {
    @ViewById
    ImageView avatar;
    @ViewById
    ImageView status;
    @ViewById
    TextView name;

    public UserView(Context context) {
        super(context);
    }

    public UserView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void show(User user) {
        if (user != null) {
            String url = Constants.BASE_URI + "avatar/" + user.userName + ".png";
            ImageView imageView = avatar;
            Utils.displayNetImage(getContext(), url, imageView);
            status.setActivated(user.online);
            name.setText(user.userName);
        }
    }

}
