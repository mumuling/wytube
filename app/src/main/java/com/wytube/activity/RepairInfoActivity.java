package com.wytube.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseJzxq;
import com.wytube.beans.RepairBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;


@KActivity(R.layout.activity_repair_infos)
public class RepairInfoActivity extends BaseActivity {

    @KBind(R.id.image_dsl)
    private ImageView mImageDsl;
    @KBind(R.id.image_pdz)
    private ImageView mImagePdz;
    @KBind(R.id.image_ywc)
    private ImageView mImageYwc;
    @KBind(R.id.image_dsl1)
    private ImageView mImageDsl1;
    @KBind(R.id.image_pdz1)
    private ImageView mImagePdz1;
    @KBind(R.id.image_ywc1)
    private ImageView mImageYwc1;
    @KBind(R.id.text_dsl)
    private TextView mTextDsl;
    @KBind(R.id.text_pdz)
    private TextView mTextPdz;
    @KBind(R.id.text_ywc)
    private TextView mTextYwc;
    @KBind(R.id.text_wx_name)
    private TextView mTextWxName;
    @KBind(R.id.text_bx_name)
    private TextView mTextBxName;
    @KBind(R.id.repair_name)
    private TextView mrepair_name;
    @KBind(R.id.content_text)
    private TextView mcontent_text;
    @KBind(R.id.text_an)
    private TextView mtext_an;
    @KBind(R.id.text_pj_content)
    private TextView mtext_pj_content;
    @KBind(R.id.text_district)
    private TextView mtext_district;
    @KBind(R.id.text_money)
    private TextView mtext_money;
    @KBind(R.id.image_an)
    private ImageView mimage_an;
    @KBind(R.id.lin_yc)
    private LinearLayout mlin_yc;
    @KBind(R.id.img_list)
    private LinearLayout mImgList;
    @KBind(R.id.rela_pdz)
    private RelativeLayout mrela_pdz;
    @KBind(R.id.rlywc_yc)
    private RelativeLayout mrlywc_yc;
    @KBind(R.id.rela_fgx)
    private RelativeLayout mrela_fgx;
    @KBind(R.id.rela_fgxs)
    private RelativeLayout mrela_fgxs;
    @KBind(R.id.linearLayout3)
    private LinearLayout mlinearLayout3;


    private RepairBean.DataBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        if (AppValue.repairInfoBean != null) {
            bean = AppValue.repairInfoBean;
        }
        initView();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化视图
     */
    @SuppressLint("SetTextI18n")
    private void initView() {
        switch (bean.getStateId()) {
            case 0:
                mTextDsl.setTextColor(getResources().getColor(R.color.colorAccent));
                mimage_an.setImageResource(R.drawable.tcjf_zpry);
                mtext_an.setText("指派人员");
                mImageDsl.setVisibility(View.GONE);
                mImageDsl1.setVisibility(View.VISIBLE);
                mImagePdz1.setVisibility(View.GONE);
                mrela_pdz.setVisibility(View.GONE);
                mrlywc_yc.setVisibility(View.GONE);
                mlin_yc.setVisibility(View.GONE);
                mrela_fgx.setVisibility(View.GONE);
                mrela_fgxs.setVisibility(View.GONE);
                break;
            case 1:
                mTextPdz.setTextColor(getResources().getColor(R.color.colorAccent));
                mImagePdz.setVisibility(View.GONE);
                mImagePdz1.setVisibility(View.VISIBLE);
                mrlywc_yc.setVisibility(View.GONE);
                mlin_yc.setVisibility(View.GONE);
                mrela_fgxs.setVisibility(View.GONE);
                break;
            case 2:
                mTextYwc.setTextColor(getResources().getColor(R.color.colorAccent));
                mImageYwc.setVisibility(View.GONE);
                mrela_pdz.setVisibility(View.VISIBLE);
                mImageYwc1.setVisibility(View.VISIBLE);
                mlinearLayout3.setVisibility(View.GONE);
                break;
            default:
        }
        loadxq(AppValue.repairInfoBean.getRepairWorkId());
        mrepair_name.setText(bean.getTypeName());/*标签*/
        mcontent_text.setText(bean.getRepairContent());/*内容*/
        mTextWxName.setText(bean.getRegUserName()+"("+bean.getMobileNo()+")");
        mtext_district.setText(bean.getCellName()+bean.getBuildingName()+bean.getUnitName()+bean.getNumberName());
        mtext_money.setText(bean.getCost()+"元");
//        mRepairTypeText.setText(bean.getTypeName());
//        mRepairMoney.setText(bean.getCost() + "元");
//        mStateText.setText(bean.getStateName());
//        mUpdateTime.setText(bean.getStarttime());
        addImage();
    }

    /*拨打报修电话*/
    @KListener(R.id.call_but_bx)
    private void call_but_bxOnClick() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + bean.getMobileNo()));
        startActivity(intent);
    }

    /*拨打维修电话*/
    @KListener(R.id.call_but_wx)
    private void call_but_wxOnClick() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + beans.getRepairWork().getRepairmanPhone()));
        startActivity(intent);
    }

    /*删除*/
    @KListener(R.id.linear_delete)
    private void mLinearDeleteOnClick() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("确定删除?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", (dialog, which) -> {});
        builder.setNegativeButton("取消", (dialog, which) -> {});
        builder.create().show();
    }


    /*指派人员---完成维修*/
    @KListener(R.id.linear_modify)
    private void linear_modifyOnClick() {
        if (bean.getStateId()==0){
            Intent intent = new Intent(this,RepairZPActivity.class);
            intent.putExtra("repairWorkId",beans.getRepairWork().getRepairWorkId());
            startActivity(intent);
            finish();
        } else if (bean.getStateId()==1) {
            Intent intent = new Intent(this,RepairWCActivity.class);
            intent.putExtra("repairWorkId",beans.getRepairWork().getRepairWorkId());
            intent.putExtra("cost",beans.getRepairWork().getCost());
            startActivity(intent);
            finish();
        }
    }

    /**
     * 添加图片
     */
    private void addImage() {
        for (RepairBean.DataBean.ImgListBean imgListBean : bean.getImgList()) {
            createAndAddImage(imgListBean.getImgUrl());
        }
    }

    /**
     * 创建并添加图片
     *
     * @param imageUrl 图片的Url
     */
    private void createAndAddImage(String imageUrl) {
        final ImageView view = new ImageView(this);
        view.setOnClickListener(v -> {
            AppValue.tempImage = imageUrl;
            startActivity(new Intent(this, ImageActivity.class));
        });
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(128), dip2px(128));
        params.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        view.setLayoutParams(params);
        Utils.loadImage(view, imageUrl);
        mImgList.addView(view);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 投诉记录详情
     * suitWorkId 投诉建议ID
     */
    BaseJzxq beans;
    private void loadxq(String repairWorkId) {
        String keyValue = "repairWorkId="+repairWorkId;
        Client.sendPost(NetParmet.ADD_JZXQ_INFO, keyValue, new Handler(msg -> {
            String json = msg.getData().getString("post");
            beans = Json.toObject(json, BaseJzxq.class);
            if (beans.getRepairWork().getRepairmanName()!=null && !beans.getRepairWork().getRepairmanName().equals("")){
                mTextBxName.setText(beans.getRepairWork().getRepairmanName()+"("+beans.getRepairWork().getRepairmanPhone()+")");
            }else if(beans.getRepairWork().getCost()!=null && !beans.getRepairWork().getCost().equals("")){
                mtext_pj_content.setText(beans.getRepairWork().getCost()+"元");
            }else if(beans.getRepairWork().getUserRecontent()!=null && !beans.getRepairWork().getUserRecontent().equals("")){
                mtext_pj_content.setText(beans.getRepairWork().getUserRecontent().toString());
            }
            return false;
        }));
    }
}
