/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authen;

import constant.CommonConst;
import dal.AccountDAO;
import entity.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @DuongNKTHE186476
 */
public class AuthenticationController extends HttpServlet {

    AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lay ve action
        String action = request.getParameter("action") != null
                ? request.getParameter("action")
                : "";

        // Dua tren action set URL trang can chuyen den
        String url;
        switch (action) {
            case "login":
                url = "view/authen/login.jsp";
                break;
            case "log-out":
                url = logOut(request, response);
                break;
            case "sign-up":
                url = "view/authen/register.jsp";
                break;
            default:
                url = "home";
        }
        // Chuyen trang
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lay ve action
        String action = request.getParameter("action") != null
                ? request.getParameter("action")
                : "";

        // Dua tren action set URL trang can chuyen den
        String url;
        switch (action) {
            case "login":
                url = loginDoPost(request, response);
                break;
            case "sign-up":
                url = signUp(request, response);
                break;
            default:
                url = "home";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    private String loginDoPost(HttpServletRequest request, HttpServletResponse response) {
        String url = null;
        // get ve thong tin nguoi dung nhap
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // kiem tra thong tin co ton tai trong db khong
        Account account = Account.builder()
                .username(username)
                .password(password)
                .build();
        Account accFoundByUsernamePass = accountDAO.findByUsernameAndPass(account);

        // true => trang home(set account vao session)
        if (accFoundByUsernamePass != null) {
            request.getSession().setAttribute(CommonConst.SESSION_ACCOUNT,
                    accFoundByUsernamePass);
            url = "home";

            // false => quay tra lai trang login(co them bao loi)
        } else {
            request.setAttribute("error", "Username or password incorrect!! DUMBASS");
            url = "view/authen/login.jsp";
        }
        return url;
    }

    private String logOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(CommonConst.SESSION_ACCOUNT);
        return "home";
    }

    private String signUp(HttpServletRequest request, HttpServletResponse response) {
        String url;
        // lay ve thong nguoi dung nhap
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // kiem tra username da ton tai trong db
        Account account = Account.builder()
                .username(username)
                .password(password)
                .build();
        boolean isExistUsername = accountDAO.checkUsernameExist(account);

        // true => tro lai trang register(them thong bao loi)
        if (isExistUsername) {
            request.setAttribute("error", "Username is already exist !!");
            url = "view/authen/register.jsp";
        // false => quay tro lai trang home (ghi tk vao db)
        } else {
            accountDAO.insert(account);
            url = "home";
        }
        return url;
        
    }
}
