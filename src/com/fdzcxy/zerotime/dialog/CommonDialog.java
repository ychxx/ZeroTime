package com.fdzcxy.zerotime.dialog;

import com.fdzcxy.zerotime.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class CommonDialog extends Dialog{
	
	private TextView mTitleView; //标题
	private TextView mContentView; //内容
	private Button mLeftBtn;
	private Button mRightBtn;
	
	private OnDialogListener mListener;

	public CommonDialog(Context context) {
		this(context,R.style.MusicDialog,null);
	}
	
	public CommonDialog(Context context,OnDialogListener listener) {
		this(context,R.style.MusicDialog,listener);
	}
	
	public CommonDialog(Context context, int theme,OnDialogListener listener) {
		super(context,theme);
		initViews(context,listener);
    }
	
	private void initViews(Context context,OnDialogListener listener){
		setContentView(R.layout.utile_dialog);
		mTitleView = (TextView)findViewById(R.id.dialog_title);
		mContentView = (TextView)findViewById(R.id.dialog_content);
		mLeftBtn = (Button)findViewById(R.id.dialog_confirm);
		mRightBtn = (Button)findViewById(R.id.dialog_cancel);
		 //设置对话框位置大小
	    Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        dialogWindow.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setAttributes(lp);//此处暂未设置偏移量
        setCanceledOnTouchOutside(false);
	    setCancelable(false);
		//设置监听
		this.mListener = listener;
		mLeftBtn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(mListener != null){
					mListener.OnLeftClick();
				}
				
			}
		});
		mRightBtn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dismiss();
				if(mListener != null){
					mListener.OnRightClick();
				}
			}
		});
		
		
	}
	
	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title){
		if(TextUtils.isEmpty(title))
			return;
		mTitleView.setVisibility(View.VISIBLE);
		mTitleView.setText(title);
	}
	
	/**
	 * 设置提示语
	 */
	public void setMsg(String msg){
		if(TextUtils.isEmpty(msg))
			return;
		mContentView.setText(msg);
	}
	
	/**
	 * 设置确定按钮字符串
	 */
	public void setLeftBtnText(String text){
		if(TextUtils.isEmpty(text))
			return;
		mLeftBtn.setText(text);
	}
	
	/**
	 * 设置取消按钮字符串
	 * @param text
	 */
	public void setRightBtnText(String text){
		if(TextUtils.isEmpty(text))
			return;
		mRightBtn.setText(text);
	}
	

	public interface OnDialogListener{
		public void OnLeftClick();
		public void OnRightClick();
	}
}
