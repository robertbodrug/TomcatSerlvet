package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


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
        String timezone= (String) servletRequest.getAttribute("lastTimezone");

        Instant instant = Instant.now();
        ZoneId zone = ZoneId.of(timezone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");

        String dateTime = instant.atZone(zone).format(formatter);
        Map<String, Object> data = Map.of("data", dateTime+timezone, "hours", dateTime.substring(11, 13), "minutes", dateTime.substring(14, 16));


        //servletResponse.getWriter().write(dateTime +" "+timezone);
        //servletResponse.getWriter().close();
        Context simpleContext = new Context(
                servletRequest.getLocale(),
               data
        );

        TemplateEngineUtil.getTemplateEngine(servletRequest.getServletContext()).process("index", simpleContext, servletResponse.getWriter());
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
