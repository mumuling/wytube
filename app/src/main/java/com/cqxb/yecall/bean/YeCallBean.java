package com.cqxb.yecall.bean;


public class YeCallBean {

    private String community;
    private String equipment;
    private String doorId;
//	private String destinationIp;


    public YeCallBean(String community, String equipment) {
        super();
        this.community = community;
        this.equipment = equipment;
    }

    public YeCallBean(String community, String equipment, String doorId) {
        super();
        this.community = community;
        this.equipment = equipment;
        this.doorId = doorId;
//		this.equipmentId = equipmentId;
    }


    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    /*public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }*/

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    /*public String getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp;
    }*/
}
