package com.fdzcxy.zerotime.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonApplication;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.Utiles.JsonResult;
import com.fdzcxy.zerotime.Utiles.ToastUtil;
import com.fdzcxy.zerotime.sharedpreferences.AbcSetting;

/**
 * 设置定时关音乐页面
 * 
 * @author Administrator
 * 
 */
public class ChangePasswordActivity extends BaseActivity implements
		OnClickListener {
	/**
	 * 确认按钮
	 */
	private Button mConfirm;
	/**
	 * 取消按钮
	 */
	private Button mCancel;
	/**
	 * 旧密码输入框
	 */
	private EditText mOldPassword;
	/**
	 * 新密码输入框
	 */
	private EditText mNewPassword;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepassword);
		mApp = (CommonApplication) getApplication();
		mAbcSetting = AbcSetting.getInstance(ChangePasswordActivity.this);
		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		((Button) findViewById(R.id.shut_break))
				.setOnClickListener(mBackOnClickListener);
		mConfirm = (Button) findViewById(R.id.change_confirm);
		mConfirm.setOnClickListener(this);
		mCancel = (Button) findViewById(R.id.change_cancel);
		mCancel.setOnClickListener(this);

		mOldPassword = (EditText) findViewById(R.id.change_old_password);
		mNewPassword = (EditText) findViewById(R.id.change_new_password);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.change_confirm: {
			StringRequest request = new StringRequest(Request.Method.POST,
					CommonUtiles.USER_CHANGE_PASSWORD_URL,
					new Response.Listener<String>() {
						public void onResponse(String response) {
							try {
								Log.d("TAG", response);
								JSONObject object = new JSONObject(response);// 将string类型转换成object
								// 输出是否已经注册
								JsonResult result = CommonUtiles
										.parseJsonResponse(object);
								ToastUtil.show(getApplicationContext(),result.getDesc());
								if ("修改密码成功".equals(result.getDesc())) {
									mApp.setmUserPassword(mNewPassword
											.getText().toString());
									ChangePasswordActivity.this.finish();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.i("qwe", "发送失败");
						}
					}) {
				protected Map<String, String> getParams()
						throws AuthFailureError {

					Map<String, String> map = new HashMap<String, String>();
					map.put("phoneNum", mApp.getmUserName().toString());
					map.put("password", mOldPassword.getText().toString());
					map.put("newpassword", mNewPassword.getText().toString());
					return map;
				}

			};

			mApp.addToRequestQueue(request, "forgot_password");
			break;
		}
		case R.id.change_cancel: {
			ChangePasswordActivity.this.finish();
			break;
		}
		}
	}
}
