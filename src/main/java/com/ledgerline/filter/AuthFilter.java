package com.ledgerline.filter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;
public class AuthFilter implements Filter {
 public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException{
  HttpServletRequest r=(HttpServletRequest)request;
  if(r.getSession().getAttribute("user")==null){((HttpServletResponse)response).sendRedirect(r.getContextPath()+"/login");return;}
  if(r.getSession().getAttribute("csrf")==null)r.getSession().setAttribute("csrf",UUID.randomUUID().toString());
  chain.doFilter(request,response);
 }
}
