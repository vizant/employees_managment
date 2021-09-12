package com.example.employeemanagment.servlet;

import com.example.employeemanagment.dao.EmployeeDao;
import com.example.employeemanagment.enitity.Employee;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "EmployeeServlet", value = "/")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDao employeeDao;

    @Override
    public void init(){
        employeeDao = EmployeeDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path){
            case "/new":
                showNewEmployeeForm(request,response);
                break;
            case "/edit":
                showEditEmployeeForm(request,response);
                break;
            case "/delete":
                deleteEmployee(request,response);
                break;
            case "/save":
                saveNewEmployee(request,response);
                break;
            case "/update":
                updateExistingEmployee(request,response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }


    private void showNewEmployeeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("employee.jsp");
        requestDispatcher.forward(request,response);
    }

    private void saveNewEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String middleName = request.getParameter("middleName");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        String mail = request.getParameter("mail");
        employeeDao.addEmployee(new Employee(firstName,lastName,middleName,dateOfBirth,mail));
        response.sendRedirect("/");
    }

    private void showEditEmployeeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("employee.jsp");
        requestDispatcher.forward(request,response);
    }

    private void updateExistingEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String middleName = request.getParameter("middleName");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        String mail = request.getParameter("mail");
        Employee employee = new Employee(id,firstName,lastName,middleName,dateOfBirth,mail);
        employeeDao.updateEmployee(employee);
        response.sendRedirect("/");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        employeeDao.deleteEmployee(id);
        response.sendRedirect("/");
    }

}
