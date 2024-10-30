/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import constant.CommonConst;
import dal.OrderDAO;
import entity.Account;
import entity.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class DashboardUserServlet extends HttpServlet {

    OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // lay id account
        Account account = (Account) session.getAttribute(CommonConst.SESSION_ACCOUNT);
        if (account == null) {
            response.sendRedirect("login.jsp"); // Nếu không có đối tượng Account, chuyển hướng đến trang đăng nhập
            return;
        }
        int userId1 = account.getId();

        // Lay cac don hang cua account tuong ung
        List<Order> listOrder = orderDAO.findOrderByUserId(userId1);

        session.setAttribute(CommonConst.SESSION_ORDER, listOrder);
        request.getRequestDispatcher("view/user/dashboard/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //set encoding UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // get sesion
        HttpSession session = request.getSession();
        
        // get action
        String action = request.getParameter("action") == null
                ? "" : request.getParameter("action");
        
        switch(action){
            case "delete":
                deleteCart(request);
                break;
        }
        response.sendRedirect("dashboard");
    }

    private void deleteCart(HttpServletRequest request) {
        // get id
        int id = Integer.parseInt(request.getParameter("id"));
        orderDAO.deleteOrderById(id);
    }
}
