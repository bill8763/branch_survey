package com.allianz.sd.core.jpa.repository;


import com.allianz.sd.core.jpa.model.Message;
import com.allianz.sd.core.jpa.model.MessageGoalStatus;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface MessageRepository extends JpaRepository<Message,Integer> {

    @Query("SELECT a FROM Message a where 1=1 and a.dataTime >= :dataTime and a.agentID = :agentID")
    public List<Message> findPullData(
            @Param("dataTime") Date dataTime ,
            @Param("agentID") String agentID);

    @Query("select b from MessageGoalStatus a , Message b\n" +
            "where a.goalSettingSeq = :goalSettingSeq\n" +
            "and a.messageID = b.messageID\n")
    public Message getMessageByGoalSettingSeq(
            @Param("goalSettingSeq") Integer goalSettingSeq);

    @Query("select b from MessageGoalStatus a , Message b , GoalSettingVersion c\n" +
            "where a.goalSettingSeq = c.goalSettingSeq\n" +
            "and a.messageID = b.messageID\n" +
            "and c.dataYear = :dataYear\n" +
            "and c.agentID = :agentID\n" +
            "")
    public Message getMessageByGoal(
            @Param("dataYear") int dataYear,
            @Param("agentID") String agentID
    );

    @Modifying()
    @Transactional
    @Query("UPDATE Message a SET a.status = 'Reading' , a.dataTime = current_date Where 1=1 and a.messageID = :messageID")
    public int updateMessageReading(
            @Param("messageID") Integer messageID);
    
    @Query(value="select a from Message a \n" + 
    		"where 1 = 1 \n" + 
    		"and a.messageCategory =:messageCategory \n" + 
    		"and a.messageType =:messageType \n" + 
    		"and to_char(a.messageTime,'yyyy-MM-dd') =:messageTime \n" +
    		"and a.agentID =:agentID ")
    public Message getNotification(@Param("messageCategory")String messageCategory,
    								@Param("messageType")String messageType,
    								@Param("messageTime")String messageTime,
    								@Param("agentID")String agentID);


    @Query(value="select count(a.messageID) from Message a \n" +
            "where 1 = 1 \n" +
            "and a.messageCategory =:messageCategory \n" +
            "and a.messageType =:messageType \n" +
            "and a.status = 'UnRead' \n" +
            "and a.agentID =:agentID ")
    public Integer getUnReadMessage(@Param("messageCategory")String messageCategory,
                                   @Param("messageType")String messageType,
                                   @Param("agentID")String agentID);
}
