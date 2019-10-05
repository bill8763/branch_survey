package com.allianz.sd.core.datasync.impl;

import java.math.BigDecimal;
import java.util.*;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.progress.CalcTeamProgress;
import com.allianz.sd.core.service.*;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.allianz.sd.core.api.model.GoalExpected;
import com.allianz.sd.core.api.model.GoalExpecteds;
import com.allianz.sd.core.api.model.SynchDetailNoDelete;
import com.allianz.sd.core.datasync.DataMatch;
import com.allianz.sd.core.datasync.DataSync;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.repository.GoalExpectedSplitValueRepository;
import com.allianz.sd.core.jpa.repository.GoalExpectedValueRepository;
import com.allianz.sd.core.jpa.repository.GoalExpectedVersionRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class GoalSettingDataSync implements DataSync , DataMatch {

    private Logger logger = LoggerFactory.getLogger(GoalSettingDataSync.class);
    private String agentID = null;

    @Autowired
    private GoalExpectedVersionRepository goalExpectedVersionRepository = null;

    @Autowired
    private GoalExpectedValueRepository goalExpectedValueRepository = null;

    @Autowired
    private GoalExpectedSplitValueRepository goalExpectedSplitValueRepository = null;

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService= null;

    @Autowired
    private DateService dateService = null;

    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private StringService stringService;

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public boolean isDifference(Object dbData, Object pushData) {
        GoalExpectedVersion goalExpectedVersion = (GoalExpectedVersion) dbData;
        GoalExpected goalExpected = (GoalExpected) pushData;

        Date pushDate = dateService.toDateTimeFormatDate(goalExpected.getSynchDetail().getLastUpdateDateTimeBackend());
        return goalExpectedVersion.getDataTime().getTime() != pushDate.getTime();
    }

    @Override
    public Map findPullData(Date appSyncDate) {
        Map<Integer, GoalExpectedVersion> map = new HashedMap<>();

        List<Integer> idList = agentUpdateVersionService.getAgentLastSyncData(agentID, DataCategory.GOALSETTING,appSyncDate);

        for(Integer id : idList) {

            Optional<GoalExpectedVersion> goalOptional = goalExpectedVersionRepository.findById(id);
            GoalExpectedVersion version = goalOptional.orElse(null);
            if(version != null) map.put(id,version);
        }

        return map;
    }

    @Override
    public Object findPushData(Object data) {
        GoalExpected goalExpected = (GoalExpected) data;

        String organizationalUnit = organizationService.getOrganizationalUnit();

        Optional<GoalExpectedVersion> expectedValue = goalExpectedVersionRepository.findByOrganizationalUnitAndAgentIDAndDataYear(organizationalUnit, agentID, goalExpected.getDataYear().intValue());

        return expectedValue.orElse(null);
    }

    @Override
    public Object data2SyncObj(Object data) {

        GoalExpectedVersion version = (GoalExpectedVersion) data;
        Integer expectedSequence = version.getGoalExpectedSeq();

        //only team(leader, zone head, cao ) need goal expected
        GoalExpecteds goalExpected = new GoalExpecteds();
        goalExpected.setDataYear(new BigDecimal(version.getDataYear()) );

        //query expected value by seq
        List<GoalExpectedValue> expectedValues = goalExpectedValueRepository.findByIdentityGoalExpectedSeq(expectedSequence);
        for(GoalExpectedValue expectedValue: expectedValues) {
            String mappingID = expectedValue.getIdentity().getMappingID();
            String setValue = expectedValue.getSetValue();
            if("FYFC".equalsIgnoreCase(mappingID)) {
                goalExpected.setFYFC(new BigDecimal(setValue) );
            }else if("ANP".equalsIgnoreCase(mappingID)) {
                goalExpected.setANP(new BigDecimal(setValue));
            }
        }


        //get expected split value by seq
        List<GoalExpectedSplitValue> expectedSplitValues = goalExpectedSplitValueRepository.findByIdentityGoalExpectedSeq(expectedSequence);
        for(GoalExpectedSplitValue expectedSplitValue:expectedSplitValues) {
            com.allianz.sd.core.api.model.GoalExpectedValue goalExpectedValue = new com.allianz.sd.core.api.model.GoalExpectedValue();
            Integer timeBaseSeq = expectedSplitValue.getIdentity().getTimeBaseSeq();
            String setValue = expectedSplitValue.getSetValue();

            goalExpectedValue.setQarter(new BigDecimal(timeBaseSeq));
            goalExpectedValue.setValue(setValue);
            goalExpected.addRecruitmentsItem(goalExpectedValue);
        }

        SynchDetailNoDelete synchDetail = new SynchDetailNoDelete();
        synchDetail.setLastUpdateDateTimeBackend(dateService.toDateTimeFormatString(version.getDataTime()));
        goalExpected.setSynchDetail(synchDetail);

        return goalExpected;

    }

    @Override
    public Integer getIdentityID(Object data) {
        GoalExpectedVersion version = (GoalExpectedVersion) data;
        return version.getGoalExpectedSeq();
    }

    @Override
    public boolean isDeleteData(Object data) {
//        Person person = (Person) data;
//        return person.getSynchDetail().isToDelete();
        return false;
    }

    @Override
    public void onDeleteData(Object data) {
        GoalExpectedVersion version = (GoalExpectedVersion) data;
        goalExpectedVersionRepository.delete(version);
    }

    @Override
    public Object onSaveData(Object dbData , Object data) {

        CalcTeamProgress calcTeamProgress = (CalcTeamProgress) beanFactory.getBean(InstanceCode.CalcTeamProgress);

        String organizationalUnit = organizationService.getOrganizationalUnit();

        //api model
        GoalExpected goalExpected = (GoalExpected)data;
        Integer dataYear = goalExpected.getDataYear().intValue();

        //db model
        GoalExpectedVersion version = (GoalExpectedVersion) dbData;

        if(version == null) {
            version = new GoalExpectedVersion();
            version.setDataYear(dataYear);
            version.setOrganizationalUnit(organizationalUnit);
            version.setAgentID(agentID);
            version.setDataTime( dateService.toDateTimeFormatDate(goalExpected.getSynchDetail().getLastUpdateDateTimeBackend()));
            goalExpectedVersionRepository.save(version);
        }

        //seq
        Integer goalExpectedSeq = version.getGoalExpectedSeq();

        //query goal_setting by agentId datayear oe
        List<GoalExpectedValue> expectedValues = goalExpectedValueRepository.getGoalExpectedValue(agentID, organizationalUnit, dataYear);

        String fyfc = goalExpected.getFYFC().toString();
        String anp = goalExpected.getANP().toString();

        if(expectedValues.size() != 0) {
            for(GoalExpectedValue expectedValue: expectedValues) {
                String mappingID = expectedValue.getIdentity().getMappingID();

                String value = "FYFC".equalsIgnoreCase(mappingID) ? fyfc : anp;
                saveGoalExpectedValue(goalExpectedSeq,mappingID,value);
            }
        }
        else {
            //新增Value
            saveGoalExpectedValue(goalExpectedSeq,"FYFC",fyfc);
            saveGoalExpectedValue(goalExpectedSeq,"ANP",anp);
        }

        List<com.allianz.sd.core.api.model.GoalExpectedValue> goalExpectedValues = goalExpected.getRecruitments();
        for(com.allianz.sd.core.api.model.GoalExpectedValue goalExpectedValue: goalExpectedValues) {

            String value = goalExpectedValue.getValue();

            if(stringService.isNumeric(value)) {
                int quarter = goalExpectedValue.getQarter().intValue();
                int val = Integer.parseInt(value);

                GoalExpectedSplitValueIdentity id = new GoalExpectedSplitValueIdentity();
                id.setGoalExpectedSeq(goalExpectedSeq);
                id.setMappingID("Recruitment");
                id.setTimeBase("QUARTER");
                id.setTimeBaseSeq(quarter);
                GoalExpectedSplitValue expectedSplitValue =  goalExpectedSplitValueRepository.findById(id).orElse(null);

                if(expectedSplitValue==null) {
                    expectedSplitValue = new GoalExpectedSplitValue();
                    expectedSplitValue.setIdentity(id);
                }
                expectedSplitValue.setSetValue(String.valueOf(val));
                expectedSplitValue.setCreateBy(agentID);
                expectedSplitValue.setUpdateBy(agentID);

                goalExpectedSplitValueRepository.save(expectedSplitValue);
            }

        }

        logger.debug("Bottom-up TeamProgress Recruitment");
        calcTeamProgress.onGoalExpectedChange(dataYear,agentID,organizationalUnit);

        return version;
    }

    private void saveGoalExpectedValue(Integer goalExpectedSeq , String mappingID , String value) {
        GoalExpectedValue expectedValue = new GoalExpectedValue();

        GoalExpectedValueIdentity goalExpectedValueIdentity = new GoalExpectedValueIdentity();
        goalExpectedValueIdentity.setGoalExpectedSeq(goalExpectedSeq);
        goalExpectedValueIdentity.setMappingID(mappingID);

        expectedValue.setIdentity(goalExpectedValueIdentity);
        expectedValue.setSetValue(value);
        expectedValue.setCreateBy(agentID);
        expectedValue.setUpdateBy(agentID);

        goalExpectedValueRepository.save(expectedValue);
    }

    @Override
    public DataMatch getDataMatchPolicy() {
        return this;
    }


    @Override
    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

}
