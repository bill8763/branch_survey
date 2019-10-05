<%@ page import="com.allianz.sd.core.progress.CalcTeamProgress" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanGetResponse" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlan" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanDetailInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanMaster" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    String agentID = (String) request.getAttribute("agentID");
    AgencyPlanGetResponse agencyPlanGetResponse = (AgencyPlanGetResponse) request.getAttribute("agencyPlanGetResponse");
%>
<html lang="en">
<head>
    <title>Allianz S&D COE DebugTools</title>
    <style>
        table{
            border: 1px solid gray;
            width: 100%;
        }
        table th , table td{
            border: 1px solid gray;
        }
    </style>
</head>
<body>
<h1>AgencyPlan-<%=agentID%></h1>

<%
    for(AgencyPlan agencyPlan : agencyPlanGetResponse.getAgencyPlans()) {
        int dataYear = agencyPlan.getDataYear().intValue();
        List<AgencyPlanMaster> masaterList = agencyPlan.getValues();
        List<AgencyPlanDetailInfo> directUnitList = agencyPlan.getDirectUnit();
        List<AgencyPlanDetailInfo> inDirectUnitList = agencyPlan.getInDirectUnit();
%>
<h2><%=dataYear%></h2>
<h3>Master</h3>
<table border="1">
    <tr>
        <th>DataType</th>
        <th>Forecast</th>
        <th>Plan</th>
        <th>Actual</th>
    </tr>
    <%
        for(AgencyPlanMaster agencyPlanMaster : masaterList) {
    %>

    <tr>
        <td><%=agencyPlanMaster.getDataType()%></td>
        <td><%=agencyPlanMaster.getForecast()%></td>
        <td><%=agencyPlanMaster.getPlan()%></td>
        <td><%=agencyPlanMaster.getActual()%></td>
    </tr>
    <%
        }
    %>
</table>

<h3>Direct</h3>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>DataType</th>
        <th>Goal</th>
        <th>Forecast</th>
        <th>Actual</th>
        <th>Plan</th>
        <th>CaseCount</th>
        <th>PerCase</th>
    </tr>

    <%
        if(directUnitList != null) {

            String[] datatypes = new String[]{"FYFC","ANP"};

            for(String datatype : datatypes) {
                for(AgencyPlanDetailInfo detailInfo : directUnitList) {

                    if(!datatype.equalsIgnoreCase(detailInfo.getDataType().toString())) continue;
    %>

    <tr>
        <td><%=detailInfo.getAgentID()%></td>
        <td><%=detailInfo.getAgentName()%></td>
        <td><%=detailInfo.getDataType()%></td>
        <td><%=detailInfo.getGoal()%></td>
        <td><%=detailInfo.getForecast()%></td>
        <td><%=detailInfo.getActual()%></td>
        <td><%=detailInfo.getPlan()%></td>
        <td><%=detailInfo.getCaseCount()%></td>
        <td><%=detailInfo.getPerCase()%></td>
    </tr>
    <%
                }
            }
        }
    %>
</table>
<h3>InDirect</h3>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>DataType</th>
        <th>Goal</th>
        <th>Forecast</th>
        <th>Plan</th>
        <th>Actual</th>
        <th>Manpower</th>
        <th>Recruitment</th>
    </tr>
    <%
        if(inDirectUnitList != null) {
            String[] datatypes = new String[]{"FYFC","ANP"};

            for(String datatype : datatypes) {
                for(AgencyPlanDetailInfo detailInfo : inDirectUnitList) {
                    if(!datatype.equalsIgnoreCase(detailInfo.getDataType().toString())) continue;
    %>


    <tr>
        <td><%=detailInfo.getAgentID()%></td>
        <td><%=detailInfo.getAgentName()%></td>
        <td><%=detailInfo.getDataType()%></td>
        <td><%=detailInfo.getGoal()%></td>
        <td><%=detailInfo.getForecast()%></td>
        <%
            if("FYFC".equalsIgnoreCase(datatype)) {
        %>
        <td><a href="drilldown/<%=dataYear%>/<%=detailInfo.getAgentID()%>" target="_blank"><%=detailInfo.getPlan()%><a/></td>
        <%
        }else {
        %>
        <td><%=detailInfo.getPlan()%></td>
        <%
            }
        %>
        <td><%=detailInfo.getActual()%></td>
        <td><%=detailInfo.getManpower()%></td>
        <td><%=detailInfo.getRecruitment()%></td>
    </tr>

    <%
                }
            }
        }
    %>

</table>

<%
    }
%>


</body>
</html>

