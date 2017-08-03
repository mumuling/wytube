package com.android.action.param;

public class CallBackRequest extends CommRequest {
    private String callerNum;// 主叫号
    private String calleeNum;// 被叫号
    private String calleeTimeLimit;// -1表示通话时长不受限制
    private String playMusicBeforeHuandupTime;// -1表示通话结束前不会播放语音
    private String isPlayTranferMusic;// fasle表示主叫接通后不会播放正在为您转接提示音

    public CallBackRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getCallerNum() {
        return callerNum;
    }

    public void setCallerNum(String callerNum) {
        this.callerNum = callerNum;
    }

    public String getCalleeNum() {
        return calleeNum;
    }

    public void setCalleeNum(String calleeNum) {
        this.calleeNum = calleeNum;
    }

    public String getCalleeTimeLimit() {
        return calleeTimeLimit;
    }

    public void setCalleeTimeLimit(String calleeTimeLimit) {
        this.calleeTimeLimit = calleeTimeLimit;
    }

    public String getPlayMusicBeforeHuandupTime() {
        return playMusicBeforeHuandupTime;
    }

    public void setPlayMusicBeforeHuandupTime(String playMusicBeforeHuandupTime) {
        this.playMusicBeforeHuandupTime = playMusicBeforeHuandupTime;
    }

    public String getIsPlayTranferMusic() {
        return isPlayTranferMusic;
    }

    public void setIsPlayTranferMusic(String isPlayTranferMusic) {
        this.isPlayTranferMusic = isPlayTranferMusic;
    }


}
