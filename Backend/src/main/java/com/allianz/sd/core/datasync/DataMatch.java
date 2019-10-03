package com.allianz.sd.core.datasync;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 下午 5:00
 * To change this template use File | Settings | File Templates.
 */
public interface DataMatch {
    public boolean isDifference(Object dbData,Object pushData);
}
