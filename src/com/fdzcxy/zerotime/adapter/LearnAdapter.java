package com.fdzcxy.zerotime.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.domain.Music;
import com.fdzcxy.zerotime.domain.Word;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LearnAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private List<Word> mWord= new ArrayList<Word>();
	/**
	 * 每日背诵单词个数，即每组个数
	 */
	private int mWordNum;
	public LearnAdapter(Context context, List<Word> word ,int wordNum) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.mWord = word;
		this.mWordNum=wordNum;
		Log.i("LearnAdapter", ""+this.mWordNum+"  "+mWord.size());
	}
	public void setList(List<Word> word){
		this.mWord = word;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return mWord.size()/mWordNum+1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mWord.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_learn, null);
			holder.mLearnDay = (TextView) convertView
					.findViewById(R.id.learn_day);
			holder.mLearnState = (TextView) convertView
					.findViewById(R.id.learn_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int temp =mWordNum*(position+1)-1;
		if(temp>=mWord.size())
			temp =mWord.size()-1;
		if(mWord.get(temp).ismIsRememberance()){
			holder.mLearnState.setText("完成");
		}else
			holder.mLearnState.setText("未完成");
		holder.mLearnDay.setText("第"+(position+1)+"天");
		return convertView;
	}
	public static class ViewHolder {
		
		TextView mLearnDay;
		TextView mLearnState;
	}
}
