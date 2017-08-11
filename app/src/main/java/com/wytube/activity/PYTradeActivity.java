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
import com.wytube.adaper.JYDialogAdapters;
import com.wytube.beans.BaseOK;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * PY交易首页
 */

@KActivity(R.layout.activity_pytrade)
public class PYTradeActivity extends Activity{

    @KBind(R.id.img_list)
    private LinearLayout mImgList;
    @KBind(R.id.shop_title)
    private EditText mShopTitle;
    @KBind(R.id.shop_goods)
    private EditText mShopGoods;
    @KBind(R.id.phone_num)
    private EditText mPhoneNum;
    @KBind(R.id.shop_text)
    private EditText mShopText;
    private List<String> imgsPath = new ArrayList<>();
    private String title, goods, phone, text;
    private int tradeType=-1;
    @KBind(R.id.publish_type)
    private TextView mPublishType;
    private Dialog dialog1;
    private LayoutInflater inflater;
    private ListView canshu_list;


    String[] dialogtitle = {"二手交易","房屋租赁","房屋出租"};
    int [] types = {1,2,3};
    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list= new ArrayList<>();
        for (int i = 0; i < dialogtitle.length; i++) {
            Map<String, Object> map= new HashMap<>();
            map.put("title", dialogtitle[i]);
            map.put("type", types[i]);
            list.add(map);
        }
        return list;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        initView();
        findViewById(R.id.back_but).setOnClickListener(v -> finish());
        findViewById(R.id.title_text).setOnClickListener(v -> finish());
    }

    /**
     * 初始化视图
     */
    private void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish == 1) {
            postData();
            AppValue.fish = -1;
        }
    }

    @KListener(R.id.add_shop_img)
    private void add_shop_imgOnClick() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 10);
    }


    /*请选择*/
    @KListener(R.id.select_type)
    private void select_typeOnClick() {
        dalogcanshu();
    }

    @KListener(R.id.publish_but)
    private void publish_butOnClick() {
        title = mShopTitle.getText().toString();
        phone = mPhoneNum.getText().toString();
        goods = mShopGoods.getText().toString();
        text = mShopText.getText().toString();
        if (title.length() <= 0) {
            Utils.showOkDialog(this, "请填写商品标题!");
            return;
        }
        if (phone.length() <= 0) {
            Utils.showOkDialog(this, "请填写联系电话!");
            return;
        }
        if (goods.length() <= 0) {
            Utils.showOkDialog(this, "请填写商品售价!");
            return;
        }
        if (text.length() <= 0) {
            Utils.showOkDialog(this, "请填写商品描述!");
            return;
        }
        if (tradeType == -1) {
            Utils.showOkDialog(this, "请选择类型!");
            return;
        }
        if (imgsPath.size()<= 0) {
            Utils.showOkDialog(this, "请选择图片!");
            return;
        }
        postData();
    }

    /**
     * 提交数据
     */
    private void postData() {
        Utils.showLoad(this);
        String keyValue = "title=" + title +
                "&name=" + AppValue.sipName +
                "&type=" + tradeType +
                "&phone=" + phone +
                "&price=" + goods +
                "&detail=" + text;
        Client.sendFile(NetParmet.USR_JY_FB, keyValue, imgsPath, new Handler(msg -> {
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
                mShopText.setText("");
                Toast.makeText(this, "交易信息发布成功!", Toast.LENGTH_SHORT).show();
                AppValue.fish = 1;
                this.finish();
            }

            return false;
        }));
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
    List<Map<String, Object>> list;
    public void dalogcanshu() {
        dialog1 = dalog();
        View view = inflater.inflate(R.layout.activity_news_dilogtxts, null);
        canshu_list = (ListView) view.findViewById(R.id.canshu_list);
        RelativeLayout rela_djxs = (RelativeLayout) view.findViewById(R.id.rela_djxs);
        dialog1.getWindow().setContentView(view);
        rela_djxs.setOnClickListener(v -> dialog1.dismiss());
        list=getData();
        JYDialogAdapters adapter = new JYDialogAdapters(this, list);
        canshu_list.setAdapter(adapter);
        canshu_list.setOnItemClickListener((parent, view1, position, id) -> {
            mPublishType.setText(list.get(position).get("title").toString());
            tradeType = (int) list.get(position).get("type");
            dialog1.dismiss();
        });
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


}

