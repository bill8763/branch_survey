package com.allianz.sd.core.notification;

public interface NotificationItemBuilder {
    public void addItem(NotificationItem item);

    public String toText();
}
