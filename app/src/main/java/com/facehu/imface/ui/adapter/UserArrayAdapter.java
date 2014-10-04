package com.facehu.imface.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.facehu.imface.R;
import com.facehu.imface.pojo.User;
import com.facehu.imface.ui.widget.UserView_;

import java.util.List;

/**
 * Created by hxb on 2014/10/4.
 */
public class UserArrayAdapter extends ArrayAdapter<User> {

    public static final int RESOURCE_ID = R.layout.view_user_item;

    public UserArrayAdapter(Context context, List<User> data) {
        super(context, RESOURCE_ID, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), RESOURCE_ID, null);
        }
        ((UserView_) convertView.findViewById(R.id.user)).show(getItem(position));
        return convertView;
    }

}
