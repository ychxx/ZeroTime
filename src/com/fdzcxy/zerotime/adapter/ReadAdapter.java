package com.fdzcxy.zerotime.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.domain.News;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReadAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private List<News> mNewsList= new ArrayList<News>();

	public ReadAdapter(Context context, List<News> newsList) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.mNewsList = newsList;
	}
	public void SetList(List<News> newsList){
		this.mNewsList = newsList;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return mNewsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mNewsList.get(position);
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
			convertView = inflater.inflate(R.layout.item_read, null);
			holder.mTitle = (TextView) convertView
					.findViewById(R.id.read_title);
			holder.mDese = (TextView) convertView
					.findViewById(R.id.read_dese);
			holder.mSource = (TextView) convertView
					.findViewById(R.id.read_source);
			holder.mPubDate = (TextView) convertView
					.findViewById(R.id.read_pubdate);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		
		holder.mTitle.setText(mNewsList.get(position).getmTitle());
		holder.mDese.setText(mNewsList.get(position).getmDese());
		holder.mSource.setText(mNewsList.get(position).getmSource());
		holder.mPubDate.setText(mNewsList.get(position).getmPubDate());
		return convertView;
	}
	public static class ViewHolder {
		
		TextView mTitle;
		TextView mDese;
		TextView mSource;
		TextView mPubDate;
	}
}
