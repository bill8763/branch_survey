<%@ page import="java.util.Map" %>
<%@ page import="com.allianz.sd.core.jpa.model.AgentData" %>
<%@ page import="com.allianz.sd.core.api.model.Error" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%!
    private boolean toAPISuccess(Error error) {
        return error != null && "200".equalsIgnoreCase(error.getCode());
    }
%>
<%
    request.setCharacterEncoding("utf-8");
    Map<AgentData, Error[]> agentResultMap = (Map<AgentData, Error[]>) request.getAttribute("agentResultMap");

%>
<html lang="en">
<head>
    <title>Allianz S&D COE System Console</title>
    <style>

        table{
            border: 1px solid gray;
            width: 100%;
            table-layout: fixed;
            font-size: 12px;
        }
        table th , table td{
            border: 1px solid gray;
            word-break: break-word;
        }

        table td span.fail{
            color : red;
        }
    </style>
</head>
<body>
<h1>API Full check Status</h1>
<h2>Total Agent Size:<%=agentResultMap.size()%></h2>
<table border="1">
    <tr>
        <th>AgentID</th>
        <th>AgentName</th>
        <th>GoalAPI</th>
        <th>ConfigAPI</th>
        <th>ProgressAPI</th>
        <th>AgencyPlanAPI</th>
        <th>ActualAPI</th>
        <th>CalendarAPI</th>
        <th>MessageAPI</th>
        <th>CustomerAPI</th>
        <th>NoteAPI</th>
        <th>GoalExpectedAPI</th>
    </tr>
    <%
        for(AgentData agentData : agentResultMap.keySet()) {
            Error[] errors = agentResultMap.get(agentData);

            //filter fail record
            boolean isFail = false;
            for(Error error : errors) {
                boolean isSuccess = toAPISuccess(error);
                if(!isSuccess) isFail = true;
            }

            if(isFail) {
    %>

    <tr>
        <td><%=agentData.getAgentID()%></td>
        <td><%=agentData.getAgentName()%></td>

        <%
            for(Error error : errors) {
                boolean isSuccess = toAPISuccess(error);
        %>
        <td><span class="<%=isSuccess ? "success" : "fail"%>"><%=isSuccess ? "V" : "X("+error.getMessage()+")"%></span></td>
        <%
            }
        %>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>

