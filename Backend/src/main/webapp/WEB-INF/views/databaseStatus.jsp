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
    Map<String,Boolean> tableValidationMap = (Map<String,Boolean>) request.getAttribute("tableValidationMap");
    Map<String,Boolean> sequenceValidationMap = (Map<String,Boolean>) request.getAttribute("sequenceValidationMap");
%>
<html lang="en">
<head>
    <title>Allianz S&D COE System Console</title>
    <style>

        .tableResult , .sequenceResult{
            width:45%;
            display: inline-block;
        }
        table{
            border: 1px solid gray;
            width: 100%;
        }
        table th , table td{
            border: 1px solid gray;
        }

        table td span.fail{
            color : red;
        }
    </style>
</head>
<body>

<div class="">
    <div class="tableResult">
        <h1>Table Status</h1>
        <table border="1">
            <tr>
                <th>TableName</th>
                <th>Success</th>
            </tr>
            <%
                for(String tableName : tableValidationMap.keySet()) {
                    boolean isSuccess = tableValidationMap.get(tableName);
            %>

            <tr>
                <td><%=tableName%></td>
                <td><span class="<%=!isSuccess ? "fail" : ""%>"><%=isSuccess ? "V" : "X"%></span></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>


    <div class="sequenceResult">
        <h1>Sequence Status</h1>
        <table border="1">
            <tr>
                <th>SequenceName</th>
                <th>Success</th>
            </tr>
            <%
                for(String sequenceName : sequenceValidationMap.keySet()) {
                    boolean isSuccess = sequenceValidationMap.get(sequenceName);
            %>

            <tr>
                <td><%=sequenceName%></td>
                <td><span class="<%=!isSuccess ? "fail" : ""%>"><%=isSuccess ? "V" : "X"%></span></td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</div>

</body>
</html>

