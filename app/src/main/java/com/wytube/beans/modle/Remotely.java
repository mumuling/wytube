package com.wytube.beans.modle;

public class Remotely {
    public static final String PERSONS = "MUCH_PERSON";

    public String name;
    public String content;
    public String doorType;
    public String doorId;
    public String sip;
    public String serial;

    public Remotely(String name, String content,String doorType,String doorId,String sip,String serial) {
        this.name = name;
        this.content = content;
        this.doorType = doorType;
        this.doorId = doorId;
        this.sip = sip;
        this.serial = serial;
    }



    @Override
    public String toString() {
        return "Person{" +"name='" + name + '\'' +", content=" + content+'}';
    }
}
