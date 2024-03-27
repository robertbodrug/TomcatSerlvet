package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@WebServlet("/time")
public class TimeServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // TODO document why this method is empty
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        String timezone = servletRequest.getParameter("timezone");
        timezone = (timezone==null)?"UTC":timezone.replace(" ","+");

        Instant instant = Instant.now();
        ZoneId zone = ZoneId.of(timezone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateTime = instant.atZone(zone).format(formatter);

        servletResponse.getWriter().write(dateTime +" "+timezone);
        servletResponse.getWriter().close();
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
