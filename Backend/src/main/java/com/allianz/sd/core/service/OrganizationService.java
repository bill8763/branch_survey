package com.allianz.sd.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 6:06
 * To change this template use File | Settings | File Templates.
 */

@Service
public class OrganizationService {
    @Value("${SND.OrganizationalUnit}")
    private String organizationalUnit = null;

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }
}
