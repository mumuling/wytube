package com.android.action.param;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.android.action.NetBase;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.action.param.NetImageLoader.ImageCache;
import com.android.action.param.NetImageLoader.ImageListener;
import com.android.action.param.NetImageLoader.OnFinishListener;

public class NetLoadImage extends NetBase {
	static final String TAG = Thread.currentThread().getStackTrace()[2].getMethodName();
	private RequestQueue requestQueue;
	private NetImageLoader imageLoader;
	private Context mContext;

	public NetLoadImage(Context context) {
		mContext = context;
		requestQueue = Volley.newRequestQueue(mContext);
		imageLoader = new NetImageLoader(requestQueue, new BitmapCache());
	}

	/**
	 * 利用Volley异步加载图片
	 */
	public void loadImageByVolley(ImageView image_view, String image_url, int default_image, int error_image, OnFinishListener finishedListener) {
		// Trace.i(TAG, "loadUrlImage:" + image_url);
		if (image_url == null) {
			return;
		}
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(1024 * 500);
		ImageListener listener = NetImageLoader.getImageListener(image_view, default_image, error_image, finishedListener);
		try {
			imageLoader.get(image_url, listener);
		} catch (NullPointerException e) {
			Log.w("NetLoadImage", "无效的url: image_url:" + image_url);
			if (image_view != null)
				image_view.setImageResource(default_image);
		}
	}

	public void loadImageByVolley(ImageView image_view, String image_url, int default_image, int error_image) {
		this.loadImageByVolley(image_view, image_url, default_image, error_image, null);
	}

	public void loadImageByVolley(String image_url, OnFinishListener finishedListener) {
		this.loadImageByVolley(null, image_url, 0, 0, finishedListener);
	}

	public void loadImageByVolley(String image_url) {
		this.loadImageByVolley(null, image_url, 0, 0);
	}

	public class BitmapCache implements ImageCache {
		private LruCache<String, Bitmap> mCache;
		final int maxPerBmp = 1024 * 1024 * 1;

		public BitmapCache() {
			int maxSize = 5 * 1024 * 1024;
			mCache = new LruCache<String, Bitmap>(maxSize) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					Log.v("",
							String.format("key:%s, w:%d, rowBytes:%d, h:%d, size:%dk", key, value.getWidth(), value.getRowBytes(), value.getHeight(),
									value.getRowBytes() * value.getHeight() / 1024));
					return value.getRowBytes() * value.getHeight();
				}
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			Bitmap bmp = mCache.get(url);
			if (bmp == null)
				Log.w("NetLoadImage", "no found bmp from cache by key url:" + url);
			return bmp;
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			if (maxPerBmp < bitmap.getRowBytes() * bitmap.getHeight()) {
				Log.e("NetLoadImage", "Bitmap is too large " + (bitmap.getRowBytes() * bitmap.getHeight() / 1024) + "k, so don't put to cache by key url:" + url);
			} else {
				Log.v("NetLoadImage", "put Bitmap to cache by key url:" + url);
				mCache.put(url, bitmap);
			}
		}
	}
}
