package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseOK;
import com.wytube.dialog.DateSelectActivity;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/9.
 * 类 描 述:
 */

@KActivity(R.layout.activity_activ_fb)
public class ActiveFbActivity extends Activity implements DateSelectActivity.DateSet{
    @KBind(R.id.title_text)
    private TextView mtitle_text;
    @KBind(R.id.text_fb)
    private TextView mtext_fb;
    @KBind(R.id.start_time)
    private TextView mstart_time;
    @KBind(R.id.stop_time)
    private TextView mstop_time;
    @KBind(R.id.shop_title)
    private EditText mshop_title;
    @KBind(R.id.qq_title)
    private EditText mqq_title;
    @KBind(R.id.zx_phone)
    private EditText mzx_phone;
    @KBind(R.id.xd_address)
    private EditText mxd_address;
    @KBind(R.id.shop_text)
    private EditText mshop_text;
    @KBind(R.id.img_list)
    private LinearLayout mImgList;
    @KBind(R.id.imag_vs)
    private ImageView mimag_vs;
    @KBind(R.id.text_xgtp)
    private TextView mtext_xgtp;


    int start=-1,stop=-1;
    private List<String> imgsPath = new ArrayList<>();
    private String title,qq,starttime,stoptime,phone,address,content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        ininviw();
    }

    private void ininviw() {
        /*返回时为-1不执行*/
        findViewById(R.id.back_but).setOnClickListener(v -> {AppValue.ActivSxg = -1;finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {AppValue.ActivSxg = -1;finish();});
        mstart_time.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        mstart_time.setSingleLine(false);
        mstart_time.setHorizontallyScrolling(false);
        DateSelectActivity.setDateSet(this);
        if (AppValue.ActivSxg == 1){
            if (AppValue.listBeseHd == null) {
                return;
            }
            mshop_title.setText(AppValue.listBeseHd.getActivityName());
            mqq_title.setText(AppValue.listBeseHd.getQq());
            mstart_time.setText(AppValue.listBeseHd.getStarttime());
            mstop_time.setText(AppValue.listBeseHd.getEndtime());
            mzx_phone.setText(AppValue.listBeseHd.getPhone());
            mxd_address.setText(AppValue.listBeseHd.getAddress());
            if (AppValue.listBeseHd.getContent()!=null){
                mshop_text.setText(AppValue.listBeseHd.getContent().toString());
            }
            Utils.loadImage(mimag_vs, AppValue.listBeseHd.getImgUrl());
            mimag_vs.setVisibility(View.VISIBLE);
            mtitle_text.setText("修改活动");
            mtext_fb.setText("修改活动");
            mtext_xgtp.setText("修改图片");
        }
    }


    /*选择开始时间*/
    @KListener(R.id.start_time)
    private void start_timeOnClick() {
        start=1;
        startActivity(new Intent(this, DateSelectActivity.class));
    }

    /*选择开始时间*/
    @KListener(R.id.stop_time)
    private void stop_timeOnClick() {
        stop=1;
        startActivity(new Intent(this, DateSelectActivity.class));
    }

    @Override
    public void setDate(String date, int year, int month, int day) {
        if (start==1){
            starttime = year + "-" + month + "-" + day;
            mstart_time.setText(starttime);
            start=-1;
        }
        if (stop==1){
            stoptime = year + "-" + month + "-" + day;
            mstop_time.setText(stoptime);
            stop=-1;
        }
    }


    @KListener(R.id.add_shop_img)
    private void add_shop_imgOnClick() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);
    }


    @KListener(R.id.publish_but)
    private void publish_butOnClick() {
        title = mshop_title.getText().toString();
        phone = mzx_phone.getText().toString();
        address = mxd_address.getText().toString();
        content = mshop_text.getText().toString();
        qq = mqq_title.getText().toString();
        if (title.length() <= 0) {
            Utils.showOkDialog(this, "请填资讯标题!");
            return;
        }
        if (mstart_time.getText().equals("")&&mstart_time.getText()==null) {
            Utils.showOkDialog(this, "请选择开始时间!");
            return;
        }
        if (mstop_time.getText().equals("")&&mstop_time.getText()==null) {
            Utils.showOkDialog(this, "请选择结束时间!");
            return;
        }
        if (phone.length()<=0) {
            Utils.showOkDialog(this, "请输入电话!");
            return;
        }
        if (address.length()<=0) {
            Utils.showOkDialog(this, "请输入地址!");
            return;
        }
        if (content.length()<=0) {
            Utils.showOkDialog(this, "请输入内容!");
            return;
        }
        if (AppValue.ActivSxg == 1) {
            postDataXg();
        }else {
            if (imgsPath.size()<= 0) {
                Utils.showOkDialog(this, "请选择图片!");
                return;
            }
            postData();
        }
    }


    /**
     * 修改
     * 参数名	必选	类型	说明
     * activityId	是	String	活动Id
     * activityName	是	String	活动名称
     * phone	是	String	电话
     * qq	是	String	qq
     * starttime	是	String	开始时间
     * endtime	是	String	结束时间
     * content	是	String	内容
     * file	否	file	活动图片
     * address	是	String	活动地址
     */
    private void postDataXg() {
        Utils.showLoad(this);
        String keyValue = "activityId=" + AppValue.listBeseHd.getActivityId() +
                "&activityName=" + title +
                "&phone=" + phone +
                "&qq=" + qq +
                "&starttime=" + mstart_time.getText() +
                "&endtime=" + mstop_time.getText() +
                "&content=" + content +
                "&address=" + address ;
        Client.sendFile(NetParmet.USR_ADD_XGHD, keyValue, imgsPath, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("file");
            BaseOK bean = Json.toObject(json, BaseOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }else {
                imgsPath.clear();
                mImgList.removeAllViews();
                Toast.makeText(this, "修改活动成功!", Toast.LENGTH_SHORT).show();
                AppValue.ActivSxg = -1;
                AppValue.fish = 1;
                this.finish();
            }

            return false;
        }));
    }


    /**
     * 发布资讯
     * 参数名	    必选	类型	说明
     * activityName	是	String	活动名称
     * phone	    是	String	电话
     * qq	        是	String	qq
     * starttime	是	String	开始时间
     * endtime	    是	String	结束时间
     * content	    是	String	内容
     * file	        是	file	活动图片
     * address	    是	String	活动地址
     */
    private void postData() {
        Utils.showLoad(this);
        String keyValue = "activityName=" + title +
                "&phone=" + phone +
                "&qq=" + qq +
                "&starttime=" + starttime +
                "&endtime=" + stoptime +
                "&content=" + content +
                "&address=" + address ;
        Client.sendFile(NetParmet.USR_ADD_FB, keyValue, imgsPath, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("file");
            BaseOK bean = Json.toObject(json, BaseOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }else {
                imgsPath.clear();
                mImgList.removeAllViews();
                Toast.makeText(this, "发布活动成功!", Toast.LENGTH_SHORT).show();
                AppValue.fish = 1;
                this.finish();
            }

            return false;
        }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgPath = cursor.getString(columnIndex);
            createAndAddImage(imgPath);
            cursor.close();
        }
    }

    /**
     * 创建并添加图片
     *
     * @param path 图片的路径
     */
    private void createAndAddImage(String path) {
        final ImageView view = new ImageView(this);
        BitmapFactory.Options options = new BitmapFactory.Options();
        /*不考虑透明的采用此方案*/
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        /*图片缩放倍数*/
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        saveBitmap(bitmap);
        view.setImageBitmap(bitmap);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(128), dip2px(128));
        params.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        view.setLayoutParams(params);
        mImgList.addView(view);
        /*添加图片后隐藏*/
        mimag_vs.setVisibility(View.GONE);
    }

    /**
     * 保存Bitmap到本地
     *
     * @param bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        String imgPath = AppValue.userTempPath + "temp_img_" + imgsPath.size() + ".jpg";
        File f = new File(imgPath);
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
        imgsPath.add(imgPath);
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AppValue.ActivSxg = -1;
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
