package com.mitrais.jpqi.springcarrot.responses;

import com.mitrais.jpqi.springcarrot.model.Notification;

import java.util.List;

public class NotificationResponse extends Response {
    Notification notification;
    List<Notification> listNotification;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public List<Notification> getListNotification() {
        return listNotification;
    }

    public void setListNotification(List<Notification> listNotification) {
        this.listNotification = listNotification;
    }
}
