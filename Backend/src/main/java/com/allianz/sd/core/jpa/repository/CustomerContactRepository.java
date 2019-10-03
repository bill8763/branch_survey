package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerContactRepository extends JpaRepository<CustomerContact,Integer> {

    @Modifying()
    @Transactional
    @Query(nativeQuery = true , value = "DELETE from TW_LH_SD_Customer_Contact_Extension where Ex_ContactID = :contactID and Ex_OrganizationalUnit = :organizationalUnit")
    public int deleteExtensionData(
            @Param("contactID") Integer contactID ,
            @Param("organizationalUnit") String organizationalUnit);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO TW_LH_SD_Customer_Contact_Extension (Ex_ContactID,Ex_OrganizationalUnit) VALUES (:contactID , :organizationalUnit )")
    public int saveExtension(@Param("contactID") Integer contactID ,
                             @Param("organizationalUnit") String organizationalUnit);
}
