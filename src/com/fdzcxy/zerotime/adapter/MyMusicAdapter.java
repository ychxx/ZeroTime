package com.fdzcxy.zerotime.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.activity.AbcTestActivity;
import com.fdzcxy.zerotime.dao.MusicDao;
import com.fdzcxy.zerotime.dao.MusicDaoImpl;
import com.fdzcxy.zerotime.dialog.CommonDialog;
import com.fdzcxy.zerotime.dialog.CommonDialog.OnDialogListener;
import com.fdzcxy.zerotime.domain.Music;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyMusicAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private List<Music> mMusic= new ArrayList<Music>();
	private MusicDao musicDao;
	public MyMusicAdapter(Context context, List<Music> music) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.mMusic = music;
		musicDao=new MusicDaoImpl(context);
	}
	public void setMusicList(List<Music> music){
		this.mMusic = music;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMusic.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mMusic.get(position);
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
			convertView = inflater.inflate(R.layout.item_music, null);
			holder.mRelativeLayout = (RelativeLayout) convertView
					.findViewById(R.id.music_list_rl);
			holder.mLove = (ImageView) convertView
					.findViewById(R.id.music_love);
			holder.mNumber = (TextView) convertView
					.findViewById(R.id.music_number);
			holder.mName = (TextView) convertView
					.findViewById(R.id.music_name);
			holder.mAuthor = (TextView) convertView
					.findViewById(R.id.music_author);
			holder.mTime = (TextView) convertView
					.findViewById(R.id.music_list_time);
			holder.mDelete = (ImageView) convertView
					.findViewById(R.id.music_list_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//判断是否是最爱的
		if(mMusic.get(position).getmIsLove()){
			holder.mLove.setBackgroundResource(R.drawable.love_blue);
		}else{
			holder.mLove.setBackgroundResource(R.drawable.love_white_blue);
		}
		final int p = position;
		holder.mLove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("Adapter", "onClick");
				
				if(mMusic.get(p).getmIsLove()){
					mMusic.get(p).setmIsLove(false);
					musicDao.updateMusic(mMusic.get(p));
					v.setBackgroundResource(R.drawable.love_white_blue);
				}
				else{
					mMusic.get(p).setmIsLove(true);
					musicDao.updateMusic(mMusic.get(p));
					v.setBackgroundResource(R.drawable.love_blue);
				}
			}
		});
		holder.mDelete.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				CommonDialog dialog = new CommonDialog(context,
						new OnDialogListener() {
							public void OnRightClick() {
							}
							public void OnLeftClick() {
								musicDao.delMusic(mMusic.get(p).getmID());
								mMusic.remove(p);
								notifyDataSetChanged();
							}
						});
				dialog.setMsg("是否确定删除音乐！");
				dialog.setRightBtnText("取消");
				dialog.setLeftBtnText("删除");
				dialog.show();				
			}
		});
		holder.mName.setText(mMusic.get(position).getmName());
		holder.mNumber.setText(position+".");
		holder.mAuthor.setText(mMusic.get(position).getmAuthor());
		holder.mTime.setText(CommonUtiles.timeconvert(mMusic.get(position).getmTime()));
		return convertView;
	}
	public static class ViewHolder {
		RelativeLayout mRelativeLayout;
		ImageView mLove;
		ImageView mDelete;
		TextView mName;
		TextView mNumber;
		TextView mAuthor;
		TextView mTime;
	}
}
