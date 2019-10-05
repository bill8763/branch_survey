<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
%>
<html lang="en">
<head>
    <title>Allianz S&D COE Console</title>

</head>
<body>

<table>
    <tr>
        <th>Setup</th>
        <th>Database status</th>
        <th>AgencyPlan-Debug</th>
        <th>TeamProgress-Debug</th>
    </tr>

    <tr>
        <td><a href="setup/index">go</a></td>
        <td><a href="database/databaseStatus">go</a></td>
        <td><a href="debug/agencyplan/0">go</a></td>
        <td><a href="debug/teamprogress/0">go</a></td>
    </tr>
</table>

</body>
</html>

