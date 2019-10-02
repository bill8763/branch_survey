package com.allianz.sd.core.console;

import com.allianz.sd.core.api.model.Error;
import com.allianz.sd.core.jpa.bean.DataCategory;
import com.allianz.sd.core.jpa.model.AgentData;
import com.allianz.sd.core.jpa.repository.AgentDataRepository;
import com.allianz.sd.core.service.AgentUpdateVersionService;
import com.allianz.sd.core.service.StringService;
import com.allianz.sd.core.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@Controller
@RequestMapping("/testing")
public class TestingConsoleController {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StringService stringService;

    @Autowired
    private AgentDataRepository agentDataRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AgentUpdateVersionService agentUpdateVersionService;

    @RequestMapping("/apiFullCheck")
    public String apiFullCheck(Model model) {

        agentUpdateVersionService.clearAgentTableCache(DataCategory.ACTUAL);
        agentUpdateVersionService.clearAgentTableCache(DataCategory.CONFIGURATION);
        agentUpdateVersionService.clearAgentTableCache(DataCategory.PROGRESS);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建議使用這種

        Map<AgentData,Error[]> agentResultMap = new LinkedHashMap<>();

        List<AgentData> agentDataList = agentDataRepository.findAvailableAgentData();

        for(AgentData agentData : agentDataList) {

            executeAPITesting(mockMvc,agentData,agentResultMap);
        }

        model.addAttribute("agentResultMap",agentResultMap);

        return "apiFullCheck";
    }

    @RequestMapping("/apiFullCheck/{agentID}")
    public String apiFullCheck(
            Model model,
            @PathVariable("agentID") String agentID) {

        agentUpdateVersionService.clearAgentTableCache(DataCategory.ACTUAL);
        agentUpdateVersionService.clearAgentTableCache(DataCategory.CONFIGURATION);
        agentUpdateVersionService.clearAgentTableCache(DataCategory.PROGRESS);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建議使用這種

        Map<AgentData,Error[]> agentResultMap = new LinkedHashMap<>();

        AgentData agentData = agentDataRepository.findById(agentID).get();

        executeAPITesting(mockMvc,agentData,agentResultMap);

        model.addAttribute("agentResultMap",agentResultMap);

        return "apiFullCheck";
    }

    private void executeAPITesting(MockMvc mockMvc,AgentData agentData , Map<AgentData,Error[]> agentResultMap) {
        Error[] errors = new Error[10];

        Error goalError = null;
        Error configError = null;
        Error progressError = null;
        Error agencyplanError = null;
        Error actualError = null;
        Error calendarError = null;
        Error messageError = null;
        Error customerError = null;
        Error noteError = null;
        Error goalExpectedError = null;

        try{

            Map<String, Object> claims = new HashMap<>();
            claims.put("AgentID",agentData.getAgentID());
            String jwtToken = tokenService.generateToken(claims);

            if(stringService.isNotNull(jwtToken)) {
                goalError = mockGoalAPI(jwtToken,mockMvc);
                configError = mockConfigurationAPI(jwtToken,mockMvc);
                progressError = mockProgressAPI(jwtToken,mockMvc);
                agencyplanError = mockAgencyPlanAPI(jwtToken,mockMvc);
                actualError = mockActualAPI(jwtToken,mockMvc);
                calendarError = mockCalendarAPI(jwtToken,mockMvc);
                messageError = mockMessageAPI(jwtToken,mockMvc);
                customerError = mockCustomerAPI(jwtToken,mockMvc);
                noteError = mockNoteAPI(jwtToken,mockMvc);
                goalExpectedError = mockGoalExpectedAPI(jwtToken,mockMvc);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        errors[0] = goalError;
        errors[1] = configError;
        errors[2] = progressError;
        errors[3] = agencyplanError;
        errors[4] = actualError;
        errors[5] = calendarError;
        errors[6] = messageError;
        errors[7] = customerError;
        errors[8] = noteError;
        errors[9] = goalExpectedError;


        agentResultMap.put(agentData,errors);
    }

    private Error mockGoalAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2018-01-27T00:00:00%2B0800");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/Goal",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockConfigurationAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2018-01-27T00:00:00+0800");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/Configuration",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockProgressAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2018-01-27T00:00:00+0800");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/Progress",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockAgencyPlanAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2018-01-27T00:00:00%2B0800");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/AgencyPlan",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockActualAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2018-01-27T00:00:00+0800");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/Actual",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockCalendarAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2019-01-13T00:00:00.000Z");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/appointment",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockMessageAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2019-01-20T09:00:06.000Z");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/message",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockCustomerAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2019-01-13T00:00:00.000Z");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/customerprofiles",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockNoteAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2019-01-13T00:00:00.000Z");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/notes",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockGoalExpectedAPI(String jwt,MockMvc mockMvc) throws Exception{
        Map<String, String> paramMap = new LinkedHashMap<>();

        paramMap.put("lastUpdateTime","2019-01-13T00:00:00.000Z");
        MvcResult mvcResult = getMVCGetResponse(mockMvc,"/goalExpected",paramMap,jwt);

        return mockResult2Error(mvcResult);
    }

    private Error mockResult2Error(MvcResult mvcResult) throws Exception {
        Error error = new Error();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        error.setCode(status + "");
        error.setMessage(content);

        return error;
    }

    private MvcResult getMVCPostResponse(MockMvc mockMvc , String url , String postBody) throws Exception {

        return mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(postBody)
                .header("Content-Type" , "application/json")
                .header("X-Request-ID" , "1")
                .header("X-Organization", "1")
                .header("X-Organization-Branch" , "1")
                .header("X-API-Version" , "1")
                .header("X-Date" , "Sun, 06 Nov 1994 08:49:37 GMT")
                .accept(MediaType.APPLICATION_JSON))
//               .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    private MvcResult getMVCGetResponse(MockMvc mockMvc , String url , Map<String, String> paramMap , String jwtToken) throws Exception {

        MockHttpServletRequestBuilder requestBuilder  = MockMvcRequestBuilders.get(url)
                .header("Authorization" , "Bearer " + jwtToken)
                .header("X-Request-ID" , "1")
                .header("Content-Type", "application/json")
                .header("X-Date" , "Sun, 06 Nov 1994 08:49:37 GMT")
                .accept(MediaType.APPLICATION_JSON);

        if(paramMap != null) {
            for(String key : paramMap.keySet()) {
                requestBuilder.param(key , paramMap.get(key));
            }
        }

        return mockMvc.perform(requestBuilder)
//                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


}
