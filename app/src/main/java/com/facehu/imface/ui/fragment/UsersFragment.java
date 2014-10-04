package com.facehu.imface.ui.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facehu.imface.R;
import com.facehu.imface.global.Constants;
import com.facehu.imface.global.GsonHelper;
import com.facehu.imface.global.Login;
import com.facehu.imface.global.Utils;
import com.facehu.imface.net.StringRequest;
import com.facehu.imface.pojo.User;
import com.facehu.imface.ui.adapter.UserArrayAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.LinkedList;

/**
 * Created by hxb on 2014/10/4.
 */
@EFragment(R.layout.fragment_users)
public class UsersFragment extends SwipeRefreshListFragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewById(android.R.id.list)
    ListView listView;
    int currentPage;

    @AfterViews
    void bindEvents() {
        ArrayAdapter<User> adapter = new UserArrayAdapter(listView.getContext(), new LinkedList<User>());
        setListAdapter(adapter);
        onRefresh();
        setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        currentPage = 0;
        getUsers();
    }

    private void getUsers() {
        final Context context = listView != null ? listView.getContext() : null;
        if (context != null && Login.isLogin(context)) {
            Utils.getRequestQueue(context).add(new StringRequest(Constants.BASE_URI + "api/facesms/getUserListByPage/" + Login.getInstance().uid + "/" + String.valueOf(currentPage * Constants.PAGE_COUNT) + "/" + String.valueOf((currentPage + 1) * Constants.PAGE_COUNT), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    User[] users = GsonHelper.getGson().fromJson(response, User[].class);
                    ArrayAdapter<User> adapter = (ArrayAdapter<User>) getListAdapter();
                    if (currentPage == 0) {
                        adapter.clear();
                    }
                    for (User user : users) {
                        adapter.add(user);
                    }
                    currentPage++;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }

}
