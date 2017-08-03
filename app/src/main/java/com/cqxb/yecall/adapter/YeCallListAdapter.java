package com.cqxb.yecall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.action.param.NetParam;
import com.cqxb.until.AsyncHttpClientManager;
import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.YeCallBean;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class YeCallListAdapter extends BaseAdapter {

    private Context context;
    private int successKey = 0;// 开门是否成功的标识

    public Context getContext() {
        return context;
    }

    public YeCallListAdapter(Context context, List<YeCallBean> yeCallList) {
        super();
        this.context = context;
        this.yeCallList = yeCallList;
    }

    private List<YeCallBean> yeCallList;

    public void addData(YeCallBean yeCallBean) {
        yeCallList.add(yeCallBean);
        notifyDataSetChanged();
    }

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
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder vh;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.yecall_list_view, null);

            vh = new ViewHolder((TextView) convertView.findViewById(R.id.yecall_community)
                    , (TextView) convertView.findViewById(R.id.yecall_equipment)
                    , (ImageView) convertView.findViewById(R.id.yecall_click_btn));

            convertView.setTag(vh);
        }

        vh = (ViewHolder) convertView.getTag();

        final YeCallBean yeCallBean = getItem(position);

        vh.getCommunity().setText(yeCallBean.getCommunity());
        vh.getEquipment().setText(yeCallBean.getEquipment());


        vh.getClickBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successKey = 0;
                JSONObject obj;
                try {
                    String doorId = yeCallBean.getDoorId();
//					String openDoorUrl = yeCallBean.getDestinationIp();
//                  String url = "http://"+ openDoorUrl+ "/door/api/opendoor?phone="+ user +"&doorId="+ doorId;
//                    String url = "http://115.28.2.168:80/door/api/open";

                    RequestParams requestParams = new RequestParams();
                    requestParams.put("phone", SettingInfo.getParams(PreferenceBean.USERACCOUNT, ""));
                    requestParams.put("door", doorId);

                    for (int i = 0; i < 3; i++) {
                        AsyncHttpClientManager.post(NetParam.USR_OPEN, requestParams, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                successKey++;
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }

                            @Override
                            public void onFinish() {
                                if (successKey > 0) {
                                    Toast.makeText(context, "开门成功", Toast.LENGTH_SHORT).show();
                                    successKey = -10;
                                }
                                super.onFinish();
                            }
                        });
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        return convertView;
    }

    public class ViewHolder {

        private TextView community;
        private TextView equipment;
        private ImageView clickBtn;

        public ViewHolder(TextView community, TextView equipment,
                          ImageView clickBtn) {
            super();
            this.community = community;
            this.equipment = equipment;
            this.clickBtn = clickBtn;
        }

        public TextView getCommunity() {
            return community;
        }

        public TextView getEquipment() {
            return equipment;
        }

        public ImageView getClickBtn() {
            return clickBtn;
        }

    }

}
