package com.ledgerline.controller;
import jakarta.servlet.http.*;import java.io.IOException;
public class LogoutServlet extends HttpServlet{protected void doGet(HttpServletRequest r,HttpServletResponse s)throws IOException{r.getSession().invalidate();s.sendRedirect(r.getContextPath()+"/login");}}
