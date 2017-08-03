package com.cqxb.yecall.until;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.VCard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.cqxb.yecall.R;
import com.cqxb.yecall.Smack;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.bean.XmlBean;


public class BaseUntil {
	//获取自定义的消息解析
	public static List<XmlBean> getMsg(String msg){
//		System.out.println("ChatListener "+msg);
		List<XmlBean> xmlBeans=new ArrayList<XmlBean>();
		Document parse = Jsoup.parse(msg);
		Elements bodyitem = parse.getElementsByTag("bodyitem");//获取标签
		for (int i = 0; i < bodyitem.size(); i++) {
			if(!TextUtils.isEmpty(bodyitem.get(i).html())){
//				System.out.println("呈现的数据 bodyitem "+i+" :"+bodyitem.get(i).html());
//				System.out.print("呈现的数据 itemtype :"+bodyitem.get(i).attr("itemtype")+"   ");
//				System.out.print("呈现的数据 sha1 :"+bodyitem.get(i).attr("sha1")+"   ");
//				System.out.print("呈现的数据 width :"+bodyitem.get(i).attr("width")+"   ");
//				System.out.println("呈现的数据 height :"+bodyitem.get(i).attr("height")+"   ");
				XmlBean xml=new XmlBean();
				if("image".equals(bodyitem.get(i).attr("itemtype"))){
					xml.setVal(null);
//					Bitmap stringToBitmap = stringToBitmap(bodyitem.get(i).html());
//					String saveBitmap = saveBitmap(stringToBitmap, TimeRender.getTimes()+".png");
//					try {
//						//String sha1 = HashCode.getSHA1(saveBitmap);//处理图片hash值
//						File f=new File(saveBitmap);
//						String path=f.getPath();
//						System.out.println("path "+path);
//						if(!TextUtils.isEmpty(path)){
//							path=path.substring(0,path.lastIndexOf("/")+1);
//						}
//						path=path+bodyitem.get(i).attr("sha1")+".png";
//						System.out.println("path "+path);
//						f.renameTo(new File(path));
//						xml.setPath(path);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					xml.setObject(stringToBitmap);
					Bitmap stringToBitmap = stringToBitmap(bodyitem.get(i).html());
					String saveBitmap = saveBitmap(stringToBitmap, bodyitem.get(i).attr("sha1")+".png");
					xml.setPath(saveBitmap);
					xml.setObject(stringToBitmap);
				}else if("voice".equals(bodyitem.get(i).attr("itemtype"))){
					xml.setVal(null);
					try {
						File ff=new File(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path)+bodyitem.get(i).attr("sha1")+".amr");
						if(!ff.exists()){//如果是自己发送的消息那么 文件存在则不在生成
							Long times = TimeRender.getTimes();
							decoderBase64File(bodyitem.get(i).html(), Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path)+times+".amr");
							File f=new File(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path)+times+".amr");
							
							String path=f.getPath();
							System.out.println("path "+path);
							if(!TextUtils.isEmpty(path)){
								path=path.substring(0,path.lastIndexOf("/")+1);
							}
							path=path+bodyitem.get(i).attr("sha1")+".amr";
							System.out.println("path "+path);
							f.renameTo(new File(path));
							xml.setPath(path);
						}else {
							xml.setPath(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path)+bodyitem.get(i).attr("sha1")+".amr");
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					xml.setVal(bodyitem.get(i).html());
				}
				xml.setHeight(bodyitem.get(i).attr("height"));
				xml.setItemtype(bodyitem.get(i).attr("itemtype"));
				xml.setSha1(bodyitem.get(i).attr("sha1"));
				xml.setWidth(bodyitem.get(i).attr("width"));
				xmlBeans.add(xml);
			}
		}
		return xmlBeans;
	}
	
	
	public static String getMsgString(String msg){
		Document parse = Jsoup.parse(msg);
		Elements bodyitem = parse.getElementsByTag("bodyitem");//获取标签
		String param="";
		for (int i = 0; i < bodyitem.size(); i++) {
			if(!TextUtils.isEmpty(bodyitem.get(i).html())){
				if("vibration".equals(bodyitem.get(i).attr("itemtype"))||"text".equals(bodyitem.get(i).attr("itemtype"))){
					param+="val="+bodyitem.get(i).html()+":itemtype="+bodyitem.get(i).attr("itemtype")+",";
				}
				if("image".equals(bodyitem.get(i).attr("itemtype"))){
					param+="val="+bodyitem.get(i).html()+":itemtype="+bodyitem.get(i).attr("itemtype")+":sha1="+bodyitem.get(i).attr("sha1")+":width="+bodyitem.get(i).attr("width")+":height="+bodyitem.get(i).attr("height")+",";
				}
				if("voice".equals(bodyitem.get(i).attr("itemtype"))||"face".equals(bodyitem.get(i).attr("itemtype"))){
					param+="val="+bodyitem.get(i).html()+":itemtype="+bodyitem.get(i).attr("itemtype")+":sha1="+bodyitem.get(i).attr("sha1")+",";
				}
				param+=",";
			}
		}
		if(param.endsWith(",")){
			param=param.substring(0,param.length()-1);
		}
		return param;
	}
	
	public static String getListString(List<XmlBean> xmlList){
		String param="";
		for (int i = 0; i < xmlList.size(); i++) {
//			System.out.println("呈现的数据  ~~:"+xmlList.get(i).getItemtype()+" "+xmlList.get(i).getSha1()+" "+xmlList.get(i).getVal());
			
			if("vibration".equals(xmlList.get(i).getItemtype())||"text".equals(xmlList.get(i).getItemtype())){
				param+="val<!@split-split1@>"+xmlList.get(i).getVal()+"<!@split-split2@>itemtype<!@split-split1@>"+xmlList.get(i).getItemtype()+"<!@split-split@>";
			}
			if("image".equals(xmlList.get(i).getItemtype())){
				param+="path<!@split-split1@>"+xmlList.get(i).getPath()+"<!@split-split2@>itemtype<!@split-split1@>"+xmlList.get(i).getItemtype()+"<!@split-split2@>sha1<!@split-split1@>"+xmlList.get(i).getSha1()+"<!@split-split2@>width<!@split-split1@>"+xmlList.get(i).getWidth()+"<!@split-split2@>height<!@split-split1@>"+xmlList.get(i).getHeight()+"<!@split-split@>";
			}
			if("voice".equals(xmlList.get(i).getItemtype())||"face".equals(xmlList.get(i).getItemtype())){
//				param+="val="+xmlList.get(i).getVal()+":itemtype="+xmlList.get(i).getItemtype()+":sha1="+xmlList.get(i).getSha1()+",";
				
				param+="val<!@split-split1@>"+xmlList.get(i).getVal()+"<!@split-split2@>itemtype<!@split-split1@>"+xmlList.get(i).getItemtype()+"<!@split-split2@>sha1<!@split-split1@>"+xmlList.get(i).getSha1()+"<!@split-split@>";
			}
		}
		if(param.endsWith(",")){
			param=param.substring(0,param.length()-1);
		}
		
//		System.out.println("呈现的数据  param:"+param);
		return param;
	}
	
	
	
	/**
	 * 将base64转换成bitmap图片
	 * @param string base64字符串
	 * @return bitmap
	 */
	public static Bitmap stringToBitmap(String string){
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	} 
	
	/**
	 *将bitmap转换成base64字符串
	 *@param bitmap
	 *@return base64 字符串
	 */
	public static String bitmaptoString(Bitmap bitmap, int bitmapQuality) {
		// 将Bitmap转换成字符串
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, bitmapQuality, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, Base64.DEFAULT);
		return string;
	}
	
	 
	/** 保存方法 */
	public static String saveBitmap(Bitmap bm, String picName) {
		
		System.out.println("ChatListener "+Environment.getRootDirectory());//手机根目录
		System.out.println("ChatListener "+Environment.getExternalStorageDirectory());//手机存储
		File f = new File(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_pathimg));
		
		System.out.println("ChatListener "+f.getAbsolutePath());
		System.out.println("ChatListener "+f.getPath());
		if(!f.exists()){
			f.mkdir();
		}
		File ff=new File(f.getAbsolutePath()+"/"+picName);
		
		try {
			ff.createNewFile();
			
			FileOutputStream out = new FileOutputStream(ff);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			Log.i("ChatListener 聊天图片", "ChatListener 聊天图片 已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ff.getAbsolutePath();
	}
	
	
	/**
	 * 保存方法  不压缩图片 
	 * @param bm  bitmap
	 * @param picName  name.png
	 * @param compress 是否压缩  100-不压缩
	 * @return
	 */
	public static String saveBitmap(Bitmap bm, String picName,int compress) {
		
		System.out.println("ChatListener "+Environment.getRootDirectory());//手机根目录
		System.out.println("ChatListener "+Environment.getExternalStorageDirectory());//手机存储
		File f = new File(YETApplication.getRenhuaSdcardDir());
		
		System.out.println("ChatListener "+f.getAbsolutePath());
		System.out.println("ChatListener "+f.getPath());
		if(!f.exists()){
			f.mkdir();
		}
		File ff=new File(f.getAbsolutePath()+"/"+picName);
		
		try {
			ff.createNewFile();
			
			FileOutputStream out = new FileOutputStream(ff);
			bm.compress(Bitmap.CompressFormat.PNG, compress, out);
			out.flush();
			out.close();
			Log.i("ChatListener 聊天图片", "ChatListener 聊天图片 已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ff.getAbsolutePath();
	}

	
	//转换格式
		public static String getMsgDistr(String msgDistr){
			//<!@split-split1@>  =   <!@split-split2@>   :    <!@split-split@>  ,
			//格式 itemtype<!@split-split1@>text<!@split-split2@>val<!@split-split1@>123<!@split-split@>itemtype<!@split-split1@>image<!@split-split2@>val<!@split-split1@>base64<!@split-split2@>sha1<!@split-split1@>BJBKNKJGHFGHJ543465435
			//格式 itemtype=text:val=123,itemtype=image:val=base64:sha1=BJBKNKJGHFGHJ543465435
			String type="";
			if(!TextUtils.isEmpty(msgDistr)){
				String[] split = msgDistr.split("<!@split-split@>");//拆开 文字 图片 
				msgDistr="";
				for (int i = 0; i < split.length; i++) {
					if(!TextUtils.isEmpty(split[i])){
						String[] split2 = split[i].split("<!@split-split2@>");//得到文字 或图片的内容以及属性 键值对形式(图片的宽高  文字的色彩 字体)
						for (int j = 0; j < split2.length; j++) {
							
							if(split2[j].endsWith("text")){
								type="text";
							}
							if(split2[j].endsWith("image")){
								type="image";
							}
							if(split2[j].endsWith("vibration")){
								type="vibration";
							}
							if(split2[j].endsWith("face")){
								type="face";
							}
							if(split2[j].endsWith("voice")){
								type="voice";
							}
						}
						for (int j = 0; j < split2.length; j++) {
							if(!TextUtils.isEmpty(split2[j])){
								String[] split3 = split2[j].split("<!@split-split1@>");
								if(type.equals("text")){
									if("val".equals(split3[0])){
										msgDistr+=split2[j]+"<!@split-split@>";
									}
								}
								if(type.equals("image")){
									if("path".equals(split3[0])){
										msgDistr+=split2[j]+"<!@split-split@>";
									}
								}
								if(type.equals("vibration")){
									if("val".equals(split3[0])){
										msgDistr+=split2[j]+"<!@split-split@>";
									}
								}
								if(type.equals("face")){
									if("sha1".equals(split3[0])){
										msgDistr+=split2[j]+"<!@split-split@>";
									}
								}
								if(type.equals("voice")){
									if("sha1".equals(split3[0])){
										msgDistr+=split2[j].replace("sha1", "voice")+"<!@split-split@>";
									}
								}
							}
						}
					}
				}
			}
			if(msgDistr.endsWith(",")){
				msgDistr=msgDistr.substring(0,msgDistr.length()-1);
			}
			return msgDistr;
		}
		
		public static String strNotNull(String str){
			if(str==null){
				return "";
			}else {
				return str.trim();
			}
		}
		
		
		//获取名片
		public VCard getVcard(String gid){
			if(!Smack.getInstance().isAuthenticated()){
				return null;
			}
			VCard vCard=new VCard();
			try {
				vCard.load(Smack.conn,gid);
			} catch (XMPPException e) {
				e.printStackTrace();
				vCard=null;
			}
			return vCard;
		}
		
		
		/**
		 * 获取jid
		 * @param from
		 * @return
		 */
		public static String getJabberID(String from) {
	        String[] res = from.split("/");
	        return res[0].toLowerCase();
	    }
		
		/**
		 * 获取nickName
		 * @param from
		 * @return
		 */
		public static String getNickName(String from) {
	        String[] res = from.split("@");
	        return res[0].toLowerCase();
	    }
		
		
		/**
		 * 获取 MM-DD HHmmss
		 * @return
		 */
		public static String getDate(String date){
			String[] times = date.split("-");
			return times[1]+"-"+times[2];
		}
		
		/**
		 * 处理字符串方法，如果字符串为NULL值返回空值，否则为源串
		 * @param src 输入待处理的源字符串
		 * @return 返回处理后的字符串
		 */
		public static String stringNoNull(String src) {
			if (src == null) {
				src = "";
			} else if (src.equalsIgnoreCase("null")) {
				src = "";
			}
			return (src);
		}
		
		/**
		 * 处理字符串方法，如果字符串为NULL值返回空值，否则为源串
		 * @param src 输入待处理的源字符串
		 * @return 返回处理后的字符串
		 */
		public static String stringNoNull(Object src) {
			if (src == null) {
				return "";
			} 
			return (src.toString());
		}
		
		
		/**
		* 加载本地图片
		* @param url
		* @return
		*/
		public static Bitmap getLoacalBitmap(String url) {
		     try {
		           FileInputStream fis = new FileInputStream(url);
		           Bitmap decodeStream = BitmapFactory.decodeStream(fis);
		           fis.close();
		           fis=null;
		           return decodeStream;
		     } catch (Exception e) {
		          e.printStackTrace();
		          return null;
		     }
		}
		
		
		
		/** 
	     * 将彩色图转换为灰度图 
	     * @param img 位图 
	     * @return  返回转换好的位图 
	     */  
	    public Bitmap convertGreyImg(Bitmap img) {  
	        int width = img.getWidth();         //获取位图的宽  
	        int height = img.getHeight();       //获取位图的高  
	          
	        int []pixels = new int[width * height]; //通过位图的大小创建像素点数组  
	          
	        img.getPixels(pixels, 0, width, 0, 0, width, height);  
	        int alpha = 0xFF << 24;   
	        for(int i = 0; i < height; i++)  {  
	            for(int j = 0; j < width; j++) {  
	                int grey = pixels[width * i + j];  
	                  
	                int red = ((grey  & 0x00FF0000 ) >> 16);  
	                int green = ((grey & 0x0000FF00) >> 8);  
	                int blue = (grey & 0x000000FF);  
	                  
	                grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);  
	                grey = alpha | (grey << 16) | (grey << 8) | grey;  
	                pixels[width * i + j] = grey;  
	            }  
	        }  
	        Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);  
	        result.setPixels(pixels, 0, width, 0, 0, width, height);  
	        return result;  
	    }  

	 
	    /**
	     * 第三：图片按比例大小压缩方法（根据Bitmap图片压缩）：
	     * @param image
	     * @param x 宽
	     * @param y 高
	     * @param size 压缩后的大小
	     * @return
	     */
	public static Bitmap comp(Bitmap image,float x,float y,int size) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > size) {
			// 判断如果图片大于256kb,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();
			// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
			// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 40f;
		if(y!=0){
			hh=y;
		}
		// 这里设置高度为800f
		float ww = 40f;
		if(x!=0){
			ww=x;
		}
		// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;
		// be=1表示不缩放
		if (w > h && w > ww) {
			// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;
		// 设置缩放比例 //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);
		// 压缩好比例大小后再进行质量压缩
	}


	/**
	 * 第一：质量压缩法：
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {
		Bitmap bitmap=null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
			int options = 100;
			while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
				baos.reset();// 重置baos即清空baos
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
				options -= 10;// 每次都减少10
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
			bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
			baos.close();
			baos=null;
		} catch (Exception e) {
			Log.e("", ""+e.getLocalizedMessage());
			bitmap=BitmapFactory.decodeResource(YETApplication.getContext().getResources(), R.drawable.transparent);
		}
		return bitmap;
	}
	
	/**
	 * 第二：图片按比例大小压缩方法（根据路径获取图片并压缩）：
	 * @param image
	 * @return
	 */
	public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }


	
	public static String getSHA1(String fileName) throws Exception{
		MessageDigest sha1= MessageDigest.getInstance("SHA1");
		return calculateHash(sha1, fileName)==null?"":calculateHash(sha1, fileName).toUpperCase();
	}
	
	public static String getMD5(String fileName) throws Exception{
		MessageDigest sha1= MessageDigest.getInstance("MD5");
		return calculateHash(sha1, fileName)==null?"":calculateHash(sha1, fileName).toUpperCase();
	}
	
	
	public static String calculateHash(MessageDigest algorithm, String fileName)
			throws Exception {

		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		DigestInputStream dis = new DigestInputStream(bis, algorithm);

		// read the file and update the hash calculation
		while (dis.read() != -1);

		// get the hash value as byte array
		byte[] hash = algorithm.digest();

		return byteArray2Hex(hash);
	}
	
	public static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}
	
	/**
	 * 将文件编码成base64
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return Base64.encodeToString(buffer, Base64.DEFAULT);
	}
	
	public static void decoderBase64File(String base64Code, String savePath)
			throws Exception {
		// byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
		FileOutputStream out = new FileOutputStream(savePath);
		out.write(buffer);
		out.close();
	}
	
	
	/**
	 * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
	 * 
	 * @param bytes
	 * @return
	 */
	public static float bytes2kb(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		if (returnValue > 1)
			return returnValue;
		BigDecimal kilobyte = new BigDecimal(1024);
		returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		return returnValue;
	}

	//字符串去重
	public static String removeDuplicate(String ss) {
		List<String> sList = new ArrayList<String>();
		String result="";
		for (int i = 0; i < ss.length(); i++) {
			sList.add(ss.charAt(i) + "");
		}
		
		HashSet h = new HashSet(sList);
		sList.clear();
		sList.addAll(h);
		
		for (int i = 0; i < sList.size(); i++) {
			result += sList.get(i);
		}
		return result;
	}

	//字符串去重
	public static String removeDuplicateWithOrder(String ss) {
		List<String> sList = new ArrayList<String>();
		String result="";
		for (int i = 0; i < ss.length(); i++) {
			sList.add(ss.charAt(i) + "");
		}
		
		HashSet h = new HashSet(sList);
		sList.clear();
		sList.addAll(h);
		
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = sList.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		sList.clear();
		sList.addAll(newList);
		
		for (int i = 0; i < sList.size(); i++) {
			result += sList.get(i);
		}
		return result;
	}
	
	//list去重
	public static List  removeDuplicate(List list)  {   
	    HashSet h  =   new  HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	}     
	
	//list去重
	public static List removeDuplicateWithOrder(List list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);
		return list;
	}
	
	//浮点型判断
	 public static boolean isDecimal(String str) {
	  if(str==null || "".equals(str))
	   return false;  
	  Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
	  return pattern.matcher(str).matches();
	 }
	 
	 	/**
		 * 格式化时间
		 * @param time
		 * @return
		 */
		public static String formatDateTime(String time) {
			SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			if(time==null ||"".equals(time)){
				return "";
			}
			Date date = null;
			try {
				date = format.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Calendar current = Calendar.getInstance();
			
			Calendar today = Calendar.getInstance();	//今天
			
			today.set(Calendar.YEAR, current.get(Calendar.YEAR));
			today.set(Calendar.MONTH, current.get(Calendar.MONTH));
			today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
			//  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
			today.set( Calendar.HOUR_OF_DAY, 0);
			today.set( Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			
			Calendar yesterday = Calendar.getInstance();	//昨天
			
			yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
			yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
			yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
			yesterday.set( Calendar.HOUR_OF_DAY, 0);
			yesterday.set( Calendar.MINUTE, 0);
			yesterday.set(Calendar.SECOND, 0);
			
			current.setTime(date);
			
			if(current.after(today)){
				return "今天 "+time.split(" ")[1];
			}else if(current.before(today) && current.after(yesterday)){
				
				return "昨天 "+time.split(" ")[1];
			}else{
				return time.split(" ")[0];
			}
		}
}
