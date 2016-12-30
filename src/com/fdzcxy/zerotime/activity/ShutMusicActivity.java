package com.fdzcxy.zerotime.activity;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.Utiles.ToastUtil;
import com.fdzcxy.zerotime.sharedpreferences.AbcSetting;

/**
 * 设置定时关音乐页面
 * 
 * @author Administrator
 * 
 */
public class ShutMusicActivity extends BaseActivity implements OnClickListener {
	/**
	 * 确认按钮
	 */
	private Button mConfirm;
	/**
	 * 取消按钮
	 */
	private Button mCancel;
	/**
	 * 输入框里的时间
	 */
	private EditText mTime;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shutmusic);
		mAbcSetting = AbcSetting.getInstance(ShutMusicActivity.this);
		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		((Button) findViewById(R.id.shut_break))
				.setOnClickListener(mBackOnClickListener);
		mTime = (EditText) findViewById(R.id.shut_time);
		mConfirm = (Button) findViewById(R.id.shut_confirm);
		mConfirm.setOnClickListener(this);
		mCancel = (Button) findViewById(R.id.shut_cancel);
		mCancel.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shut_confirm: {
			if (TextUtils.isEmpty(mTime.getText()))
				ToastUtil.show(ShutMusicActivity.this, "关闭时间不能为空");
			else {
				int t = Integer.parseInt(mTime.getText().toString());
				CommonUtiles.alarmShut(ShutMusicActivity.this,
						(AlarmManager) getSystemService(Context.ALARM_SERVICE),
						t);
				ToastUtil.show(ShutMusicActivity.this, mTime.getText()+"分钟后自动关闭");
			}

			break;
		}
		case R.id.shut_cancel: {
			finish();
			break;
		}
		}
	}

}
