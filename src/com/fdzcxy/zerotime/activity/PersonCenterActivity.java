package com.fdzcxy.zerotime.activity;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.ActivityCollector;
import com.fdzcxy.zerotime.Utiles.CommonApplication;
import com.fdzcxy.zerotime.adapter.LearnAdapter;
import com.fdzcxy.zerotime.sharedpreferences.AbcSetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 个人中心页面
 * 
 * @author Administrator
 * 
 */
public class PersonCenterActivity extends BaseActivity implements
		OnClickListener {
	/**
	 * 修改密码按钮
	 */
	private Button mPassword;
	/**
	 * 退出账号按钮
	 */
	private Button mOut;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personcenter);
		mApp = (CommonApplication) getApplication();
		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		((Button) findViewById(R.id.personcenter_break))
				.setOnClickListener(mBackOnClickListener);
		mPassword = (Button) findViewById(R.id.personcenter_password);
		mPassword.setOnClickListener(this);
		mOut = (Button) findViewById(R.id.personcenter_out);
		mOut.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.personcenter_password: {
			Intent intent = new Intent();
			intent.setClass(PersonCenterActivity.this,
					ChangePasswordActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.personcenter_out: {
			mApp.setmUserName("");
			mApp.setmUserPassword("");
			Intent intent =new Intent();
			intent.setClass(PersonCenterActivity.this, LoginActivity.class);
			startActivity(intent);
			ActivityCollector.finishAll();
			break;
		}
		}
	}
}
