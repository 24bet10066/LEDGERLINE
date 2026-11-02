package com.ledgerline.controller;
import com.ledgerline.config.Bootstrap;
import com.ledgerline.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
public class LoginServlet extends HttpServlet {
 public void init() throws ServletException { try{Bootstrap.initialize();}catch(Exception e){throw new ServletException("Database initialization failed",e);} }
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{if(r.getSession().getAttribute("user")!=null){s.sendRedirect(r.getContextPath()+"/app/dashboard");return;}r.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(r,s);}
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{
  try{var u=new UserDao().authenticate(r.getParameter("email"),r.getParameter("password"));if(u==null){r.setAttribute("error","Email or password is incorrect.");doGet(r,s);return;}r.getSession().setAttribute("user",u);s.sendRedirect(r.getContextPath()+"/app/dashboard");}catch(Exception e){throw new ServletException("Login failed",e);}
 }
}
