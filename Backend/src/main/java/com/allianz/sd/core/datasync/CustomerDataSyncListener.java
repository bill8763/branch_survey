package com.allianz.sd.core.datasync;

import com.allianz.sd.core.api.model.Person;
import com.allianz.sd.core.jpa.model.Customer;

public interface CustomerDataSyncListener {
    public void postSave(Customer customer, Person person);

    public void onPullData(Customer customer, Person person);
}
