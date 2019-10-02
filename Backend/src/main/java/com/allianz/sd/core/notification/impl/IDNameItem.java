package com.allianz.sd.core.notification.impl;

import com.allianz.sd.core.notification.NotificationItem;

public class IDNameItem implements NotificationItem {
    private String id = null;
    private String name = null;

    public IDNameItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
