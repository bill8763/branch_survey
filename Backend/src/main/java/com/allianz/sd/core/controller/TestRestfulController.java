package com.allianz.sd.core.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import com.allianz.sd.core.jpa.model.*;
import com.allianz.sd.core.jpa.model.Calendar;
import com.allianz.sd.core.service.*;
import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/1/22
 * Time: 上午 9:32
 * To change this template use File | Settings | File Templates.
 */


import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailRepository;
import com.allianz.sd.core.jpa.repository.AgencyPlanDetailSyncRepository;
import com.allianz.sd.core.jpa.repository.AgencyPlanPileRepository;
import com.allianz.sd.core.jpa.repository.AgencyPlanRepository;
import com.allianz.sd.core.jpa.repository.AgentDailyDataRepository;
import com.allianz.sd.core.jpa.repository.AgentDataRepository;
import com.allianz.sd.core.jpa.repository.AgentDeviceRepository;
import com.allianz.sd.core.jpa.repository.AgentSalesDataRepository;
import com.allianz.sd.core.jpa.repository.AgentUpdateVersionRepository;
import com.allianz.sd.core.jpa.repository.AgentYearDataRepository;
import com.allianz.sd.core.jpa.repository.CalendarRepository;
import com.allianz.sd.core.jpa.repository.CodeRepository;
import com.allianz.sd.core.jpa.repository.CodeTypeRepository;
import com.allianz.sd.core.jpa.repository.CustomerContactRepository;
import com.allianz.sd.core.jpa.repository.CustomerRepository;
import com.allianz.sd.core.jpa.repository.GoalExpectedSplitValueRepository;
import com.allianz.sd.core.jpa.repository.GoalExpectedValueRepository;
import com.allianz.sd.core.jpa.repository.GoalExpectedVersionRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingSplitRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingSplitValueRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingValueRepository;
import com.allianz.sd.core.jpa.repository.GoalSettingVersionRepository;
import com.allianz.sd.core.jpa.repository.PerformanceTableMonthRepository;
import com.allianz.sd.core.jpa.repository.PersonalProgressRepository;
import com.allianz.sd.core.jpa.repository.SysDataRepository;
import com.allianz.sd.core.jpa.repository.SysYearDataRepository;
import com.allianz.sd.core.jpa.repository.TeamProgressDetailRepository;
import com.allianz.sd.core.jpa.repository.TeamProgressRepository;
import com.allianz.sd.core.jpa.repository.VersionControlAgentRepository;
import com.allianz.sd.core.jpa.repository.VersionControlRepository;
import com.allianz.sd.core.service.bean.GoalSettingStatus;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/restful")
public class TestRestfulController {


    @Autowired
    private OrganizationService organizationService = null;

    @Autowired
    private CalendarRepository calendarRepository = null;

    @Autowired
    private CustomerContactRepository customerContactRepository = null;

    @Autowired
    private CodeRepository codeRepository = null;

    @Autowired
    private CodeTypeRepository codeTypeRepository = null;

    @Autowired
    private UpdateCodeTable updateCodeTable = null;

    @Autowired
    private UpdateSysTable updateSysTable = null;

    @Autowired
    private UpdatePormanceTable updatePormanceTable = null;

    @Autowired
    private UpdateGoalTable updateGoalTable = null;

    @Autowired
    private UpdateAgentDataTable updateAgentDataTable = null;
    
    @Autowired
    private DateService dateService = null;

    @Autowired
    private UpdateLanguage updateLanguage = null;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AgentUpdateVersionRepository agentUpdateVersionRepository = null;

    @Autowired
    private AgentDataRepository agentDataRepository = null;

    @Autowired
    private AgentYearDataRepository agentYearDataRepository = null;

    @Autowired
    private VersionControlRepository versionControlRepository = null;

    @Autowired
    private VersionControlAgentRepository versionControlAgentRepository = null;

    @Autowired
    private SequenceIDService sequenceIDService;

    @Autowired
    private SysDataRepository sysDataRepository;
    
    @Autowired
    private SysYearDataRepository sysYearDataRepository;
    
    @Autowired
    private PerformanceTableMonthRepository performanceTableMonthRepository;

    @Autowired
    private AgencyPlanDetailSyncRepository agencyPlanDetailSyncRepository;
    
    @Autowired
    private PersonalProgressRepository personalProgressRepository;
    
    @Autowired
    private TeamProgressRepository teamProgressRepository;
    
    @Autowired
    private TeamProgressDetailRepository teamProgressDetailRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    private GoalSettingRepository goalSettingRepository;
    
    @Autowired
    private GoalSettingVersionRepository goalSettingVersionRepository;
    
    @Autowired
    private GoalSettingValueRepository goalSettingValueRepository;
    
    @Autowired
    private GoalSettingSplitRepository goalSettingSplitRepository;
    
    @Autowired
    private GoalSettingSplitValueRepository goalSettingSplitValueRepository;
    
    @Autowired
    private GoalExpectedVersionRepository goalExpectedVersionRepository;
    
    @Autowired
    private GoalExpectedValueRepository goalExpectedValueRepository;
    
    @Autowired
    private GoalSettingSplitValueRepository goalSettingSplitvalueRepository;

    @Autowired
    private GoalExpectedSplitValueRepository goalExpectedSplitValueRepository;

    @Autowired
    private UpdateActualSalesDataTable updateActualSalesDataTable;

    @Autowired
    private UpdateAgentDailyDataTable updateAgentDailyDataTable;

    @Autowired
    private UpdateAgencyPlanTable updateAgencyPlanTable;

    @Autowired
    private AgencyPlanRepository agencyPlanRepository;

    @Autowired
    private AgencyPlanDetailRepository agencyPlanDetailRepository;

    @Autowired
    private AgentSalesDataRepository agentSalesDataRepository;

    @Autowired
    private AgentDailyDataRepository agentDailyDataRepository;

    @Autowired
    private AgencyPlanPileRepository agencyPlanPileRepository;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean ;

    @Autowired
    private AgentDeviceRepository agentDeviceRepository;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private SysDataService sysDataService;

    @RequestMapping("/ApproveGoal/{agentID}")
    public List<GoalSettingValue> approveGoal(
            @PathVariable("agentID") String agentID
    ) {
        int dataYear = dateService.getCurrentYear();
        return goalService.getGoalValue(dataYear,organizationService.getOrganizationalUnit(),agentID);
    }

    @RequestMapping("/calcTeamProgress/{agentID}/{dataYear}")
    public void calcTeamProgress(@PathVariable("agentID") String agentID,
                               @PathVariable("dataYear") int dataYear) {

        String organizationUnit = organizationService.getOrganizationalUnit();

        AgencyPlanIdentity agencyPlanIdentity = new AgencyPlanIdentity();
        agencyPlanIdentity.setAgentID(agentID);
        agencyPlanIdentity.setDataYear(dataYear);
        agencyPlanIdentity.setOrganizationalUnit(organizationUnit);

        List<AgencyPlanDetail> agencyPlanDetails = agencyPlanDetailRepository.findDetailByAgentID(dataYear,agentID);

        Optional<AgencyPlan> optionalAgencyPlan = agencyPlanRepository.findById(agencyPlanIdentity);

        List<AgencyPlan> agencyPlans = new ArrayList<>();
        agencyPlans.add(optionalAgencyPlan.get());

        progressService.calcTeamProgress(agencyPlans,agencyPlanDetails);
    }

    @RequestMapping("/agentDevice/clean")
    @ResponseBody
    public boolean agentDeviceClean() {
        boolean isSuccess = false;

        try{
            agentDeviceRepository.deleteAll();

            isSuccess = true;
        }catch(Exception e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    @RequestMapping("/actual/update")
    @ResponseBody
    public Boolean actualUpdate(@RequestParam("file") MultipartFile file) {
        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updateActualSalesDataTable.update(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/dailyData/update")
    @ResponseBody
    public Boolean dailyDataUpdate(@RequestParam("file") MultipartFile file) {
        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updateAgentDailyDataTable.update(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/sysdata/update")
    @ResponseBody
    public Boolean sysdataUpdate(@RequestParam("file") MultipartFile file) {
        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updateSysTable.update(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping("/sysdata")
    @ResponseBody
    public List<SysData> sysData() {
        return sysDataRepository.findAll();
    }

    @PostMapping("/sysdata/add")
    @ResponseBody
    public SysData addSysData(@RequestBody SysData sysData) {
        return sysDataRepository.save(sysData);
    }
    
    @RequestMapping("/sysyeardata")
    @ResponseBody
    public List<SysYearData> sysyearData() {
        return sysYearDataRepository.findAll();
    }
    
    @PostMapping("/sysyeardata/add")
    @ResponseBody
    public SysYearData addSysYearData(@RequestBody SysYearData sysyearData) {
    	return sysYearDataRepository.save(sysyearData);
    }

    @RequestMapping("/PerformanceTableMonth/update")
    @ResponseBody
    public Boolean performanceTableMonthUpdate(@RequestParam("file") MultipartFile file) {
        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updatePormanceTable.update(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    @RequestMapping("/PerformanceTableMonth")
    @ResponseBody
    public List<PerformanceTableMonth> PerformanceTableMonth() {
        return performanceTableMonthRepository.findAll();
    }
    
    @PostMapping("/PerformanceTableMonth/add")
    @ResponseBody
    public PerformanceTableMonth addPerformanceTableMonth(@RequestBody PerformanceTableMonth performanceTableMonth) {
    	return performanceTableMonthRepository.save(performanceTableMonth);
    }

    @RequestMapping("/version")
    @ResponseBody
    public List<VersionControl> versions() {
        return versionControlRepository.findAll();
    }

    @PostMapping("/version/add")
    @ResponseBody
    public VersionControl addVersion(@RequestParam("version") String version,
                                     @RequestParam("isEnabled") String isEnabled,
                                     @RequestParam("deviceSystem") String deviceSystem,
                                     @RequestParam("updateType") String updateType,
                                     @RequestParam("description") String description,
                                     @RequestParam("file") MultipartFile file,
                                     @RequestParam("appLink") String appLink) {
        VersionControl versionControl = new VersionControl();
        try{
            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            File sqlFile = resource.getFile();

//            appUpdateSQLService.storeSQLFile(version,sqlFile);

            VersionControlIdentity identity = new VersionControlIdentity();
            identity.setVersion(version);
            identity.setDeviceSystem(deviceSystem);

            versionControl.setIdentity(identity);
            versionControl.setEnabled(isEnabled);
            versionControl.setUpdateType(updateType);
            versionControl.setDescription(description);
            versionControl.setAppLink(appLink);

            versionControlRepository.save(versionControl);

            sqlFile.delete();
        }catch(Exception e) {
            e.printStackTrace();
        }

        return versionControl;
    }

    @RequestMapping("/version/agent")
    @ResponseBody
    public List<VersionControlAgent> versionAgents() {
        return versionControlAgentRepository.findAll();
    }

    @RequestMapping("/version/agent/add")
    @ResponseBody
    public List<VersionControlAgent> addVersionAgents(@RequestParam String version,
                                                      @RequestParam String agentIds) {

        this.versionControlAgentRepository.deleteVersion(version);

        String[] ids = agentIds.split(",");
        for(String id : ids) {
            VersionControlAgentIdentity identity = new VersionControlAgentIdentity();

            identity.setAgentId(id);
            identity.setVersion(version);

            VersionControlAgent versionControlAgent = new VersionControlAgent();
            versionControlAgent.setIdentity(identity);

            versionControlAgentRepository.save(versionControlAgent);
        }

        return versionControlAgentRepository.findAll();
    }


    @RequestMapping("/agentdata")
    @ResponseBody
    public List<AgentData> agentdata() {
        return agentDataRepository.findAll();
    }

    @RequestMapping("/agentData/update")
    @ResponseBody
    public Boolean agentDataUpdate(@RequestParam("file") MultipartFile file) {
        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updateAgentDataTable.update(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    

    

    @RequestMapping("/agentyeardata")
    @ResponseBody
    public List<AgentYearData> agentyeardata() {
        return agentYearDataRepository.findAll();
    }


    @RequestMapping("/test")
    @ResponseBody
    public void test() {
        try{


        }catch(Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("/customer")
    @ResponseBody
    public List<Customer> customer() {
        return customerRepository.findAll();
//        return vwCustomerRepository.findAll();
    }

    @RequestMapping("/customer/contact")
    @ResponseBody
    public List<CustomerContact> customerContact() {
        return customerContactRepository.findAll();
    }

    @RequestMapping("/customer/contact/delete")
    @ResponseBody
    public void deleteCustomerContact() {
        customerContactRepository.deleteAll();
    }

    @RequestMapping("/customer/delete")
    @ResponseBody
    public void deleteCustomer() {
        this.customerRepository.deleteAll();
    }


    @RequestMapping("/customer/addDeleteCustomer/{agentID}")
    @ResponseBody
    public Customer addDeleteCustomer(@ApiParam(value = "agentID",required=true) @PathVariable("agentID") String agentID) {
        Customer customer = new Customer();

        List<BigDecimal> list = sequenceIDService.getId("customer",new BigDecimal(1));

        Date todayDate = dateService.getTodayDate();

        SysYearData sysYearData = sysDataService.getSysYearData(organizationService.getOrganizationalUnit(),agentID,dateService.getCurrentYear());
        Integer overTimeDayAutoDelete = sysYearData.getSysData().getCustomerDataDeleteLimit();
        Date overtime = dateService.addDate(todayDate, java.util.Calendar.DATE,overTimeDayAutoDelete * -1);

        customer.setOrganizationalUnit(organizationService.getOrganizationalUnit());
        customer.setCustomerID(list.get(0).intValue());
        customer.setFirstName("titan");
        customer.setLastName("shanwen");
        customer.setFollow("N");
        customer.setAgentId(agentID);
        customer.setDataSource("APP");
        customer.setCreateBy("admin");
        customer.setUpdateBy("admin");
        customer.setDataTime(new java.util.Date());
        customer.setOverTimeAlertTime(overtime);
        customer.setCompleteness((float) 0.5);

        Customer c = (Customer) customerRepository.save(customer);
        agentUpdateVersionRepository.insertAgentLastTme(new java.util.Date(),"1", DataCategory.CUSTOMER,c.getCustomerID());

        return c;
    }


    @RequestMapping("/customer/addOverTimeCustomer/{agentID}")
    @ResponseBody
    public Customer addOverTimeCustomer(@ApiParam(value = "agentID",required=true) @PathVariable("agentID") String agentID) {
        Customer customer = new Customer();

        List<BigDecimal> list = sequenceIDService.getId("customer",new BigDecimal(1));

        Date todayDate = dateService.getTodayDate();

        SysYearData sysYearData = sysDataService.getSysYearData(organizationService.getOrganizationalUnit(),agentID,dateService.getCurrentYear());
        Integer overTimeMonthAlert = sysYearData.getSysData().getCustomerDataTrackingLimit();


        Date overtime = dateService.addDate(todayDate, java.util.Calendar.MONTH,overTimeMonthAlert * -1);
        overtime = dateService.addDate(overtime,java.util.Calendar.DATE,-1);

        customer.setOrganizationalUnit(organizationService.getOrganizationalUnit());
        customer.setCustomerID(list.get(0).intValue());
        customer.setFirstName("titan");
        customer.setLastName("shanwen");
        customer.setFollow("N");
        customer.setAgentId(agentID);
        customer.setDataSource("APP");
        customer.setCreateBy("admin");
        customer.setUpdateBy("admin");
        customer.setDataTime(new java.util.Date());
        customer.setAppUpdateTime(overtime);
        customer.setCompleteness((float) 0.5);
//        customer.setOverTimeAlertTime(new java.util.Date());

        Customer c = (Customer) customerRepository.save(customer);
        agentUpdateVersionRepository.insertAgentLastTme(new java.util.Date(),"1", DataCategory.CUSTOMER,c.getCustomerID());

        return c;
    }

    @RequestMapping("/customer/add/{agentID}/{CustomerID}")
    @ResponseBody
    public Customer addCustomer(
            @PathVariable("agentID") String agentID,
            @PathVariable("CustomerID") Integer customerID) {
        Customer customer = new Customer();

        customer.setCustomerID(customerID);
        customer.setOrganizationalUnit(organizationService.getOrganizationalUnit());
        customer.setFirstName("titan" + customerID);
        customer.setLastName("shanwen");
        customer.setFollow("N");
        customer.setAgentId(agentID);
        customer.setDataSource("OPUS");
        customer.setCreateBy("admin");
        customer.setUpdateBy("admin");

        customer.setDataTime(new java.util.Date());

        CustomerTel tel = new CustomerTel();
        tel.setOrganizationalUnit(organizationService.getOrganizationalUnit());
        tel.setTelType("Home");
        tel.setTel("0987654321");
        tel.setDataSource("APP");
        tel.setCreateBy("admin");
        tel.setUpdateBy("admin");

        customer.addTel(tel);

        agentUpdateVersionRepository.insertAgentLastTme(new java.util.Date(),agentID, DataCategory.CUSTOMER,customerID);

        return (Customer) customerRepository.save(customer);
    }

    @PostMapping("/updateLanguage")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = updateLanguage.getLanguage(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/updateLanguage")
    @ResponseBody
    public String uploadFileAndSaveLanguage(@RequestParam("file") MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = updateLanguage.saveLanguage(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @RequestMapping("/codetype")
    @ResponseBody
    public List<CodeType> codetype() {
        return codeTypeRepository.findAll();
    }

    @RequestMapping("/codetype/update")
    @ResponseBody
    public Boolean codeTypeUpdate(@RequestParam("file") MultipartFile file) {
        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updateCodeTable.updateType(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }





    @RequestMapping("/code")
    @ResponseBody
    public List<Code> code() {
        return codeRepository.findAll();
    }


    @RequestMapping("/code/update")
    @ResponseBody
    public Boolean codeUpdate(@RequestParam("file") MultipartFile file) {

        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updateCodeTable.update(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/calendar/delete")
    @ResponseBody
    public void deleteCalendar() {
        this.calendarRepository.deleteAll();
    }

    @RequestMapping("/calendar/addReminder")
    @ResponseBody
    public void calendarAddReminder(
            @RequestParam(value = "agentID", required = true) String agentID) {

        Date today = dateService.getTodayDate();

        //建立以分鐘為單位的測試案例
        Map<String,String> minTestMap = new HashedMap<>();
        minTestMap.put("5","6");
        minTestMap.put("15","7");
        minTestMap.put("30","8");
        minTestMap.put("60","9");
        minTestMap.put("120","10");

        for(String min : minTestMap.keySet()) {
            Calendar calendar = new Calendar();
            calendar.setCalendarId(sequenceIDService.getId("calendar",new BigDecimal(1)).get(0).intValue());
            calendar.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            calendar.setAgentId(agentID);
            calendar.setTitle("測試推播"+min+"分鐘");
            calendar.setLocation(calendar.getTitle() + "地址地址!!");
            calendar.setAlert("Y");
            calendar.setAlert1(minTestMap.get(min));
            calendar.setCalendarType("1");
            calendar.setDataSource("APP");
            calendar.setCreateBy("admin");
            calendar.setUpdateBy("admin");
            calendar.setDataTime(new java.util.Date());


            java.util.Date startTimeDate = dateService.addDate(today,java.util.Calendar.MINUTE,Integer.parseInt(min) + 1);
            java.util.Date endTimeDate = dateService.addDate(startTimeDate,java.util.Calendar.MINUTE,5);

            calendar.setAllDay("N");
            calendar.setStartTime(startTimeDate);
            calendar.setEndTime(endTimeDate);

            calendarRepository.save(calendar);
        }

        //建立以天為單位的測試案例
        Map<String,String> dayTestMap = new HashedMap<>();
        dayTestMap.put("1","11");
        dayTestMap.put("2","12");
        dayTestMap.put("7","13");

        for(String day : dayTestMap.keySet()) {
            Calendar calendar = new Calendar();
            calendar.setCalendarId(sequenceIDService.getId("calendar",new BigDecimal(1)).get(0).intValue());
            calendar.setOrganizationalUnit(organizationService.getOrganizationalUnit());
            calendar.setAgentId(agentID);
            calendar.setTitle("測試推播"+day+"天");
            calendar.setLocation(calendar.getTitle() + "地址地址!!");
            calendar.setAlert("Y");
            calendar.setAlert1(dayTestMap.get(day));
            calendar.setCalendarType("1");
            calendar.setDataSource("APP");
            calendar.setCreateBy("admin");
            calendar.setUpdateBy("admin");
            calendar.setDataTime(new java.util.Date());


            java.util.Date startTimeDate = dateService.addDate(today,java.util.Calendar.DATE,Integer.parseInt(day));
            java.util.Date endTimeDate = dateService.addDate(startTimeDate,java.util.Calendar.MINUTE,5);

            calendar.setAllDay("N");
            calendar.setStartTime(startTimeDate);
            calendar.setEndTime(endTimeDate);

            calendarRepository.save(calendar);
        }
    }


    @RequestMapping("/calendar")
    @ResponseBody
    public List<Calendar> calendar() {

        return calendarRepository.findAll();

    }

    @RequestMapping("/calendar/minReminder")
    @ResponseBody
    public List<Calendar> calendarMinReminder() {

        String today = dateService.getTodayString();
        today = today.substring(0,12) + "00";

        java.util.Date now = dateService.parseDateString2Date(today);

        return calendarRepository.findByReminder(new Date(now.getTime()),"N");
    }

    @RequestMapping("/calendar/dayReminder")
    @ResponseBody
    public List<Calendar> calendayDayReminder() {

        String today = dateService.getTodayString();
        today = today.substring(0,8) + "000000";

        java.util.Date now = dateService.parseDateString2Date(today);


        return calendarRepository.findByReminder(new Date(now.getTime()),"Y");
    }



    @GetMapping("/salesData")
    @ResponseBody
    private List<AgentSalesData> getSalesData() {
        return agentSalesDataRepository.findAll();
    }

    @GetMapping("/dailyData")
    @ResponseBody
    private List<AgentDailyData> getAgentDailyData() {
        return agentDailyDataRepository.findAll();
    }

    @GetMapping("/agencyPlan")
    @ResponseBody
    public List<AgencyPlan> getAgencyPlan() {
        return agencyPlanRepository.findAll();
    }

    @GetMapping("/agencyPlanDetail")
    @ResponseBody
    public List<AgencyPlanDetail> getAgencyPlanDetail() {
        return agencyPlanDetailRepository.findAll();
    }

    @GetMapping("/agencyPlanPile")
    @ResponseBody
    private List<AgencyPlanPile> getAgencyPlanPile() {
        return agencyPlanPileRepository.findAll();
    }


    @RequestMapping("/agencyPlan/update")
    @ResponseBody
    public Boolean agencyPlanUpdate(@RequestParam("file") MultipartFile file) {
        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updateAgencyPlanTable.update(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @PostMapping("/agencyPlanDetailSync/add")
    @ResponseBody
    public AgencyPlanDetailSync addAgencyPlanDetailSync(@RequestBody AgencyPlanDetailSync agencyPlanDetailSync) {
    	return agencyPlanDetailSyncRepository.save(agencyPlanDetailSync);
    }
    
    @PostMapping("/personProgress/add")
    @ResponseBody
    public PersonalProgress addPersonalProgress(@RequestBody PersonalProgress personalProgress) {
        return personalProgressRepository.save(personalProgress);
    }

    @PostMapping("/teamProgress/add")
    @ResponseBody
    public TeamProgress addTeamProgress(@RequestBody TeamProgress teamProgress) {
    	return teamProgressRepository.save(teamProgress);
    }
    
    @PostMapping("/teamProgressDetail/add")
    @ResponseBody
    public TeamProgressDetail addTeamProgressDetail(@RequestBody TeamProgressDetail progressDetail) {
    	return teamProgressDetailRepository.save(progressDetail);
    }

    @RequestMapping("/goalSetting/update")
    @ResponseBody
    public Boolean goalSettingUpdate(@RequestParam("file") MultipartFile file) {
        boolean result = false;
        try{

            String fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            result = updateGoalTable.update(resource.getFile());
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    


    @GetMapping("/goalSetting")
    @ResponseBody
    private List<GoalSetting> getGoalSetting() {
        return goalSettingRepository.findAll();
    }

    @GetMapping("/goalSetting/{agentID}/{dataYear}")
    @ResponseBody
    private GoalSetting getGoalSetting(
            @PathVariable("agentID") String agentID,
            @PathVariable("dataYear") int dataYear) {
        AgentYearDataIdentity agentYearDataIdentity = new AgentYearDataIdentity();
        agentYearDataIdentity.setAgentID(agentID);
        agentYearDataIdentity.setDataYear(dataYear);
        agentYearDataIdentity.setOrganizationalUnit(organizationService.getOrganizationalUnit());

        return goalSettingRepository.findById(agentYearDataIdentity).get();
    }

    @GetMapping("/goalSettingVersion")
    @ResponseBody
    private List<GoalSettingVersion> getGoalSettingVersion() {
        return goalSettingVersionRepository.findAll();
    }

    @GetMapping("/goalSettingVersion/{agentID}/{dataYear}")
    @ResponseBody
    private List<GoalSettingVersion> getGoalSettingVersion(
            @PathVariable("agentID") String agentID,
            @PathVariable("dataYear") int dataYear) {
        return goalSettingVersionRepository.getGoalSettingVersion(agentID,organizationService.getOrganizationalUnit(),dataYear);
    }

    @GetMapping("/goalSettingValue")
    @ResponseBody
    private List<GoalSettingValue> getGoalSettingValue() {
        return goalSettingValueRepository.findAll();
    }

    @GetMapping("/goalSettingValue/{goalSettingSeq")
    @ResponseBody
    private List<GoalSettingValue> getGoalSettingValue(
            @PathVariable("goalSettingSeq") int goalSettingSeq) {
        return goalSettingValueRepository.getGoalSettingValue(goalSettingSeq);
    }

    @GetMapping("/goalSettingSplit")
    @ResponseBody
    private List<GoalSettingSplit> getGoalSettingSplit() {
        return goalSettingSplitRepository.findAll();
    }

    @PostMapping("goalSettingSplit/update")
    @ResponseBody
    private void getGoalSettingSplit(@RequestBody List<GoalSettingSplit> datas) {
        for(GoalSettingSplit goalSettingSplit : datas) {
            goalSettingSplitRepository.save(goalSettingSplit);
        }
    }

    @PostMapping("/goalSetting/add")
    @ResponseBody
    public GoalSetting addGoalSetting(@RequestBody GoalSetting goalSetting) {
    	return goalSettingRepository.save(goalSetting);
    }
    
    @PostMapping("/goalSettingVersion/add")
    @ResponseBody
    public GoalSettingVersion addGoalSettingVersion(@RequestBody GoalSettingVersion version) {
    	return goalSettingVersionRepository.save(version);
    }
    
    @PostMapping("/goalSettingValue/add")
    @ResponseBody
    public GoalSettingValue addGoalSettingValue(@RequestBody GoalSettingValue goalSettingValue) {
    	return goalSettingValueRepository.save(goalSettingValue);
    }
    
    @PostMapping("/goalSettingSplit/add")
    @ResponseBody
    public GoalSettingSplit addGoalSettingSplit(@RequestBody GoalSettingSplit goalSettingSplit) {
    	return goalSettingSplitRepository.save(goalSettingSplit);
    }

    @GetMapping("/goalSettingSplitValue")
    @ResponseBody
    public List<GoalSettingSplitValue> getGoalSettingSplitValue() {
        return goalSettingSplitValueRepository.findAll();
    }


    @PostMapping("/goalSettingSplitValue/add")
    @ResponseBody
    public GoalSettingSplitValue addGoalSettingSplitValue(@RequestBody GoalSettingSplitValue goalSettingSplitValue) {
    	return goalSettingSplitValueRepository.save(goalSettingSplitValue);
    }
    
    @PostMapping("/goalExpectedVersion/add")
    @ResponseBody
    public GoalExpectedVersion addGoalExpectedVersion(@RequestBody GoalExpectedVersion version) {
    	return goalExpectedVersionRepository.save(version);
    }
    
    @PostMapping("/goalExpectedValue/add")
    @ResponseBody
    public GoalExpectedValue addGoalExpectedValue(@RequestBody GoalExpectedValue goalExpectedValue) {
    	return goalExpectedValueRepository.save(goalExpectedValue);
    }

    
    @PostMapping("/goalExpectedSplitValue/add")
    @ResponseBody
    public GoalExpectedSplitValue addGoalExpectedSplitValue(@RequestBody GoalExpectedSplitValue goalExpectedSplitValue) {
    	return goalExpectedSplitValueRepository.save(goalExpectedSplitValue);
    }
    
    @PostMapping("/pushTest/changeDeadline")
    @ResponseBody
    public String changeDeadline(
    		@RequestParam(value = "agentID", required = true) String agentID,
    		@RequestParam(value = "year", required = true) Integer year)  {
    	JSONObject returnObj = new JSONObject();
    	try {

    	    Date deadline = dateService.getTodayDate();
    	    deadline = dateService.addDate(deadline,java.util.Calendar.DATE,-1);

	    	AgentYearDataIdentity identity = new AgentYearDataIdentity();
	    	identity.setDataYear(year);
	    	identity.setAgentID(agentID);
	    	identity.setOrganizationalUnit(organizationService.getOrganizationalUnit());
			GoalSetting goalSetting = goalSettingRepository.getOne(identity);
			if(goalSetting!=null)goalSetting.setGoalSettingDeadline(deadline);
	    	goalSettingRepository.save(goalSetting);
	    	returnObj.put("success", true);
		}catch (Exception e) {
			returnObj.put("success", false);
			e.printStackTrace();
		}
		
	    return returnObj.toString();
    }
    
    @PostMapping("/pushTest/changeSubmitDeadline")
    @ResponseBody
    public String changeSubmitDeadline(
    		@RequestParam(value = "agentID", required = true) String agentID,
    		@RequestParam(value = "year", required = true) Integer year)  {
    	JSONObject returnObj = new JSONObject();
    	try {
	    	List<GoalSettingVersion> goalSettingVersions = goalSettingVersionRepository.findByStatusAndDataYearAndAgentID(
	    			GoalSettingStatus.PENDING_APPROVAL.toString(), year, agentID);
	    	if(goalSettingVersions.size()!=0) {
	    		GoalSettingVersion goalSettingVersion= goalSettingVersions.get(0);

	    		Date dueDate = dateService.addDate(goalSettingVersion.getGoalSubmitDate(), java.util.Calendar.DATE, -10); 
	    		goalSettingVersion.setGoalSubmitDate(dueDate);
	    		goalSettingVersionRepository.save(goalSettingVersion);
	    		returnObj.put("success", true);
	    	}else {
	    		returnObj.put("success", false);
	    		returnObj.put("errorMessage", "無待審核資料");
	    	}
		}catch (Exception e) {
			returnObj.put("success", false);
			e.printStackTrace();
		}
	    return returnObj.toString();
    }
    
    @PostMapping("/pushTest/changeGoalLeader")
    @ResponseBody
    public String changeGoalLeader(
    		@RequestParam(value = "agentID", required = true) String agentID,
    		@RequestParam(value = "leader", required = true) String leader,
    		@RequestParam(value = "year", required = true) Integer year)  {
    	JSONObject returnObj = new JSONObject();
    	try {
    		AgentYearDataIdentity id = new AgentYearDataIdentity();
    		id.setAgentID(agentID);
    		id.setDataYear(year);
    		id.setOrganizationalUnit(organizationService.getOrganizationalUnit());
    		AgentYearData agentYearData = agentYearDataRepository.findById(id).orElse(null);    		
    		if(agentYearData!=null) {
    			agentYearData.setGoalSigningSupervisor(leader);
    			agentYearDataRepository.save(agentYearData);
    			returnObj.put("success", true);
    		}else {
    			returnObj.put("success", false);
    			returnObj.put("errorMessage", "無agent資料");
    		}
    	}catch (Exception e) {
    		returnObj.put("success", false);
    		e.printStackTrace();
    	}
    	return returnObj.toString();
    }
    
    @RequestMapping("/quartz/{job}")
    @ResponseBody
    public String quartzTrigger( @PathVariable("job") String jobName) {

    	JSONObject returnObj = new JSONObject();
    	try {
            //Create a new Job 
//    		("calculationAgencyPlanJob", "calculationAgencyPlanGroup");
    		String name = jobName + "Job";
    		String grouop = jobName + "Group";
            JobKey jobKey = JobKey.jobKey(name, grouop);
            
            System.out.println(jobName+","+ jobName+"Group"+",class:"+jobName);
            
            Class c = Class.forName("com.allianz.sd.core.schedule.job." + jobName);  
            JobDetail job = JobBuilder.newJob(c).withIdentity(jobKey).storeDurably(true).build();

            Scheduler scheduler = schedulerFactoryBean.getScheduler();
             //Register this job to the scheduler   
             scheduler.addJob(job, true);

             //Start a Job      
             scheduler.triggerJob(jobKey);
            
    		returnObj.put("success", true);
    	}catch (Exception e) {
    		returnObj.put("success", false);
    		e.printStackTrace();
		}
    	
        return returnObj.toString();
    }
    
    
    /**
     *update from json data
     * 
     */
    
    @RequestMapping(value="/agencyPlan/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int agencyplanaUpdateFromJSON(@RequestBody List<AgencyPlan> agencyPlans) {
    	agencyPlanRepository.deleteAll();
        return agencyPlanRepository.saveAll(agencyPlans).size();
    }
    @RequestMapping(value="/agencyPlanDetail/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int agencyplanDetailaUpdateFromJSON(@RequestBody List<AgencyPlanDetail> agencyPlanDetails) {
    	agencyPlanDetailRepository.deleteAll();
    	return agencyPlanDetailRepository.saveAll(agencyPlanDetails).size();
    }
    @RequestMapping(value="/agencyPlanPile/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int agencyplanPileaUpdateFromJSON(@RequestBody List<AgencyPlanPile> datas) {
    	agencyPlanDetailRepository.deleteAll();
    	return agencyPlanPileRepository.saveAll(datas).size();
    }
    @RequestMapping(value="/agentData/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int agentDataUpdateFromJSON(@RequestBody List<AgentData> agentDatas) {
    	agentDataRepository.deleteAll();
        return agentDataRepository.saveAll(agentDatas).size();
    }
    @RequestMapping(value="/agentSalesData/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int agentSalesDataUpdateFromJSON(@RequestBody List<AgentSalesData> agentSalesData) {
    	agentSalesDataRepository.deleteAll();
    	return agentSalesDataRepository.saveAll(agentSalesData).size();
    }
    @RequestMapping(value="/agentYearData/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int agentYearDataUpdateFromJSON(@RequestBody List<AgentYearData> agentYearData) {
    	agentYearDataRepository.deleteAll();
    	return agentYearDataRepository.saveAll(agentYearData).size();
    }
    @RequestMapping(value="/goalSetting/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int goalSettingUpdateFromJSON(@RequestBody List<GoalSetting> goalSetting) {
    	goalSettingRepository.deleteAll();
    	return goalSettingRepository.saveAll(goalSetting).size();
    }
    @RequestMapping(value="/goalSettingSplit/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int goalSettingSplitUpdateFromJSON(@RequestBody List<GoalSettingSplit> goalSettingSplit) {
    	goalSettingSplitRepository.deleteAll();
    	return goalSettingSplitRepository.saveAll(goalSettingSplit).size();
    }
    @RequestMapping(value="/goalSettingSplitvalue/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int goalSettingSplitvalueUpdateFromJSON(@RequestBody List<GoalSettingSplitValue> goalSettingSplitvalue) {
    	goalSettingSplitvalueRepository.deleteAll();
    	return goalSettingSplitvalueRepository.saveAll(goalSettingSplitvalue).size();
    }
    @RequestMapping(value="/goalSettingValue/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int goalSettingValueUpdateFromJSON(@RequestBody List<GoalSettingValue> goalSettingValue) {
    	goalSettingValueRepository.deleteAll();
    	return goalSettingValueRepository.saveAll(goalSettingValue).size();
    }
    @RequestMapping(value="/goalSettingVersion/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int goalSettingVersionUpdateFromJSON(@RequestBody List<GoalSettingVersion> goalSettingVersion) {
    	goalSettingVersionRepository.deleteAll();
    	return goalSettingVersionRepository.saveAll(goalSettingVersion).size();
    }
    @RequestMapping(value="/performanceTableMonth/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int performanceTableMonthUpdateFromJSON(@RequestBody List<PerformanceTableMonth> performanceTableMonth) {
    	performanceTableMonthRepository.deleteAll();
    	return performanceTableMonthRepository.saveAll(performanceTableMonth).size();
    }
    @RequestMapping(value="/sysData/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int sysDataUpdateFromJSON(@RequestBody List<SysData> sysData) {
    	sysDataRepository.deleteAll();
    	return sysDataRepository.saveAll(sysData).size();
    }
    @RequestMapping(value="/sysYearData/update/JSON", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public int sysYearDataUpdateFromJSON(@RequestBody List<SysYearData> sysYearData) {
    	sysYearDataRepository.deleteAll();
    	return sysYearDataRepository.saveAll(sysYearData).size();
    }
}
