<%@ page import="java.util.List" %>
<%@ page import="com.allianz.sd.core.api.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    String agentID = (String) request.getAttribute("agentID");
    ProgressGetResponse progressGetResponse = (ProgressGetResponse) request.getAttribute("progressGetResponse");
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
<h1>TeamProgress-<%=agentID%></h1>

<%
    for(Progress progress : progressGetResponse.getProgress()) {
        int dataYear = progress.getDataYear().intValue();
        List<TeamProgressValue> teamProgressValues = progress.getTeam().getValues();
        List<TeamProgressDetail> directUnitList = progress.getTeam().getDirectUnit();
        List<TeamProgressDetail> inDirectUnitList = progress.getTeam().getInDirectUnit();
%>
<h2><%=dataYear%></h2>
<h3>Master</h3>
<table border="1">
    <tr>
        <th>Timebase</th>
        <th>DataType</th>
        <th>Goal</th>
        <th>Forecast</th>
        <th>Actual</th>
        <th>Shortfall</th>
    </tr>
    <%
        if(teamProgressValues != null) {
            for(TeamProgressValue teamProgressValue : teamProgressValues) {
    %>

    <tr>
        <td><%=teamProgressValue.getTimeBase()%></td>
        <td><%=teamProgressValue.getDataType()%></td>
        <td><%=teamProgressValue.getGoal()%></td>
        <td><%=teamProgressValue.getForecast()%></td>
        <td><%=teamProgressValue.getActual()%></td>
        <td><%=teamProgressValue.getShortfall()%></td>
    </tr>
    <%
            }
        }
    %>
</table>

<h3>Direct</h3>
<table>
    <tr>
        <th>Timebase</th>
        <th>DataType</th>
        <th>ID</th>
        <th>Name</th>
        <th>Goal</th>
        <th>Forecast</th>
        <th>Actual</th>
        <th>Shortfall</th>
    </tr>

    <%
        if(directUnitList != null) {
            for(TeamProgressDetail detailInfo : directUnitList) {
    %>

    <tr>
        <td><%=detailInfo.getTimeBase()%></td>
        <td><%=detailInfo.getDataType()%></td>
        <td><%=detailInfo.getAgentID()%></td>
        <td><%=detailInfo.getAgentName()%></td>
        <td><%=detailInfo.getGoal()%></td>
        <td><%=detailInfo.getForecast()%></td>
        <td><%=detailInfo.getActual()%></td>
        <td><%=detailInfo.getShortfall()%></td>

    </tr>
    <%
            }
        }
    %>
</table>
<h3>InDirect</h3>
<table>
    <tr>
        <th>Timebase</th>
        <th>DataType</th>
        <th>ID</th>
        <th>Name</th>
        <th>Goal</th>
        <th>Forecast</th>
        <th>Actual</th>
        <th>Shortfall</th>
    </tr>
    <%
        if(inDirectUnitList != null) {
            for(TeamProgressDetail detailInfo : inDirectUnitList) {
    %>


    <tr>
        <td><%=detailInfo.getTimeBase()%></td>
        <td><%=detailInfo.getDataType()%></td>
        <td><%=detailInfo.getAgentID()%></td>
        <td><%=detailInfo.getAgentName()%></td>
        <td><%=detailInfo.getGoal()%></td>
        <td><%=detailInfo.getForecast()%></td>
        <td><%=detailInfo.getActual()%></td>
        <td><%=detailInfo.getShortfall()%></td>

    </tr>

    <%
            }
        }
    %>

</table>

<%
    }
%>


</body>
</html>

