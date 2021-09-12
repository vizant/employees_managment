<%@ page import="com.example.employeemanagment.dao.EmployeeDao" %>
<%@ page import="com.example.employeemanagment.enitity.Employee" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello World!</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <style>
        tr:hover {
            background-color: #c4ffd5;
            color: black;
        }

        td {
            border: 1px #DDD solid;
            padding: 5px;
            cursor: pointer;
        }

    </style>
</head>
<%
    List<Employee> employees = EmployeeDao.getInstance().getEmployees();
    employees.sort(Comparator.comparingInt(Employee::getId));
    request.setAttribute("list",employees);
%>
<body>
<div class="container p-1">
    <div class="mb-2">
        <a id="add" class="btn btn-success btn-lg"
           href="<%=request.getContextPath()%>/new">Add</a>
    </div>
    <table class="table" id="table">
        <thead class="thead-light">
            <tr>
                <th scope="col">FirstName</th>
                <th scope="col">LastName</th>
                <th scope="col">MiddleName</th>
                <th scope="col">Date of birthday</th>
                <th scope="col">Mail</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="employee" items="${list}">
            <tr onclick="document.location = '<%=request.getContextPath()%>/edit?id=${employee.id}';">
                <td><c:out value="${employee.firstName}" /></td>
                <td><c:out value="${employee.lastName}" /></td>
                <td><c:out value="${employee.middleName}" /></td>
                <td><c:out value="${employee.dateOfBirth}" /></td>
                <td><c:out value="${employee.mail}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>