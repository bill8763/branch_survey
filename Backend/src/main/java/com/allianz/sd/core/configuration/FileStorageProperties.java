package com.allianz.sd.core.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/26
 * Time: 上午 11:13
 * To change this template use File | Settings | File Templates.
 */
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}