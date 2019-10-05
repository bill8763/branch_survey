<%@ page import="java.util.Map" %>
<%@ page import="org.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    JSONObject jsonObject = (JSONObject) request.getAttribute("jsonObject");
    boolean success = jsonObject.getBoolean("success");
%>
<html lang="en">
<head>
    <title>Allianz S&D COE Console</title>

</head>
<body>
<script>
    alert('<%=success ? "Upload Success!!" : "Upload Fail!!"%>');
    window.location = 'index';
</script>
</body>
</html>

