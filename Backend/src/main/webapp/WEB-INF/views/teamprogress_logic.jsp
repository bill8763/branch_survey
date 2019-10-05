<%@ page import="java.util.List" %>
<%@ page import="com.allianz.sd.core.api.model.*" %>
<%@ page import="com.allianz.sd.core.progress.CalcTeamProgress" %>
<%@ page import="com.allianz.sd.core.service.bean.TeamProgressTimeField" %>
<%@ page import="com.allianz.sd.core.service.bean.TeamProgressDataType" %>
<%@ page import="com.allianz.sd.core.progress.CalcTeamProgressGoalStrategy" %>
<%@ page import="com.allianz.sd.core.progress.CalcTeamProgressForecastStrategy" %>
<%@ page import="com.allianz.sd.core.progress.CalcTeamProgressShortfallStrategy" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.allianz.sd.core.service.bean.PerformanceTime" %>
<%@ page import="com.allianz.sd.core.cisl.bean.AgentYearDataCISL" %>
<%@ page import="com.allianz.sd.core.jpa.model.AgentYearData" %>
<%@ page import="com.allianz.sd.core.SNDSpec" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!
    String toDescription(Object obj) {
        try{
            SNDSpec sndSpec = obj.getClass().getAnnotation(SNDSpec.class);
            return sndSpec.desc();
        }catch(Exception e) {
            return "未說明規格";
        }
    }
%>
<%
    request.setCharacterEncoding("utf-8");
    CalcTeamProgress calcTeamProgress = (CalcTeamProgress) request.getAttribute("calcTeamProgress");
    PerformanceTime performanceTime = new PerformanceTime();
    performanceTime.setPlanClacStartMonth(9);
    performanceTime.setActualCalcEndMonth(8);
    performanceTime.setDays(30);
    performanceTime.setEndMonthOfYear(12);
    performanceTime.setMonth(9);
    performanceTime.setQuarter(3);
    performanceTime.setYear(2019);

    List<Integer> list = new ArrayList<>();
    list.add(7);
    list.add(8);
    list.add(8);
    performanceTime.setMonthOfQuarter(list);

    AgentYearData directAgentYearData = new AgentYearData();
    directAgentYearData.setAppUseMode("AG");


    AgentYearData indirectAgentYearData = new AgentYearData();
    indirectAgentYearData.setAppUseMode("AL");

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
<h1>TeamProgress Logic</h1>

<h2>Direct</h2>
<table border="1">

    <%
        for(TeamProgressTimeField timeField : TeamProgressTimeField.values()) {

    %>
    <tr>
        <th colspan="5"><%=timeField.toString()%></th>
    </tr>
    <tr>
        <th></th>
        <th>FYFC</th>
        <th>ANP</th>
        <th>Manpower</th>
        <th>Recruitment</th>
    </tr>

    <tr>
        <td>Goal</td>
        <%
            //FYFC/ANP/Manpower/Recruitment
            for(TeamProgressDataType dataType : TeamProgressDataType.values()) {
                CalcTeamProgressGoalStrategy calcGoal = calcTeamProgress.getGoalCalcStrategy(performanceTime, timeField, dataType, directAgentYearData, "DIRECT");

        %>
        <td><%=toDescription(calcGoal)%></td>
        <%
            }
        %>
    </tr>

    <tr>
        <td>Forecast</td>
        <%
            //FYFC/ANP/Manpower/Recruitment
            for(TeamProgressDataType dataType : TeamProgressDataType.values()) {
                CalcTeamProgressForecastStrategy calcGoal = calcTeamProgress.getForecastCalcStrategy(performanceTime, timeField, dataType, directAgentYearData, "DIRECT");

        %>
        <td><%=toDescription(calcGoal)%></td>
        <%
            }
        %>
    </tr>

    <tr>
        <td>Shartfall</td>
        <%
            //FYFC/ANP/Manpower/Recruitment
            for(TeamProgressDataType dataType : TeamProgressDataType.values()) {
                CalcTeamProgressShortfallStrategy calcGoal = calcTeamProgress.getShortfallCalcStrategy(performanceTime, timeField, dataType, directAgentYearData, "DIRECT");

        %>
        <td><%=toDescription(calcGoal)%></td>
        <%
            }
        %>
    </tr>

    <%
        }
    %>

</table>



<h2>InDirect</h2>
<table border="1">

    <%
        for(TeamProgressTimeField timeField : TeamProgressTimeField.values()) {

    %>
    <tr>
        <th colspan="5"><%=timeField.toString()%></th>
    </tr>
    <tr>
        <th></th>
        <th>FYFC</th>
        <th>ANP</th>
        <th>Manpower</th>
        <th>Recruitment</th>
    </tr>

    <tr>
        <td>Goal</td>
        <%
            //FYFC/ANP/Manpower/Recruitment
            for(TeamProgressDataType dataType : TeamProgressDataType.values()) {
                CalcTeamProgressGoalStrategy calcGoal = calcTeamProgress.getGoalCalcStrategy(performanceTime, timeField, dataType, indirectAgentYearData, "INDIRECT");

        %>
        <td><%=toDescription(calcGoal)%></td>
        <%
            }
        %>
    </tr>

    <tr>
        <td>Forecast</td>
        <%
            //FYFC/ANP/Manpower/Recruitment
            for(TeamProgressDataType dataType : TeamProgressDataType.values()) {
                CalcTeamProgressForecastStrategy calcGoal = calcTeamProgress.getForecastCalcStrategy(performanceTime, timeField, dataType, indirectAgentYearData, "INDIRECT");

        %>
        <td><%=toDescription(calcGoal)%></td>
        <%
            }
        %>
    </tr>

    <tr>
        <td>Shartfall</td>
        <%
            //FYFC/ANP/Manpower/Recruitment
            for(TeamProgressDataType dataType : TeamProgressDataType.values()) {
                CalcTeamProgressShortfallStrategy calcGoal = calcTeamProgress.getShortfallCalcStrategy(performanceTime, timeField, dataType, indirectAgentYearData, "INDIRECT");

        %>
        <td><%=toDescription(calcGoal)%></td>
        <%
            }
        %>
    </tr>

    <%
        }
    %>

</table>

</body>
</html>

