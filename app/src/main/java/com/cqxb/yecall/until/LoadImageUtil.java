package com.cqxb.yecall.until;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.cqxb.yecall.YETApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

public class LoadImageUtil {

	public Bitmap saveImgAndShow(String path) {
		Bitmap downloadBitmap = downloadBitmap(path);
		return downloadBitmap;
	}

	/**
	 * 从网络上获取Bitmap，并进行适屏和分辨率处理。
	 * 
	 * @param context
	 * @param url
	 * @return
	 */
	private Bitmap downloadBitmap(String url) {
		HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 15000);
        HttpConnectionParams.setSoTimeout(params, 15000);
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.e("BaseUntil", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream in = null;
				try {
					in = entity.getContent();
					return scaleBitmap(readInputStream(in));
				} finally {
					if (in != null) {
						in.close();
						in = null;
					}
					entity.consumeContent();
				}
			}
		} catch (IOException e) {
			request.abort();
			Log.e("BaseUntil", "I/O error while retrieving bitmap from " + url,
					e);
		} catch (IllegalStateException e) {
			request.abort();
			Log.e("BaseUntil", "Incorrect URL: " + url);
		} catch (Exception e) {
			request.abort();
			Log.e("BaseUntil", "Error while retrieving bitmap from " + url, e);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return null;
	}

	private byte[] readInputStream(InputStream in) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		in.close();
		return out.toByteArray();
	}

	/**
	 * 按使用设备屏幕和纹理尺寸适配Bitmap
	 * 
	 * @param context
	 * @param in
	 * @return
	 */
	private static Bitmap scaleBitmap(byte[] data) {
		WindowManager windowMgr = (WindowManager) YETApplication.getContext()
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowMgr.getDefaultDisplay().getMetrics(outMetrics);
		int scrWidth = outMetrics.widthPixels;
		int scrHeight = outMetrics.heightPixels;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
				options);
		int imgWidth = options.outWidth;
		int imgHeight = options.outHeight;
		
		if (imgWidth > scrWidth || imgHeight > scrHeight) {
			options.inSampleSize = calculateInSampleSize(options, scrWidth,
					scrHeight);
		}
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

		return bitmap;
	}

	/**
	 * 计算Bitmap抽样倍数
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 原始图片宽高
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// 计算目标宽高与原始宽高的比值
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// 选择两个比值中较小的作为inSampleSize的值
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			if (inSampleSize < 1) {
				inSampleSize = 1;
			}
		}

		return inSampleSize;
	}
}
