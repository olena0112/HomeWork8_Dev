package com.goit.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        String timeZoneParam = request.getParameter("timezone");
        ZoneId zoneId;

        if (timeZoneParam != null && !timeZoneParam.isEmpty()) {
            try {
                Optional<String> offsetString = Optional.of(timeZoneParam.replaceAll("[^0-9\\-]+", ""));
                int offsetHours = Integer.parseInt(offsetString.orElse("0"));
                zoneId = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(offsetHours));
            } catch (NumberFormatException e) {
                zoneId = ZoneId.of("UTC");
            }
        } else {
            zoneId = ZoneId.of("UTC");
        }
        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
        String formattedTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (timeZoneParam != null && !timeZoneParam.isEmpty()) {
            formattedTime = currentTime.format(formatter) + " UTC" + zoneId.getRules().getOffset(currentTime.toInstant()).getId();
        }else  formattedTime=currentTime.format(formatter) + " UTC";
        response.getWriter().write(formattedTime);
    }
}