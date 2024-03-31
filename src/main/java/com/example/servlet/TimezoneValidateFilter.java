package com.example.servlet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.Arrays;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");
        if(timezone==null){
            timezone= req.getHeader("Cookie");
            if(timezone!=null){
                timezone = timezone.substring(13);
            }else {
                timezone="UTC";
            }
        }else {
            timezone = timezone.replace(" ","+");
            res.addCookie(new Cookie("lastTimezone", timezone));
        }

            try {
                ZoneId.of(timezone);
            } catch (DateTimeException e) {
                res.setStatus(400);
                res.getWriter().write("Invalid timezone");
                res.getWriter().close();
            }
        //System.out.println(timezone);
        req.setAttribute("lastTimezone", timezone);
        super.doFilter(req, res, chain);
    }
}
