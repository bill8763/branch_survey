<%@ page import="java.util.Map" %>
<%@ page import="org.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    Boolean result = (Boolean) request.getAttribute("result");
%>
<html lang="en">
<head>
    <title>Allianz S&D COE Console</title>

</head>
<body>
<script>
    alert('<%=result ? "Upload Success!!" : "Upload Fail!!"%>');
    window.location = 'index';
</script>
</body>
</html>

