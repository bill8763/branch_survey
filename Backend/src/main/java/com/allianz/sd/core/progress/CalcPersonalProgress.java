package com.allianz.sd.core.progress;

import com.allianz.sd.core.jpa.model.AgentYearData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public interface CalcPersonalProgress {

    /**
     * 取出該年度需計算PersonalProgress的業務員年度資料
     * @param dataYear
     * @param organizationalUnit
     * @return
     */
    public List<AgentYearData> getCalcPersonalAgentYearDatas(int dataYear , String organizationalUnit);

    public boolean isFindOverShowDetail(BigDecimal actual , BigDecimal goal);

    public boolean isScheduleOverShowDetail(BigDecimal actual , BigDecimal goal);

    public boolean isMeetOverShowDetail(BigDecimal actual , BigDecimal goal);

    public boolean isSubmitOverShowDetail(BigDecimal actual , BigDecimal goal);

    public boolean isInforceOverShowDetail(BigDecimal actual , BigDecimal goal);
}
