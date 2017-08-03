package com.cqxb.yecall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.android.action.NetAction;
import com.android.action.NetBase.OnResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.yecall.R;
import com.cqxb.yecall.adapter.TreeAdapter;
import com.cqxb.yecall.adapter.TreeNodeAdapter;
import com.cqxb.yecall.bean.EmployeeBean;
import com.cqxb.yecall.bean.TreeElementBean;
import com.cqxb.yecall.bean.TreeNodeBean;
import com.cqxb.yecall.bean.TreeOrgBean;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.cqxb.ui.OrgRefreshableView;
import com.cqxb.ui.OrgRefreshableView.PullToRefreshListener;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class OrganizeActivity extends Fragment {
	Button myButton;
	private ArrayList<TreeElementBean> mPdfOutlinesCount = new ArrayList<TreeElementBean>();
//	private OrganizeTreeViewAdapter treeViewAdapter = null;
	private ListView menu;
	private ListView son;
	private TreeNodeAdapter nodeAdapter;
	private TreeAdapter adapter;
	List<TreeOrgBean> parseArray =new ArrayList<TreeOrgBean>();
	List<EmployeeBean> employeeBeans=new ArrayList<EmployeeBean>();
	private String TAG="OrganizeActivity";
	private OrgRefreshableView refresh;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		initialData();
//		InputStream open=null;
//		try {
//			open = getActivity().getAssets().open("tree.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String string = getString(open);
//		Object object = JSON.parseObject(string).get("Objects");
//		parseArray=JSON.parseArray(object.toString(), TreeOrgBean.class);
		String org = SettingInfo.getParams(PreferenceBean.ORG, "");
//		Log.e(TAG, "   org.......:"+org);
		TreeOrgBean parseObject = JSON.parseObject(org, TreeOrgBean.class);
		parseArray.add(parseObject);
		if(parseArray!=null){
			if(parseArray.get(0)!=null)
				if(parseArray.get(0).getObjects().size()>0)
					parseArray.get(0).setParent(true);
		}
		View rootView = inflater.inflate(R.layout.activity_organize, container,
				false);// 关联布局文件
		refresh=(OrgRefreshableView)rootView.findViewById(R.id.refreshable_view);
		refresh.hideHead();
		refresh.setOnRefreshListener(new PullToRefreshListener() {
			
			@Override
			public void onRefresh() {
				handler.sendEmptyMessageDelayed(1, 0);
			}
		}, R.id.menu);
		
		menu = (ListView) rootView.findViewById(R.id.menu);
//		treeViewAdapter = new OrganizeTreeViewAdapter(getActivity(),
//				R.layout.tree_item, mPdfOutlinesCount);
		adapter= new TreeAdapter(parseArray, getActivity());
		menu.setAdapter(adapter);
		menu.setOnItemClickListener(new MenuClick());
		son = (ListView) rootView.findViewById(R.id.son);
		son.setOnItemClickListener(new PeopleInfo());
		return rootView;
	}


	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				new NetAction().setOrg(SettingInfo.getAccount(), new OnResponseListener() {
					
					@Override
					public void onResponse(String statusCode, CommReply reply) {
						if(CommReply.SUCCESS.equals(statusCode)){
//							Log.e("", "reply:"+reply.getBody());
							TreeOrgBean parseObject = JSON.parseObject(reply.getBody(), TreeOrgBean.class);
							parseArray.clear();
							employeeBeans.clear();
							parseArray.add(parseObject);
							if(parseArray!=null){
								if(parseArray.get(0)!=null)
									if(parseArray.get(0).getObjects().size()>0)
										parseArray.get(0).setParent(true);
							}
							adapter.updateListView(parseArray);
//							nodeAdapter.updateListView(employeeBeans);
							refresh.finishRefreshing();
						}else {
							T.show(getActivity(), "更新失败！", 0);
							refresh.finishRefreshing();
						}
						
					}
				}).exec();
				
				break;

			default:
				break;
			}
		}
		
	};
	
	private class MenuClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			String jsonString = JSON.toJSONString(parseArray.get(position));
			TreeOrgBean item =JSON.parseObject(jsonString, TreeOrgBean.class);
			if (!"group".equals(item.getType())  ) {
//				Toast.makeText(getActivity(), item.getName(), 0).show();
				return;
			} else {
//				Toast.makeText(getActivity(), item.getName(), 0).show();
			}
//			Toast.makeText(getApplicationContext(), item.isExpanded()?""+item.isExpanded():""+item.isExpanded(), 0).show();
			
			if (item.isExpanded()) {
				parseArray.get(position).setExpanded(false);
				
				TreeOrgBean ele =JSON.parseObject(jsonString, TreeOrgBean.class);
				//控制父项如果没有人存在则不清空当前显示的人
				for (int i = 0; i < ele.getObjects().size(); i++) {
					if("people".equals(ele.getObjects().get(i).getType())){
						employeeBeans.clear();
					}
				}
				ArrayList<TreeOrgBean> temp = new ArrayList<TreeOrgBean>();

				for (int i = position + 1; i < parseArray.size(); i++) {
					if (ele.getLevel() >= parseArray.get(i).getLevel()) {
						break;
					}
					temp.add(parseArray.get(i));
				}
				for (TreeOrgBean element : ele.getObjects()) {
					if("people".equals(element.getType())){
						String json= JSON.toJSONString(element.getObjects());
						List<TreeNodeBean> treeNode = JSON.parseArray(json, TreeNodeBean.class);
						EmployeeBean employeeBean=new EmployeeBean();
						employeeBean.setLevel(element.getLevel());
						employeeBean.setName(element.getName());
						employeeBean.setObjects(treeNode);
						employeeBean.setType(element.getType());
						employeeBean.setPosition(element.getPosition());
						employeeBeans.add(employeeBean);
						System.out.println("===name - type:"+element.getType()+"   "+element.getName()+"  "+employeeBeans.size());
					}
				}
				if(nodeAdapter==null){
					nodeAdapter=new TreeNodeAdapter(getActivity(), employeeBeans);
					son.setAdapter(nodeAdapter);
				} else {
					nodeAdapter.updateListView(employeeBeans);
				}
				
				parseArray.removeAll(temp);
				
				adapter.updateListView(parseArray);
			} else {
				TreeOrgBean obj =JSON.parseObject(jsonString, TreeOrgBean.class);
				//控制父项如果没有人存在则不清空当前显示的人
				for (int i = 0; i < obj.getObjects().size(); i++) {
					if("people".equals(obj.getObjects().get(i).getType())){
						employeeBeans.clear();
					}
				}
//				System.out.println("===name - type:"+obj.getType());
				obj.setExpanded(true);
				obj.setParent(true);
				int level = obj.getLevel();
				int nextLevel = level + 1;
				parseArray.remove(position);
				parseArray.add(position,obj);
				for (TreeOrgBean element : obj.getObjects()) {
					element.setParent(true);
					if("group".equals(element.getType())){
						element.setLevel(nextLevel);
						element.setExpanded(false);
						parseArray.add(position + 1, element);
					}else {
						System.out.println("===name:"+JSON.toJSONString(element.getObjects()));
						String json= JSON.toJSONString(element.getObjects());
						List<TreeNodeBean> treeNode = JSON.parseArray(json, TreeNodeBean.class);
						EmployeeBean employeeBean=new EmployeeBean();
						employeeBean.setLevel(element.getLevel());
						employeeBean.setName(element.getName());
						employeeBean.setObjects(treeNode);
						employeeBean.setType(element.getType());
						employeeBean.setPosition(element.getPosition());
						employeeBeans.add(employeeBean);
						element.setParent(false);
					}
				}
				if(nodeAdapter==null){
					nodeAdapter=new TreeNodeAdapter(getActivity(), employeeBeans);
					son.setAdapter(nodeAdapter);
				} else {
					nodeAdapter.updateListView(employeeBeans);
				}
				adapter.updateListView(parseArray);
			}
		}

	}
	
	private class PeopleInfo implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			EmployeeBean employeeBean = employeeBeans.get(arg2);
			if(employeeBean.getObjects()==null){
				T.show(getActivity(), "该用户还未激活", 0);
				return;
			}
//			Toast.makeText(getActivity(), employeeBean.getName()+":"+JSON.toJSONString(employeeBean.getObjects()), 0).show();
			startActivity(new Intent(getActivity(),OrgContactActivity.class).putExtra("orgInfo", employeeBean.getName()+"="+JSON.toJSONString(employeeBean.getObjects())));
			
		}
		
	}
	
	public static String getString(InputStream inputStream) {
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	public String getData(){
		String value="";
		return value;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
