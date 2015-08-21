package com.samsam.friut;

/**
 * Created by NghiaTrinh on 8/18/2015.
 */
public class GameScreenInfo {
    private int screenId;
    private boolean isMuteHuman;
    private boolean isEnglish;

    public GameScreenInfo(int screenId, boolean isMuteHuman, boolean isEnglish) {
        this.screenId = screenId;
        this.isMuteHuman = isMuteHuman;
        this.isEnglish = isEnglish;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public boolean isMuteHuman() {
        return isMuteHuman;
    }

    public void setIsMuteHuman(boolean isMuteHuman) {
        this.isMuteHuman = isMuteHuman;
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    public void setIsEnglish(boolean isEnglish) {
        this.isEnglish = isEnglish;
    }
}
