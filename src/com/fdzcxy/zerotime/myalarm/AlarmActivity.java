package com.fdzcxy.zerotime.myalarm;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.ActivityCollector;
import com.fdzcxy.zerotime.Utiles.MusicHelper;
import com.fdzcxy.zerotime.activity.AbcLearnActivity;
import com.fdzcxy.zerotime.activity.BaseActivity;
import com.fdzcxy.zerotime.activity.ScanMusicActivity;
import com.fdzcxy.zerotime.dialog.CommonDialog;
import com.fdzcxy.zerotime.dialog.CommonDialog.OnDialogListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;

public class AlarmActivity extends BaseActivity {
	/**
	 * 振动
	 */
	private Vibrator vibrator;
	/**
	 * 提醒的背诵第position天的单词
	 */
	private static int mPosition;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		// 唤醒屏幕并在锁屏前面显示
		Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

	}

	/**
	 * 2秒后停止振动线程
	 */
	Thread thread = new Thread() {
		public void run() {
			try {
				Thread.sleep(3000);
				// 停止振动
				if (vibrator != null)
					vibrator.cancel();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//判断闹钟的事件是关闭软件还是弹出提示对话框
		if (AlarmActivity.this.getIntent().getExtras().getString("ACT")
				.equals("alarmSet")) {

			mPosition = AlarmActivity.this.getIntent().getExtras()
					.getInt("position");
			vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 100, 400, 100, 400 };
			// 重复式振动
			vibrator.vibrate(pattern, 2);
			ShowDialog();
			thread.start();
		} else if (AlarmActivity.this.getIntent().getExtras().getString("ACT")
				.equals("alarmShut")) {
			Intent intent = new Intent();
			intent.setAction("com.fdzcxy.zerotime.service.MusicService");
			intent.putExtra("ACT", -1);
			startService(intent);
			
			ActivityCollector.finishAll();
		}
	}

	public void onStop() {
		super.onStop();
		// 停止振动
		if (vibrator != null)
			vibrator.cancel();
	}

	/**
	 * 显示闹钟操作的对话框
	 */
	void ShowDialog() {
		CommonDialog dialog = new CommonDialog(AlarmActivity.this,
				new OnDialogListener() {

					@Override
					public void OnRightClick() {
						Intent intent = new Intent();
						intent.setClass(AlarmActivity.this,
								AbcLearnActivity.class);
						startActivity(intent);
						AlarmActivity.this.finish();
					}

					@Override
					public void OnLeftClick() {
						AlarmActivity.this.finish();
					}
				});

		dialog.setMsg("应该背第" + mPosition + "天的单词了！！");
		dialog.setRightBtnText("去背单词");
		dialog.setLeftBtnText("取消");
		dialog.show();
	}
}
