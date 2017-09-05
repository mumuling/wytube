package com.wytube.adaper;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.action.param.NetParam;
import com.cqxb.ui.CircleProgress;
import com.cqxb.until.ACache;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.google.gson.reflect.TypeToken;
import com.wytube.beans.BaseOK;
import com.wytube.beans.modle.RemoMM;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.shared.ToastUtils;
import com.wytube.threads.GsonUtil;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.wytube.beans.modle.RemoMM.PERSONSmm;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/25.
 * 类 描 述:
 */

public class YeCallListPasswordAdapter extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<Map<String, Object>> list;
    static Handler handler = new Handler();
    List<RemoMM> remotelys;
    ACache mCache = null;

    private int getDayHour() {// 获取时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        String str = format.format(date);
        return Integer.parseInt(str);
    }

    public YeCallListPasswordAdapter(Context mContext, List<Map<String, Object>> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            mholder = new viewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.yecall_list_password, null);
            mholder.getPasswordButton = (Button) convertView.findViewById(R.id.btn_expand_is_show);
            mholder.llExpand = (LinearLayout) convertView.findViewById(R.id.yecall_ll_expand);
            mholder.btnCopy = (ImageView) convertView.findViewById(R.id.btn_copy_string);
            mholder.password = (TextView) convertView.findViewById(R.id.everyday_password);
            mholder.circle = (CircleProgress) convertView.findViewById(R.id.circle_password_progress);
            mholder.community = (TextView) convertView.findViewById(R.id.yecall_community);
            mholder.equipment = (TextView) convertView.findViewById(R.id.yecall_equipment);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }

        mholder.community.setText(list.get(position).get("name").toString());
        mholder.equipment.setText(list.get(position).get("content").toString());
        mholder.circle.setProgress(getDayHour());// 用当前时间的小时作为密码有效期
        // 单击获取密码显示折叠内容
        mholder.getPasswordButton.setTag(mholder);
        mholder.getPasswordButton.setOnClickListener(v -> {
            YeCallListPasswordAdapter.viewHolder vh = (YeCallListPasswordAdapter.viewHolder) v.getTag();
            LinearLayout ll = vh.llExpand;
            final TextView tv = vh.password;
            if (ll.getVisibility() != View.VISIBLE) {
                ll.setVisibility(View.VISIBLE);
                vh.beginTranstion();
                initPassword(position, tv, vh);// 刷密码
            } else {
                ll.setVisibility(View.GONE);
                vh.StopTranstion();
            }
        });

        mholder.btnCopy.setTag(mholder.password);
        mholder.btnCopy.setOnClickListener(v -> {
            TextView password = (TextView) v.getTag();
            // API最低要求11
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText("*"+ password.getText().toString() + "#");
            Toast.makeText(mContext,"密码已复制到粘贴板上,请直接粘贴发送给您的访客",Toast.LENGTH_LONG).show();
        });
        return convertView;
    }

    public class viewHolder {
        Button getPasswordButton;// 获取密码
        LinearLayout llExpand;// 折叠界面
        ImageView btnCopy;// 复制按钮
        TextView password;// 动态密码
        CircleProgress circle;
        TextView community;
        TextView equipment;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int randomMath = (int) (Math.random() * 9000 + 1000);
                // Log.w("ani", "数字：" + randomMath);
                password.setText(randomMath + "");
                // password.setText(randomMath);
                handler.postDelayed(r, 200);}
        };
        public void beginTranstion() {
            handler.post(r);
        }
        public void StopTranstion() {
            handler.removeCallbacks(r);
            // Log.w("ani", "结束了 安全");
        }
    }

    // 初始化密码
    private void initPassword(final int position, final TextView password, final YeCallListPasswordAdapter.viewHolder vh) {

        String user = SettingInfo.getParams(PreferenceBean.USERNAME, "");
        String equipmentId = list.get(position).get("doorId").toString();

        Client.sendPost(NetParam.USR_DOOR_INFO, "phone="+user +"&door="+equipmentId, new Handler(msg -> {
            String json = msg.getData().getString("post");
            BaseOK bean = Json.toObject(json, BaseOK.class);
            if (bean == null) {
                mCache = ACache.get(mContext);
                JSONArray results = mCache.getAsJSONArray(PERSONSmm);
                if (results!=null){
                    vh.StopTranstion();
                    /*缓存数据*/
                    Type mType = new TypeToken<List<RemoMM>>(){}.getType();
                    remotelys = GsonUtil.getGson().fromJson(results.toString(), mType);
                    password.setText(remotelys.get(position).remotelys);
                }else {
                    vh.StopTranstion();
                    ToastUtils.showToast(mContext,"网络异常!");
                }
                return false;
            }else {
                vh.StopTranstion();
            }
            if (bean.getCode()==200){
                vh.StopTranstion();
                /*缓存*/
                mCache = ACache.get(mContext);
                remotelys = new ArrayList<RemoMM>();
                for (int i = 0; i < list.size() ; i++) {
                    remotelys.add(new RemoMM(bean.getData()));
                    String personArray = GsonUtil.getGson().toJson(remotelys);
                    mCache.put(PERSONSmm, personArray, 60*60*24*14);
                }
                password.setText(bean.getData());
            }else {
                vh.StopTranstion();
            }
            return false;
        }));
    }
}
