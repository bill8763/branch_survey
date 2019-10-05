package com.allianz.sd.core.impl;

import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.sysdata.CalcWorkingMonth;

public class DefaultCalcWorkingMonth implements CalcWorkingMonth {

	public int getWorkingMonth(SysYearData sysYearData) {
		//第一個+1 getMonth 從0開始  
		//第二個+1 WorkingMonth ==  PerformanceSettlementMonth +1
 		return sysYearData.getSysData().getPerformanceSettlementMonth().getMonth() + 1+1;
	}

	

}
