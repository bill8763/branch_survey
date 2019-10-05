<%@ page import="com.allianz.sd.core.progress.CalcTeamProgress" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanGetResponse" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlan" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanDetailInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanMaster" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    List result = (List) request.getAttribute("result");
    Map<String, Object> hints = (Map<String, Object>) request.getAttribute("hints");
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
<h1>Command</h1>

<table border="1">
    <%--    <tr>--%>
    <%--        <%--%>
    <%--            for(String colName : hints.keySet()) {--%>
    <%--        %>--%>
    <%--        <th><%=colName%></th>--%>
    <%--        <%--%>
    <%--            }--%>
    <%--        %>--%>
    <%--    </tr>--%>
    <%
        for(Object obj : result) {
            Object[] array = (Object[]) obj;
    %>

    <tr>
        <%
            for(Object data : array) {
        %>
        <td><%=String.valueOf(data)%></td>
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

