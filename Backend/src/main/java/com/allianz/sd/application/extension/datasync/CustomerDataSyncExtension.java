package com.allianz.sd.application.extension.datasync;

import com.allianz.sd.application.extension.jpa.model.CustomerExtension;
import com.allianz.sd.application.extension.jpa.repository.CustomerExtensionRepository;
import com.allianz.sd.core.api.model.Extension;
import com.allianz.sd.core.api.model.Person;
import com.allianz.sd.core.datasync.CustomerDataSyncListener;
import com.allianz.sd.core.jpa.model.Customer;

import com.allianz.sd.core.service.StringService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CustomerDataSyncExtension implements CustomerDataSyncListener {

    private final static String NAMPA = "MANPA";
    private final static String RECENTSTATUS = "RecentStatus";

    @Autowired
    private CustomerExtensionRepository customerExtensionRepository;

    @Autowired
    private StringService stringService;

    @Override
    public void postSave(Customer customer, Person person) {

        CustomerExtension customerExtension = new CustomerExtension();
        customerExtension.setExCustomerID(customer.getCustomerID());
        customerExtension.setExOrganizationalUnit(customer.getOrganizationalUnit());

        List<Extension> extensionList = person.getExtensions();
        for(Extension extension : extensionList) {
            String id = extension.getId();
            String value = extension.getValue();

            if(NAMPA.equalsIgnoreCase(id)) customerExtension.setManpa(value);
            if(RECENTSTATUS.equalsIgnoreCase(id)) customerExtension.setRecentStatus(value);
        }

        customerExtensionRepository.save(customerExtension);
    }

    @Override
    public void onPullData(Customer customer, Person person) {
        Optional<CustomerExtension> extensionOptional = customerExtensionRepository.findById(customer.getCustomerID());
        CustomerExtension customerExtension = extensionOptional.orElse(null);
        if(customerExtension != null) {

            String manpaVal = customerExtension.getManpa();
            String recentStatusVal = customerExtension.getRecentStatus();

            if(stringService.isNull(manpaVal)) manpaVal = "";
            if(stringService.isNull(recentStatusVal)) recentStatusVal = "";

            Extension nampa = new Extension();
            nampa.setId(NAMPA);
            nampa.setValue(manpaVal);
            nampa.setDatatype("text");

            Extension recentStatus = new Extension();
            recentStatus.setId(RECENTSTATUS);
            recentStatus.setValue(recentStatusVal);
            recentStatus.setDatatype("text");

            person.addExtensionsItem(nampa);
            person.addExtensionsItem(recentStatus);
        }

    }
}
