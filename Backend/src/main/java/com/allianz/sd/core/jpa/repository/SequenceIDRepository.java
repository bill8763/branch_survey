package com.allianz.sd.core.jpa.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 下午 3:46
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SequenceIDRepository {

    @PersistenceContext
    private EntityManager em;  // 注入实体管理器


    public BigDecimal getSequenceID(String category) {

        String sequenceName = null;

        if("customer".equalsIgnoreCase(category)) sequenceName = "TW_LH_SD_Sequence_Customer";
        if("calendar".equalsIgnoreCase(category)) sequenceName = "TW_LH_SD_Sequence_Calendar";
        if("customer_contact".equalsIgnoreCase(category)) sequenceName = "TW_LH_SD_Sequence_Customer_Contact";
        if("message".equalsIgnoreCase(category)) sequenceName = "TW_LH_SD_Sequence_Message";
        if("batchlog".equalsIgnoreCase(category)) sequenceName = "TW_LH_SD_Sequence_BatchLog";

        if(StringUtils.isEmpty(sequenceName)) {
            return null;
        }


        javax.persistence.Query q = em.createNativeQuery("select "+sequenceName+".nextval from dual");
        return (BigDecimal)q.getSingleResult();
    }


}