package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.net.NetParmet;
import com.wytube.shared.NetUtil;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/14.
 * 类 描 述: 物品发布
 */

@KActivity(R.layout.activity_wpjyfb_info)
public class TraWPfbInfoActivity extends Activity {
    @KBind(R.id.Imag_tj)
    private ImageView mImag_tj;
    @KBind(R.id.shop_name)
    private EditText mshop_name;
    @KBind(R.id.text_number)
    private EditText mtext_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> finish());
        findViewById(R.id.title_text).setOnClickListener(v -> finish());
    }

    /*选择图片*/
    @KListener(R.id.Imag_tj)
    private void Imag_tjOnClick() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);
    }

    /*确定*/
    private boolean IsOk = false;
    @KListener(R.id.borrow_but)
    private void borrow_butOnClick() {
        if (mshop_name.length() <= 0) {
            Utils.showOkDialog(this, "请输入物品名称!");
            return;
        }
        if (mtext_number.length() <= 0) {
            Utils.showOkDialog(this, "请输入物品数量!");
            return;
        }
        if (IsOk){
            new Thread(uploadImageRunnable).start();
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    finish();
                    AppValue.fish=1;
                }
            }, 1000);
        }else {
            Utils.showOkDialog(this, "请选择图片!");
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && null != data) {
            startPhotoZoom(data.getData());
        }
        if (requestCode == 2) {
            IsOk=true;
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                saveBitmap(photo);
                mImag_tj.setImageBitmap(photo);
            }
        }
    }


    /**
     * 使用HttpUrlConnection模拟post表单进行文件
     * 上传平时很少使用，比较麻烦
     * 原理是： 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
     */
    private String resultStr = "";	// 服务端返回结果集
    Runnable uploadImageRunnable = new Runnable() {
        @Override
        public void run() {
            if(TextUtils.isEmpty(NetParmet.USR_WPJY_TJ)){
                Toast.makeText(TraWPfbInfoActivity.this, "还没有设置上传服务器的路径！", Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String, String> textParams = new HashMap<String, String>();
            Map<String, File> fileparams = new HashMap<String, File>();
            try {
                // 创建一个URL对象
                URL url = new URL(NetParmet.USR_WPJY_TJ);
                textParams = new HashMap<String, String>();
                fileparams = new HashMap<String, File>();
                // 要上传的图片文件
                File file = new File(AppValue.userJIEPath);
                /*
                 * goodsName	是	String	借用物品名称
                 * goodsNum	    是	String	数量
                 * */
                fileparams.put("file", file);
                textParams.put("goodsName", mshop_name.getText().toString());
                textParams.put("goodsNum", mtext_number.getText().toString());
                // 利用HttpURLConnection对象从网络中获取网页数据
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
                conn.setConnectTimeout(5000);
                // 设置允许输出（发送POST请求必须设置允许输出）
                conn.setDoOutput(true);
                // 设置使用POST的方式发送
                conn.setRequestMethod("POST");
                // 设置不使用缓存（容易出现问题）
                conn.setUseCaches(false);
                conn.setRequestProperty("Charset", "UTF-8");//设置编码
                // 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
                conn.setRequestProperty("ser-Agent", "Fiddler");
                conn.setRequestProperty("Authorization", AppValue.authorization);
                conn.setRequestProperty("Token", AppValue.token);
                // 设置contentType
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + NetUtil.BOUNDARY);
                OutputStream os = conn.getOutputStream();
                DataOutputStream ds = new DataOutputStream(os);
                NetUtil.writeStringParams(textParams, ds);
                NetUtil.writeFileParams(fileparams, ds);
                NetUtil.paramsEnd(ds);
                // 对文件流操作完,要记得及时关闭
                os.close();
                // 服务器返回的响应吗
                int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
                // 对响应码进行判断
                if (code == 200) {// 返回的响应码200,是成功
                    // 得到网络返回的输入流
                    InputStream is = conn.getInputStream();
                    resultStr = NetUtil.readString(is);
                    Toast.makeText(TraWPfbInfoActivity.this, "物品发布成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TraWPfbInfoActivity.this, "请求URL失败！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
        }
    };
    Handler handler = new Handler(msg -> {
        switch (msg.what) {
            case 0:
                try {
                    // 返回数据示例，根据需求和后台数据灵活处理
                    // {"status":"1","statusMessage":"上传成功","imageUrl":"http://120.24.219.49/726287_temphead.jpg"}
                    JSONObject jsonObject = new JSONObject(resultStr);
                    // 服务端以字符串“1”作为操作成功标记
                    if (jsonObject.optString("status").equals("1")) {
                        BitmapFactory.Options option = new BitmapFactory.Options();
                        // 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图，3为三分之一
                        option.inSampleSize = 1;
                        // 服务端返回的JsonObject对象中提取到图片的网络URL路径
                        String imageUrl = jsonObject.optString("imageUrl");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return false;
    });

    /**
     * 保存Bitmap到本地
     * @param bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        File f = new File(AppValue.userJIEPath);
        if (f.exists()) {
            f.delete();
        } else {
            File path = new File(f.getParent());
            if (!path.exists()) {
                path.mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 裁剪图片方法实现
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }
}
