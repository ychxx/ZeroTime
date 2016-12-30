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
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private String[] mStringList;

	public MySpinnerAdapter(Context context, String[] stringList) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.mStringList = stringList;
	}

	@Override
	public int getCount() {
		return mStringList.length;
	}

	@Override
	public Object getItem(int position) {
		return mStringList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 加载显示布局
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_spinner, null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.spinner_tv);
			holder.imageView=(ImageView) convertView.findViewById(R.id.spinner_xiala);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(mStringList[position].toString());
		holder.imageView.setVisibility(View.VISIBLE);
		return convertView;
	}

	/**
	 * 加载下拉布局
	 */
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_spinner, null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.spinner_tv);
			holder.imageView=(ImageView) convertView.findViewById(R.id.spinner_xiala);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(mStringList[position].toString());
		holder.imageView.setVisibility(View.GONE);
		return convertView;
	}

	public static class ViewHolder {
		TextView textView;
		ImageView imageView;
	}

}