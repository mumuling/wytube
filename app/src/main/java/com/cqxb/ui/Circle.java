package com.cqxb.ui;

import com.cqxb.yecall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class Circle extends View{
	public float currentX = -250; 
	public float currentY = -250;
	private Canvas mCanvas;
	
	public Circle(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Circle(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}	

	@Override 
	protected void onDraw(Canvas canvas) {  
		// TODO Auto-generated method stub  
		super.onDraw(canvas); 
		Paint p = new Paint();  
		p.setAntiAlias(true);//设置画笔去锯齿，没有此语句，画的线或图片周围不圆滑
		p.setColor(Color.RED);  
		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.come_js);
		Matrix matrix=new Matrix();
		matrix.postScale(1, 1);
		try {
			System.out.println("x-:"+mBitmap.getWidth()+" Y-:"+mBitmap.getHeight());
			canvas.drawBitmap(mBitmap, currentX, currentY, p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		mCanvas=canvas;
	}
	
	 public void clear(){
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		mCanvas.drawPaint(paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
		invalidate();
	}
	
}
