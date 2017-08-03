package com.cqxb.yecall.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.cqxb.until.AsyncHttpClientManager;
import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.YeCallBean;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class YeCallListPasswordAdapter extends BaseAdapter {

    static Handler handler = new Handler();


    private int getDayHour() {// 获取时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        String str = format.format(date);

        return Integer.parseInt(str);
    }

    private Context context;

    public Context getContext() {
        return context;
    }

    public YeCallListPasswordAdapter(Context context, List<YeCallBean> yeCallList) {
        super();
        this.context = context;
        this.yeCallList = yeCallList;
    }

    private List<YeCallBean> yeCallList;

//	public List<YeCallBean> getYeCallList() {
//		return yeCallList;
//	}
//
//	public void addData(YeCallBean yeCallBean) {
//		yeCallList.add(yeCallBean);
//		notifyDataSetChanged();
//	}

    @Override
    public int getCount() {
        return yeCallList.size();
    }

    @Override
    public YeCallBean getItem(int position) {
        return yeCallList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.yecall_list_password, null);

            holder.getPasswordButton = (Button) convertView.findViewById(R.id.btn_expand_is_show);
            holder.llExpand = (LinearLayout) convertView.findViewById(R.id.yecall_ll_expand);
            holder.btnCopy = (ImageView) convertView.findViewById(R.id.btn_copy_string);
            holder.password = (TextView) convertView.findViewById(R.id.everyday_password);

            holder.circle = (CircleProgress) convertView.findViewById(R.id.circle_password_progress);

            holder.community = (TextView) convertView.findViewById(R.id.yecall_community);
            holder.equipment = (TextView) convertView.findViewById(R.id.yecall_equipment);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.community.setText(yeCallList.get(position).getCommunity());
        holder.equipment.setText(yeCallList.get(position).getEquipment());

        holder.circle.setProgress(getDayHour());// 用当前时间的小时作为密码有效期

        // 单击获取密码显示折叠内容
        holder.getPasswordButton.setTag(holder);
        holder.getPasswordButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewHolder vh = (ViewHolder) v.getTag();
                LinearLayout ll = vh.llExpand;
                final TextView tv = vh.password;

                String user = SettingInfo.getParams(PreferenceBean.USERACCOUNT, "");

                if (ll.getVisibility() != View.VISIBLE) {
                    ll.setVisibility(View.VISIBLE);

                    ACache aCache = ACache.get(context);

                    if (aCache.getAsString("@password" + user + position) != null) {

                        vh.beginTranstion();

                        initPassword(position, tv, vh);// 刷密码
                    } else if (aCache.getAsString("@password" + user + position).equals("")) {

                        vh.beginTranstion();
                        initPassword(position, tv, vh);// 刷密码
                    } else if (aCache.getAsString("@password" + user + position) != null) {
//						Log.w("bug", "password = " + aCache.getAsString("@password" + user + position));
                        tv.setText(aCache.getAsString("@password" + user + position));
                    }

                } else {
                    ll.setVisibility(View.GONE);

                    vh.StopTranstion();

                }

            }
        });

        // 复制内容
        holder.btnCopy.setTag(holder.password);
        holder.btnCopy.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView password = (TextView) v.getTag();

                // API最低要求11
                ClipboardManager clipboard = (ClipboardManager) getContext()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", "*"
                        + password.getText().toString() + "#");
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context, "密码已复制到粘贴板上,请直接粘贴发送给您的访客",
                        Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    final static class ViewHolder {
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
                handler.postDelayed(r, 200);
            }
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
    private void initPassword(final int position, final TextView password, final ViewHolder vh) {

        String user = SettingInfo.getParams(PreferenceBean.USERACCOUNT, "");
        String equipmentId = yeCallList.get(position).getDoorId();

//        final String url = "http://115.28.2.168:80/door/api/getkey";

        RequestParams requestParams = new RequestParams();
        requestParams.put("phone", user);
        requestParams.put("door", equipmentId);

        AsyncHttpClientManager.post(NetParam.USR_DOOR_INFO, requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                if (statusCode == 200) {
                    try {
                        vh.StopTranstion();

                        password.setText(response.getString("data"));
                        ACache aCache = ACache.get(context);
                        aCache.put("@password" + SettingInfo.getParams(PreferenceBean.USERACCOUNT, "") + position,
                                response.getString("data"));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
                vh.StopTranstion();
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }
}
