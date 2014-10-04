package com.facehu.imface.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facehu.imface.R;
import com.facehu.imface.global.Constants;
import com.facehu.imface.global.Login;
import com.facehu.imface.global.Utils;
import com.facehu.imface.net.LoginCookieHandler;
import com.facehu.imface.net.StringRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewById
    AutoCompleteTextView mEmailView;
    @ViewById
    EditText mPasswordView;
    @ViewById
    View mProgressView;
    @ViewById
    View mLoginFormView;
    @ViewById
    Button mEmailSignInButton;

    @AfterViews
    void bindEvents() {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }


    public void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            final String url = Constants.BASE_URI + "login";
            final Context context = getApplicationContext();
            Utils.getRequestQueue(context).add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                    } catch (JSONException e) {
                    }
                    if (jsonObject != null && "success".equals(jsonObject.opt("status"))) {
                        Toast.makeText(context, jsonObject.optString("desc", "登陆成功"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, jsonObject != null ? jsonObject.optString("desc", "登陆失败，请重试") : "jsonObject null", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "登陆失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }, new LoginCookieHandler(url) {
                @Override
                public void updateLogin(String uid, String authO, String authSession) {
                    Login.updateLogin(context, uid, authO, authSession);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("loginEmail", email);
                    param.put("loginPassword", password);
                    return param;
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}



