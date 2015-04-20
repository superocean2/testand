package com.android.nghiatrinh.thuchi.model;

import com.orm.SugarRecord;

/**
 * Created by NghiaTrinh on 4/3/2015.
 */
public class Setting extends SugarRecord<Setting> {
    String name;
    String value;
    public Setting() {
    }

    public Setting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
