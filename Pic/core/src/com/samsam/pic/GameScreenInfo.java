package com.samsam.pic;

/**
 * Created by NghiaTrinh on 8/18/2015.
 */
public class GameScreenInfo {
    private int screenId;
    private boolean isMuteHuman;
    private boolean isMuteAnimal;
    private boolean isEnglish;

    public GameScreenInfo(int screenId, boolean isMuteHuman, boolean isMuteAnimal, boolean isEnglish) {
        this.screenId = screenId;
        this.isMuteHuman = isMuteHuman;
        this.isMuteAnimal = isMuteAnimal;
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

    public boolean isMuteAnimal() {
        return isMuteAnimal;
    }

    public void setIsMuteAnimal(boolean isMuteAnimal) {
        this.isMuteAnimal = isMuteAnimal;
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    public void setIsEnglish(boolean isEnglish) {
        this.isEnglish = isEnglish;
    }
}
