package com.goit.servlets;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import javax.servlet.http.HttpServletRequest;
@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String timezoneParam = httpRequest.getParameter("timezone");

        if (isValidTimezone(timezoneParam)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Invalid timezone");
        }
    }


    private boolean isValidTimezone(String timeZone) {
        if (timeZone == null || timeZone.isEmpty()) {
            return true;
        }

        try {

            ZoneId.of(timeZone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void destroy() {

    }
}




