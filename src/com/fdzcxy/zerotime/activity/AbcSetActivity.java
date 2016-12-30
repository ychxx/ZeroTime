package com.fdzcxy.zerotime.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.Utiles.ToastUtil;
import com.fdzcxy.zerotime.sharedpreferences.AbcSetting;

public class AbcSetActivity extends BaseActivity {

	/**
	 * 返回上一层按钮
	 */
	private Button mback;
	/**
	 * 每日背诵单词个数
	 */
	private static EditText mSetNum;
	/**
	 * 艾宾浩斯方式
	 */
	private static RadioButton mAibinhaosi;
	/**
	 * 自定义方式
	 */
	private static RadioButton mCustom;
	/**
	 * 第一周期的天数
	 */
	private static EditText mDay1;
	/**
	 * 第一周期的小时
	 */
	private static EditText mHours1;
	/**
	 * 第一周期的分钟
	 */
	private static EditText mMinutes1;
	/**
	 * 第二周期的天数
	 */
	private static EditText mDay2;
	/**
	 * 第二周期的小时
	 */
	private static EditText mHours2;
	/**
	 * 第二周期的分钟
	 */
	private static EditText mMinutes2;
	/**
	 * 第三周期的天数
	 */
	private static EditText mDay3;
	/**
	 * 第三周期的小时
	 */
	private static EditText mHours3;
	/**
	 * 第三周期的分钟
	 */
	private static EditText mMinutes3;
	/**
	 * 第四周期的天数
	 */
	private static EditText mDay4;
	/**
	 * 第四周期的小时
	 */
	private static EditText mHours4;
	/**
	 * 第四周期的分钟
	 */
	private static EditText mMinutes4;
	/**
	 * 第五周期的天数
	 */
	private static EditText mDay5;
	/**
	 * 第五周期的小时
	 */
	private static EditText mHours5;
	/**
	 * 第五周期的分钟
	 */
	private static EditText mMinutes5;
	/**
	 * 第六周期的天数
	 */
	private static EditText mDay6;
	/**
	 * 第六周期的小时
	 */
	private static EditText mHours6;
	/**
	 * 第六周期的分钟
	 */
	private static EditText mMinutes6;
	/**
	 * 第七周期的天数
	 */
	private static EditText mDay7;
	/**
	 * 第七周期的小时
	 */
	private static EditText mHours7;
	/**
	 * 第七周期的分钟
	 */
	private static EditText mMinutes7;
	/**
	 * 第八周期的天数
	 */
	private static EditText mDay8;
	/**
	 * 第八周期的小时
	 */
	private static EditText mHours8;
	/**
	 * 第八周期的分钟
	 */
	private static EditText mMinutes8;
	/**
	 * 保存按钮
	 */
	private static Button mSave;
	/**
	 * 取消按钮
	 */
	private static Button mCancel;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abc_set);
		mAbcSetting = AbcSetting.getInstance(AbcSetActivity.this);
		mAbcSetting.getAbcSetting();
		initView();
		initData();
	}

	protected void initView() {
		mback = (Button) findViewById(R.id.abc_set_break);
		mback.setOnClickListener(mBackOnClickListener);

		mSetNum = (EditText) findViewById(R.id.abc_set_num);

		mSave = (Button) findViewById(R.id.abc_set_save);
		mSave.setOnClickListener(mSaveButtonOnClickListener);

		mCancel = (Button) findViewById(R.id.abc_set_cancel);
		mCancel.setOnClickListener(mBackOnClickListener);

		mAibinhaosi = (RadioButton) findViewById(R.id.abc_set_aibinhaosi);
		mAibinhaosi.setOnClickListener(mRadioButtonOnClickListener);
		mCustom = (RadioButton) findViewById(R.id.abc_set_custom);
		mCustom.setOnClickListener(mRadioButtonOnClickListener);

		mDay1 = (EditText) findViewById(R.id.abc_set_day1);
		mHours1 = (EditText) findViewById(R.id.abc_set_hours1);
		mMinutes1 = (EditText) findViewById(R.id.abc_set_minutes1);

		mDay2 = (EditText) findViewById(R.id.abc_set_day2);
		mHours2 = (EditText) findViewById(R.id.abc_set_hours2);
		mMinutes2 = (EditText) findViewById(R.id.abc_set_minutes2);

		mDay3 = (EditText) findViewById(R.id.abc_set_day3);
		mHours3 = (EditText) findViewById(R.id.abc_set_hours3);
		mMinutes3 = (EditText) findViewById(R.id.abc_set_minutes3);

		mDay4 = (EditText) findViewById(R.id.abc_set_day4);
		mHours4 = (EditText) findViewById(R.id.abc_set_hours4);
		mMinutes4 = (EditText) findViewById(R.id.abc_set_minutes4);

		mDay5 = (EditText) findViewById(R.id.abc_set_day5);
		mHours5 = (EditText) findViewById(R.id.abc_set_hours5);
		mMinutes5 = (EditText) findViewById(R.id.abc_set_minutes5);

		mDay6 = (EditText) findViewById(R.id.abc_set_day6);
		mHours6 = (EditText) findViewById(R.id.abc_set_hours6);
		mMinutes6 = (EditText) findViewById(R.id.abc_set_minutes6);

		mDay7 = (EditText) findViewById(R.id.abc_set_day7);
		mHours7 = (EditText) findViewById(R.id.abc_set_hours7);
		mMinutes7 = (EditText) findViewById(R.id.abc_set_minutes7);

		mDay8 = (EditText) findViewById(R.id.abc_set_day8);
		mHours8 = (EditText) findViewById(R.id.abc_set_hours8);
		mMinutes8 = (EditText) findViewById(R.id.abc_set_minutes8);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		mSetNum.setText("" + mAbcSetting.getmWordNum());
		Log.i("1111", "initData:"+mAbcSetting.getmMemoryWay());
		if (mAbcSetting.getmMemoryWay() == 0) {
			mAibinhaosi.setChecked(true);
			mCustom.setChecked(false);
		} else {
			mCustom.setChecked(true);
			mAibinhaosi.setChecked(false);
		}
		setText(mAbcSetting.getmMemoryCycle());

	}

	/**
	 * 保存按钮点击事件
	 */
	public OnClickListener mSaveButtonOnClickListener = new OnClickListener() {

		public void onClick(View v) {
			mAbcSetting.setmWordNum(stringToInt(mSetNum.getText()
					.toString()));
			if (mAibinhaosi.isChecked()) {
				mAbcSetting.setmMemoryWay(0);
			} else {
				mAbcSetting.setmMemoryWay(1);
			}

			int[] memoryCycle = new int[8];
			int day = stringToInt(mDay1.getText().toString());
			int hours = stringToInt(mHours1.getText().toString());
			int minutes = stringToInt(mMinutes1.getText().toString());
			memoryCycle[0] = day * 24 * 60 + hours * 60 + minutes;

			day = stringToInt(mDay2.getText().toString());
			hours = stringToInt(mHours2.getText().toString());
			minutes = stringToInt(mMinutes2.getText().toString());
			memoryCycle[1] = day * 24 * 60 + hours * 60 + minutes;

			day = stringToInt(mDay3.getText().toString());
			hours = stringToInt(mHours3.getText().toString());
			minutes = stringToInt(mMinutes3.getText().toString());
			memoryCycle[2] = day * 24 * 60 + hours * 60 + minutes;

			day = stringToInt(mDay4.getText().toString());
			hours = stringToInt(mHours4.getText().toString());
			minutes = stringToInt(mMinutes4.getText().toString());
			memoryCycle[3] = day * 24 * 60 + hours * 60 + minutes;

			day = stringToInt(mDay5.getText().toString());
			hours = stringToInt(mHours5.getText().toString());
			minutes = stringToInt(mMinutes5.getText().toString());
			memoryCycle[4] = day * 24 * 60 + hours * 60 + minutes;

			day = stringToInt(mDay6.getText().toString());
			hours = stringToInt(mHours6.getText().toString());
			minutes = stringToInt(mMinutes6.getText().toString());
			memoryCycle[5] = day * 24 * 60 + hours * 60 + minutes;

			day = stringToInt(mDay7.getText().toString());
			hours = stringToInt(mHours7.getText().toString());
			minutes = stringToInt(mMinutes7.getText().toString());
			memoryCycle[6] = day * 24 * 60 + hours * 60 + minutes;

			day = stringToInt(mDay8.getText().toString());
			hours = stringToInt(mHours8.getText().toString());
			minutes = stringToInt(mMinutes8.getText().toString());
			memoryCycle[7] = day * 24 * 60 + hours * 60 + minutes;

			mAbcSetting.setmMemoryCycle(memoryCycle);
			mAbcSetting.setAbcSetting();
			ToastUtil.show(AbcSetActivity.this, "保存成功");
			AbcSetActivity.this.finish();
		}
	};

	/**
	 * string转成int，为空则转成0
	 */
	private int stringToInt(String string) {
		int temp = 0;
		if (TextUtils.isEmpty(string))
			return 0;
		try {
			temp = Integer.parseInt(string);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return temp;
	}

	/**
	 * RadioButton按钮互斥事件
	 */
	public OnClickListener mRadioButtonOnClickListener = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.abc_set_aibinhaosi: {
				mAibinhaosi.setChecked(true);
				mCustom.setChecked(false);
				setText(CommonUtiles.Ebbinghaus);
				Log.i("1111", "Radioutton:"+CommonUtiles.Ebbinghaus[0]);
				break;
			}
			case R.id.abc_set_custom: {
				mAibinhaosi.setChecked(false);
				mCustom.setChecked(true);
				setText(mAbcSetting.getmMemoryCycle());
				Log.i("1111", "Radioutton:"+mAbcSetting.getmMemoryCycle()[0]);
				break;
			}
			}
		}
	};

	/**
	 * 设置八个周期的内容
	 * 
	 * @param MemoryCycle
	 *            八个周期的数组（分钟）
	 */
	private void setText(int MemoryCycle[]) {
		int temp = MemoryCycle[0];
		mDay1.setText("" + temp / 60 / 24);
		mHours1.setText("" + temp / 60 % 24);
		mMinutes1.setText("" + temp % 60);

		temp = MemoryCycle[1];
		mDay2.setText("" + temp / 60 / 24);
		mHours2.setText("" + temp / 60 % 24);
		mMinutes2.setText("" + temp % 60);

		temp = MemoryCycle[2];
		mDay3.setText("" + temp / 60 / 24);
		mHours3.setText("" + temp / 60 % 24);
		mMinutes3.setText("" + temp % 60);

		temp = MemoryCycle[3];
		mDay4.setText("" + temp / 60 / 24);
		mHours4.setText("" + temp / 60 % 24);
		mMinutes4.setText("" + temp % 60);

		temp = MemoryCycle[4];
		mDay5.setText("" + temp / 60 / 24);
		mHours5.setText("" + temp / 60 % 24);
		mMinutes5.setText("" + temp % 60);

		temp = MemoryCycle[5];
		mDay6.setText("" + temp / 60 / 24);
		mHours6.setText("" + temp / 60 % 24);
		mMinutes6.setText("" + temp % 60);

		temp = MemoryCycle[6];
		mDay7.setText("" + temp / 60 / 24);
		mHours7.setText("" + temp / 60 % 24);
		mMinutes7.setText("" + temp % 60);

		temp = MemoryCycle[7];
		mDay8.setText("" + temp / 60 / 24);
		mHours8.setText("" + temp / 60 % 24);
		mMinutes8.setText("" + temp % 60);
	}
}
