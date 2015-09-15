package com.radomar.les19.model;

/**
 * Created by Radomar on 10.09.2015
 */
public final class NotificationModel {

    public String title;
    public String subTitle;
    public String message;

    public NotificationModel() {
    }

    public NotificationModel(String title, String subTitle, String message) {
        this.title = title;
        this.subTitle = subTitle;
        this.message = message;
    }
}
