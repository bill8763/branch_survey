package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */

public interface CustomerRepository<T,S> extends JpaRepository<Customer,Integer> {

    //used for count customer age cron job
    public List<Customer> findByBirthdayMonthAndBirthdayDate(String birthdayMonth , String birthdayDate);

    //used for overtime no maintain alert to agent
    @Query("SELECT a FROM Customer a where 1=1 and a.appUpdateTime <= :time and a.dataSource <> 'OPUS' and a.overTimeAlertTime is null")
    public List<Customer> findByAPPUpdateTimeLessThanEqual(@Param("time") Date time);

    //used for overtime no maintain auto delete job
    @Query("SELECT a FROM Customer a where 1=1 and to_char(a.overTimeAlertTime,'yyyy-MM-dd') = :time and a.dataSource <> 'OPUS'")
    public List<Customer> findByOverTimeAlertTime(@Param("time") String time);


    @Modifying()
    @Transactional
    @Query(nativeQuery = true , value = "DELETE from TW_LH_SD_Customer_Extension where Ex_CustomerID = :customerID and Ex_OrganizationalUnit = :organizationalUnit")
    public int deleteExtensionData(
            @Param("customerID") Integer customerID ,
            @Param("organizationalUnit") String organizationalUnit);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO TW_LH_SD_Customer_Extension (Ex_CustomerID,Ex_OrganizationalUnit) VALUES (:customerID , :organizationalUnit )")
    public int saveExtension(@Param("customerID") Integer customerID ,
                             @Param("organizationalUnit") String organizationalUnit);
}
