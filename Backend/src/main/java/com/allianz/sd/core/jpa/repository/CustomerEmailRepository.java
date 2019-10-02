package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.CustomerEmail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerEmailRepository extends JpaRepository<CustomerEmail,Integer> {
//    @Modifying()
//    @Transactional
//    @Query("DELETE from CustomerEmail a Where 1=1 and a.customerID = :customerID")
//    public int deleteByCustomerID(
//            @Param("customerID") Integer customerID);
}
