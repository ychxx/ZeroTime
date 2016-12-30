package com.fdzcxy.zerotime.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class NewUserActivity extends BaseActivity {

	/**
	 * 输入的手机号码
	 */
	private static EditText mPhone;
	/**
	 * 输入的验证码
	 */
	private static EditText mVerification;
	/**
	 * 输入的密码
	 */
	private static EditText mPassword;
	/**
	 * 提交验证码
	 */
	private Button mVerificationBt;
	/**
	 * 提交注册
	 */
	private Button mSubmit;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		mApp = (CommonApplication) NewUserActivity.this.getApplication();
		initView();
		
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		mPhone = (EditText) findViewById(R.id.new_user_phone);
		mVerification = (EditText) findViewById(R.id.new_user_verification);
		mPassword = (EditText) findViewById(R.id.new_user_password);
		mVerificationBt = (Button) findViewById(R.id.new_user_verification_bt);
		mSubmit = (Button) findViewById(R.id.new_user_submit);

		mVerificationBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 判断手机号格式
				String phone = mPhone.getText().toString();
				if (CommonUtiles.isPhoneNum(phone))
					sendVerification(NewUserActivity.this, phone);
				else
					ToastUtil.show(NewUserActivity.this, "手机号格式不对！");
			}
		});
		mSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				submitUser(NewUserActivity.this);
			}
		});
	}

	/**
	 * 提交注册信息
	 */
	public static void submitUser(final Context context) {
		// 判断手机号格式
		String phone = mPhone.getText().toString();
		if (!CommonUtiles.isPhoneNum(phone)) {
			ToastUtil.show(context, "手机号格式不对！");
			return;
		}
		// 判断密码格式
		if (TextUtils.isEmpty(mPassword.getText().toString())) {
			ToastUtil.show(context, "密码不能为空！");
			return;
		}
		// 判断验证码格式
		String ve = mVerification.getText().toString();
		if (TextUtils.isEmpty(ve)) {
			ToastUtil.show(context, "验证码不能为空！");
			return;
		}
		submitVerification(context, phone, ve);
	}

	/**
	 * 发送获取验证码请求
	 * 
	 * @param context
	 * @param phone
	 *            手机号
	 */
	public static void sendVerification(final Context context, String phone) {
		BmobSMS.requestSMSCode(context, phone, CommonUtiles.TEMPLATE,
				new RequestSMSCodeListener() {

					public void done(Integer smsId, BmobException ex) {
						// TODO Auto-generated method stub
						if (ex == null) {// 验证码发送成功
							ToastUtil.show(context, "验证码已发送，请稍后！");
						} else {
							// ToastUtil.show(context, "验证码发送失败！");
							ToastUtil.show(context, ex.toString());
						}
					}
				});
	}

	/**
	 * 提交验证码和账号密码
	 * 
	 * @param context
	 * @param phone
	 *            手机号
	 * @param verification
	 *            6位验证码
	 */
	public static void submitVerification(final Context context, String phone,
			String verification) {
		BmobSMS.verifySmsCode(context, phone, verification,
				new VerifySMSCodeListener() {
					public void done(BmobException ex) {
						if (ex == null) {// 短信验证码已验证成功
							StringRequest request = new StringRequest(Request.Method.POST,
									CommonUtiles.USER_REGISTER_URL, 
									new Response.Listener<String>() {
										public void onResponse(String response) {
											try {
												Log.d("TAG", response);
												JSONObject object = new JSONObject(response);// 将string类型转换成object
												// 输出是否已经注册
												JsonResult result = CommonUtiles.parseJsonResponse(object);
												ToastUtil.show(context.getApplicationContext(), result.getDesc());
												if ("注册成功".equals(result.getDesc())) {
													mApp.setmUserName(mPhone.getText().toString());
													mApp.setmUserPassword(mPassword.getText().toString());
													Intent intent = new Intent();
													intent.setClass(context, MainActivity.class);
													context.startActivity(intent);
													((Activity) context).finish();
												}
											} catch (JSONException e) {
												e.printStackTrace();
											}
										}
									}, 
									new Response.ErrorListener() {
										@Override
										public void onErrorResponse(VolleyError error) {
											Log.i("qwe", "发送失败");
										}
									}) {
								protected Map<String, String> getParams() throws AuthFailureError {

									Map<String, String> map = new HashMap<String, String>();
									map.put("phoneNum", mPhone.getText().toString());
									map.put("password", mPassword.getText().toString());
									return map;
								}

							};

							mApp.addToRequestQueue(request,"register");
//							BmobUser user = new BmobUser();
//							user.setUsername(mPhone.getText().toString());
//							user.setPassword(mPassword.getText().toString());
//							user.signUp(context, new SaveListener() {
//								public void onSuccess() {
//									// TODO Auto-generated method stub
//									ToastUtil.show(context, "注册成功！");
//									Intent intent = new Intent();
//									intent.setClass(context, MainActivity.class);
//									context.startActivity(intent);
//								}
//
//								@Override
//								public void onFailure(int code, String msg) {
//									// TODO Auto-generated method stub
//									ToastUtil.show(context, "注册失败！");
//								}
//							});
						} else {
							ToastUtil.show(context, "验证码错误！");
						}
					}
				});
	}
}
