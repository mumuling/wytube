package com.cqxb.yecall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.until.Config;
import com.cqxb.yecall.R;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.bean.CallLogBean;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.t9search.util.ViewUtil;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.NetUtil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wytube.activity.OrderActivity;

import org.linphone.DialerFragment;
import org.linphone.InCallActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneManager.AddressType;
import org.linphone.ui.AddressText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialingAdapter extends BaseAdapter {

    private int currentItem = -1;

    private static Handler handler = new Handler();

    private Context mContext;
    private List<Contacts> cList;
    private ImageLoader imageLoader;
    private String[] imageUrls;
    private DisplayImageOptions options;
    private List<Runnable> rList = new ArrayList<Runnable>();//存线程
    private List<MediaPlayer> medias = new ArrayList<MediaPlayer>();//存媒体
    private List<ViewHolder> viewHolders = new ArrayList<ViewHolder>();
    private DBHelper dbHelper;

    public DialingAdapter(Context context, List<Contacts> list) {
        mContext = context;
        cList = list;
    }

    @Override
    public int getCount() {
        return cList.size();
    }

    @Override
    public Object getItem(int position) {
        return cList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * listView优化
     */
    final static class ViewHolder {
        TextView callNumber;// 姓名
        TextView cpr_number;// 电话
        TextView callContent;// 时间
        CheckBox cpr_contactDetail;// 展开按钮
        ImageView callType;// 来电图标状态

        LinearLayout llExpand;// 展开项
        LinearLayout llAudio;// 录音项
        LinearLayout ll_screenshots;// 截图项
        LinearLayout ll_btn;//扩展图片

        ImageButton button;// 录音播放按钮
        TextView allTime;// 录音总长度
        TextView nowPlayTime;// 录音当前进度
        SeekBar processeekBar2;// 录音进度条

        Gallery gallery;// 画廊

        RelativeLayout rlFreeCall;//免费拨打

        LinearLayout llFreeCall;//免费拨打
        LinearLayout llCalling;//直接拨打
        LinearLayout llBackCalling;//回拨

        HorizontalScrollView scrollView;
        LinearLayout ll_scroll;//可滑动的LinearLayout
        LinearLayout ll_del_btn;//删除按钮

        Boolean isRun = false; // 线程运行标识

        MediaPlayer mediaPlayer = new MediaPlayer();

        Runnable r = new Runnable() {

            @Override
            public void run() {
                int CurrentPosition = mediaPlayer.getCurrentPosition();
                nowPlayTime.setText(ShowTime(CurrentPosition));
                int mMax = mediaPlayer.getDuration();
                processeekBar2.setMax(mMax);
                processeekBar2.setProgress(CurrentPosition);
                handler.postDelayed(r, 100);
                isRun = true;
            }
        };

        public void StartbarUpdate() {// 更新进度条
            handler.post(r);
        }

        public void ReSetBar() {// 移除消息通知
            if (isRun) {
                isRun = false;
                handler.removeCallbacks(r);
            }
        }
    }

    // 滑动可显示删除按钮，用于删除数据
    private void scrollFillScreenWidth(LinearLayout scrollLayout) {
        //获取屏幕宽度
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) scrollLayout.getLayoutParams();

        lp.width = width;

        scrollLayout.setLayoutParams(lp);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.call_phone_recored, null);
            holder.callNumber = (TextView) convertView.findViewById(R.id.cpr_callNumber);
            holder.cpr_number = (TextView) convertView.findViewById(R.id.cpr_number);
            holder.callContent = (TextView) convertView.findViewById(R.id.cpr_callContext);
            holder.cpr_contactDetail = (CheckBox) convertView.findViewById(R.id.cpr_contactDetail);

            holder.callType = (ImageView) convertView.findViewById(R.id.cpr_callType);

            holder.llExpand = (LinearLayout) convertView.findViewById(R.id.layout_show_expand);
            holder.llAudio = (LinearLayout) convertView.findViewById(R.id.ll_audio_play);
            holder.button = (ImageButton) convertView.findViewById(R.id.ib_historydetail_controlPlay);

            holder.allTime = (TextView) convertView.findViewById(R.id.tv_historydetail_playDuration);
            holder.nowPlayTime = (TextView) convertView.findViewById(R.id.tv_historydetail_playTime);

            holder.processeekBar2 = (SeekBar) convertView.findViewById(R.id.sp_historydetail_playProgress);

            holder.gallery = (Gallery) convertView.findViewById(R.id.g_historydetail_gallery);
            holder.ll_screenshots = (LinearLayout) convertView.findViewById(R.id.ll_screenshots);

            holder.rlFreeCall = (RelativeLayout) convertView.findViewById(R.id.history_contact_free_call);

            holder.llFreeCall = (LinearLayout) convertView.findViewById(R.id.freeCall);
            holder.llCalling = (LinearLayout) convertView.findViewById(R.id.calling);
            holder.llBackCalling = (LinearLayout) convertView.findViewById(R.id.backCall);

            holder.ll_btn = (LinearLayout) convertView.findViewById(R.id.ll_cpr_contactDetail);
            holder.ll_scroll = (LinearLayout) convertView.findViewById(R.id.history_ll_scroll);
            holder.ll_del_btn = (LinearLayout) convertView.findViewById(R.id.history_delete_btn);

            holder.scrollView = (HorizontalScrollView) convertView.findViewById(R.id.history_scrollView);

            rList.add(holder.r);
            medias.add(holder.mediaPlayer);
            viewHolders.add(holder);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Contacts contactBean = cList.get(position);
        final String recordFile = BaseUntil.stringNoNull(contactBean.getRecordFile()).trim();
        final String photoFile = BaseUntil.stringNoNull(contactBean.getPhotoFile()).trim();
        final String beginTime = BaseUntil.stringNoNull(contactBean.getStamp()).trim();


        // 额外控件的可见
        if (currentItem == position) {
            holder.cpr_contactDetail.setChecked(true);
            holder.llExpand.setVisibility(View.VISIBLE);

            if (recordFile.equals("")) {// 控制展开项录音是否显示
                holder.llAudio.setVisibility(View.GONE);
            } else {
                File file = new File(recordFile);
                if (!file.exists()) {
                    holder.llAudio.setVisibility(View.GONE);
                }
            }

            if (!photoFile.equals("")) {// 判断是否通话记录中有截图信息
                imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));

                imageUrls = photoFile.split(",");
                options = new DisplayImageOptions.Builder()
                        .showStubImage(R.drawable.ic_stub)
                        .showImageForEmptyUri(R.drawable.ic_empty)
                        .showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
                        .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565)
                        .build();

                // 自API 16之后就被抛弃了
                // Gallery gallery = (Gallery)
                holder.gallery.setAdapter(new ImageGalleryAdapter());
                holder.gallery.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
//                        startImagePagerActivity(position);

                        OrderActivity.instance.showImg(imageUrls[position]);
                        Config.imgShow = true;
                    }
                });
            } else {
                holder.ll_screenshots.setVisibility(View.GONE);
            }

        } else {
            holder.llExpand.setVisibility(View.GONE);
            holder.cpr_contactDetail.setChecked(false);
        }


        // 编辑按钮监听，展开额外控件
        holder.ll_btn.setTag(position);
        holder.ll_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                if (tag == currentItem) {//再次点击
                    currentItem = -1;//给currentItem 一个无效值
                } else {
                    currentItem = tag;
                }
                //通知更新数据重新加载
                stopMediaplayer();//清理线程
                notifyDataSetChanged();

            }
        });

        // 录音播放
        holder.button.setOnClickListener(new ButtonListener(holder, recordFile));


        //listView左滑效果
        scrollFillScreenWidth(holder.ll_scroll);

        holder.ll_del_btn.setTag(holder.scrollView);
        holder.ll_del_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                HorizontalScrollView scrollView = (HorizontalScrollView) v.getTag();
                cList.remove(position);
                scrollView.fullScroll(ScrollView.FOCUS_UP);
                notifyDataSetChanged();

                dbHelper = new DBHelper(mContext);
                dbHelper.open();

                boolean deleteData = dbHelper.deleteData(CallLogBean.TABLE, CallLogBean.STARTCALL + "=?", new String[]{beginTime});
                dbHelper.close();
                if (!recordFile.equals("")) {
                    File file = new File(recordFile);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                if (!photoFile.equals("")) {
                    for (String tempPhoto : photoFile.split(",")) {
                        File file = new File(tempPhoto);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
                ContactBase cb = new ContactBase(mContext);
                List<Contacts> allcontact = cb.getPhoneCallLists();
                YETApplication.getinstant().setThjl(allcontact);
                DialerFragment.instance().refreshList(allcontact);

            }
        });


        // 来电号码
        if (TextUtils.isEmpty(contactBean.getContactName())) {
            holder.callNumber.setText(contactBean.getNumber());
        } else {
            holder.callNumber.setText(contactBean.getContactName());
        }

        // 这一部分用于判断未接、呼入、呼出状态
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String actCallNumber = BaseUntil.stringNoNull(contactBean.getNumber()).trim();
        String callTime = BaseUntil.stringNoNull(contactBean.getStamp()).trim();
        if (!callTime.equals("")) {
            callTime = sdf.format(new Date(Long.parseLong(callTime)));
            callTime = BaseUntil.formatDateTime(callTime);
        }
        if (actCallNumber.indexOf("sip:") != -1) {
            actCallNumber = actCallNumber.replaceFirst("sip:", "");
            if (actCallNumber.indexOf("@") != -1) {
                actCallNumber = actCallNumber.substring(0,
                        actCallNumber.indexOf("@"));
            }
        }

        holder.cpr_number.setText(actCallNumber.trim());
        if (contactBean.getType() == CallLog.Calls.INCOMING_TYPE) {
            // "呼入";
            holder.callType
                    .setBackgroundResource(R.drawable.icon_incoming_call);
            holder.callContent.setText("  " + callTime);
        } else if (contactBean.getType() == CallLog.Calls.OUTGOING_TYPE) {
            // "呼出";
            holder.callType
                    .setBackgroundResource(R.drawable.icon_outgoing_call);
            holder.callContent.setText("  " + callTime);
        } else if (contactBean.getType() == CallLog.Calls.MISSED_TYPE) {
            // "未接";
            holder.callType.setBackgroundResource(R.drawable.icon_missed_call);
            holder.callContent.setText("  " + callTime);
        } else if (contactBean.getType() == 4) {
            // "其他" 联系人
            holder.cpr_number.setVisibility(View.GONE);
            holder.callNumber.setText("" + contactBean.getContactName());
            holder.callType.setVisibility(View.GONE);
            holder.callContent.setText("" + contactBean.getNumber());
        }

        // show the first alphabet of name
        // show name and phone number
        if (contactBean.getSearchByType() != null) {
            switch (contactBean.getSearchByType()) {
                case SearchByNull:
                    ViewUtil.showTextNormal(holder.callNumber,
                            contactBean.getName());
                    if (false == contactBean.isBelongMultipleContactsPhone()) {
                        ViewUtil.showTextNormal(holder.callContent,
                                contactBean.getPhoneNumber());
                    } else {
                        if (true == contactBean.isFirstMultipleContacts()) {
                            if (true == contactBean.getNextContacts()
                                    .isHideMultipleContacts()) {
                                ViewUtil.showTextNormal(
                                        holder.callContent,
                                        contactBean.getPhoneNumber()
                                                + mContext.getString(
                                                        R.string.phone_number_count,
                                                        Contacts.getMultipleNumbersContactsCount(contactBean) + 1));
                            } else {
                                ViewUtil.showTextNormal(
                                        holder.callContent,
                                        contactBean.getPhoneNumber()
                                                + "("
                                                + mContext.getString(R.string.click_to_hide)
                                                + ")");
                            }
                        } else {
                            if (false == contactBean.isHideMultipleContacts()) {
                            } else {
                            }
                            ViewUtil.showTextNormal(holder.callContent,
                                    contactBean.getPhoneNumber());
                        }
                    }
                    break;
                case SearchByPhoneNumber:
                    ViewUtil.showTextNormal(holder.callNumber, contactBean.getName());
                    ViewUtil.showTextHighlight(holder.callContent, contactBean.getPhoneNumber(), contactBean.getMatchKeywords().toString());
                    break;
                case SearchByName:
                    ViewUtil.showTextHighlight(holder.callNumber, contactBean.getName(), contactBean.getMatchKeywords().toString());
                    ViewUtil.showTextNormal(holder.callContent, contactBean.getPhoneNumber());
                    break;
                default:
                    break;
            }
        }

        //直接拨打
        holder.llCalling.setTag(holder);
        holder.llCalling.setOnClickListener(new OnClickListener() {//直拨

            @Override
            public void onClick(View v) {
                ViewHolder holder = (ViewHolder) v.getTag();

                int networkState = NetUtil.getNetworkState(mContext);
                if (networkState == 0) {// 无连接
                    T.show(mContext, "请检查网络连接！", 0);
                    return;
                } else {
                    // 处理号码前面加0
                    SettingInfo.setParams(PreferenceBean.PHONEADDZERO, "addZero");
                    calling(holder.callNumber.getText().toString(), holder.cpr_number.getText().toString());
                }
            }
        });

        //回拨
        holder.llBackCalling.setTag(holder);
        holder.llBackCalling.setOnClickListener(new OnClickListener() {//回拨

            @Override
            public void onClick(View v) {
                ViewHolder holder = (ViewHolder) v.getTag();

                SettingInfo.setParams(PreferenceBean.PHONEADDZERO, "addZero");
                callBack(holder.callNumber.getText().toString(), holder.cpr_number.getText().toString());
            }
        });

        //免费拨打
        holder.rlFreeCall.setTag(holder);
        holder.rlFreeCall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                ViewHolder holder = (ViewHolder) v.getTag();

                freeCall(holder.callNumber.getText().toString(), holder.cpr_number.getText().toString());
            }
        });


        //展开项的免费拨打
        holder.llFreeCall.setTag(holder);
        holder.llFreeCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewHolder holder = (ViewHolder) v.getTag();

                freeCall(holder.callNumber.getText().toString(), holder.cpr_number.getText().toString());
            }
        });

        return convertView;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<Contacts> list) {
        this.cList = list;

        stopMediaplayer();

        notifyDataSetChanged();
    }

    /**
     * 播放按钮监听
     *
     * @author Home
     */
    class ButtonListener implements OnClickListener {
        private String recordFile;
        private boolean isPrepared = false;
        private ViewHolder holder;

        public ButtonListener(ViewHolder holder, String recordFile) {
            this.holder = holder;
            this.recordFile = recordFile;
        }

        @Override
        public void onClick(View v) {
            if (holder.mediaPlayer.isPlaying()) {
                holder.button.setBackgroundResource(R.drawable.playaudio);
                holder.mediaPlayer.pause();
            } else {
                try {
                    if (!isPrepared) {
                        holder.button.setBackgroundResource(R.drawable.playaudio_over);
                        holder.mediaPlayer.reset();
                        holder.mediaPlayer.setDataSource(recordFile);
                        holder.mediaPlayer.prepare();
                        holder.mediaPlayer.start();
                        // --------调用进度条
                        holder.ReSetBar();
                        holder.StartbarUpdate();
                        // --------调用进度条
                        int Alltime = holder.mediaPlayer.getDuration();
                        holder.allTime.setText(ShowTime(Alltime));
                        isPrepared = true;
                    } else {
                        holder.button.setBackgroundResource(R.drawable.playaudio_over);
                        holder.mediaPlayer.start();
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 时间显示函数,我们获得音乐信息的是以毫秒为单位的，把转换成我们熟悉的00:00格式
    public static String ShowTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

//    private void startImagePagerActivity(int position) {
//        Intent intent = new Intent(mContext, ImageDetailActivity.class);
//        intent.putExtra("imgUrl", imageUrls[position]);
//        mContext.startActivity(intent);
//    }

    class ImageGalleryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = (ImageView) convertView;
            if (imageView == null) {
                imageView = (ImageView) LayoutInflater.from(mContext).inflate(
                        R.layout.item_gallery_image, parent, false);
            }
            Bitmap loacalBitmap = BaseUntil
                    .getLoacalBitmap(imageUrls[position]);
            imageView.setImageBitmap(loacalBitmap);
            return imageView;
        }
    }

    //安全机制，停止媒体移除消息派发
    public void stopMediaplayer() {
        if (rList.size() > 0) {
            for (int i = 0; i < rList.size(); i++) {

                if (medias.get(i) != null && medias.get(i).isPlaying()) {
                    medias.get(i).stop();
                }

                handler.removeCallbacks(rList.get(i));

                viewHolders.get(i).button.setBackgroundResource(R.drawable.playaudio);
                viewHolders.get(i).processeekBar2.setProgress(0);
            }
        }
    }

    // 免费拨打
    private void freeCall(String name, String number) {

        if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
            DialerFragment.instance().justLogin(mContext);
        } else {
            SettingInfo.setParams(PreferenceBean.CALLSTATUS, "拨号");
            SettingInfo.setParams(PreferenceBean.CALLNAME, name);
            SettingInfo.setParams(PreferenceBean.CALLPHONE, number);
            if (number.length() <= 11) {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
            } else {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(mContext,
                            InCallActivity.class);
                    intent.putExtra("VideoEnabled", false);
                    mContext.startActivity(intent);
                }
            }).start();

            AddressType address = new AddressText(mContext, null);
            address.setDisplayedName(name);
            address.setText(number);
            LinphoneManager.getInstance().newOutgoingCall(address);
        }
    }


    //直接拨打
    private void calling(String name, String number) {
        if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
            DialerFragment.instance().justLogin(mContext);
        } else {
            SettingInfo.setParams(PreferenceBean.CALLSTATUS, "拨号");
            SettingInfo.setParams(PreferenceBean.CALLNAME, name);
            String prefix = "0";
            if (number.indexOf("00") == 0 || number.indexOf("013") == 0
                    || number.indexOf("014") == 0 || number.indexOf("015") == 0
                    || number.indexOf("016") == 0 || number.indexOf("017") == 0
                    || number.indexOf("018") == 0) {
                prefix = "";
            }
            SettingInfo.setParams(PreferenceBean.CALLPHONE, prefix + number);

            if (number.length() <= 11) {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
            } else {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
            }
            // LinphoneActivity.instance().startIncallActivity(null);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(mContext,
                            InCallActivity.class);
                    intent.putExtra("VideoEnabled", false);
                    mContext.startActivity(intent);
                }
            }).start();
            AddressType address = new AddressText(mContext, null);
            address.setDisplayedName(name);
            address.setText(prefix + number);
            LinphoneManager.getInstance().newOutgoingCall(address);
        }
    }

    //回拨
    public void callBack(final String name, String number) {
        new NetAction().callBack(SettingInfo.getAccount(), number,
                new OnMyResponseListener() {

                    @Override
                    public void onResponse(String json) {
                        if (!"".equals(BaseUntil.stringNoNull(json))) {
                            JSONObject parseObject = JSONObject
                                    .parseObject(json);
                            if (CommReply.SUCCESS.equals(parseObject
                                    .getString("statuscode"))) {
                                T.show(mContext, ""
                                        + parseObject.getString("reason"), 1);
                                SettingInfo.setParams(
                                        PreferenceBean.CALLBACKSTART,
                                        "callBackStart");
                                SettingInfo.setParams(
                                        PreferenceBean.CALLBACKSELF,
                                        "callBackSelf");
                                SettingInfo.setParams(
                                        PreferenceBean.CALLBACKNAME, name);
                                // T.show(getApplicationContext(),
                                // String.format(getString(R.string.call_back),
                                // number), 1);
                            } else {
                                T.show(mContext, ""
                                        + parseObject.getString("reason"), 0);
                            }
                        } else {
                            T.show(mContext,
                                    mContext.getString(R.string.service_error), 0);
                        }
                    }
                }).execm();
    }
}
