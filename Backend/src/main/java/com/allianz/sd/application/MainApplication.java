package com.allianz.sd.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.allianz.sd.core.configuration.FileStorageProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/1/22
 * Time: 上午 9:20
 * To change this template use File | Settings | File Templates.
 */

//這邊使用 Java Class 作為設定，而非XML
@Configuration

//啟用 Spring Boot 自動配置，將自動判斷專案使用到的套件，建立相關的設定。
@EnableConfigurationProperties({
        FileStorageProperties.class
})

@EnableAutoConfiguration

//自動掃描 Spring Bean 元件
@ComponentScan( basePackages = {"com.allianz.sd"} )

//掃瞄JPA接口的路徑
@EnableJpaRepositories(basePackages = {"com.allianz.sd.core.jpa.repository","com.allianz.sd.application"})


//整合JPA Transaction
@EnableTransactionManagement

//掃瞄POJO的路徑
@EntityScan(basePackages = {"com.allianz.sd.core.jpa.model","com.allianz.sd.application"})

@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

    //加這個是為了讓部署可以在其他container而不吃default的tomcat
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApplication.class);
    }

	public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }



}
