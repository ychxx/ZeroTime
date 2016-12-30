package com.fdzcxy.zerotime.dialog;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.ActivityCollector;
import com.fdzcxy.zerotime.Utiles.MusicHelper;
import com.fdzcxy.zerotime.activity.AbcSetActivity;
import com.fdzcxy.zerotime.activity.MainActivity;
import com.fdzcxy.zerotime.activity.ScanMusicActivity;
import com.fdzcxy.zerotime.activity.ShutMusicActivity;
import com.fdzcxy.zerotime.sharedpreferences.AbcSetting;
import com.fdzcxy.zerotime.sharedpreferences.MusicSetting;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 音乐下方弹出的对话框框
 * 
 * @author Administrator
 * 
 */
public class BelowDialog extends Dialog implements OnClickListener {
	/**
	 * 播放模式图ImageView
	 */
	private ImageView mModeImg;
	/**
	 * 播放模式文字TextView
	 */
	private TextView mModeTv;
	private Button mDismiss;
	private LinearLayout mShutMusic;
	private LinearLayout mMode;
	private LinearLayout mScan;
	private LinearLayout mOut;
	private Context context;

	public BelowDialog(Context context) {
		super(context, R.style.MusicDialog);
		this.context = context;
		initViews();
	}

	private void initViews() {
		setContentView(R.layout.utile_dialog_below);
		mShutMusic = (LinearLayout) findViewById(R.id.below_shut);
		mMode = (LinearLayout) findViewById(R.id.below_mode);
		mScan = (LinearLayout) findViewById(R.id.below_scan);
		mOut = (LinearLayout) findViewById(R.id.below_out);
		mModeImg = (ImageView) findViewById(R.id.below_mode_img);
		mModeTv = (TextView) findViewById(R.id.below_mode_tv);
		mDismiss = (Button) findViewById(R.id.below_dismiss);

		// 初始化设置显示的音乐模式
		MusicSetting musicSetting = MusicSetting.getInstance(context);
		int tempMode = musicSetting.getmMusicMode();
		mModeImg.setBackgroundResource(MusicHelper.musicModeImg[tempMode]);
		mModeTv.setText(MusicHelper.musicModeString[tempMode]);
		MusicHelper.PLAY_MODE = tempMode;
		// 设置对话框位置大小
		Window dialogWindow = getWindow();
		dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL);
		dialogWindow.setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setAttributes(lp);// 此处暂未设置偏移量
		setCanceledOnTouchOutside(false);
		setCancelable(false);
		mDismiss.setOnClickListener(this);
		mShutMusic.setOnClickListener(this);
		mMode.setOnClickListener(this);
		mScan.setOnClickListener(this);
		mOut.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.below_dismiss: {
			dismiss();
			break;
		}
		case R.id.below_shut: {
			dismiss();
			Intent intent = new Intent(context, ShutMusicActivity.class);
			context.startActivity(intent);
			break;
		}
		case R.id.below_mode: {
			MusicSetting musicSetting = MusicSetting.getInstance(context);
			int tempMode = musicSetting.getmMusicMode();
			tempMode = (tempMode + 1) % 4;
			musicSetting.setmMusicMode(tempMode);
			mModeImg.setBackgroundResource(MusicHelper.musicModeImg[tempMode]);
			mModeTv.setText(MusicHelper.musicModeString[tempMode]);
			MusicHelper.PLAY_MODE = tempMode;
			break;
		}
		case R.id.below_scan: {
			dismiss();
			Intent intent = new Intent(context, ScanMusicActivity.class);
			context.startActivity(intent);
			break;
		}
		case R.id.below_out: {
			dismiss();
			Intent intent = new Intent();
			intent.setAction("com.fdzcxy.zerotime.service.MusicService");
			intent.putExtra("ACT", -1);
			getContext().startService(intent);
			ActivityCollector.finishAll();
			break;
		}
		}

	}
}
