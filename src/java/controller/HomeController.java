/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.CategoryDAO;
import dal.ProductDAO;
import entity.Category;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @DuongNKTHE186476
 */
public class HomeController extends HttpServlet {
    
    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Product> listProduct = findProductDoGet(request);
        
        List<Category> listCategory = categoryDAO.findAll();
        
        // set list vao session
        HttpSession ss = request.getSession();
        ss.setAttribute("listProduct", listProduct);
        ss.setAttribute("listCategory", listCategory);
        request.getRequestDispatcher("view/homepage/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

    private List<Product> findProductDoGet(HttpServletRequest request) {
        // get ve search
        String actionSearch = request.getParameter("search") == null 
                ? "defaut" : request.getParameter("search");
        
        List<Product> listProduct;
        
        switch(actionSearch){
            case "category":
                String categoryId = request.getParameter("categoryId");
                listProduct = productDAO.findByCategory(categoryId);
                break;            
            default:
                listProduct = productDAO.findAll();
        }
        return listProduct;
    }
}
