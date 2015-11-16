package com.depaul.edu.se491.filters;

import com.depaul.edu.se491.config.AppConfig;
import com.depaul.edu.se491.global.AppConstants;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by Tomislav S. Mitic on 1/24/15.
 */
@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {

        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        headers.add(AppConstants.ACCESS_CONTROL_ALLOW_ORIGIN_KEY, AppConfig.getReferer(requestContext));
        headers.add(AppConstants.ACCESS_CONTROL_ALLOW_METHODS_KEY, AppConstants.ACCESS_CONTROL_ALLOW_METHODS_VALUE);
        headers.add(AppConstants.ACCESS_CONTROL_ALLOW_HEADERS_KEY, AppConstants.ACCESS_CONTROL_ALLOW_HEADERS_VALUE);
    }
}
