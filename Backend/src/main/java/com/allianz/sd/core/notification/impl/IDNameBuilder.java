package com.allianz.sd.core.notification.impl;

import com.allianz.sd.core.notification.NotificationItem;
import com.allianz.sd.core.notification.NotificationItemBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IDNameBuilder implements NotificationItemBuilder {

    public List<IDNameItem> notificationItems = new ArrayList<>();

    @Override
    public void addItem(NotificationItem item) {
        notificationItems.add((IDNameItem) item);
    }

    @Override
    public String toText() {
        StringBuffer text = new StringBuffer();

        for(IDNameItem item : notificationItems) {
            text.append(item.getId() + "("+item.getName()+")\n");
        }

        return text.toString();
    }
}
