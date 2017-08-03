package com.cqxb.yecall.adapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.android.action.param.NetParam;
import com.cqxb.yecall.R;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.LoadImageUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class DialerViewAdapter extends PagerAdapter{
	private List<String> mPaths;
	private List<String> deleteList;

	private Context mContext;

	public DialerViewAdapter(Context cx) {  
        mContext = cx;  
    }  
      
    public void change(List<String> paths,List<String> deleteList) {  
        mPaths = paths;  
        this.deleteList=deleteList;
        notifyDataSetChanged();
    }

    @Override  
    public int getCount() {  
        return mPaths.size();  
    }  
  
    @Override  
    public boolean isViewFromObject(View view, Object obj) {  
        return view == (View) obj;
    }

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
//		Log.e("", "path:"+realPath);
		View iv = LayoutInflater.from(mContext).inflate(R.layout.activity_dialer_view_item, null);
		final ImageView image=(ImageView)iv.findViewById(R.id.image);
//		YETApplication.getinstant().getNetLoadImage().loadImageByVolley(image, realPath, R.drawable.transparent,
//                R.drawable.transparent);
		String params=mPaths.get(position);
		if(params.startsWith("默认图片:")){
			String[] split = params.split(":");
			image.setBackgroundResource(Integer.parseInt(split[1]));
			((ViewPager) container).addView(iv, 0);
			Log.e("", "加载默认图片");
			return iv;
		}
		JSONObject parse = JSONObject.parseObject(params);
		final String realPath=NetParam.SERVER+parse.get("advertisementUrl");
		final String saveName=realPath.substring(realPath.lastIndexOf("/")+1, realPath.length());
		Bitmap loacalBitmap = BaseUntil.getLoacalBitmap(YETApplication.getRenhuaSdcardDir()+saveName);
		if(loacalBitmap!=null){
			Message msg=new Message();
			ImaView img=new ImaView();
			img.setImageView(image);
			img.setBitMap(loacalBitmap);  
			msg.obj=img;
			msg.what=1;
			handler.sendMessage(msg);
			Log.e("", "加载本地缓存图片");
		}else {
			Log.e("", "加载服务器图片");
			new Thread(new Runnable() {
				@Override
				public void run() {
					Bitmap saveImgAndShow = new LoadImageUtil().saveImgAndShow(realPath);
					Message msg=new Message();
					ImaView img=new ImaView();
					img.setImageView(image);
					if(saveImgAndShow!=null){
						img.setBitMap(saveImgAndShow);  
						msg.obj=img;
						msg.what=1;
						handler.sendMessage(msg);
						BaseUntil.saveBitmap(saveImgAndShow, saveName,50);
					}else {
						img.setImgSource(R.drawable.transparent);
						msg.what=2;
						msg.obj=img;
						handler.sendMessage(msg);
					}
				}
			}).start();
		}
		((ViewPager) container).addView(iv, 0);
		return iv;
	}
	
	public class ImaView{
		private ImageView imageView;
		private Bitmap bitMap;
		private int imgSource;
		
		public ImageView getImageView() {
			return imageView;
		}
		public void setImageView(ImageView imageView) {
			this.imageView = imageView;
		}
		public Bitmap getBitMap() {
			return bitMap;
		}
		public void setBitMap(Bitmap bitMap) {
			this.bitMap = bitMap;
		}
		public int getImgSource() {
			return imgSource;
		}
		public void setImgSource(int imgSource) {
			this.imgSource = imgSource;
		}
		
	}
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ImaView img=(ImaView)msg.obj;
			if(msg.what==1){
				BitmapDrawable drawable=new BitmapDrawable(mContext.getResources(), img.getBitMap());
				img.imageView.setBackground(drawable);
//				image.setImageBitmap((Bitmap)msg.obj);
				if(deleteList!=null){
					for (int i = 0; i < deleteList.size(); i++) {
						final String paths=deleteList.get(i);
						JSONObject parse = JSONObject.parseObject(paths);
						final String path=parse.get("advertisementUrl").toString();
						new Thread(new Runnable() {
							@Override
							public void run() {
								String fileNme=path.substring(path.lastIndexOf("/")+1, path.length());
								File file=new File(YETApplication.getRenhuaSdcardDir()+fileNme);
								Log.e("", "删除的对象路径:"+file.getAbsolutePath()+"   "+fileNme+"    "+file.exists());
								if(file.exists()){
									boolean delete = file.delete();
									Log.e("", "删除:"+delete);
								}
							}
						}).start();
					}
					deleteList=null;
				}
			}else {
				img.imageView.setBackgroundResource(img.getImgSource());
			}
		}
		
	};

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
