package com.wytube.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;


@KActivity(R.layout.activity_visitor_info)
public class VisitorInfoActivity extends BaseActivity {
//    private List<VisitorListBean.DataBean> list;

    @KBind(R.id.visitor_info_list)
    private ListView mVisitorInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
//        loadData();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

//    private void loadData() {
//        Utils.showLoad(this);
//        Client.sendPost(NetParmet.VISITOR_INFO, "", new Handler(msg -> {
//            Utils.exitLoad();
//            String json = msg.getData().getString("post");
//            VisitorListBean bean = Json.toObject(json, VisitorListBean.class);
//            if (bean == null) {
//                Utils.showNetErrorDialog(VisitorInfoActivity.this);
//                return false;
//            }
//            if (!bean.isSuccess()) {
//                Utils.showOkDialog(VisitorInfoActivity.this, bean.getMessage());
//                return false;
//            }
//            list = bean.getData();
//            VisitorAdapters VisitorAdapters = new VisitorAdapters(this, list);
//            mVisitorInfoList.setAdapter(VisitorAdapters);
//            return false;
//        }));
//    }
}
