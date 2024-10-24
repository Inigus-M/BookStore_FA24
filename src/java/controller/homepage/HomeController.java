/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.homepage;

import constant.CommonConst;
import dal.CategoryDAO;
import dal.ProductDAO;
import entity.Category;
import entity.PageControl;
import entity.Product;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class HomeController extends HttpServlet {

    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PageControl pageControl = new PageControl();
        List<Product> listProduct = findProductDoGet(request, pageControl);
        List<Category> listCategory = categoryDAO.findAll();

        // set list vao session
        HttpSession ss = request.getSession();
        ss.setAttribute(CommonConst.SESSION_PRODUCT, listProduct);
        ss.setAttribute(CommonConst.SESSION_CATEGORY, listCategory);
        request.setAttribute("pageControl", pageControl);
        request.getRequestDispatcher("view/homepage/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("home");
    }

    private List<Product> findProductDoGet(HttpServletRequest request, PageControl pagecontrol) {
        // get ve page
        String pageRaw = request.getParameter("page");
        //valid page
        int page;
        try {
            page = Integer.parseInt(pageRaw);
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        //get ve search
        String actionSearch = request.getParameter("search") == null
                ? "default"
                : request.getParameter("search");
        //get list product dao
        List<Product> listProduct;
        //get request URL
        String requestURL = request.getRequestURL().toString();
        int totalRecord = 0;
        switch (actionSearch) {
            case "category":
                String categoryId = request.getParameter("categoryId");
                totalRecord = productDAO.findTotalRecordByCategory(categoryId);
                listProduct = productDAO.findByCategory(categoryId, page);
                pagecontrol.setUrlPattern(requestURL + "?search=category&categoryId=" + categoryId + "&");
                break;
            case "searchByName":
                String keyword = request.getParameter("keyword");
                totalRecord = productDAO.findTotalRecordByName(keyword);
                listProduct = productDAO.findByName(keyword, page);
                pagecontrol.setUrlPattern(requestURL + "?search=searchByName&keyword=" + keyword + "&");
                break;
            default:
                totalRecord = productDAO.findTotalRecord();
                listProduct = productDAO.findByPage(page);
                pagecontrol.setUrlPattern(requestURL + "?");
        }
        //total record
        //total page
        int totalPage = (totalRecord % CommonConst.RECORD_PER_PAGE) == 0
                ? (totalRecord / CommonConst.RECORD_PER_PAGE)
                : (totalRecord / CommonConst.RECORD_PER_PAGE) + 1;
        //set total record, total page, page vao pageControl
        pagecontrol.setPage(page);
        pagecontrol.setTotalPage(totalPage);
        pagecontrol.setTotalRecord(totalRecord);
        return listProduct;
    }

}
