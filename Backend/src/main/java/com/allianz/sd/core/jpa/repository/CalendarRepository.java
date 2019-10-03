package com.allianz.sd.core.jpa.repository;

import com.allianz.sd.core.jpa.model.Calendar;
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
public interface CalendarRepository extends JpaRepository<Calendar,Integer> {

    @Query("SELECT a FROM Calendar a where 1=1 and a.startTime >= :date and a.isAllDay = :isAllDay and a.isAlert = 'Y'")
    public List<Calendar> findByReminder(@Param("date") Date date , @Param("isAllDay") String isAllDay);

    @Modifying()
    @Transactional
    @Query(nativeQuery = true , value = "DELETE from TW_LH_SD_Calendar_Extension where Ex_CalendarID = :calendarID and Ex_OrganizationalUnit = :organizationalUnit")
    public int deleteExtensionData(
            @Param("calendarID") Integer calendarID ,
            @Param("organizationalUnit") String organizationalUnit);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO TW_LH_SD_Calendar_Extension (Ex_CalendarID,Ex_OrganizationalUnit) VALUES (:calendarID , :organizationalUnit )")
    public int saveExtension(@Param("calendarID") Integer calendarID ,
                             @Param("organizationalUnit") String organizationalUnit);
}
