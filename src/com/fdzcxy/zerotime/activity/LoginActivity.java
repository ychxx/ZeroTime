package com.fdzcxy.zerotime.activity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {

	/**
	 * 忘记密码按钮
	 */
	private Button mBtnForgotPassword;
	/**
	 * 新用户注册按钮
	 */
	private Button mBtnNewUser;
	/**
	 * 登入按钮
	 */
	private Button mBtnLogin;
	/**
	 * 用户手机号
	 */
	private static EditText mPhone;
	/**
	 * 用户密码
	 */
	private static EditText mPassword;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 绑定为引导页视图
		setContentView(R.layout.activity_guide);
		mApp = (CommonApplication) getApplication();
	}

	/**
	 * 2秒后发送请求修改绑定的视图
	 */
	protected void onResume() {
		super.onResume();
		Thread thread = new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
					Message m = new Message();
					m.what = 1;
					LoginActivity.this.mHandler.sendMessage(m);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		};
		thread.start();
	}

	/**
	 * 修改登入界面的绑定视图
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				//判断是否登入过
				if (mApp.getmUserName() == null
						|| TextUtils.isEmpty(mApp.getmUserName())){
					LayoutInflater mInflater = LayoutInflater
							.from(LoginActivity.this);
					View view = mInflater.inflate(R.layout.activity_login, null);
					setContentView(view);
					initView();
					// 测试用直接免登入按钮
					Button button1 = (Button) findViewById(R.id.button1);
					button1.setVisibility(View.VISIBLE);
					button1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.setClass(LoginActivity.this, MainActivity.class);
							startActivity(intent);
						}
					});
				}else{
					Intent intent =new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				}
			}
		}
	};

	/**
	 * 初始化视图和绑定按钮事件
	 */
	private void initView() {
		mPhone = (EditText) findViewById(R.id.login_phone);
		mPassword = (EditText) findViewById(R.id.login_password);

		mBtnForgotPassword = (Button) findViewById(R.id.login_forgot_password);
		mBtnForgotPassword.setOnClickListener(this);

		mBtnNewUser = (Button) findViewById(R.id.login_new_user);
		mBtnNewUser.setOnClickListener(this);

		mBtnLogin = (Button) findViewById(R.id.login);
		mBtnLogin.setOnClickListener(this);
	}

	/**
	 * 点击按钮进行页面跳转
	 */
	public void onClick(View v) {
		Intent intent = new Intent();

		switch (v.getId()) {

		case R.id.login_forgot_password: {
			intent.setClass(LoginActivity.this, ForgotPwdActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.login_new_user: {
			intent.setClass(LoginActivity.this, NewUserActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.login: {
			login(LoginActivity.this);
			break;
		}
		}

	}

	private void login(final Context context) {

		if (!CommonUtiles.isPhoneNum(mPhone.getText().toString())) {
			ToastUtil.show(LoginActivity.this, "手机格式错误");
			return;
		}
		if (TextUtils.isEmpty(mPassword.getText().toString())) {
			ToastUtil.show(LoginActivity.this, "密码不能为空");
			return;
		}
		StringRequest request = new StringRequest(Request.Method.POST,
				CommonUtiles.USER_LOGIN_URL, resSuccess, resError) {
			protected Map<String, String> getParams() throws AuthFailureError {

				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNum", mPhone.getText().toString());
				map.put("password", mPassword.getText().toString());
				return map;
			}

		};

		mApp.addToRequestQueue(request, "login");
		// BmobUser bu = new BmobUser();
		// bu.setUsername(mPhone.getText().toString());
		// bu.setPassword(mPassword.getText().toString());
		// // 利用login方法进行注册
		// bu.login(context, new SaveListener() {
		// @Override
		// public void onSuccess() {
		// // TODO Auto-generated method stub
		//
		// Intent intent = new Intent();
		// intent.setClass(context, MainActivity.class);
		// startActivity(intent);
		// }
		//
		// @Override
		// public void onFailure(int code, String msg) {
		// // TODO Auto-generated method stub
		// ToastUtil.show(context, "账号或者密码错误!！");
		// }
		// });

	}

	/**
	 * 发送请求失败事件
	 */
	Response.ErrorListener resError = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			Log.i("qwe", "发送失败");
		}
	};
	/**
	 * 发送请求成功事件
	 */
	Response.Listener<String> resSuccess = new Response.Listener<String>() {
		public void onResponse(String response) {
			try {
				Log.d("TAG", response);
				JSONObject object = new JSONObject(response);// 将string类型转换成object
				// 输出是否已经注册
				JsonResult result = CommonUtiles.parseJsonResponse(object);
				ToastUtil.show(getApplicationContext(), result.getDesc());
				if ("登录成功".equals(result.getDesc())) {

					mApp.setmUserName(mPhone.getText().toString());
					mApp.setmUserPassword(mPassword.getText().toString());
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};

}
