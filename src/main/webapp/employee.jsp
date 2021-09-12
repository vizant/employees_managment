<%@ page import="com.example.employeemanagment.enitity.Employee" %>
<%@ page import="com.example.employeemanagment.dao.EmployeeDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello World!</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<%
    if(request.getParameterMap().containsKey("id")) {
        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = EmployeeDao.getInstance().getEmployee(id);
        request.setAttribute("employee", employee);
        System.out.println(employee);
    }
%>
<body>
<div class="container col-md-5 mt-2">
    <div class="card">
        <div class="card-body">
            <c:if test="${employee != null}">
                <form action="/update" method="post">
            </c:if>
            <c:if test="${employee == null}">
                <form action="/save" method="post">
            </c:if>

            <c:if test="${employee != null}">
                <input type="hidden" name="id" value="${employee.id}"/>
            </c:if>
            <fieldset>
                <label for="firstName">First name</label>
                <input id="firstName" name="firstName" class="form-control" type="text"
                       value="${employee.firstName}" required/>
            </fieldset>
            <fieldset>
                <label for="lastName">Last name</label>
                <input id="lastName" name="lastName" class="form-control" type="text"
                       value="${employee.lastName}" required/>
            </fieldset>
            <fieldset>
                <label for="middleName">Middle name</label>
                <input id="middleName" name="middleName" class="form-control" type="text"
                       value="${employee.middleName}" required/>
            </fieldset>
            <fieldset>
                <label for="dateOfBirth">Date of birthday</label>
                <input id="dateOfBirth" name="dateOfBirth" class="form-control" type="date"
                       value="${employee.dateOfBirth}" required/>
            </fieldset>
            <fieldset>
                <label for="mail">Mail</label>
                <input id="mail" name="mail" class="form-control" type="email"
                       value="${employee.mail}" required/>
            </fieldset>
            <div class="mt-1">
                <button type="submit" class="btn btn-success">Save</button>
                <c:if test="${employee != null}">
                    <a class="btn btn-danger" href="<%=request.getContextPath()%>/delete?id=${employee.id}">Delete</a>
                </c:if>
            </div>

        </div>
    </div>
</div>
</body>
</html>