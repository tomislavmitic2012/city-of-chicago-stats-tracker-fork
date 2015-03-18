package com.depaul.edu.se491.filters;

import com.depaul.edu.se491.utils.TokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Tom Mitic on 3/9/15.
 */
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    private final UserDetailsService uds;

    public AuthenticationTokenProcessingFilter(UserDetailsService uds) {
        this.uds = uds;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest http = this.getAsHttpRequest(servletRequest);

        String authToken = this.extractAuthTokenFromRequest(http);
        String userName = TokenUtils.getUserNameFromToken(authToken);

        if (userName != null) {
            UserDetails details = this.uds.loadUserByUsername(userName);
            if (TokenUtils.validateToken(authToken, details)) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
                auth.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(http));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest req) {
        if (!(req instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }
        return (HttpServletRequest) req;
    }

    private String extractAuthTokenFromRequest(HttpServletRequest req) {

        /* Get token from the header */
        String auth = req.getHeader("X-Auth-Token");

        /* If the token isn't found get it from the request parameter */
        if (auth == null) {
            auth = req.getParameter("token");
        }
        return auth;
    }
}
