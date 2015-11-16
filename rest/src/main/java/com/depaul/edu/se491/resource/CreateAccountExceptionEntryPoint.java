package com.depaul.edu.se491.resource;

import com.depaul.edu.se491.config.AppConfig;
import com.depaul.edu.se491.global.AppConstants;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Tomislav S. Mitic on 3/9/15.
 */
public class CreateAccountExceptionEntryPoint {

    public void commence(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Exception e) throws IOException, ServletException {
        httpServletResponse.setHeader(AppConstants.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, AppConfig.getReferer(httpServletRequest));
        httpServletResponse.setHeader(AppConstants.ACCESS_CONTROL_ALLOW_METHODS_KEY, AppConstants.ACCESS_CONTROL_ALLOW_METHODS_VALUE);
        httpServletResponse.setHeader(AppConstants.ACCESS_CONTROL_ALLOW_HEADERS_KEY, AppConstants.ACCESS_CONTROL_ALLOW_HEADERS_VALUE);
        httpServletResponse.sendError(
                HttpServletResponse.SC_UNAUTHORIZED
                , String.format(AppConstants.UNAUTHORIZED_USER_GENERAL_AUTHENTICATION_PROBLEM, e.getMessage()));
    }
}
