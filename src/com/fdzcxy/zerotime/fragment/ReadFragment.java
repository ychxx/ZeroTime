package com.fdzcxy.zerotime.fragment;

import java.util.ArrayList;
import java.util.List;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.activity.WebActivity;
import com.fdzcxy.zerotime.adapter.MySpinnerAdapter;
import com.fdzcxy.zerotime.adapter.ReadAdapter;
import com.fdzcxy.zerotime.dao.ReadDao;
import com.fdzcxy.zerotime.dao.ReadDaoImpl;
import com.fdzcxy.zerotime.domain.News;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ReadFragment extends BaseFragment {
	View view;
	ListView mReadLV;
	Spinner mSpinner;
	ReadDao mReadDao;
	private static List<News> mNewsList = new ArrayList<News>();
	private static ReadAdapter mReadAdapter;
	private MySpinnerAdapter mMySpinnerAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_read, container, false);
		return view;
	}

	public void onStart() {
		super.onStart();
		initView();
		initData();

	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		mReadLV = (ListView) view.findViewById(R.id.read_lv);
		mSpinner = (Spinner) view.findViewById(R.id.read_spinner);
	}

	private void initData() {
		mReadDao = new ReadDaoImpl(ReadFragment.this.getActivity());
		String channelName = CommonUtiles.CHANNEL_NAME[CommonUtiles.SELECT_CHANNEL_NUM];
		String channelID = CommonUtiles.CHANNEL_ID[CommonUtiles.SELECT_CHANNEL_NUM];
		mNewsList = mReadDao.loadNews(channelName, channelID);
		mReadAdapter = new ReadAdapter(ReadFragment.this.getActivity(),
				mNewsList);
		mReadLV.setAdapter(mReadAdapter);
		mReadLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(ReadFragment.this.getContext(),
						WebActivity.class);
				//绑定数据（网站地址）
				Bundle bundle = new Bundle();
				bundle.putString("weburl", mNewsList.get(position).getmLink());
				intent.putExtras(bundle);
				
				startActivity(intent);

			}
		});

		mMySpinnerAdapter = new MySpinnerAdapter(
				ReadFragment.this.getContext(), CommonUtiles.CHANNEL_NAME);
		mSpinner.setAdapter(mMySpinnerAdapter);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 下拉框选中
				mReadDao = new ReadDaoImpl(ReadFragment.this.getActivity());
				CommonUtiles.SELECT_CHANNEL_NUM = position;
				String channelName = CommonUtiles.CHANNEL_NAME[CommonUtiles.SELECT_CHANNEL_NUM];
				String channelID = CommonUtiles.CHANNEL_ID[CommonUtiles.SELECT_CHANNEL_NUM];
				mNewsList = mReadDao.loadNews(channelName, channelID);
			}
			public void onNothingSelected(AdapterView<?> parent) {
				// 下拉框不选中, 直接按返回

			}
		});
	}

	/**
	 * 接受MusicService服务传来的更新播放框信息
	 */
	public Handler mHandlerRefreshListView = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == mApp.READFRAGMENT_REFRESHLISTVIEW) {
				// RefreshPlay();
				Bundle bundle = msg.getData();
				ArrayList list = bundle.getParcelableArrayList("newsList");
				mNewsList = (List<News>) list.get(0);
				Log.i("1111", mNewsList.get(0).toString());
				mReadAdapter.SetList(mNewsList);
				mReadAdapter.notifyDataSetChanged();
			}
		}
	};

}
