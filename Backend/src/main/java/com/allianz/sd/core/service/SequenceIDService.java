package com.allianz.sd.core.service;

import com.allianz.sd.core.jpa.repository.SequenceIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/18
 * Time: 下午 3:38
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SequenceIDService {

    @Autowired
    private SequenceIDRepository sequenceIDRepository;

    public List<BigDecimal> getId(String category,BigDecimal idNums) {
        List<BigDecimal> result = new ArrayList<BigDecimal>();

        int nums = idNums.intValue();
        for(int i=0;i<nums;i++) {
            BigDecimal num = sequenceIDRepository.getSequenceID(category);
            if(num != null) {
                result.add(num);
            }
        }

        return result;
    }

}
