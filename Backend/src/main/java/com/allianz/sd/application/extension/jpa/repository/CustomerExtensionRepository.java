package com.allianz.sd.application.extension.jpa.repository;


import com.allianz.sd.application.extension.jpa.model.CustomerExtension;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 12:41
 * To change this template use File | Settings | File Templates.
 */

public interface CustomerExtensionRepository<T,S> extends JpaRepository<CustomerExtension,Integer> {

}
