package com.allianz.sd.core.impl;

import com.allianz.sd.core.customer.CustomerFullNameConvert;
import com.allianz.sd.core.jpa.model.Customer;

public class DefaultCustomerFullNameConvert implements CustomerFullNameConvert {
    @Override
    public String getFullName(Customer customer) {
        return customer.getLastName() + " " + customer.getFirstName();
    }
}
