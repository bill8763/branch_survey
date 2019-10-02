package com.allianz.sd.core.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UtilsService {

    private static Logger logger = LoggerFactory.getLogger(UtilsService.class);

    @Autowired
    private StringService stringService;

    public boolean isNotEmptyBean(Object obj) {
        return obj != null && !obj.getClass().getSimpleName().equalsIgnoreCase("NullBean");
    }

    public long calcTotalFromMap(Map<Integer,String> map , int start , int end) {
        long total = 0;

        for(int i=start ; i<= end ; i++) {
            if(map.containsKey(i)) {
                String val = map.get(i);
                if(stringService.isNumeric(val)) total += Long.parseLong(val);
            }
        }

        return total;
    }

    public Map<Integer,String> getCompareMap(Map<Integer,String> originalMap , Map<Integer,String> afterMap) {
        Map<Integer,String> differentPlanMap = new LinkedHashMap<Integer,String>();

        //算出每個月差異 放入 differentPlanMap key = month , value = diff
        if(originalMap.size() == afterMap.size()) {
            for(Integer key : originalMap.keySet()) {

                String originalVal = originalMap.get(key);
                String afterVal = afterMap.get(key);
                if(stringService.isNumeric(originalVal) && stringService.isNumeric(afterVal)) {
                    long diff = Long.parseLong(afterVal) - Long.parseLong(originalVal);

                    logger.debug("month = ["+key+"] , ori = ["+originalVal+"] , now = ["+afterVal+"] , diff = ["+diff+"]");

                    differentPlanMap.put(key, diff + "");
                }

            }
        }

        return differentPlanMap;
    }

    public void fillMap(Map<Integer,String> map , int startKey , int keySize , String fillVal) {
        for(int i=startKey;i<=keySize;i++) {
            if(!map.containsKey(i)) {
                map.put(i,fillVal);
            }
        }
    }

    public Map<Integer, String> listInteger2Map(List<Object[]> list) {
        return listInteger2Map(list,-1,"0");
    }

    public Map<Integer, String> listInteger2Map(List<Object[]> list , int fillNumber , String fillVal) {
        Map<Integer, String> map = new LinkedHashMap<>();

        for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);

            String val1 = String.valueOf(obj[0]);
            String val2 = String.valueOf(obj[1]);
            if(stringService.isNull(val2)) val2 = "";
            int s1 = Integer.parseInt(val1);
            map.put(s1, val2);
        }

        fillMap(map,fillNumber,fillVal);

        return map;
    }

    public void fillMap(Map<Integer, String> map,int fillNumber , String fillVal) {
        for(int i=1;i<=fillNumber;i++) {
            if(!map.containsKey(i)) map.put(i,fillVal);
        }
    }


    public Map<Integer,String> mergeMap(Map<Integer,String> ori , Map<Integer,String> after) {
        Map<Integer,String> map = new LinkedHashMap<>();

        logger.trace("ori size = ["+ori.size()+"] , after size = ["+after.size()+"]");

        for(Integer i : ori.keySet()) {
            String val = after.getOrDefault(i,"0");
            String val2 = ori.getOrDefault(i,"0");
            logger.trace("val = ["+val+"] , val2 = ["+val2+"]");

            String val3 = "0";
            if((!stringService.isNull(val) && stringService.isNumeric(val))
                    && (!stringService.isNull(val2) && stringService.isNumeric(val2))) {
                val3 = (Long.parseLong(val) + Long.parseLong(val2)) + "";
            }

            map.put(i,val3);
        }

        return map;
    }
}
