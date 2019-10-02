package com.allianz.sd.core.console;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.allianz.sd.core.InstanceCode;
import com.allianz.sd.core.api.model.AgencyPlanGetResponse;
import com.allianz.sd.core.api.model.ProgressGetResponse;
import com.allianz.sd.core.jpa.model.AgencyPlanDetail;
import com.allianz.sd.core.jpa.model.AgentSalesData;
import com.allianz.sd.core.jpa.model.SysData;
import com.allianz.sd.core.jpa.model.SysYearData;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailRepository;
import com.allianz.sd.core.jpa.repository.AgencyPlanPileRepository;
import com.allianz.sd.core.progress.CalcTeamProgress;
import com.allianz.sd.core.service.AgencyPlanSyncService;
import com.allianz.sd.core.service.GoalService;
import com.allianz.sd.core.service.OrganizationService;
import com.allianz.sd.core.service.PlanService;
import com.allianz.sd.core.service.SalesDataService;
import com.allianz.sd.core.service.SysDataService;
import com.allianz.sd.core.service.bean.Goal;

@Controller
@RequestMapping("/debug")
public class DebugToolsController {
    private Logger logger = LoggerFactory.getLogger(DebugToolsController.class);

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private AgencyPlanSyncService agencyPlanSyncService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AgencyPlanPileRepository agencyPlanPileRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    private PlanService planService;
    
    @Autowired
    private SalesDataService salesDataService;

    @Autowired
    private SysDataService sysDataService;
    
    @Autowired
    private AgencyPlanDetailRepository agencyPlanDetailRepository;
    
    
    @PersistenceContext
    private EntityManager em;  // 注入实体管理器

    @RequestMapping("/command")
    public String command(Model model) {
        return "command";
    }

    @RequestMapping("/executeCommand")
    public String executeCommand(
            @RequestParam(value = "command", required = true) String command,
            Model model) {

        javax.persistence.Query q = em.createNativeQuery(command);
        List result = q.getResultList();

        Map<String, Object> hints = q.getHints();
        model.addAttribute("result",result);
        model.addAttribute("hints",hints);
        return "executeCommand";
    }

    @RequestMapping("/agencyplan/{agentID}")
    public String agencyplan(
            Model model,
            @PathVariable("agentID")String agentID) {

        String organizationalUnit = organizationService.getOrganizationalUnit();

        //get AgencyPlan Result
        AgencyPlanGetResponse agencyPlanGetResponse = null;
        try{
            agencyPlanGetResponse = agencyPlanSyncService.getAgencyPlanSyncResponse(agentID, organizationalUnit);
        }catch(Exception e) {
            logger.error("debug agencyplan fail!!["+agentID+"]",e);
        }

        model.addAttribute("agentID",agentID);
        model.addAttribute("agencyPlanGetResponse",agencyPlanGetResponse);
        return "agencyplan";
    }

    @RequestMapping("/agencyplan/drilldown/{dataYear}/{agentID}")
    public String agencyplanDrilldown(
            Model model,
            @PathVariable("dataYear") int dataYear,
            @PathVariable("agentID") String agentID) {

        Map<String,Object[]> result = new LinkedHashMap<>();

        List<String> agentIdList = agencyPlanPileRepository.getAgentIDList(agentID,dataYear);

        try{
            for(String subAgentID : agentIdList) {

                //Get SysYearData
                SysYearData sysYearData = sysDataService.getSysYearData(organizationService.getOrganizationalUnit(),subAgentID,dataYear);
                int yearPlanStartMonth = sysDataService.getRemainingPlanCalcStartMonth(sysYearData);

                Goal goal = goalService.getYearGoal(subAgentID,dataYear);

                result.put(subAgentID,new Object[]{goal,yearPlanStartMonth});
            }
        }catch(Exception e) {
            logger.error("agencyplanDrilldown fail!!",e);
        }

        model.addAttribute("agentID",agentID);
        model.addAttribute("dataYear",dataYear);
        model.addAttribute("result",result);

        return "agencyplanDrilldown";
    }
    //2019 09 19 AgenyPlan Forecast DrillDown By Jeff
    @RequestMapping("/agencyplan/forecastdrilldown/{dataYear}/{agentID}")
    public String agencyplanForecastDrilldown(
            Model model,
            @PathVariable("dataYear") int dataYear,
            @PathVariable("agentID") String agentID) {
//
//        Map<String,Object[]> result = new LinkedHashMap<>();
//
////        List<String> agentIdList = agencyPlanPileRepository.getAgentIDList(agentID,dataYear);
//        List<AgencyPlanDetail> agencyPlanDetailList = agencyPlanDetailRepository.findDetailByAgentID(dataYear, agentID);
//        System.out.println("agencyPlanDetailList size = " + agencyPlanDetailList.size());
//        
//        String organizationalUnit = organizationService.getOrganizationalUnit();
////        	if(agencyPlanDetailList.size() ==0) {
////        		 List<String> agencyPlanDetailList = agencyPlanPileRepository.getAgentIDList(agentID,dataYear);
////        	}
//        
//        
//        try{
//            for(AgencyPlanDetail agencyPlanDetail : agencyPlanDetailList) {
//
//            	String subAgentID = agencyPlanDetail.getSubAgentID();
//            	String displayBlock = agencyPlanDetail.getIdentity().getDisplayBlock();
//            	String performanceType = "DIRECT".equalsIgnoreCase(displayBlock) ? "P" : "T" ;
//            	if(agentID.equalsIgnoreCase(subAgentID)) performanceType = "T";
//            	
//                //Get SysYearData
//                SysYearData sysYearData = sysDataService.getSysYearData(organizationalUnit,subAgentID,dataYear);
//                int yearPlanStartMonth = sysDataService.getRemainingPlanCalcStartMonth(sysYearData);
//                Goal goal = goalService.getYearGoal(subAgentID,dataYear);
//                
//                
//                String appSysCtrl = sysYearData.getIdentity().getAppSysCtrl();
//                SysData sysData = sysDataService.getSysData(organizationalUnit, appSysCtrl);
//                Date actualEndMonth = sysData.getPerformanceSettlementMonth();
//                Map<Integer,AgentSalesData> actual = salesDataService.getAgentSalesDataByYear(dataYear, subAgentID, organizationalUnit,performanceType);
//                
//                result.put(subAgentID,new Object[]{goal,yearPlanStartMonth,actual,actualEndMonth.getMonth() + 1});
//            }
//        }catch(Exception e) {
//            logger.error("agencyplanDrilldown fail!!",e);
//        }
//
//        model.addAttribute("agentID",agentID);
//        model.addAttribute("dataYear",dataYear);
//        model.addAttribute("result",result);

        return "agencyplanForeCastDrilldown";
    }

    @RequestMapping("/teamprogress/{agentID}")
    public String teamprogress(
            Model model,
            @PathVariable("agentID")String agentID) throws Exception {

        String organizationalUnit = organizationService.getOrganizationalUnit();
//        ProgressGetResponse progressGetResponse = progressAPIService.getProgressResponse(agentID, organizationalUnit);
//        CalcTeamProgress calcTeamProgress = (CalcTeamProgress) beanFactory.getBean(InstanceCode.CalcTeamProgress);

//        model.addAttribute("progressGetResponse",progressGetResponse);

        return "teamprogress";
    }

    @RequestMapping("/teamprogress/logic")
    public String teamprogressLogic(Model model) throws Exception {

        CalcTeamProgress calcTeamProgress = (CalcTeamProgress) beanFactory.getBean(InstanceCode.CalcTeamProgress);

        model.addAttribute("calcTeamProgress",calcTeamProgress);

        return "teamprogress_logic";
    }
}
