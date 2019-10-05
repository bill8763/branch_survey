<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");

    Long languageSize = (Long) request.getAttribute("languageSize");
    Long codeSize = (Long) request.getAttribute("codeSize");
    Long sysDataSize = (Long) request.getAttribute("sysDataSize");

%>
<html lang="en">
<head>
    <title>Allianz S&D COE Setup Console</title>
    <style>


    </style>
</head>
<body>

<h1>
    Setup-console
</h1>

<h2>Setup Language</h2>
<p>Language database record size:<%=languageSize%></p>
<%
    if(languageSize.longValue() != 0) {
%>
Download APP <a href="downloadLanguage">language.json</a>
<%
    }
%>
<form action="uploadLanguage" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" value="Submit"/>
</form>

<h2>Setup Code</h2>
<p>Code database record size:<%=codeSize%></p>
<form action="uploadCode" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" value="Submit"/>
</form>

<h2>Setup SysData</h2>
<p>SysData database record size:<%=sysDataSize%></p>
<form action="uploadSysData" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" value="Submit"/>
</form>

</body>
</html>

