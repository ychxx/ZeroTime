package com.fdzcxy.zerotime.fragment;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.activity.AbcLearnActivity;
import com.fdzcxy.zerotime.activity.AbcSetActivity;
import com.fdzcxy.zerotime.activity.AbcTestActivity;
import com.fdzcxy.zerotime.activity.MyMusicActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class ABCFragment extends BaseFragment implements OnClickListener {
	/**
	 * 绑定的视图XML
	 */
	private View view;
	/**
	 * 学习按钮
	 */
	private Button mLearnBt;
	/**
	 * 测试按钮
	 */
	private Button mTestBt;
	/**
	 * 设置按钮
	 */
	private Button mSetBt;
//	/**
//	 * 翻译按钮
//	 */
//	private Button mTranslationBt;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_abc, container,false);
		initView();
		return view;
	}
	protected void initView() {
		mLearnBt = (Button) view.findViewById(R.id.acb_learn);
		mTestBt = (Button) view.findViewById(R.id.acb_test);
		mSetBt = (Button) view.findViewById(R.id.abc_set);
//		mTranslationBt = (Button) view.findViewById(R.id.abc_translation);
		mLearnBt.setOnClickListener(this);
		mTestBt.setOnClickListener(this);
		mSetBt.setOnClickListener(this);
//		mTranslationBt.setOnClickListener(this);
	}
	/**
	 * 跳转到其他页面
	 */
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.acb_learn: {
			intent.setClass(ABCFragment.this.getActivity(),
					AbcLearnActivity.class);
			break;
		}
		case R.id.acb_test: {
			intent.setClass(ABCFragment.this.getActivity(),
					AbcTestActivity.class);
			break;
		}
		case R.id.abc_set: {
			intent.setClass(ABCFragment.this.getActivity(),
					AbcSetActivity.class);
			break;
		}
//		case R.id.abc_translation: {
//			intent.setClass(ABCFragment.this.getActivity(),
//					DownloadMusicActivity.class);
//			break;
//		}
		}
		startActivity(intent);
	}
}
