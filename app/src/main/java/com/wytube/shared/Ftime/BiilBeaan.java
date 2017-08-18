package com.wytube.shared.Ftime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BiilBeaan {
    //类型--内容
    public static final int ITEM = 0;
    //类型--顶部悬浮的标题
    public static final int SECTION = 1;

    public final int type; //所属于的类型
    public final BiilBeaan.DataBean messages; //listview显示的item的数据实体类,可根据自己的项目来设置

    public int sectionPosition; //顶部悬浮的标题的位置
    public int listPosition; //内容的位置

    public int getSectionPosition() {
        return sectionPosition;
    }

    public void setSectionPosition(int sectionPosition) {
        this.sectionPosition = sectionPosition;
    }

    public BiilBeaan.DataBean getMessages() {
        return messages;
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

    public int getType() {
        return type;
    }

    public BiilBeaan(int type, BiilBeaan.DataBean messages) {
        super();
        this.type = type;
        this.messages = messages;
    }

    public BiilBeaan(int type, BiilBeaan.DataBean messages,
                             int sectionPosition, int listPosition) {
        super();
        this.type = type;
        this.messages = messages;
        this.sectionPosition = sectionPosition;
        this.listPosition = listPosition;
    }

    @Override
    public String toString() {
        return messages.getMonth();
    }

    private boolean success;
    private String message;
    private int code;
    private String date;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }



    public static class DataBean {
        /**
         * createDate : null
         * modifyDate : null
         * createUser : null
         * status : null
         * sorted : null
         * remark : 自动生成物业费
         * billId : 00011c94d9884164992df32c1a783d35
         * billName : 物业费
         * typeId : 1
         * userId : null
         * numberId : CF03C12CE6A54F6CA075A426C7BA04AD
         * price : 1.50
         * num : 85
         * totalMoney : 127.50
         * stateId : 0
         * fromType : null
         * starttime : null
         * externalTradeNo : null
         * externalRespCode : null
         * totalFee : null
         * accountReceivable : null
         * month : 2017-08
         * orderNo :
         * cellId : null
         * buildingId : null
         * unitId : null
         * cellName : 长航小区
         * buildingName : 1栋
         * unitName : 1单元
         * numberName : 3005
         * userName : null
         */

        private Object createDate;
        private Object modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private String remark;
        private String billId;
        private String billName;
        private String typeId;
        private Object userId;
        private String numberId;
        private String price;
        private String num;
        private String totalMoney;
        private String stateId;
        private Object fromType;
        private Object starttime;
        private Object externalTradeNo;
        private Object externalRespCode;
        private Object totalFee;
        private Object accountReceivable;
        private String month;
        private String orderNo;
        private Object cellId;
        private Object buildingId;
        private Object unitId;
        private String cellName;
        private String buildingName;
        private String unitName;
        private String numberName;
        private Object userName;
        public boolean isCheck;

        public boolean isisCheck() {
            return isCheck;
        }
        public void setisCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(Object modifyDate) {
            this.modifyDate = modifyDate;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getSorted() {
            return sorted;
        }

        public void setSorted(Object sorted) {
            this.sorted = sorted;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getBillId() {
            return billId;
        }

        public void setBillId(String billId) {
            this.billId = billId;
        }

        public String getBillName() {
            return billName;
        }

        public void setBillName(String billName) {
            this.billName = billName;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getNumberId() {
            return numberId;
        }

        public void setNumberId(String numberId) {
            this.numberId = numberId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public Object getFromType() {
            return fromType;
        }

        public void setFromType(Object fromType) {
            this.fromType = fromType;
        }

        public Object getStarttime() {
            return starttime;
        }

        public void setStarttime(Object starttime) {
            this.starttime = starttime;
        }

        public Object getExternalTradeNo() {
            return externalTradeNo;
        }

        public void setExternalTradeNo(Object externalTradeNo) {
            this.externalTradeNo = externalTradeNo;
        }

        public Object getExternalRespCode() {
            return externalRespCode;
        }

        public void setExternalRespCode(Object externalRespCode) {
            this.externalRespCode = externalRespCode;
        }

        public Object getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(Object totalFee) {
            this.totalFee = totalFee;
        }

        public Object getAccountReceivable() {
            return accountReceivable;
        }

        public void setAccountReceivable(Object accountReceivable) {
            this.accountReceivable = accountReceivable;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
        }

        public Object getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(Object buildingId) {
            this.buildingId = buildingId;
        }

        public Object getUnitId() {
            return unitId;
        }

        public void setUnitId(Object unitId) {
            this.unitId = unitId;
        }

        public String getCellName() {
            return cellName;
        }

        public void setCellName(String cellName) {
            this.cellName = cellName;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getNumberName() {
            return numberName;
        }

        public void setNumberName(String numberName) {
            this.numberName = numberName;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }
    }

    public static ArrayList<BiilBeaan> getData(
            List<BiilBeaan.DataBean> details) {
        //最后我们要返回带有分组的list,初始化
        ArrayList<BiilBeaan> list = new ArrayList<BiilBeaan>();
        //时间转换的util类
        TimeManagement management = new TimeManagement();
        //WarnDetail作为key是yyyy-MM-dd格式,List<WarnDetail>是对应的值是HH:mm:ss格式
        Map<BiilBeaan.DataBean, List<BiilBeaan.DataBean>> map = new HashMap<DataBean, List<DataBean>>();
        // 按照warndetail里面的时间进行分类
        BiilBeaan.DataBean detail = new BiilBeaan.DataBean();
        for (int i = 0; i < details.size(); i++) {
            try {
                String key = management.exchangeStringDate(details.get(i)
                        .getMonth());
                if (detail.getMonth() != null && !"".equals(detail.getMonth())) {
                    //判断这个Key对象有没有生成,保证是唯一对象.如果第一次没有生成,那么new一个对象,之后同组的其他item都指向这个key
                    boolean b = !key.equals(detail.getMonth());
                    if (b) {
                        detail = new BiilBeaan.DataBean();
                    }
                }
                detail.setMonth(key);
                //把属于当天yyyy-MM-dd的时间HH:mm:ss全部指向这个key
                List<BiilBeaan.DataBean> warnDetails = map.get(detail);
                //判断这个key对应的值有没有初始化,若第一次进来,这new一个arryalist对象,之后属于这一天的item都加到这个集合里面
                if (warnDetails == null) {
                    warnDetails = new ArrayList<BiilBeaan.DataBean>();
                }
                String time = details.get(i).getMonth();
                time = management.exchangeStringTime(time);
                //把HH:mm:ss时间替换之前yyyy-MM-dd HH:mm:ss格式的时间.标识属于标题下的子类
                details.get(i).setMonth(time);
                warnDetails.add(details.get(i));
                map.put(detail, warnDetails);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 用迭代器遍历map添加到list里面
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            BiilBeaan.DataBean key = (BiilBeaan.DataBean) entry.getKey();
            //我们的key(yyyy-MM-dd)作为标题.类别属于SECTION
            list.add(new BiilBeaan(0, key));
            List<BiilBeaan.DataBean> li = (List<BiilBeaan.DataBean>) entry.getValue();
            for (BiilBeaan.DataBean warnDetail : li) {
                //对应的值(HH:mm:ss)作为标题下的item,类别属于ITEM
                list.add(new BiilBeaan(1, warnDetail));
            }
        }
        // 把分好类的hashmap添加到list里面便于显示
        return list;
    }

}
