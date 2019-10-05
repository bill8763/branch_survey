<%@ page import="com.allianz.sd.core.api.model.AgencyPlan" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanDetailInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanMaster" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.allianz.sd.core.service.bean.Goal" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!
    long toLong(String value) {
        try{
            return Long.parseLong(value);
        }catch(Exception e) {
            return 0;
        }
    }
%>

<%
    request.setCharacterEncoding("utf-8");
    String agentID = (String) request.getAttribute("agentID");
    Integer dataYear = (Integer) request.getAttribute("dataYear");
    Map<String,Object[]> result = (Map<String,Object[]>) request.getAttribute("result");
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
<h1>AgencyPlanDrilldown-<%=agentID%>(<%=dataYear%>)</h1>

<h2>Bottomup-list</h2>
<table border="1">
    <tr>
        <th>AgentID</th>
        <th>PlanCalcStartMonth</th>
        <th>1</th>
        <th>2</th>
        <th>3</th>
        <th>4</th>
        <th>5</th>
        <th>6</th>
        <th>7</th>
        <th>8</th>
        <th>9</th>
        <th>10</th>
        <th>11</th>
        <th>12</th>
        <th>Calc Total</th>
    </tr>
    <%
        Map<Integer,Long> planTotalMonth = new LinkedHashMap<>();
        long total = 0;

        for(String subAgentID : result.keySet()) {
            Object[] objects = result.get(subAgentID);
            Goal goal = (Goal) objects[0];
            int yearPlanStartMonth = (Integer) objects[1];

            Map<Integer,String> fyfcPlan = goal.getFyfcPlan();
            Long totalFyfc = goal.getTotalFyfcPlan(yearPlanStartMonth);
            total += totalFyfc;
    %>

    <tr>
        <td><%=subAgentID%></td>
        <td><%=yearPlanStartMonth%></td>
        <%
            for(Integer month : fyfcPlan.keySet()) {
                long plan = toLong(fyfcPlan.get(month));
                Long totalPlan = planTotalMonth.get(month);

                if(totalPlan == null) totalPlan = plan;
                else totalPlan += plan;
                planTotalMonth.put(month,totalPlan);
        %>
        <td><%=plan%></td>
        <%
            }
        %>
        <td><%=totalFyfc%></td>
    </tr>
    <%
        }
    %>

    <tr>
        <td></td>
        <td></td>
        <%
            for(Integer month : planTotalMonth.keySet()) {
        %>
        <td><%=planTotalMonth.get(month)%></td>
        <%
            }
        %>
        <td><%=total%></td>
    </tr>
</table>

</body>
</html>

