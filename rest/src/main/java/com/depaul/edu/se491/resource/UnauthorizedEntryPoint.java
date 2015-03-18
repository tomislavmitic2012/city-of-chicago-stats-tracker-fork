package com.depaul.edu.se491.resource;

import com.depaul.edu.se491.global.AppConstants;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Tomislav S. Mitic on 3/9/15.
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException e) throws IOException, ServletException {
            httpServletResponse.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED
                    , AppConstants.UNAUTHORIZED_MESSAGE
            );
    }
}
