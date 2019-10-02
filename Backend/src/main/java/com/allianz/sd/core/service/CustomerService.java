package com.allianz.sd.core.service;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.customer.CustomerFullNameConvert;
import com.allianz.sd.core.jpa.model.Customer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private BeanFactory beanFactory;

    public String convertFullName(Customer customer) {
        CustomerFullNameConvert convert = (CustomerFullNameConvert) beanFactory.getBean(InstanceCode.CustomerNameRule);
        return convert.getFullName(customer);
    }
}
