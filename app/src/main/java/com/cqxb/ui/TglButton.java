package com.cqxb.ui;


import com.cqxb.yecall.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TglButton extends View implements OnTouchListener{

	private String TOGGLTBUTTON="toggleButton";
	//因为可能很多地方用到所以加个id标识来区分按钮的状态
	private int mId=0;
	//
	private Context mContext;
	//选中的标识
	private long check =0;  //0-未选择  1-选中
	//图标
	private Bitmap no,off,sch;
	//当前点击的位置
	private float currClick;
	//按钮图标所在位置
	private float currPosition;
	//提供一个状态改变的监听来给用户实现功能
	private OnChangedListener listener;
	
	private SharedPreferences preferences;
	
	public TglButton(Context context) {
		super(context);
		mContext=context;
		init();
	}

	public TglButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext=context;
		init();
	}

	public TglButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		init();
	}
	
	public void init(){
		mId=getId();
		preferences=PreferenceManager.getDefaultSharedPreferences(mContext);
		try {
			check=preferences.getLong(TOGGLTBUTTON+mId, -1);
			System.out.println("当前的状态   init:"+check+"    "+mId+"     "+getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		no=BitmapFactory.decodeResource(getResources(), R.drawable.toggleopen);
		off=BitmapFactory.decodeResource(getResources(), R.drawable.toggleclose);
		sch=BitmapFactory.decodeResource(getResources(), R.drawable.togglecircle);
		
		setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Matrix matrix=new Matrix();
		Paint paint=new Paint();
		if(check==-1||check==0){
			canvas.drawBitmap(off, matrix, paint);
			saveStatus(0);
			check=0;
			currPosition=-1;
		}else {
			canvas.drawBitmap(no, matrix, paint);
			saveStatus(1);
			check=1;
			currPosition=no.getWidth()-sch.getWidth();
		}
		try {
			canvas.drawBitmap(sch, currPosition,0, paint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void saveStatus(int status){
		try {
			preferences.edit().putLong(TOGGLTBUTTON + mId, status).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction()==KeyEvent.ACTION_DOWN){
			if(event.getX()<=sch.getWidth()){
				//标识关
				currPosition=0;
				saveStatus(0);
				check=0;
			}else if(event.getX()>sch.getWidth()){
				//标识开
				currPosition=no.getWidth()-sch.getWidth();
				saveStatus(1);
				check=1;
			}
		}else if(event.getAction()==KeyEvent.ACTION_UP){
			
		}
		 if(listener != null){  
			listener.onChange(TglButton.this, check);
		} 
		 System.out.println("当前的状态......:"+check+"   "+getX()+"    "+event.getX()+"    "+sch.getWidth()+"    "+no.getWidth()+"   "+preferences.getLong(TOGGLTBUTTON+mId, -1));
		 //当前的状态......:                      0          444.0           41.550415          62                       128
		 invalidate();
		return false;
	}
	
	 /** 
     * 供外部调用的方法 
     * @param listener 
     */  
    public void setOnChangedListener(OnChangedListener listener){  
    	this.listener=listener;
    }  

   //状态改变的时候做的事情
	public interface OnChangedListener{
		/**
		 * 状态改变后需要实现的事件
		 * @param button
		 * @param check
		 */
		public void onChange(TglButton button,long check);
	}
	
	/**
	 * 供使用者设置是否选中
	 * @param check  0-未选中  1- 选中
	 */
	@SuppressLint("NewApi")
	public void setChecked(int check){
		if(check==0){
			currPosition=0;
			check=0;
			saveStatus(0);
		}else {
			currPosition=getX()+(no.getWidth()-sch.getWidth());
			check=1;
			saveStatus(1);
		}
		invalidate();
	}
	
}
