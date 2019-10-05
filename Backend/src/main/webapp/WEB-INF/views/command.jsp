<%@ page import="com.allianz.sd.core.progress.CalcTeamProgress" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanGetResponse" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlan" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanDetailInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.allianz.sd.core.api.model.AgencyPlanMaster" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
%>
<html lang="en">
<head>
    <title>Allianz S&D COE DebugTools</title>

</head>
<body>
<h1>Command</h1>

<form action="executeCommand" method="post">
    <input type="text" name="command"/>

    <input type="submit" value="Submit"/>
</form>


</body>
</html>

