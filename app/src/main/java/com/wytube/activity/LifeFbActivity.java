package com.wytube.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.SHFBDialogAdapters;
import com.wytube.beans.BaseOK;
import com.wytube.beans.BaseSHfw;
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
 * 创建时间: 2017/8/10.
 * 类 描 述: 发布服务
 */

@KActivity(R.layout.activity_shfw_fb)
public class LifeFbActivity extends Activity {
    @KBind(R.id.imag_vs)
    private ImageView mimag_vs;
    @KBind(R.id.shop_title)
    private EditText mshop_title;
    @KBind(R.id.shop_phone)
    private EditText mshop_phone;
    @KBind(R.id.shop_address)
    private EditText mshop_address;
    @KBind(R.id.img_list)
    private LinearLayout mImgList;
    @KBind(R.id.publish_type)
    private TextView mPublishType;
    @KBind(R.id.shop_name)
    private EditText mshop_name;

    private Dialog dialog1;
    private LayoutInflater inflater;
    private ListView canshu_list;
    private List<String> imgsPath = new ArrayList<>();
    private String title, phone,name, address,tradeType;
    private List<BaseSHfw.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    @KListener(R.id.add_shop_img)
    private void add_shop_imgOnClick() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);
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

    @KListener(R.id.publish_but)
    private void publish_butOnClick() {
        title = mshop_title.getText().toString();
        phone = mshop_phone.getText().toString();
        address = mshop_address.getText().toString();
        name = mshop_name.getText().toString();
        if (imgsPath.size()<= 0) {
            Utils.showOkDialog(this, "请选择图片!");
            return;
        }
        if (name.length()<= 0) {
            Utils.showOkDialog(this, "请输入姓名!");
            return;
        }
        if (title.length() <= 0) {
            Utils.showOkDialog(this, "请填服务标题!");
            return;
        }
        if (phone.length() <= 0) {
            Utils.showOkDialog(this, "请填写电话!");
            return;
        }
        if (address.length() <= 0) {
            Utils.showOkDialog(this, "请填写地址!");
            return;
        }
        if (tradeType.length()<=0) {
            Utils.showOkDialog(this, "请选择类型!");
            return;
        }
        postData();

    }

    /**
     * 发布服务
     * shopName	    是	String	标题
     * concat	    是	String	联系电话
     * name	        是  String	名字
     * remark	    是  String	地址
     * longitude	否	String	经度
     * latitude	    否	String	纬度
     * shopTypeId	是	String	服务类别id
     * file	        是	file	图片
     */
    private void postData() {
        Utils.showLoad(this);
        String keyValue = "shopName=" + title +
                "&concat=" + phone +
                "&name=" + name +
                "&remark=" + address +
                "&shopTypeId=" + tradeType;
        Client.sendFile(NetParmet.USR_SHFWXQ_FB, keyValue, imgsPath, new Handler(msg -> {
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
                Toast.makeText(this, "生活服务发布成功!", Toast.LENGTH_SHORT).show();
                AppValue.fish = 1;
                this.finish();
            }
            return false;
        }));
    }


    @KListener(R.id.select_type)
    private void select_typeOnClick() {
        dalogcanshu();
    }

    public Dialog dalog() {
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        inflater = LayoutInflater.from(this);
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getWindow().setDimAmount(0.3f);
        DisplayMetrics dm2 = this.getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = frame.width(); //(int) (dm2.widthPixels); // 设置宽度
        lp.height = frame.height(); //(int) (dm2.heightPixels-titleBarHeight); // 设置高度
        dialog.getWindow().setAttributes(lp);
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return dialog;
    }

    /**
     * 选择类型
     */
    public void dalogcanshu() {
        dialog1 = dalog();
        View view = inflater.inflate(R.layout.activity_news_dilogtxt, null);
        canshu_list = (ListView) view.findViewById(R.id.canshu_list);
        RelativeLayout rela_djxs = (RelativeLayout) view.findViewById(R.id.rela_djxs);
        dialog1.getWindow().setContentView(view);
        rela_djxs.setOnClickListener(v -> dialog1.dismiss());
        loadData();
        canshu_list.setOnItemClickListener((parent, view1, position, id) -> {
            mPublishType.setText(bean.getData().get(position).getName());
            tradeType = bean.getData().get(position).getShopTypeId();
            dialog1.dismiss();
        });
    }

    /**
     * 生活服务  获取商铺类型
     */
    BaseSHfw bean;
    private void loadData() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_SHFW, "", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            bean = Json.toObject(json, BaseSHfw.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            list = bean.getData();
            SHFBDialogAdapters adapter = new SHFBDialogAdapters(this, list);
            canshu_list.setAdapter(adapter);
            return false;
        }));
    }

}
